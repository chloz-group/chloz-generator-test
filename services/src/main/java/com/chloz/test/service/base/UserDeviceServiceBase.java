package com.chloz.test.service.base;

import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface UserDeviceServiceBase extends DefaultDomainService<Long, UserDeviceDto, SimpleUserDeviceFilter> {

	Optional<UserDeviceDto> findById(Long id);

}