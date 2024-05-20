package com.chloz.test.repository.config;

import com.chloz.test.domain.User;
import com.chloz.test.repository.CustomJpaRepositoryFactoryBean;
import com.chloz.test.repository.RepositoryBaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.chloz.test.repository"}, repositoryBaseClass = RepositoryBaseImpl.class, repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableTransactionManagement
@EntityScan(basePackages = {"com.chloz.test.domain"})
@EnableJpaAuditing(auditorAwareRef = "jpaAuditorAwareProvider", dateTimeProviderRef = "jpaAuditorDateTimeProvider")
public class DatabaseConfiguration {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
	@Bean
	public AuditorAware<User> jpaAuditorAwareProvider() {
		return () -> {
			Optional<Object> opt = Optional.ofNullable(SecurityContextHolder.getContext())
					.map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
					.map(Authentication::getDetails);
			User u = null;
			if (opt.isPresent()) {
				Object o = opt.get();
				if (o instanceof User) {
					u = (User) o;
				}
			}
			return Optional.ofNullable(u);
		};
	}

	@Bean
	public DateTimeProvider jpaAuditorDateTimeProvider() {
		return () -> {
			return Optional.of(OffsetDateTime.now());
		};
	}

}