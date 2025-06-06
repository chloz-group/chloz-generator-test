package com.chloz.test.web.security.jwt.base;

import com.chloz.test.service.UserService;
import com.chloz.test.web.Constants;
import com.chloz.test.web.security.DefaultRequestAuthenticationProvider;
import com.chloz.test.web.security.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class JwtAuthorizationProviderBase extends DefaultRequestAuthenticationProvider {

	private final TokenProvider tokenProvider;
	protected JwtAuthorizationProviderBase(UserService userService, TokenProvider tokenProvider) {
		super(userService);
		this.tokenProvider = tokenProvider;
	}

	@Override
	public Authentication authenticate(ServletRequest request, ServletResponse response) {
		Authentication auth = null;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String bearerToken = httpServletRequest.getHeader(Constants.AUTHORIZATION_HEADER);
		String token = null;
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
		}
		if (token != null) {
			Claims claims = tokenProvider.validateToken(token);
			if (claims != null) {
				List<String> authorities = (List<String>) claims.get(TokenProviderBase.AUTHORITIES_KEY);
				auth = this.createAuthentication(claims.getSubject(), authorities);
			}
		}
		return auth;
	}

}