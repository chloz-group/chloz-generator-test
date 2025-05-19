package com.chloz.test.web.mapper.base;

import com.chloz.test.service.UserGroupService;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.web.dto.UserGroupDto;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.mapper.RoleMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserGroupMapperBase extends DomainMapper<UserGroup, UserGroupDto> {

	private final ApplicationContext applicationContext;

	private final UserGroupService service;
	public UserGroupMapperBase(UserGroupService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public UserGroup entityFromIdOrModelFromDto(UserGroupDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
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
		return model;
	}

}