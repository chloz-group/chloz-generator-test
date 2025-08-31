package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.dataaccess.filter.TownFilter;
import com.chloz.test.service.TownService;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.TownResourceBase;
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
@RequestMapping(path = Constants.API_BASE_PATH + "/towns")
public class TownResource extends TownResourceBase {

	public TownResource(TownService service) {
		super(service);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<TownDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<TownDto> getPageByFilter(@ParameterObject SimpleTownFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<TownDto> search(@RequestBody TownFilter filter, @ParameterObject Pageable pageable,
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
	@PutMapping(path = "{id}")
	public ResponseEntity<TownDto> update(@NotNull @PathVariable("id") Long id, @Valid @RequestBody TownDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(id, dto, graph);
	}

	@Override
	@PatchMapping(path = "{id}")
	public ResponseEntity<TownDto> partialUpdate(@NotNull @PathVariable("id") Long id, @RequestBody TownDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.partialUpdate(id, dto, graph);
	}

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<TownDto>> bulkCreate(@Valid @RequestBody List<TownDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

	@Override
	@PutMapping(path = "bulk")
	public ResponseEntity<List<TownDto>> bulkUpdate(@Valid @RequestBody List<TownDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkUpdate(dto, graph);
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