package com.chloz.test.service;

import com.chloz.test.service.base.UserServiceBase;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.dto.UserRegistrationDto;

public interface UserService extends UserServiceBase {

	UserDto createNewUser(UserRegistrationDto user, String password, String graph);

}