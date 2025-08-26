package com.chloz.test.dataaccess.query.impl;

import com.chloz.test.dataaccess.query.base.CountryQueryBuilderImplBase;
import com.chloz.test.dataaccess.query.CountryQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CountryQueryBuilderImpl extends CountryQueryBuilderImplBase implements CountryQueryBuilder {

	public CountryQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}