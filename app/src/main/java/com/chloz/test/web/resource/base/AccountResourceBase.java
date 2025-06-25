package com.chloz.test.web.resource.base;

import com.chloz.test.domain.User;
import com.chloz.test.domain.VerificationCode;
import com.chloz.test.domain.enums.VerificationType;
import com.chloz.test.service.UserService;
import com.chloz.test.service.exception.EmailAlreadyUsedException;
import com.chloz.test.service.exception.InvalidPasswordException;
import com.chloz.test.service.exception.LoginAlreadyUsedException;
import com.chloz.test.service.exception.PhoneAlreadyUsedException;
import com.chloz.test.service.messaging.MessageType;
import com.chloz.test.service.utils.SecurityUtils;
import com.chloz.test.web.Constants;
import com.chloz.test.web.dto.*;
import com.chloz.test.web.exception.AccountResourceException;
import com.chloz.test.web.exception.InvalidVerificationCodeException;
import com.chloz.test.web.exception.UserAccountNotFoundException;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.resource.DefaultResource;
import com.chloz.test.web.security.jwt.TokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing the current user's account and authentication
 */
public class AccountResourceBase extends DefaultResource {

	private final Logger log = LoggerFactory.getLogger(AccountResourceBase.class);

	private final UserService userService;

	private final UserMapper userMapper;

	private final AuthenticationManager authenticationManager;

	private final TokenProvider tokenProvider;
	public AccountResourceBase(UserService userService, UserMapper userMapper,
			AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	/**
	 * {@code POST  /register} : register the user.
	 *
	 * @param dto
	 *            the managed user View Model.
	 * @throws InvalidPasswordException
	 *             {@code 400 (Bad Request)} if the password is incorrect.
	 * @throws EmailAlreadyUsedException
	 *             {@code 400 (Bad Request)} if the email is already used.
	 * @throws LoginAlreadyUsedException
	 *             {@code 400 (Bad Request)} if the login is already used.
	 */
	public ResponseEntity<UserDto> registerAccount(@Valid UserRegistrationDto dto) {
		if (!checkPasswordLength(dto.getPassword())) {
			throw new InvalidPasswordException();
		}
		User user = userService.registerNewUser(userMapper.modelFromDto(dto), dto.getPassword());
		if (!StringUtils.isBlank(user.getEmail())) {
			userService.sendActivationCodes(user, MessageType.EMAIL);
		}
		if (!StringUtils.isBlank(user.getPhone())) {
			userService.sendActivationCodes(user, MessageType.SMS);
		}
		UserDto result = this.userMapper.mapToDto(user, "*");
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	/**
	 * {@code GET  /authenticate} : check if the user is authenticated, and return
	 * its login.
	 *
	 * @param request
	 *            the HTTP request.
	 * @return the login if the user is authenticated.
	 */
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * {@code GET  /account} : get the current user.
	 *
	 * @return the current user.
	 * @throws RuntimeException
	 *             {@code 500 (Internal Server Error)} if the user couldn't be
	 *             returned.
	 */
	public UserDto getAccount() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userService::findOneByLogin)
				.map(user -> userMapper.mapToDto(user, "*"))
				.orElseThrow(() -> new AccountResourceException("User could not be found", "A005"));
	}

	/**
	 * {@code POST  /account} : update the current user information.
	 *
	 * @param userDTO
	 *            the current user information.
	 * @throws EmailAlreadyUsedException
	 *             {@code 400 (Bad Request)} if the email is already used.
	 * @throws PhoneAlreadyUsedException
	 *             {@code 400 (Bad Request)} if the phone is already used.
	 * @throws RuntimeException
	 *             {@code 500 (Internal Server Error)} if the user login wasn't
	 *             found.
	 */
	public void updateAccount(@Valid UserDto userDTO) {
		String userLogin = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new AccountResourceException("Current user login not found", "A005"));
		Optional<User> existingUser = userService.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
			throw new EmailAlreadyUsedException();
		}
		existingUser = userService.findOneByPhone(userDTO.getPhone());
		if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
			throw new PhoneAlreadyUsedException();
		}
		Optional<User> userOpt = userService.findOneByLogin(userLogin);
		if (!userOpt.isPresent()) {
			throw new AccountResourceException("User could not be found", "A005");
		}
		User user = userOpt.get();
		if (user.getEmail() != null && !user.getEmail().equals(userDTO.getEmail())) {
			user.setEmailChecked(false);
		}
		if (user.getPhone() != null && !user.getPhone().equals(userDTO.getPhone())) {
			user.setPhoneChecked(false);
		}
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		userService.save(user);
	}

	/**
	 * {@code POST  /account/change-password} : changes the current user's password.
	 *
	 * @param passwordChangeDto
	 *            current and new password.
	 * @throws InvalidPasswordException
	 *             {@code 400 (Bad Request)} if the new password is incorrect.
	 */
	public void changePassword(PasswordChangeDto passwordChangeDto) {
		if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		SecurityUtils.getCurrentUserLogin().flatMap(userService::findOneByLogin).ifPresent(user -> userService
				.changePassword(user, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword()));
	}

	protected static boolean checkPasswordLength(String password) {
		return !StringUtils.isEmpty(password) && password.length() >= Constants.PASSWORD_MIN_LENGTH
				&& password.length() <= Constants.PASSWORD_MAX_LENGTH;
	}

	/**
	 * Make the authorization and return the token
	 *
	 * @param loginDto
	 * @return
	 */
	@Transactional
	public ResponseEntity<AuthTokenDto> authorize(@Valid LoginDto loginDto) {
		User user = this.findUser(loginDto.getUsername());
		if (user == null) {
			throw new UserAccountNotFoundException("#E#A006(401::" + loginDto.getUsername() + ") User account "
					+ loginDto.getUsername() + " not found");
		}
		boolean accountActivated = Optional.ofNullable(user.getActivated()).orElse(false);
		boolean otpCodeNotProvided = loginDto.getSmsCode() == null && loginDto.getEmailCode() == null;
		if (!accountActivated && otpCodeNotProvided) {
			// user account is not activated
			throw new AccountResourceException("Account not activated", "A007");
		}
		boolean accountLocked = Optional.ofNullable(user.getAccountLocked()).orElse(false);
		if (accountLocked && otpCodeNotProvided) {
			// user account is locked
			throw new AccountResourceException("Locked account", "A008");
		}
		boolean checkCodes = this.verifyCodes(user, loginDto);
		RuntimeException e = null;
		String jwt = null;
		if (!checkCodes) {
			e = new InvalidVerificationCodeException();
		} else {
			try {
				jwt = this.authenticate(loginDto, user, accountActivated, accountLocked);
			} catch (AuthenticationException ex) {
				log.warn("Authentication failed {}", ex.getMessage());
				e = ex;
			}
		}
		if (e == null) {
			user.setAttempts(0);
		} else {
			user.setAttempts(Optional.ofNullable(user.getAttempts()).orElse(0) + 1);
			if (user.getAttempts() >= Constants.MAX_LOGIN_ATTEMPTS) {
				user.setAccountLocked(true);
			}
		}
		this.userService.save(user);
		if (e != null) {
			throw e;
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(Constants.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(AuthTokenDto.builder().token(jwt).build(), httpHeaders, HttpStatus.OK);
	}

	private String authenticate(LoginDto loginDto, User user, boolean accountActivated, boolean accountLocked) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		if (!accountActivated) {
			// this is a new user so we activate it
			user.setDisabled(false);
			user.setActivated(true);
			user.setAccountLocked(false);
			this.userService.save(user);
		} else if (accountLocked) {
			user.setAccountLocked(false);
			this.userService.save(user);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = Optional.ofNullable(loginDto.getRememberMe()).orElse(false);
		return tokenProvider.createToken(authentication, rememberMe);
	}

	public void requestAuthenticationCode(@Valid AuthenticationCodeRequestDto lp) {
		User user = this.findUser(lp.getUsername());
		if (user == null) {
			throw new UserAccountNotFoundException(
					"#E#A006(401::" + lp.getUsername() + ") User account " + lp.getUsername() + " not found");
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(lp.getUsername(),
				lp.getPassword());
		AuthenticationException e = null;
		try {
			authenticationManager.authenticate(token);
		} catch (AuthenticationException ex) {
			e = ex;
		}
		if (e != null) {
			throw e;
		}
		if (lp.isGenerateNewCode()) {
			this.userService.createVerificationCodes(user);
		}
		if (!StringUtils.isBlank(user.getPhone())) {
			userService.sendActivationCodes(user, MessageType.SMS);
		}
		if (!StringUtils.isBlank(user.getEmail())) {
			userService.sendActivationCodes(user, MessageType.EMAIL);
		}
	}

	protected User findUser(String username) {
		return this.userService.findOneByLogin(username)
				.orElseGet(() -> this.userService.findOneByEmailIgnoreCase(username)
						.orElseGet(() -> this.userService.findOneByPhone(username).orElse(null)));
	}

	protected boolean verifyCodes(User user, LoginDto loginDto) {
		// Check verifications codes
		boolean ok = true;
		if (loginDto.getSmsCode() != null) {
			ok = checkVerificationCode(user, loginDto.getSmsCode(), VerificationType.PHONE);
			if (ok)
				user.setPhoneChecked(true);
		}
		if (ok && loginDto.getEmailCode() != null) {
			ok = checkVerificationCode(user, loginDto.getEmailCode(), VerificationType.EMAIL);
			if (ok)
				user.setEmailChecked(true);
		}
		return ok;
	}

	protected boolean checkVerificationCode(User user, String code, VerificationType verificationType) {
		boolean ok = false;
		List<VerificationCode> codeList = this.userService.findUserVerificationCodesOrderByExpiryDateDesc(user,
				verificationType);
		VerificationCode verificationCode = Optional
				.ofNullable(codeList).orElse(new ArrayList<>()).stream().filter(vc -> vc.isEnable() && !vc.isCodeUsed()
						&& vc.getExpiryDate().isAfter(Instant.now()) && vc.getCode().equals(code))
				.findFirst().orElse(null);
		if (verificationCode != null) {
			ok = true;
			verificationCode.setCodeUsed(true);
			verificationCode.setDisabled(true);
			this.userService.saveVerificationCode(verificationCode);
		}
		return ok;
	}

}