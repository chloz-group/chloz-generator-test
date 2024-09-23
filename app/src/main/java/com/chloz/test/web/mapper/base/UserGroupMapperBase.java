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
	public UserGroup entityFromIdOrElseFromDto(UserGroupDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserGroup with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public UserGroup entityFromDto(UserGroupDto dto) {
		if (dto == null) {
			return null;
		}
		UserGroup ent = new UserGroup();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserGroup with id " + dto.getId() + " does not exists"));
		}
		ent.setDisabled(dto.getDisabled());
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setDescription(dto.getDescription());
		if (dto.getUsers() != null) {
			if (ent.getUsers() != null)
				ent.getUsers().clear();
			else
				ent.setUsers(new ArrayList<>());
			dto.getUsers().stream().map(applicationContext.getBean(UserMapper.class)::entityFromIdOrElseFromDto)
					.forEach(ent.getUsers()::add);
		}
		if (dto.getRoles() != null) {
			if (ent.getRoles() != null)
				ent.getRoles().clear();
			else
				ent.setRoles(new ArrayList<>());
			dto.getRoles().stream().map(applicationContext.getBean(RoleMapper.class)::entityFromIdOrElseFromDto)
					.forEach(ent.getRoles()::add);
		}
		return ent;
	}

}