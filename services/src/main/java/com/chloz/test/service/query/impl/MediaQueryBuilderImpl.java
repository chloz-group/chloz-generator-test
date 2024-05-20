package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.MediaQueryBuilderImplBase;
import com.chloz.test.service.query.MediaQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MediaQueryBuilderImpl extends MediaQueryBuilderImplBase implements MediaQueryBuilder {

	public MediaQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}