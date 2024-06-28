package com.chloz.test.web.mapper;

import com.chloz.test.service.TemplateService;
import com.chloz.test.web.mapper.base.TemplateMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TemplateMapper extends TemplateMapperBase {

	public TemplateMapper(TemplateService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}