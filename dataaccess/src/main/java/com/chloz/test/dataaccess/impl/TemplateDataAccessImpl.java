package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.TemplateRepository;
import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.dataaccess.base.TemplateDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.TemplateQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TemplateDataAccessImpl extends TemplateDataAccessBaseImplBase implements TemplateDataAccess {

	private final TemplateRepository repository;
	public TemplateDataAccessImpl(TemplateRepository repository, TemplateQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}