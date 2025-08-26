package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Role;
import java.util.Optional;

public interface RoleDataAccessBase extends FilterDomainDataAccess<Role, String, SimpleRoleFilter> {

	Optional<Role> findByName(String name);

}