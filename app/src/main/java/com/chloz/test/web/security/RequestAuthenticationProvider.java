package com.chloz.test.web.security;

import org.springframework.security.core.Authentication;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Interface to be implemented by every authentication provider {Jwt, Keycloack,
 * Google, Facebook, ...}
 */
public interface RequestAuthenticationProvider {

	/**
	 * Authenticate the request and give the {@link Authentication} object where
	 * details is an instance of {@link com.chloz.test.domain.User}
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public Authentication authenticate(ServletRequest request, ServletResponse response);

}