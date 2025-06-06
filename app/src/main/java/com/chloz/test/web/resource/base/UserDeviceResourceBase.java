package com.chloz.test.web.resource.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.web.Constants;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.web.resource.SimpleDomainResource;
import com.chloz.test.web.dto.UserDeviceDto;
import com.chloz.test.web.mapper.UserDeviceMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

public class UserDeviceResourceBase extends SimpleDomainResource<UserDevice, Long, UserDeviceDto> {

	private final UserDeviceService service;

	private final UserDeviceMapper mapper;
	public UserDeviceResourceBase(UserDeviceService service, UserDeviceMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<UserDeviceDto> create(@Valid UserDeviceDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new UserDevice cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<UserDeviceDto> update(@Valid UserDeviceDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<UserDeviceDto>> bulkUpdate(@Valid List<UserDeviceDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<UserDeviceDto> updateFields(@Valid UserDeviceDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<UserDevice> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		// set fields
		UserDevice ent = opt.get();
		ent.setId(dto.getId());
		ent.setToken(dto.getToken());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(UserDevice model, UserDeviceDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(UserDevice model, UserDeviceDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}