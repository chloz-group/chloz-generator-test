package com.chloz.test.web.mapper;

import com.chloz.test.service.UserService;
import com.chloz.test.web.mapper.base.UserMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends UserMapperBase {

	public UserMapper(UserService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}