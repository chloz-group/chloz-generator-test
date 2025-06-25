package com.chloz.test.web.security;

import com.chloz.test.domain.User;
import com.chloz.test.service.UserService;
import com.chloz.test.web.exception.UserAccountNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public abstract class DefaultRequestAuthenticationProvider implements RequestAuthenticationProvider {

	private final UserService userService;
	protected DefaultRequestAuthenticationProvider(UserService userService) {
		this.userService = userService;
	}

	protected Authentication createAuthentication(String subject, List<String> authorities) {
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null,
				authorities.stream().map(SimpleGrantedAuthority::new).toList());
		User user = null;
		if (subject.contains("@")) {
			user = userService.findOneByEmailIgnoreCase(subject).orElse(null);
		} else {
			user = userService.findOneByLogin(subject).orElse(null);
			if (user == null) {
				user = userService.findOneByPhone(subject).orElse(null);
			}
		}
		if (user == null) {
			throw new UserAccountNotFoundException("#E#A005 User " + subject + " not found");
		}
		auth.setDetails(user);
		return auth;
	}

}