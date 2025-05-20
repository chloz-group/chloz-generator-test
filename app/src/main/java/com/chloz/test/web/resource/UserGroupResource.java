package com.chloz.test.web.resource;

import com.chloz.test.service.UserGroupService;
import com.chloz.test.service.filter.SimpleUserGroupFilter;
import com.chloz.test.service.filter.UserGroupFilter;
import com.chloz.test.web.dto.UserGroupDto;
import com.chloz.test.web.mapper.UserGroupMapper;
import com.chloz.test.web.resource.base.UserGroupResourceBase;
import com.chloz.test.web.Constants;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/userGroups")
public class UserGroupResource extends UserGroupResourceBase {

	public UserGroupResource(UserGroupService service, UserGroupMapper mapper) {
		super(service, mapper);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<UserGroupDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<UserGroupDto> getPageByFilter(@ParameterObject SimpleUserGroupFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<UserGroupDto> search(@RequestBody UserGroupFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<UserGroupDto> create(@Valid @RequestBody UserGroupDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PutMapping
	public ResponseEntity<UserGroupDto> update(@Valid @RequestBody UserGroupDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<UserGroupDto>> bulkCreate(@Valid @RequestBody List<UserGroupDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

	@Override
	@PutMapping(path = "bulk")
	public ResponseEntity<List<UserGroupDto>> bulkUpdate(@Valid @RequestBody List<UserGroupDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkUpdate(dto, graph);
	}

	@Override
	@PostMapping(path = "updateFields")
	public ResponseEntity<UserGroupDto> updateFields(@Valid @RequestBody UserGroupDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.updateFields(dto, graph);
	}

	@PostMapping(path = "enable-status/{ids}")
	public ResponseEntity<Void> updateEnableStatus(@NotNull @PathVariable("ids") List<Long> ids,
			@NotNull @RequestParam("status") boolean status) {
		return super.updateEnableStatus(ids, status);
	}

	@Override
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteById(@NotNull @PathVariable("id") Long id) {
		return super.deleteById(id);
	}

	@Override
	@DeleteMapping(path = "bulk/{ids}")
	public ResponseEntity<Void> deleteAllById(@NotNull @PathVariable("ids") List<Long> ids) {
		return super.deleteAllById(ids);
	}

}