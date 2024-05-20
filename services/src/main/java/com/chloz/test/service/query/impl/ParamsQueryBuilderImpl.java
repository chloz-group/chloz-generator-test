package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.ParamsQueryBuilderImplBase;
import com.chloz.test.service.query.ParamsQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ParamsQueryBuilderImpl extends ParamsQueryBuilderImplBase implements ParamsQueryBuilder {

	public ParamsQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}