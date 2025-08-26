package com.chloz.test.service;

import com.chloz.test.repository.config.DatabaseConfiguration;
import com.chloz.test.service.config.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Import({DatabaseConfiguration.class, ServiceConfiguration.class})
public class ChlozTestServiceTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChlozTestServiceTestsApplication.class, args);
	}

}