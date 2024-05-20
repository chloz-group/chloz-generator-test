package com.chloz.test.web.mapper;

import com.chloz.test.service.TownService;
import com.chloz.test.web.mapper.base.TownMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TownMapper extends TownMapperBase {

	public TownMapper(TownService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}