package com.chloz.test.web.security;

import com.chloz.test.service.UserService;
import com.chloz.test.service.security.jwt.JwtTokenProvider;
import com.chloz.test.web.security.base.JwtAuthorizationProviderBase;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationProvider extends JwtAuthorizationProviderBase {

	protected JwtAuthorizationProvider(UserService userService, JwtTokenProvider tokenProvider) {
		super(userService, tokenProvider);
	}

}