package com.chloz.test.web.security.jwt;

import com.chloz.test.service.UserService;
import com.chloz.test.web.security.jwt.base.JwtAuthorizationProviderBase;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationProvider extends JwtAuthorizationProviderBase {

	protected JwtAuthorizationProvider(UserService userService, TokenProvider tokenProvider) {
		super(userService, tokenProvider);
	}

}