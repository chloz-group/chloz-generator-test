package com.chloz.test.service.exception;

import com.chloz.test.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

	private static final long serialVersionUID = 1L;
	public InvalidPasswordException() {
		super("Invalid password", "A002", 401);
	}

}