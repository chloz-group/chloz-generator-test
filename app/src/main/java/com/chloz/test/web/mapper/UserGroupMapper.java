package com.chloz.test.web.mapper;

import com.chloz.test.service.UserGroupService;
import com.chloz.test.web.mapper.base.UserGroupMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserGroupMapper extends UserGroupMapperBase {

	public UserGroupMapper(UserGroupService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}