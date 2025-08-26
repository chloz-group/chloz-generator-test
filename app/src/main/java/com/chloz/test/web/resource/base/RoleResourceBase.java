package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.service.RoleService;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class RoleResourceBase extends DefaultDomainResource<String, RoleDto, SimpleRoleFilter> {

	private final RoleService service;
	public RoleResourceBase(RoleService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<RoleDto> create(@Valid RoleDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<RoleDto> update(@Valid RoleDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<RoleDto>> bulkUpdate(@Valid List<RoleDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<RoleDto> updateFields(@Valid RoleDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.updateFields(dto, graph));
	}

}