package com.chloz.test.web.resource.base;

import com.chloz.test.domain.User;
import com.chloz.test.service.UserService;
import com.chloz.test.service.exception.EmailAlreadyUsedException;
import com.chloz.test.service.exception.InvalidPasswordException;
import com.chloz.test.service.exception.LoginAlreadyUsedException;
import com.chloz.test.service.exception.PhoneAlreadyUsedException;
import com.chloz.test.service.filter.SimpleUserFilter;
import com.chloz.test.web.Constants;
import com.chloz.test.web.dto.*;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.resource.FilterDomainResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class UserResourceBase extends FilterDomainResource<User, Long, UserDto, SimpleUserFilter> {

	private final UserService service;

	private final UserMapper mapper;
	public UserResourceBase(UserService service, UserMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<UserDto> create(UserDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new User cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<UserDto> update(@Valid UserDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		Optional<User> existingUser = service.findOneByEmailIgnoreCase(dto.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(dto.getId()))) {
			throw new EmailAlreadyUsedException();
		}
		existingUser = service.findOneByPhone(dto.getPhone());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(dto.getId()))) {
			throw new PhoneAlreadyUsedException();
		}
		existingUser = service.findOneByLogin(dto.getLogin());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(dto.getId()))) {
			throw new LoginAlreadyUsedException();
		}
		Boolean emailChecked = null;
		Boolean phoneChecked = null;
		if (existingUser.isPresent() && existingUser.get().getEmail() != null
				&& !existingUser.get().getEmail().equals(dto.getEmail())) {
			emailChecked = false;
		}
		if (existingUser.isPresent() && existingUser.get().getPhone() != null
				&& !existingUser.get().getPhone().equals(dto.getPhone())) {
			phoneChecked = false;
		}
		this.handleDtoBeforeUpdate(dto);
		User user = this.mapper.entityFromDto(dto);
		this.handleModelBeforeUpdate(user, dto);
		if (emailChecked != null) {
			user.setEmailChecked(emailChecked);
		}
		if (phoneChecked != null) {
			user.setPhoneChecked(phoneChecked);
		}
		user = this.service.save(user);
		this.handleModelAfterUpdate(user, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(user, graph));
	}

	public void changePassword(User user, PasswordChangeDto passwordChangeDto) {
		if (!AccountResourceBase.checkPasswordLength(passwordChangeDto.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		service.changePassword(user, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
	}

	public void changePassword(User user, String password) {
		service.changePassword(user, password);
	}

	@Override
	protected void handleModelBeforeCreate(User model, UserDto dto) {
		super.handleModelBeforeCreate(model, dto);
		if (model.getVerificationCodeList() != null) {
			model.getVerificationCodeList().forEach(el -> el.setUsedFor(model));
		}
	}

	@Override
	protected void handleModelBeforeUpdate(User model, UserDto dto) {
		super.handleModelBeforeUpdate(model, dto);
		if (model.getVerificationCodeList() != null) {
			model.getVerificationCodeList().forEach(el -> el.setUsedFor(model));
		}
	}

}