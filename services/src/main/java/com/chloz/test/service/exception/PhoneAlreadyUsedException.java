package com.chloz.test.service.exception;

import com.chloz.test.common.exception.BusinessException;

public class PhoneAlreadyUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;
	public PhoneAlreadyUsedException() {
		super("Phone is already in use!", "A004", 409);
	}

}