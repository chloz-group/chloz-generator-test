package com.chloz.test.web.mapper.base;

import com.chloz.test.service.UserDeviceService;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.web.dto.UserDeviceDto;
import com.chloz.test.web.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserDeviceMapperBase extends DomainMapper<UserDevice, UserDeviceDto> {

	private final ApplicationContext applicationContext;

	private final UserDeviceService service;
	public UserDeviceMapperBase(UserDeviceService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public UserDevice entityFromIdOrModelFromDto(UserDeviceDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserDevice with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public UserDevice modelFromDto(UserDeviceDto dto) {
		if (dto == null) {
			return null;
		}
		UserDevice model = new UserDevice();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setToken(dto.getToken());
		if (dto.getId() != null) {
			service.findById(dto.getId()).ifPresent(ent -> {
				model.setCreatedBy(ent.getCreatedBy());
				model.setCreatedDate(ent.getCreatedDate());
				model.setLastModifiedBy(ent.getLastModifiedBy());
				model.setLastModifiedDate(ent.getLastModifiedDate());
			});
		}
		return model;
	}

}