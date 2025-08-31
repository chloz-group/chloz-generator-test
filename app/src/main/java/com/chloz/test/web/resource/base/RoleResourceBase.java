package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.service.RoleService;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class RoleResourceBase extends DefaultDomainResource<String, RoleDto, SimpleRoleFilter> {

	private final RoleService service;
	public RoleResourceBase(RoleService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<RoleDto> update(String name, @Valid RoleDto dto, String graph) {
		if (dto.getName() != null && !Objects.equals(name, dto.getName())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setName(name);
		return super.update(dto, graph);
	}

	public ResponseEntity<RoleDto> partialUpdate(String name, RoleDto dto, String graph) {
		if (dto.getName() != null && !Objects.equals(name, dto.getName())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setName(name);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}