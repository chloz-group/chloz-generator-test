package com.chloz.test.service.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.SimpleDomainService;
import java.util.*;

public interface UserDeviceServiceBase extends SimpleDomainService<UserDevice, Long> {

	Optional<UserDevice> findById(Long id);

}