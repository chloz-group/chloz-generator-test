package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Country;
import com.chloz.test.service.CountryService;
import com.chloz.test.web.Constants;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleCountryFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.CountryDto;
import com.chloz.test.web.mapper.CountryMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

public class CountryResourceBase extends FilterDomainResource<Country, String, CountryDto, SimpleCountryFilter> {

	private final CountryService service;

	private final CountryMapper mapper;
	public CountryResourceBase(CountryService service, CountryMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<CountryDto> create(@Valid CountryDto dto, String graph) {
		if (dto.getCode() != null) {
			throw new BadRequestException("A new Country cannot already have the code field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<CountryDto> update(@Valid CountryDto dto, String graph) {
		if (dto.getCode() == null || service.findById(dto.getCode()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<CountryDto>> bulkUpdate(@Valid List<CountryDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getCode() == null || service.findById(dto.getCode()).isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<CountryDto> updateFields(@Valid CountryDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Country> opt = service.findById(dto.getCode());
		if (dto.getCode() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		// set fields
		Country ent = opt.get();
		ent.setCode(dto.getCode());
		ent.setName(dto.getName());
		ent.setCallingCode(dto.getCallingCode());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Country model, CountryDto dto) {
		super.handleModelBeforeCreate(model, dto);
		if (model.getTowns() != null) {
			model.getTowns().forEach(el -> el.setCountry(model));
		}
	}

	@Override
	protected void handleModelBeforeUpdate(Country model, CountryDto dto) {
		super.handleModelBeforeUpdate(model, dto);
		if (model.getTowns() != null) {
			model.getTowns().forEach(el -> el.setCountry(model));
		}
	}

}