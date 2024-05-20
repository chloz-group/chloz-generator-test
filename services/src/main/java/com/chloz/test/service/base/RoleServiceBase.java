package com.chloz.test.service.base;

import com.chloz.test.domain.Role;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleRoleFilter;
import java.util.*;

public interface RoleServiceBase extends FilterDomainService<Role, String, SimpleRoleFilter> {

	Optional<Role> findById(String name);

}