package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.UserDevice;
import java.util.Optional;

public interface UserDeviceDataAccessBase extends FilterDomainDataAccess<UserDevice, Long, SimpleUserDeviceFilter> {

	Optional<UserDevice> findByToken(String token);

}