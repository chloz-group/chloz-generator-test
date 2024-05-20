package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.UserDeviceQueryBuilderImplBase;
import com.chloz.test.service.query.UserDeviceQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceQueryBuilderImpl extends UserDeviceQueryBuilderImplBase implements UserDeviceQueryBuilder {

	public UserDeviceQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}