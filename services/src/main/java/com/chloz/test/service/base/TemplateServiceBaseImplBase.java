package com.chloz.test.service.base;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.base.TemplateRepositoryBase;
import com.chloz.test.service.impl.SimpleDomainServiceImpl;
import java.util.Optional;

public class TemplateServiceBaseImplBase extends SimpleDomainServiceImpl<Template, Long>
		implements
			TemplateServiceBase {

	private final TemplateRepositoryBase repository;
	public TemplateServiceBaseImplBase(TemplateRepositoryBase repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Optional<Template> findByCode(String templateCode) {
		return this.repository.findByCode(templateCode);
	}

}