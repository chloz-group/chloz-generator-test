package com.chloz.test.common.exception;

import lombok.Getter;

/**
 * Custom business exception to be thrown in case of functional (non-technical)
 * errors.
 */
@Getter
public class BusinessException extends RuntimeException implements ExceptionDetailsProvider {

	private String errorCode;

	private transient Object[] messageArguments;

	private Integer statusCode;
	public BusinessException(String message, String errorCode) {
		super(message);
		this.init(errorCode, null, new Object[0]);
	}

	public BusinessException(String message, String errorCode, Integer statusCode) {
		super(message);
		this.init(errorCode, statusCode, new Object[0]);
	}

	public BusinessException(String message, String errorCode, Integer statusCode, Object... messageArguments) {
		super(message);
		this.init(errorCode, statusCode, messageArguments);
	}

	public BusinessException(Throwable cause, String errorCode, Integer statusCode, Object... messageArguments) {
		super(cause);
		this.init(errorCode, statusCode, messageArguments);
	}

	public BusinessException(String message, Throwable cause, String errorCode, Integer statusCode,
			Object... messageArguments) {
		super(message, cause);
		this.init(errorCode, statusCode, messageArguments);
	}

	private void init(String errorCode, Integer statusCode, Object... messageArguments) {
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
		this.statusCode = statusCode;
	}

}