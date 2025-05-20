package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Role;
import com.chloz.test.service.RoleService;
import com.chloz.test.web.Constants;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleRoleFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.RoleDto;
import com.chloz.test.web.mapper.RoleMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

public class RoleResourceBase extends FilterDomainResource<Role, String, RoleDto, SimpleRoleFilter> {

	private final RoleService service;

	private final RoleMapper mapper;
	public RoleResourceBase(RoleService service, RoleMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<RoleDto> create(@Valid RoleDto dto, String graph) {
		if (dto.getName() != null) {
			throw new BadRequestException("A new Role cannot already have the name field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<RoleDto> update(@Valid RoleDto dto, String graph) {
		if (dto.getName() == null || service.findById(dto.getName()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<RoleDto>> bulkUpdate(@Valid List<RoleDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getName() == null || service.findById(dto.getName()).isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<RoleDto> updateFields(@Valid RoleDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Role> opt = service.findById(dto.getName());
		if (dto.getName() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		// set fields
		Role ent = opt.get();
		ent.setName(dto.getName());
		ent.setDescription(dto.getDescription());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Role model, RoleDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(Role model, RoleDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}