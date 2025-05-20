package com.chloz.test.web.resource;

import com.chloz.test.domain.User;
import com.chloz.test.service.UserService;
import com.chloz.test.service.filter.SimpleUserFilter;
import com.chloz.test.service.filter.UserFilter;
import com.chloz.test.web.dto.*;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.resource.base.UserResourceBase;
import com.chloz.test.web.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/users")
public class UserResource extends UserResourceBase {

	private final UserMapper mapper;

	private final UserService service;
	public UserResource(UserService service, UserMapper mapper) {
		super(service, mapper);
		this.mapper = mapper;
		this.service = service;
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<UserDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<UserDto> getPageByFilter(@ParameterObject SimpleUserFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<UserDto> search(@RequestBody UserFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserRegistrationDto dto,
			@Nullable @RequestParam("graph") String graph) {
		User user = this.mapper.modelFromDto(dto);
		user = this.service.createNewUser(user, dto.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(user, graph));
	}

	@Override
	@PutMapping
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	// TODOs : authorize this to only the administrator
	@PostMapping(path = "{id}/change-password")
	public void changePassword(@NotNull @PathVariable("id") Long id, @RequestParam String password) {
		Optional<User> opt;
		if (id == null || (opt = service.findById(id)).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		super.changePassword(opt.get(), password);
	}

}