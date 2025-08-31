package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.service.UserService;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class UserResourceBase extends DefaultDomainResource<Long, UserDto, SimpleUserFilter> {

	private final UserService service;
	public UserResourceBase(UserService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<UserDto> update(Long id, @Valid UserDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public void changePassword(Long id, String password) {
		service.changePassword(id, password);
	}

}