package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.service.mapper.DomainMapper;
import com.chloz.test.service.mapper.UserMapper;
import com.chloz.test.service.mapper.RoleMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserGroupMapperBase extends DomainMapper<UserGroup, UserGroupDto> {

	private final ApplicationContext applicationContext;

	private final UserGroupDataAccess dataAccess;
	public UserGroupMapperBase(UserGroupDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public UserGroup entityFromIdOrModelFromDto(UserGroupDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserGroup with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public UserGroup modelFromDto(UserGroupDto dto) {
		if (dto == null) {
			return null;
		}
		UserGroup model = new UserGroup();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setDescription(dto.getDescription());
		if (dto.getUsers() != null) {
			model.setUsers(new ArrayList<>());
			dto.getUsers().stream().map(applicationContext.getBean(UserMapper.class)::entityFromIdOrModelFromDto)
					.forEach(model.getUsers()::add);
		}
		if (dto.getRoles() != null) {
			model.setRoles(new ArrayList<>());
			dto.getRoles().stream().map(applicationContext.getBean(RoleMapper.class)::entityFromIdOrModelFromDto)
					.forEach(model.getRoles()::add);
		}
		if (dto.getId() != null) {
			setCommonField(model, dataAccess.findById(dto.getId()));
		}
		return model;
	}

	@Override
	public void partialUpdate(UserGroup ent, UserGroupDto dto) {
		// set model simple fields
		if (dto.getId() != null)
			ent.setId(dto.getId());
		if (dto.getName() != null)
			ent.setName(dto.getName());
		if (dto.getDescription() != null)
			ent.setDescription(dto.getDescription());
		// set model relations
	}

}