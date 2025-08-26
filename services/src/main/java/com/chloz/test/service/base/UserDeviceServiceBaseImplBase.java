package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.dataaccess.UserDeviceDataAccess;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.service.mapper.UserDeviceMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

public class UserDeviceServiceBaseImplBase
		extends
			DefaultDomainServiceImpl<UserDevice, Long, UserDeviceDto, SimpleUserDeviceFilter>
		implements
			UserDeviceServiceBase {

	private final UserDeviceDataAccess dataAccess;

	private final UserDeviceMapper mapper;
	public UserDeviceServiceBaseImplBase(UserDeviceDataAccess dataAccess, UserDeviceMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public Optional<UserDeviceDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public UserDeviceDto update(UserDeviceDto dto, String graph) {
		if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<UserDeviceDto> bulkUpdate(List<UserDeviceDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public UserDeviceDto updateFields(UserDeviceDto dto, String graph) {
		Optional<UserDevice> opt = dataAccess.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		UserDevice ent = opt.get();
		ent.setId(dto.getId());
		ent.setToken(dto.getToken());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}