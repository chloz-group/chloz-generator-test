package com.chloz.test.service.base;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.base.TemplateRepositoryBase;
import com.chloz.test.service.filter.SimpleTemplateFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.TemplateQueryBuilder;
import java.util.Optional;

public class TemplateServiceBaseImplBase extends FilterDomainServiceImpl<Template, Long, SimpleTemplateFilter>
		implements
			TemplateServiceBase {

	private final TemplateRepositoryBase repository;
	public TemplateServiceBaseImplBase(TemplateRepositoryBase repository, TemplateQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Template> findByCode(String templateCode) {
		return this.repository.findByCode(templateCode);
	}

}