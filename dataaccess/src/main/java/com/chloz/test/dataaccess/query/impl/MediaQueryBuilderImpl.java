package com.chloz.test.dataaccess.query.impl;

import com.chloz.test.dataaccess.query.base.MediaQueryBuilderImplBase;
import com.chloz.test.dataaccess.query.MediaQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MediaQueryBuilderImpl extends MediaQueryBuilderImplBase implements MediaQueryBuilder {

	public MediaQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}