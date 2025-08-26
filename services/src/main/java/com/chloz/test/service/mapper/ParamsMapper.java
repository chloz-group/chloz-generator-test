package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.service.mapper.base.ParamsMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ParamsMapper extends ParamsMapperBase {

	public ParamsMapper(ParamsDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}