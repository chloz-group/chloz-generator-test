package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.dataaccess.filter.CountryFilter;
import com.chloz.test.service.CountryService;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.CountryResourceBase;
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
@RequestMapping(path = Constants.API_BASE_PATH + "/countrys")
public class CountryResource extends CountryResourceBase {

	public CountryResource(CountryService service) {
		super(service);
	}

	@GetMapping(path = "{code}")
	public ResponseEntity<CountryDto> getByCode(@NotNull @PathVariable("code") String code,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(code, graph);
	}

	@Override
	@GetMapping
	public PagedModel<CountryDto> getPageByFilter(@ParameterObject SimpleCountryFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@PostMapping(path = "search")
	public PagedModel<CountryDto> search(@RequestBody CountryFilter filter, @ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<CountryDto> create(@Valid @RequestBody CountryDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PutMapping
	public ResponseEntity<CountryDto> update(@Valid @RequestBody CountryDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.update(dto, graph);
	}

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<CountryDto>> bulkCreate(@Valid @RequestBody List<CountryDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

	@Override
	@PutMapping(path = "bulk")
	public ResponseEntity<List<CountryDto>> bulkUpdate(@Valid @RequestBody List<CountryDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkUpdate(dto, graph);
	}

	@Override
	@PatchMapping
	public ResponseEntity<CountryDto> partialUpdate(@Valid @RequestBody CountryDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.partialUpdate(dto, graph);
	}

	@PostMapping(path = "enable-status/{codes}")
	public ResponseEntity<Void> updateEnableStatus(@NotNull @PathVariable("codes") List<String> codes,
			@NotNull @RequestParam("status") boolean status) {
		return super.updateEnableStatus(codes, status);
	}

	@Override
	@DeleteMapping(path = "{code}")
	public ResponseEntity<Void> deleteById(@NotNull @PathVariable("code") String code) {
		return super.deleteById(code);
	}

	@Override
	@DeleteMapping(path = "bulk/{codes}")
	public ResponseEntity<Void> deleteAllById(@NotNull @PathVariable("codes") List<String> codes) {
		return super.deleteAllById(codes);
	}

}