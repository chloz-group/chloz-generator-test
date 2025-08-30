package com.chloz.test.service.base;

import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface UserGroupServiceBase extends DefaultDomainService<Long, UserGroupDto, SimpleUserGroupFilter> {

	Optional<UserGroupDto> findById(Long id);

}