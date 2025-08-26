package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.service.mapper.UserGroupMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

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
	public Optional<UserGroupDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public UserGroupDto update(UserGroupDto dto, String graph) {
		if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<UserGroupDto> bulkUpdate(List<UserGroupDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public UserGroupDto updateFields(UserGroupDto dto, String graph) {
		Optional<UserGroup> opt = dataAccess.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		UserGroup ent = opt.get();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setDescription(dto.getDescription());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}