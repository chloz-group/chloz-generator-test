package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.UserDeviceDataAccess;
import com.chloz.test.service.mapper.base.UserDeviceMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceMapper extends UserDeviceMapperBase {

	public UserDeviceMapper(UserDeviceDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}