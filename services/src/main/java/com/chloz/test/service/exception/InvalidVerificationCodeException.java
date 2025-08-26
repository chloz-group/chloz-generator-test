package com.chloz.test.service.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidVerificationCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	public InvalidVerificationCodeException() {
		super("Incorrect verification code");
	}

}