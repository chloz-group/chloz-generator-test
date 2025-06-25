package com.chloz.test.web.exception;

import com.chloz.test.common.exception.BusinessException;

public class AccountResourceException extends BusinessException {

	public AccountResourceException(String message, String errorCode) {
		super(message, errorCode, 401);
	}

}