package com.chloz.test.service.mapper.base;

import com.chloz.test.domain.Role;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.service.mapper.DomainMapper;

public class RoleMapperBase extends DomainMapper<Role, RoleDto> {

	@Override
	public Role entityFromIdOrModelFromDto(RoleDto dto) {
		return this.modelFromDto(dto);
	}

	@Override
	public void partialUpdate(Role entity, RoleDto roleDto) {
		if (roleDto.getDisabled() != null) {
			entity.setDisabled(roleDto.getDisabled());
		}
		if (roleDto.getDescription() != null) {
			entity.setDescription(roleDto.getDescription());
		}
	}

}