package com.chloz.test.web.config;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class JacksonConfiguration {

	@Bean
	public Hibernate6Module hibernate6Module() {
		return new Hibernate6Module();
	}

	@Bean
	public ProblemModule problemModule() {
		return new ProblemModule();
	}

	@Bean
	public ConstraintViolationProblemModule constraintViolationProblemModule() {
		return new ConstraintViolationProblemModule();
	}

}