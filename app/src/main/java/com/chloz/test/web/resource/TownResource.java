package com.chloz.test.web.resource;

import com.chloz.test.service.TownService;
import com.chloz.test.service.filter.SimpleTownFilter;
import com.chloz.test.service.filter.TownFilter;
import com.chloz.test.web.dto.TownDto;
import com.chloz.test.web.mapper.TownMapper;
import com.chloz.test.web.resource.base.TownResourceBase;
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
@RequestMapping(path = Constants.API_BASE_PATH + "/towns")
public class TownResource extends TownResourceBase {

	public TownResource(TownService service, TownMapper mapper) {
		super(service, mapper);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<TownDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public Page<TownDto> getPageByFilter(@ParameterObject SimpleTownFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public Page<TownDto> search(@RequestBody TownFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<TownDto> create(@Valid @RequestBody TownDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PutMapping
	public ResponseEntity<TownDto> update(@Valid @RequestBody TownDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	@PostMapping(path = "updateFields")
	public ResponseEntity<TownDto> updateFields(@Valid @RequestBody TownDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.updateFields(dto, graph);
	}

	@Override
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteById(@NotNull @PathVariable("id") Long id) {
		return super.deleteById(id);
	}

}