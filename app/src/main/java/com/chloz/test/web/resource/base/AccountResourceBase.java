package com.chloz.test.web.resource.base;

import com.chloz.test.service.AccountService;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.dto.UserRegistrationDto;
import com.chloz.test.service.dto.PasswordChangeDto;
import com.chloz.test.service.dto.AuthTokenDto;
import com.chloz.test.service.dto.LoginDto;
import com.chloz.test.service.dto.AuthenticationCodeRequestDto;
import com.chloz.test.service.exception.EmailAlreadyUsedException;
import com.chloz.test.service.exception.InvalidPasswordException;
import com.chloz.test.service.exception.LoginAlreadyUsedException;
import com.chloz.test.service.exception.PhoneAlreadyUsedException;
import com.chloz.test.web.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * REST controller for managing the current user's account and authentication
 */
public class AccountResourceBase {

	private final Logger log = LoggerFactory.getLogger(AccountResourceBase.class);

	private final AccountService accountService;
	public AccountResourceBase(AccountService accountService) {
		this.accountService = accountService;
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
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.registerAccount(dto));
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
		return accountService.getAccount();
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
		accountService.updateAccount(userDTO);
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
		accountService.changePassword(passwordChangeDto);
	}

	/**
	 * Make the authorization and return the token
	 *
	 * @param loginDto
	 * @return
	 */
	public ResponseEntity<AuthTokenDto> authorize(@Valid LoginDto loginDto) {
		AuthTokenDto jwt = accountService.authorize(loginDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(Constants.AUTHORIZATION_HEADER, "Bearer " + jwt.getToken());
		return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
	}

	public void requestAuthenticationCode(@Valid AuthenticationCodeRequestDto lp) {
		accountService.requestAuthenticationCode(lp);
	}

}