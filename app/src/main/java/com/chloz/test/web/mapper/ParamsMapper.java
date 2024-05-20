package com.chloz.test.web.mapper;

import com.chloz.test.service.ParamsService;
import com.chloz.test.web.mapper.base.ParamsMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ParamsMapper extends ParamsMapperBase {

	public ParamsMapper(ParamsService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}