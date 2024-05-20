package com.chloz.test.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class ChlozGeneratorTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChlozGeneratorTestApplication.class, args);
	}

}