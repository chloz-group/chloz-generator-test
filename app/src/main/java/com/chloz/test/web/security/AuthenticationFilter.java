package com.chloz.test.web.security;

import com.chloz.test.web.security.base.AuthenticationFilterBase;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * The Authentication filter
 */
@Component
public class AuthenticationFilter extends AuthenticationFilterBase {

	public AuthenticationFilter(List<RequestAuthenticationProvider> authenticationProviders) {
		super(authenticationProviders);
	}

}