package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.service.mapper.base.TownMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TownMapper extends TownMapperBase {

	public TownMapper(TownDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}