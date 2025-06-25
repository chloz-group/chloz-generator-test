package com.chloz.test.web.exception;

import com.chloz.test.common.exception.BusinessException;

public class BadRequestException extends BusinessException {

	public BadRequestException(String message) {
		super(message, null, 400);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause, null, 400);
	}

	public BadRequestException(String message, Throwable cause, Object... messageArguments) {
		super(message, cause, null, 400, messageArguments);
	}

}