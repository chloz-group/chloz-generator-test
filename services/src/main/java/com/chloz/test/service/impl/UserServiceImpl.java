package com.chloz.test.service.impl;

import com.chloz.test.domain.User;
import com.chloz.test.repository.UserRepository;
import com.chloz.test.repository.VerificationCodeRepository;
import com.chloz.test.service.UserService;
import com.chloz.test.service.base.UserServiceBaseImplBase;
import com.chloz.test.service.messaging.DefaultMessagingService;
import com.chloz.test.service.query.UserQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends UserServiceBaseImplBase implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private final PasswordEncoder passwordEncoder;

	private final UserRepository repository;
	public UserServiceImpl(UserRepository repository, UserQueryBuilder queryBuilder,
			VerificationCodeRepository verificationCodeRepository, DefaultMessagingService messagingService,
			PasswordEncoder passwordEncoder) {
		super(repository, queryBuilder, verificationCodeRepository, messagingService, passwordEncoder);
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}

	@Override
	@Transactional
	public User createNewUser(User user, String password) {
		User newUser = prepareUserCreation(user);
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setPassword(encryptedPassword);
		newUser.setAccountLocked(false);
		newUser.setEmailChecked(false);
		newUser.setPhoneChecked(false);
		newUser.setActivated(true);
		newUser.setDisabled(false);
		newUser = repository.save(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

}