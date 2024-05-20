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
	public UserDevice entityFromIdOrElseFromDto(UserDeviceDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserDevice with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public UserDevice entityFromDto(UserDeviceDto dto) {
		if (dto == null) {
			return null;
		}
		UserDevice ent = new UserDevice();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("UserDevice with id " + dto.getId() + " does not exists"));
		}
		ent.setId(dto.getId());
		ent.setToken(dto.getToken());
		return ent;
	}

}