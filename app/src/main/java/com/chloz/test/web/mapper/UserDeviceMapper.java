package com.chloz.test.web.mapper;

import com.chloz.test.service.UserDeviceService;
import com.chloz.test.web.mapper.base.UserDeviceMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceMapper extends UserDeviceMapperBase {

	public UserDeviceMapper(UserDeviceService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}