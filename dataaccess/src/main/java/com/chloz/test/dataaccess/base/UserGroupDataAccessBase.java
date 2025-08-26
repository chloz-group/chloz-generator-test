package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.UserGroup;
import java.util.Optional;

public interface UserGroupDataAccessBase extends FilterDomainDataAccess<UserGroup, Long, SimpleUserGroupFilter> {
}