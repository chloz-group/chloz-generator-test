package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.service.CountryService;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class CountryResourceBase extends DefaultDomainResource<String, CountryDto, SimpleCountryFilter> {

	private final CountryService service;
	public CountryResourceBase(CountryService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<CountryDto> create(@Valid CountryDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<CountryDto> update(@Valid CountryDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<CountryDto>> bulkUpdate(@Valid List<CountryDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<CountryDto> updateFields(@Valid CountryDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.updateFields(dto, graph));
	}

}