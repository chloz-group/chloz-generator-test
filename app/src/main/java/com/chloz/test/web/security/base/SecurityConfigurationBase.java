package com.chloz.test.web.security.base;

import com.chloz.test.web.security.AuthenticationFilter;
import com.chloz.test.web.security.jwt.TokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import java.util.Arrays;
import java.util.List;
import static com.chloz.test.web.Constants.API_BASE_PATH;

public class SecurityConfigurationBase {

	@Value("${security.cors.allowedOrigins:}")
	private String allowedOrigins;

	@Value("${security.cors.allowedMethods:GET,HEAD,POST,PUT,DELETE,OPTIONS}")
	private String allowedMethods;

	@Value("${security.cors.allowedHeaders:*}")
	private String allowedHeaders;

	@Value("${security.cors.exposedHeaders:}")
	private String exposedHeaders;

	@Value("${security.cors.allowedOriginPatterns:}")
	private String allowedOriginPatterns;

	@Value("${security.cors.allowCredentials:true}")
	private Boolean allowCredentials;

	@Value("${security.cors.maxAge:3600}")
	private Long maxAge;

	@Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
	private String swaggerUiPath;

	@Value("${springdoc.api-docs.path:/v3/api-docs}")
	private String apiDocsPath;

	private final TokenProvider tokenProvider;

	private final AuthenticationFilter authenticationFilter;

	private final SecurityProblemSupport problemSupport;
	public SecurityConfigurationBase(TokenProvider tokenProvider, AuthenticationFilter authenticationFilter,
			SecurityProblemSupport problemSupport) {
		this.tokenProvider = tokenProvider;
		this.authenticationFilter = authenticationFilter;
		this.problemSupport = problemSupport;
	}

	/*
	 * public WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
	 * web.ignoring() .antMatchers(HttpMethod.OPTIONS, "/**")
	 * .antMatchers("/swagger/**"); }
	 */
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http = http.csrf(csrf -> csrf.disable())
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(problemSupport)
						.accessDeniedHandler(problemSupport));
		http = addFilters(http);
		return http.authorizeHttpRequests(authz -> {
			filterPublicRequests(authz);
			filterUserRequests(authz);
			filterOtherRequests(authz);
			authz.anyRequest().authenticated();
		}).build();
	}

	protected HttpSecurity addFilters(HttpSecurity http) {
		http.addFilterBefore(this.authenticationFilter, BasicAuthenticationFilter.class);
		http.addFilterBefore(corsFilter(), AuthenticationFilter.class);
		return http;
	}

	protected AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry filterPublicRequests(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
		return authz.requestMatchers(this.swaggerUiPath).permitAll().requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers(this.apiDocsPath + "/**").permitAll()
				.requestMatchers(HttpMethod.POST, API_BASE_PATH + "/authenticate").permitAll()
				.requestMatchers(API_BASE_PATH + "/request-authentication-code").permitAll()
				.requestMatchers(API_BASE_PATH + "/register").permitAll();
	}

	protected AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry filterUserRequests(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
		return authz;
	}

	protected AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry filterOtherRequests(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
		return authz;
	}

	public CorsFilter corsFilter() {
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(allowCredentials);
		config.setAllowedHeaders(toList(allowedHeaders, ","));
		config.setAllowedMethods(toList(allowedMethods, ","));
		config.setAllowedOrigins(toList(allowedOrigins, " "));
		config.setExposedHeaders(toList(exposedHeaders, ","));
		config.setAllowedOriginPatterns(toList(allowedOriginPatterns, ","));
		config.setMaxAge(maxAge);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(API_BASE_PATH + "/**", config);
		source.registerCorsConfiguration("/management/**", config);
		return new CorsFilter(source);
	}

	private List<String> toList(String list, String separator) {
		if (StringUtils.isBlank(list)) {
			return null;
		} else {
			return Arrays.asList(StringUtils.split(list.trim(), separator));
		}
	}

}