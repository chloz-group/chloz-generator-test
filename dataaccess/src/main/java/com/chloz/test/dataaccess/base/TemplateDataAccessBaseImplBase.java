package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.TemplateRepository;
import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.TemplateQueryBuilder;
import java.util.Optional;

public class TemplateDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Template, Long, SimpleTemplateFilter>
		implements
			TemplateDataAccessBase {

	private final TemplateRepository repository;

	private final TemplateQueryBuilder queryBuilder;
	public TemplateDataAccessBaseImplBase(TemplateRepository repository, TemplateQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Optional<Template> findByCode(String code) {
		return this.repository.findByCode(code);
	};

}