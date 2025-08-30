package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.service.UserGroupService;
import com.chloz.test.service.dto.UserGroupDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class UserGroupResourceBase extends DefaultDomainResource<Long, UserGroupDto, SimpleUserGroupFilter> {

	private final UserGroupService service;
	public UserGroupResourceBase(UserGroupService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<UserGroupDto> create(@Valid UserGroupDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<UserGroupDto> update(@Valid UserGroupDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<UserGroupDto>> bulkUpdate(@Valid List<UserGroupDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<UserGroupDto> partialUpdate(@Valid UserGroupDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}