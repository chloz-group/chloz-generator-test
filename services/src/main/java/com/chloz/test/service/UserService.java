package com.chloz.test.service;

import com.chloz.test.domain.User;
import com.chloz.test.service.base.UserServiceBase;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends UserServiceBase {

	@Transactional
	User createNewUser(User user, String password);

}