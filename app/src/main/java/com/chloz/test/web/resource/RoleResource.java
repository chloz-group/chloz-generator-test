package com.chloz.test.web.resource;

import com.chloz.test.service.RoleService;
import com.chloz.test.service.filter.SimpleRoleFilter;
import com.chloz.test.service.filter.RoleFilter;
import com.chloz.test.web.dto.RoleDto;
import com.chloz.test.web.mapper.RoleMapper;
import com.chloz.test.web.resource.base.RoleResourceBase;
import com.chloz.test.web.Constants;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/roles")
public class RoleResource extends RoleResourceBase {

	public RoleResource(RoleService service, RoleMapper mapper) {
		super(service, mapper);
	}

	@GetMapping(path = "{name}")
	public ResponseEntity<RoleDto> getByName(@NotNull @PathVariable("name") String name,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(name, graph);
	}

	@Override
	@GetMapping
	public PagedModel<RoleDto> getPageByFilter(@ParameterObject SimpleRoleFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<RoleDto> search(@RequestBody RoleFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

}