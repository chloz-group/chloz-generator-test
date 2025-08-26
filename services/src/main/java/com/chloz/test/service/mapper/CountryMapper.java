package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.service.mapper.base.CountryMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper extends CountryMapperBase {

	public CountryMapper(CountryDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}