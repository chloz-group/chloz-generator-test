package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.service.mapper.base.UserGroupMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserGroupMapper extends UserGroupMapperBase {

	public UserGroupMapper(UserGroupDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}