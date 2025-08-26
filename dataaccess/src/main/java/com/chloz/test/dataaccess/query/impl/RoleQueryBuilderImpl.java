package com.chloz.test.dataaccess.query.impl;

import com.chloz.test.dataaccess.query.base.RoleQueryBuilderImplBase;
import com.chloz.test.dataaccess.query.RoleQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RoleQueryBuilderImpl extends RoleQueryBuilderImplBase implements RoleQueryBuilder {

	public RoleQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}