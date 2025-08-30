package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.service.mapper.UserGroupMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class UserGroupServiceBaseImplBase
		extends
			DefaultDomainServiceImpl<UserGroup, Long, UserGroupDto, SimpleUserGroupFilter>
		implements
			UserGroupServiceBase {

	private final UserGroupDataAccess dataAccess;

	private final UserGroupMapper mapper;
	public UserGroupServiceBaseImplBase(UserGroupDataAccess dataAccess, UserGroupMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserGroupDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected Long getIdFromDto(UserGroupDto dto) {
		return dto.getId();
	}

}