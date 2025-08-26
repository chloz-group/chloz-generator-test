package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.dataaccess.RoleDataAccess;
import com.chloz.test.domain.Role;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.service.mapper.RoleMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

public class RoleServiceBaseImplBase extends DefaultDomainServiceImpl<Role, String, RoleDto, SimpleRoleFilter>
		implements
			RoleServiceBase {

	private final RoleDataAccess dataAccess;

	private final RoleMapper mapper;
	public RoleServiceBaseImplBase(RoleDataAccess dataAccess, RoleMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public Optional<RoleDto> findById(String name) {
		return dataAccess.findById(name).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public RoleDto update(RoleDto dto, String graph) {
		if (dto.getName() == null || dataAccess.findById(dto.getName()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<RoleDto> bulkUpdate(List<RoleDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getName() == null || dataAccess.findById(dto.getName()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public RoleDto updateFields(RoleDto dto, String graph) {
		Optional<Role> opt = dataAccess.findById(dto.getName());
		if (dto.getName() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		Role ent = opt.get();
		ent.setName(dto.getName());
		ent.setDescription(dto.getDescription());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}