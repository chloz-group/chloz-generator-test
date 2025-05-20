package com.chloz.test.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

	// Keep the causal chain of causes is disabled by default
	@Override
	public boolean isCausalChainsEnabled() {
		return false;
	}

}