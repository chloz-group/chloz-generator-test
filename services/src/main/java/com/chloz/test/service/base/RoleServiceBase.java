package com.chloz.test.service.base;

import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface RoleServiceBase extends DefaultDomainService<String, RoleDto, SimpleRoleFilter> {

	Optional<RoleDto> findById(String name);

	RoleDto updateFields(RoleDto dto, String graph);

}