package com.chloz.test.web.resource;

import com.chloz.test.service.TemplateService;
import com.chloz.test.service.filter.SimpleTemplateFilter;
import com.chloz.test.service.filter.TemplateFilter;
import com.chloz.test.web.dto.TemplateDto;
import com.chloz.test.web.mapper.TemplateMapper;
import com.chloz.test.web.resource.base.TemplateResourceBase;
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
@RequestMapping(path = Constants.API_BASE_PATH + "/templates")
public class TemplateResource extends TemplateResourceBase {

	public TemplateResource(TemplateService service, TemplateMapper mapper) {
		super(service, mapper);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<TemplateDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<TemplateDto> getPageByFilter(@ParameterObject SimpleTemplateFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<TemplateDto> search(@RequestBody TemplateFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<TemplateDto> create(@Valid @RequestBody TemplateDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PutMapping
	public ResponseEntity<TemplateDto> update(@Valid @RequestBody TemplateDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<TemplateDto>> bulkCreate(@Valid @RequestBody List<TemplateDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

	@Override
	@PutMapping(path = "bulk")
	public ResponseEntity<List<TemplateDto>> bulkUpdate(@Valid @RequestBody List<TemplateDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkUpdate(dto, graph);
	}

	@Override
	@PostMapping(path = "updateFields")
	public ResponseEntity<TemplateDto> updateFields(@Valid @RequestBody TemplateDto dto,
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