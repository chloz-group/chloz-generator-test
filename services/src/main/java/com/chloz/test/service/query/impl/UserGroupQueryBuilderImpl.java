package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.UserGroupQueryBuilderImplBase;
import com.chloz.test.service.query.UserGroupQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserGroupQueryBuilderImpl extends UserGroupQueryBuilderImplBase implements UserGroupQueryBuilder {

	public UserGroupQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}