package com.chloz.test.web.config;

import com.chloz.test.web.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.HandlerMethod;
import java.util.Objects;

@Configuration
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class OpenApiConfiguration {

	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI().info(new Info().title("Chloz Generator test API")
				.description("Chloz Generator test application API").version("v1.0.0")
				.license(new License().name("Apache 2.0").url("http://www.chlozgeneratortest.com")));
	}

	@Bean
	public OperationCustomizer operationIdCustomizer() {
		OperationCustomizer c = (Operation operation, HandlerMethod handlerMethod) -> {
			Class<?> superClazz = handlerMethod.getBeanType().getSuperclass();
			if (Objects.nonNull(superClazz)) {
				String beanName = handlerMethod.getBeanType().getSimpleName();
				operation.setOperationId(String.format("%s", handlerMethod.getMethod().getName()));
			}
			return operation;
		};
		return c;
	}

}