package com.chloz.test.service.impl;

import com.chloz.test.repository.base.TemplateRepositoryBase;
import com.chloz.test.service.TemplateService;
import com.chloz.test.service.base.TemplateServiceBaseImplBase;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TemplateServiceImpl extends TemplateServiceBaseImplBase implements TemplateService {

	public TemplateServiceImpl(TemplateRepositoryBase repository) {
		super(repository);
	}

}