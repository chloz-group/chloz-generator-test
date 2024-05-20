package com.chloz.test.web.mapper;

import com.chloz.test.service.CountryService;
import com.chloz.test.web.mapper.base.CountryMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper extends CountryMapperBase {

	public CountryMapper(CountryService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}