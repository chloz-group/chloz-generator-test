package com.chloz.test.repository;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.base.UserDeviceRepositoryBase;
import java.util.Optional;

public interface UserDeviceRepository extends UserDeviceRepositoryBase {

	Optional<UserDevice> findByToken(String token);

}