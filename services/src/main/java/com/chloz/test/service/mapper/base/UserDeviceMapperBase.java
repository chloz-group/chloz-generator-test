package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.UserDeviceDataAccess;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.service.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UserDeviceMapperBase extends DomainMapper<UserDevice, UserDeviceDto> {

	private final ApplicationContext applicationContext;

	private final UserDeviceDataAccess dataAccess;
	public UserDeviceMapperBase(UserDeviceDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public UserDevice entityFromIdOrModelFromDto(UserDeviceDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId()).orElseThrow(
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
			setCommonField(model, dataAccess.findById(dto.getId()));
		}
		return model;
	}

	@Override
	public void partialUpdate(UserDevice ent, UserDeviceDto dto) {
		// set model simple fields
		if (dto.getId() != null)
			ent.setId(dto.getId());
		if (dto.getToken() != null)
			ent.setToken(dto.getToken());
		// set model relations
	}

}