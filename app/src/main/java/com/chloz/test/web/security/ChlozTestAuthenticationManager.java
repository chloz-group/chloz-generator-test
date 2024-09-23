package com.chloz.test.web.security;

import com.chloz.test.domain.User;
import com.chloz.test.service.UserService;
import com.chloz.test.web.exception.UserAccountNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ChlozTestAuthenticationManager implements AuthenticationManager {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;
	public ChlozTestAuthenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		User user = this.findUser((String) token.getPrincipal());
		if (user == null) {
			throw new UserAccountNotFoundException("Unknown user account or password incorrect");
		}
		String providedPassword = (String) token.getCredentials();
		String currentEncryptedPassword = user.getPassword();
		if (!passwordEncoder.matches(providedPassword, currentEncryptedPassword)) {
			throw new BadCredentialsException("Unknown user account or password incorrect");
		}
		List<SimpleGrantedAuthority> userRoles = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).toList();
		List<SimpleGrantedAuthority> groupRoles = user.getGroups().stream().filter(g -> !g.isDeleted())
				.map(g -> g.getRoles()).reduce(new ArrayList<>(), (roles1, roles2) -> {
					roles1.addAll(roles2);
					return roles1;
				}).stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
		List<GrantedAuthority> roles = new ArrayList<>(userRoles);
		roles.addAll(groupRoles);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getPrincipal(), null,
				roles);
		auth.setDetails(user);
		return auth;
	}

	private User findUser(String subject) {
		User user;
		if (subject.contains("@")) {
			user = userService.findOneByEmailIgnoreCase(subject).orElse(null);
		} else {
			user = userService.findOneByLogin(subject).orElse(null);
			if (user == null) {
				user = userService.findOneByPhone(subject).orElse(null);
			}
		}
		return user;
	}

}