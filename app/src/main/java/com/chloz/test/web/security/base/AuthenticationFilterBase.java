package com.chloz.test.web.security.base;

import com.chloz.test.web.security.RequestAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationFilterBase extends GenericFilterBean {

	private final List<RequestAuthenticationProvider> authenticationProviders;
	public AuthenticationFilterBase(List<RequestAuthenticationProvider> authenticationProviders) {
		this.authenticationProviders = authenticationProviders;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		for (RequestAuthenticationProvider provider : authenticationProviders) {
			Authentication auth = provider.authenticate(request, response);
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				break;
			}
		}
		chain.doFilter(request, response);
	}

}