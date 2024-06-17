package com.chloz.test.web.resource.base;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.UserGroupService;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleUserGroupFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.UserGroupDto;
import com.chloz.test.web.mapper.UserGroupMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class UserGroupResourceBase extends FilterDomainResource<UserGroup, Long, UserGroupDto, SimpleUserGroupFilter> {

	private final UserGroupService service;

	private final UserGroupMapper mapper;
	public UserGroupResourceBase(UserGroupService service, UserGroupMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<UserGroupDto> create(@Valid UserGroupDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new UserGroup cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<UserGroupDto> update(@Valid UserGroupDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		return super.update(dto, graph);
	}

	public ResponseEntity<UserGroupDto> updateFields(@Valid UserGroupDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<UserGroup> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		// set fields
		UserGroup ent = opt.get();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setDescription(dto.getDescription());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(UserGroup model, UserGroupDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(UserGroup model, UserGroupDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}