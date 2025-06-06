package com.chloz.test.web.mapper.base;

import com.chloz.test.domain.Role;
import com.chloz.test.web.dto.RoleDto;
import com.chloz.test.web.mapper.DomainMapper;

public class RoleMapperBase extends DomainMapper<Role, RoleDto> {

	@Override
	public Role entityFromIdOrModelFromDto(RoleDto dto) {
		return this.modelFromDto(dto);
	}

}