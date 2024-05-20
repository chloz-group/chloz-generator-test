package com.chloz.test.service.query.impl;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.query.base.QueryBuilderBaseImplBase;
import com.chloz.test.service.query.QueryBuilder;
import org.springframework.context.ApplicationContext;

public abstract class QueryBuilderImpl<T extends SimpleFilter, Q extends EntityPathBase>
		extends
			QueryBuilderBaseImplBase<T, Q>
		implements
			QueryBuilder<T, Q> {

	public QueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}