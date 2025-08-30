package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.dataaccess.RoleDataAccess;
import com.chloz.test.domain.Role;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.service.mapper.RoleMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
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
	@Transactional(readOnly = true)
	public Optional<RoleDto> findById(String name) {
		return dataAccess.findById(name).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected String getIdFromDto(RoleDto dto) {
		return dto.getName();
	}

}