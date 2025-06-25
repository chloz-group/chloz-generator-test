package com.chloz.test.service.exception;

import com.chloz.test.common.exception.BusinessException;

public class EmailAlreadyUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;
	public EmailAlreadyUsedException() {
		super("Email is already in use!", "A003", 409);
	}

}