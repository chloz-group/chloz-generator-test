package com.chloz.test.web.advice;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends ExceptionHandlerBase {

	public ExceptionHandler(MessageSource messageSource) {
		super(messageSource);
		this.addErrorCode = true;
	}

	// Keep the causal chain of causes is disabled by default
	@Override
	public boolean isCausalChainsEnabled() {
		return false;
	}

}