package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.service.UserService;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.web.resource.DefaultDomainResource;

public class UserResourceBase extends DefaultDomainResource<Long, UserDto, SimpleUserFilter> {

	private final UserService service;
	public UserResourceBase(UserService service) {
		super(service);
		this.service = service;
	}

	public void changePassword(Long id, String password) {
		service.changePassword(id, password);
	}

}