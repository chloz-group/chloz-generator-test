package com.chloz.test.dataaccess.query.impl;

import com.chloz.test.dataaccess.query.base.UserDeviceQueryBuilderImplBase;
import com.chloz.test.dataaccess.query.UserDeviceQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceQueryBuilderImpl extends UserDeviceQueryBuilderImplBase implements UserDeviceQueryBuilder {

	public UserDeviceQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}