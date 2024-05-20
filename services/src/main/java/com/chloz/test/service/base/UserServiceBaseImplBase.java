package com.chloz.test.service.base;

import com.chloz.test.domain.User;
import com.chloz.test.domain.VerificationCode;
import com.chloz.test.domain.enums.VerificationType;
import com.chloz.test.repository.UserRepository;
import com.chloz.test.repository.VerificationCodeRepository;
import com.chloz.test.service.Constants;
import com.chloz.test.service.Utils;
import com.chloz.test.service.exception.EmailAlreadyUsedException;
import com.chloz.test.service.exception.InvalidPasswordException;
import com.chloz.test.service.exception.PhoneAlreadyUsedException;
import com.chloz.test.service.exception.UsernameAlreadyUsedException;
import com.chloz.test.service.filter.SimpleUserFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.messaging.DefaultMessagingService;
import com.chloz.test.service.messaging.MessageType;
import com.chloz.test.service.query.UserQueryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class UserServiceBaseImplBase extends FilterDomainServiceImpl<User, Long, SimpleUserFilter>
		implements
			UserServiceBase {

	/**
	 * The verification code length
	 */
	private static final int VERIFICATION_CODE_LENGTH = 5;

	/**
	 * The lifetime of a verification code. Default to 30 minutes
	 */
	private static final Duration VERIFICATION_CODE_LIFE = Duration.ofMinutes(30);

	private final Logger log = LoggerFactory.getLogger(UserServiceBaseImplBase.class);

	private final VerificationCodeRepository verificationCodeRepository;

	private final DefaultMessagingService messagingService;

	private final UserRepository repository;

	private final UserQueryBuilder queryBuilder;

	private final PasswordEncoder passwordEncoder;

	@Value("${spring.application.name}")
	private String applicationName;
	public UserServiceBaseImplBase(UserRepository repository, UserQueryBuilder queryBuilder,
			VerificationCodeRepository verificationCodeRepository, DefaultMessagingService messagingService,
			PasswordEncoder passwordEncoder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
		this.verificationCodeRepository = verificationCodeRepository;
		this.messagingService = messagingService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public User registerNewUser(User user, String password) {
		User newUser = prepareUserCreation(user);
		String encryptedPassword = passwordEncoder.encode(password);
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setAccountLocked(true);
		newUser.setEmailChecked(false);
		newUser.setPhoneChecked(false);
		newUser.setActivated(false);
		newUser.setDisabled(true);
		newUser = repository.save(newUser);
		createVerificationCodes(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	protected User prepareUserCreation(User user) {
		repository.findOneByLogin(user.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new UsernameAlreadyUsedException();
			}
		});
		repository.findOneByPhone(user.getPhone()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new PhoneAlreadyUsedException();
			}
		});
		repository.findOneByEmailIgnoreCase(user.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});
		// TODO : check if user email and phone number are in a correct format
		return user;
	}

	@Override
	public void createVerificationCodes(User user) {
		List<VerificationType> verificationTypeList = new ArrayList<>();
		if (!StringUtils.isBlank(user.getEmail())) {
			verificationTypeList.add(VerificationType.EMAIL);
		}
		if (!StringUtils.isBlank(user.getPhone())) {
			verificationTypeList.add(VerificationType.PHONE);
		}
		String code = Utils.generateVerificationCode(VERIFICATION_CODE_LENGTH);
		for (VerificationType v : verificationTypeList) {
			createVerificationCode(user, v, code);
		}
	}

	protected VerificationCode createVerificationCode(User user, VerificationType v, String code) {
		VerificationCode verificationCode = VerificationCode.builder().code(code).codeUsed(false)
				.expiryDate(Instant.now().plusSeconds(VERIFICATION_CODE_LIFE.toSeconds())).verificationType(v)
				.usedFor(user).build();
		return verificationCodeRepository.save(verificationCode);
	}

	private boolean removeNonActivatedUser(User existingUser) {
		if (existingUser.getActivated() != null && existingUser.getActivated()) {
			return false;
		}
		repository.deletePermanently(existingUser);
		return true;
	}

	@Transactional
	@Override
	public void changePassword(User user, String currentClearTextPassword, String newPassword) {
		String currentEncryptedPassword = user.getPassword();
		if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
			throw new InvalidPasswordException();
		}
		String encryptedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encryptedPassword);
		log.debug("Changed password for User: {}", user);
	}

	@Transactional
	@Override
	public void changePassword(User user, String newPassword) {
		String encryptedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encryptedPassword);
		log.debug("Changed password for User: {}", user);
	}

	@Override
	public void sendActivationCodes(User user, MessageType messageType) {
		VerificationType verType = null;
		switch (messageType) {
			case EMAIL :
				verType = VerificationType.EMAIL;
				break;
			case SMS :
				verType = VerificationType.PHONE;
				break;
			default :
				verType = null;
		}
		List<VerificationCode> codeList = this.findUserVerificationCodesOrderByExpiryDateDesc(user, verType);
		VerificationCode verificationCode = Optional.ofNullable(codeList).orElse(new ArrayList<>()).stream()
				.filter(vc -> {
					return vc.isEnableAndNotDeleted() && vc.getExpiryDate().isAfter(Instant.now()) && !vc.getCodeUsed();
				}).findFirst().orElse(null);
		if (verificationCode != null) {
			// TODO : Find a way to get the locale
			Locale locale = Locale.ENGLISH;
			Map<String, Object> templateParams = new HashMap<>();
			templateParams.put("user", user);
			templateParams.put("activationCode", verificationCode);
			Map<String, Object> messageParams = new HashMap<>();
			if (messageType == MessageType.EMAIL) {
				messageParams.put(DefaultMessagingService.MAIL_PARAM_TO, user.getEmail());
			}
			if (messageType == MessageType.SMS) {
				messageParams.put(DefaultMessagingService.SMS_PARAM_TO, user.getPhone());
			}
			messagingService.sendMessageFromTemplateAsynchronous(Collections.singleton(messageType), locale,
					applicationName, Constants.TEMPLATE_MESSAGE_USER_ACTIVATION_CODE, templateParams, messageParams);
		}
	}

	@Override
	public List<VerificationCode> findUserVerificationCodesOrderByExpiryDateDesc(User user,
			VerificationType verificationType) {
		return this.verificationCodeRepository.findByUsedForAndVerificationTypeOrderByExpiryDateDesc(user,
				verificationType);
	}

	@Override
	public void saveVerificationCode(VerificationCode verificationCode) {
		this.verificationCodeRepository.save(verificationCode);
	}

	public Optional<User> findOneByEmailIgnoreCase(String email) {
		return repository.findOneByEmailIgnoreCase(email);
	}

	@Override
	public Optional<User> findOneByLogin(String login) {
		return repository.findOneByLogin(login);
	}

	@Override
	public Optional<User> findOneByPhone(String phone) {
		return repository.findOneByPhone(phone);
	}

}