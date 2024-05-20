package com.chloz.test.web.resource;

import com.chloz.test.service.ParamsService;
import com.chloz.test.service.filter.SimpleParamsFilter;
import com.chloz.test.service.filter.ParamsFilter;
import com.chloz.test.web.dto.ParamsDto;
import com.chloz.test.web.mapper.ParamsMapper;
import com.chloz.test.web.resource.base.ParamsResourceBase;
import com.chloz.test.web.Constants;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/paramss")
public class ParamsResource extends ParamsResourceBase {

	public ParamsResource(ParamsService service, ParamsMapper mapper) {
		super(service, mapper);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<ParamsDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public Page<ParamsDto> getPageByFilter(@ParameterObject SimpleParamsFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public Page<ParamsDto> search(@RequestBody ParamsFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<ParamsDto> create(@Valid @RequestBody ParamsDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PutMapping
	public ResponseEntity<ParamsDto> update(@Valid @RequestBody ParamsDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	@PostMapping(path = "updateFields")
	public ResponseEntity<ParamsDto> updateFields(@Valid @RequestBody ParamsDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.updateFields(dto, graph);
	}

	@Override
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteById(@NotNull @PathVariable("id") Long id) {
		return super.deleteById(id);
	}

}