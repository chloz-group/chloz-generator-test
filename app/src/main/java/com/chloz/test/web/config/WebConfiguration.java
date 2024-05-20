package com.chloz.test.web.config;

import com.chloz.test.repository.config.DatabaseConfiguration;
import com.chloz.test.service.config.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@Import({DatabaseConfiguration.class, ServiceConfiguration.class})
@ComponentScan(basePackages = {"com.chloz.test.web"})
public class WebConfiguration {

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

}