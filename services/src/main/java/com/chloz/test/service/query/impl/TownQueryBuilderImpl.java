package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.TownQueryBuilderImplBase;
import com.chloz.test.service.query.TownQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TownQueryBuilderImpl extends TownQueryBuilderImplBase implements TownQueryBuilder {

	public TownQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}