package com.chloz.test.service;

import com.chloz.test.repository.config.DatabaseConfiguration;
import com.chloz.test.service.config.ServiceConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import({ServiceConfiguration.class, DatabaseConfiguration.class})
public class ServiceConfigurationTest {
}