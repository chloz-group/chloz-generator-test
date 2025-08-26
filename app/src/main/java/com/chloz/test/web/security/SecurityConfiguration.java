package com.chloz.test.web.security;

import static com.chloz.test.web.Constants.API_BASE_PATH;
import com.chloz.test.web.security.base.SecurityConfigurationBase;
import com.chloz.test.service.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends SecurityConfigurationBase {

	public SecurityConfiguration(JwtTokenProvider tokenProvider, AuthenticationFilter authenticationFilter,
			SecurityProblemSupport problemSupport) {
		super(tokenProvider, authenticationFilter, problemSupport);
	}

	@Override
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return super.filterChain(http);
	}

	@Bean
	@Override
	public CorsFilter corsFilter() {
		return super.corsFilter();
	}

	@Override
	protected AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeHttpRequests(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
		return super.authorizeHttpRequests(authz)
				.requestMatchers(HttpMethod.GET, API_BASE_PATH + "/medias/download-key/*").permitAll();
	}

}