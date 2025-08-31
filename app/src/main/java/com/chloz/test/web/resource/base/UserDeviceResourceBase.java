package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class UserDeviceResourceBase extends DefaultDomainResource<Long, UserDeviceDto, SimpleUserDeviceFilter> {

	private final UserDeviceService service;
	public UserDeviceResourceBase(UserDeviceService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<UserDeviceDto> update(Long id, @Valid UserDeviceDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public ResponseEntity<UserDeviceDto> partialUpdate(Long id, UserDeviceDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}