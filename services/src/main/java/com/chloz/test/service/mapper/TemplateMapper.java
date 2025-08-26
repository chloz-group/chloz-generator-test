package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.service.mapper.base.TemplateMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TemplateMapper extends TemplateMapperBase {

	public TemplateMapper(TemplateDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}