package com.chloz.test.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

	// Keep the causal chain of causes is disabled by default
	@Override
	public boolean isCausalChainsEnabled() {
		return false;
	}

	/*
	 * @Override public ResponseEntity<Problem> handleThrowable(Throwable throwable,
	 * NativeWebRequest request) { return
	 * ProblemHandling.super.handleThrowable(throwable, request); }
	 */
}