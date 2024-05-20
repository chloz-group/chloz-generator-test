package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.UserQueryBuilderImplBase;
import com.chloz.test.service.query.UserQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserQueryBuilderImpl extends UserQueryBuilderImplBase implements UserQueryBuilder {

	public UserQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}