package com.chloz.test.web.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAccountNotFoundException extends AuthenticationException {

	public UserAccountNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UserAccountNotFoundException(String msg) {
		super(msg);
	}

}