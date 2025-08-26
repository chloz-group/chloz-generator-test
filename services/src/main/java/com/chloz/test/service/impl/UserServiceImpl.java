package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.domain.User;
import com.chloz.test.repository.UserRepository;
import com.chloz.test.repository.VerificationCodeRepository;
import com.chloz.test.service.UserService;
import com.chloz.test.service.base.UserServiceBaseImplBase;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.dto.UserRegistrationDto;
import com.chloz.test.service.messaging.DefaultMessagingService;
import com.chloz.test.service.mapper.UserMapper;
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

	private final UserMapper mapper;
	public UserServiceImpl(UserRepository repository, UserDataAccess dataAccess, UserMapper mapper,
			VerificationCodeRepository verificationCodeRepository, DefaultMessagingService messagingService,
			PasswordEncoder passwordEncoder) {
		super(repository, dataAccess, mapper, verificationCodeRepository, messagingService, passwordEncoder);
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public UserDto createNewUser(UserRegistrationDto dto, String password, String graph) {
		User user = this.mapper.modelFromDto(dto);
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
		return mapper.mapToDto(user, graph);
	}

}