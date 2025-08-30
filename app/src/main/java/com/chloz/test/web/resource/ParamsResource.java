package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.dataaccess.filter.ParamsFilter;
import com.chloz.test.service.ParamsService;
import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.ParamsResourceBase;
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
@RequestMapping(path = Constants.API_BASE_PATH + "/paramss")
public class ParamsResource extends ParamsResourceBase {

	public ParamsResource(ParamsService service) {
		super(service);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<ParamsDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<ParamsDto> getPageByFilter(@ParameterObject SimpleParamsFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<ParamsDto> search(@RequestBody ParamsFilter filter, @ParameterObject Pageable pageable,
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

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<ParamsDto>> bulkCreate(@Valid @RequestBody List<ParamsDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

	@Override
	@PutMapping(path = "bulk")
	public ResponseEntity<List<ParamsDto>> bulkUpdate(@Valid @RequestBody List<ParamsDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkUpdate(dto, graph);
	}

	@Override
	@PatchMapping
	public ResponseEntity<ParamsDto> partialUpdate(@Valid @RequestBody ParamsDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.partialUpdate(dto, graph);
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