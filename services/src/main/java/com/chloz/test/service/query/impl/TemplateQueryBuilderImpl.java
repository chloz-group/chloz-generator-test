package com.chloz.test.service.query.impl;

import com.chloz.test.service.query.base.TemplateQueryBuilderImplBase;
import com.chloz.test.service.query.TemplateQueryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TemplateQueryBuilderImpl extends TemplateQueryBuilderImplBase implements TemplateQueryBuilder {

	public TemplateQueryBuilderImpl(ApplicationContext applicationContext) {
		super(applicationContext);
	}

}