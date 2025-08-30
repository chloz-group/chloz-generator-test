package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.dataaccess.UserDeviceDataAccess;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.service.mapper.UserDeviceMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
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
	@Transactional(readOnly = true)
	public Optional<UserDeviceDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected Long getIdFromDto(UserDeviceDto dto) {
		return dto.getId();
	}

}