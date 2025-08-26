package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.service.TemplateService;
import com.chloz.test.service.base.TemplateServiceBaseImplBase;
import com.chloz.test.service.mapper.TemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemplateServiceImpl extends TemplateServiceBaseImplBase implements TemplateService {

	private final TemplateDataAccess dataAccess;
	public TemplateServiceImpl(TemplateDataAccess dataAccess, TemplateMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}