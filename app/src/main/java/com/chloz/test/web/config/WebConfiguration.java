package com.chloz.test.web.config;

import com.chloz.test.repository.config.DatabaseConfiguration;
import com.chloz.test.service.config.ServiceConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.Locale;

@Configuration
@Import({DatabaseConfiguration.class, ServiceConfiguration.class})
@ComponentScan(basePackages = {"com.chloz.test.web"})
public class WebConfiguration {

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver() {

			@Override
			public Locale resolveLocale(HttpServletRequest request) {
				// check if a lang parameter if provided
				String langParam = request.getParameter("lang");
				if (langParam != null && !langParam.isEmpty()) {
					return Locale.forLanguageTag(langParam);
				}
				// Get the Accept-Language header if no lang parameter is provided
				return super.resolveLocale(request);
			}

		};
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

}