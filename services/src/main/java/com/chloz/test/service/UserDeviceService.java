package com.chloz.test.service;

import com.chloz.test.domain.User;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.base.UserDeviceServiceBase;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserDeviceService extends UserDeviceServiceBase {

	void deleteToken(String s);

	Optional<UserDevice> findByToken(String token);

	/**
	 * Find the devices owned by users
	 *
	 * @param users
	 * @return
	 */
	List<UserDevice> findDevicesOwnedByUsers(List<User> users);

}