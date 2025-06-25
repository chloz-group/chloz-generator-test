package com.chloz.test.service.exception;

import com.chloz.test.common.exception.BusinessException;

public class UsernameAlreadyUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;
	public UsernameAlreadyUsedException() {
		super("Login name already used!", "A001", 409);
	}

}