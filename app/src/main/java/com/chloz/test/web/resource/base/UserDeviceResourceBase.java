package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class UserDeviceResourceBase extends DefaultDomainResource<Long, UserDeviceDto, SimpleUserDeviceFilter> {

	private final UserDeviceService service;
	public UserDeviceResourceBase(UserDeviceService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<UserDeviceDto> create(@Valid UserDeviceDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<UserDeviceDto> update(@Valid UserDeviceDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<UserDeviceDto>> bulkUpdate(@Valid List<UserDeviceDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<UserDeviceDto> partialUpdate(@Valid UserDeviceDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}