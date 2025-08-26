package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.domain.User;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.mapper.DomainMapper;
import com.chloz.test.service.mapper.RoleMapper;
import com.chloz.test.service.mapper.MediaMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserMapperBase extends DomainMapper<User, UserDto> {

	private final ApplicationContext applicationContext;

	private final UserDataAccess dataAccess;
	public UserMapperBase(UserDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public User entityFromIdOrModelFromDto(UserDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("User with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public User modelFromDto(UserDto dto) {
		if (dto == null) {
			return null;
		}
		User model = new User();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setLogin(dto.getLogin());
		model.setEmail(dto.getEmail());
		model.setPhone(dto.getPhone());
		model.setPhoneChecked(dto.getPhoneChecked());
		model.setAccountLocked(dto.getAccountLocked());
		model.setEmailChecked(dto.getEmailChecked());
		model.setActivated(dto.getActivated());
		model.setAttempts(dto.getAttempts());
		model.setFirstName(dto.getFirstName());
		model.setName(dto.getName());
		model.setLang(dto.getLang());
		if (dto.getRoles() != null) {
			model.setRoles(new ArrayList<>());
			dto.getRoles().stream().map(applicationContext.getBean(RoleMapper.class)::entityFromIdOrModelFromDto)
					.forEach(model.getRoles()::add);
		}
		model.setPicture(applicationContext.getBean(MediaMapper.class).entityFromIdOrModelFromDto(dto.getPicture()));
		if (dto.getId() != null) {
			dataAccess.findById(dto.getId()).ifPresent(ent -> {
				model.setCreatedBy(ent.getCreatedBy());
				model.setCreatedDate(ent.getCreatedDate());
				model.setLastModifiedBy(ent.getLastModifiedBy());
				model.setLastModifiedDate(ent.getLastModifiedDate());
			});
		}
		return model;
	}

}