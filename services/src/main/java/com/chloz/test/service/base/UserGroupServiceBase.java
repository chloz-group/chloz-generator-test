package com.chloz.test.service.base;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleUserGroupFilter;
import java.util.*;

public interface UserGroupServiceBase extends FilterDomainService<UserGroup, Long, SimpleUserGroupFilter> {

	Optional<UserGroup> findById(Long id);

}