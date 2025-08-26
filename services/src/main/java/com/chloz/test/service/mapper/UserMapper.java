package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.service.mapper.base.UserMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends UserMapperBase {

	public UserMapper(UserDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}