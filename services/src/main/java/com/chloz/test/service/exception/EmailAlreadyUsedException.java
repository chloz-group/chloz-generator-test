package com.chloz.test.service.exception;

public class EmailAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public EmailAlreadyUsedException() {
		super("Email is already in use!");
	}

}