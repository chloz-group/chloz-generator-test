package com.chloz.test.web.mapper.base;

import com.chloz.test.service.UserService;
import com.chloz.test.domain.User;
import com.chloz.test.web.dto.UserDto;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.mapper.RoleMapper;
import com.chloz.test.web.mapper.MediaMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserMapperBase extends DomainMapper<User, UserDto> {

	private final ApplicationContext applicationContext;

	private final UserService service;
	public UserMapperBase(UserService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public User entityFromIdOrElseFromDto(UserDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("User with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public User entityFromDto(UserDto dto) {
		if (dto == null) {
			return null;
		}
		User ent = new User();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("User with id " + dto.getId() + " does not exists"));
		}
		ent.setId(dto.getId());
		ent.setLogin(dto.getLogin());
		ent.setEmail(dto.getEmail());
		ent.setPhone(dto.getPhone());
		ent.setPhoneChecked(dto.getPhoneChecked());
		ent.setAccountLocked(dto.getAccountLocked());
		ent.setEmailChecked(dto.getEmailChecked());
		ent.setActivated(dto.getActivated());
		ent.setAttempts(dto.getAttempts());
		ent.setFirstName(dto.getFirstName());
		ent.setName(dto.getName());
		ent.setLang(dto.getLang());
		if (dto.getRoles() != null) {
			if (ent.getRoles() != null)
				ent.getRoles().clear();
			else
				ent.setRoles(new ArrayList<>());
			dto.getRoles().stream().map(applicationContext.getBean(RoleMapper.class)::entityFromIdOrElseFromDto)
					.forEach(ent.getRoles()::add);
		}
		ent.setPicture(applicationContext.getBean(MediaMapper.class).entityFromIdOrElseFromDto(dto.getPicture()));
		return ent;
	}

}