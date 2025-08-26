package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.service.AccountService;
import com.chloz.test.service.UserService;
import com.chloz.test.service.base.AccountServiceBase;
import com.chloz.test.service.mapper.UserMapper;
import com.chloz.test.service.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing user's account and authentication
 */
@Service
@Transactional
public class AccountServiceImpl extends AccountServiceBase implements AccountService {

	private final UserService userService;

	private final UserMapper userMapper;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider tokenProvider;
	public AccountServiceImpl(UserService userService, UserMapper userMapper,
			AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
			UserDataAccess userDataAccess) {
		super(userService, userMapper, authenticationManager, tokenProvider, userDataAccess);
		this.userService = userService;
		this.userMapper = userMapper;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

}