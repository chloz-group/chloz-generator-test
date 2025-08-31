package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.service.UserGroupService;
import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class UserGroupResourceBase extends DefaultDomainResource<Long, UserGroupDto, SimpleUserGroupFilter> {

	private final UserGroupService service;
	public UserGroupResourceBase(UserGroupService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<UserGroupDto> update(Long id, @Valid UserGroupDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public ResponseEntity<UserGroupDto> partialUpdate(Long id, UserGroupDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}