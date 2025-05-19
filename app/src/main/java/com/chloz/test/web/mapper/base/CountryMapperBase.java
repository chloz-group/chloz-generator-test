package com.chloz.test.web.mapper.base;

import com.chloz.test.service.CountryService;
import com.chloz.test.domain.Country;
import com.chloz.test.web.dto.CountryDto;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.mapper.TownMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CountryMapperBase extends DomainMapper<Country, CountryDto> {

	private final ApplicationContext applicationContext;

	private final CountryService service;
	public CountryMapperBase(CountryService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public Country entityFromIdOrModelFromDto(CountryDto dto) {
		if (dto != null && dto.getCode() != null) {
			return service.findById(dto.getCode()).orElseThrow(
					() -> new NoSuchElementException("Country with code " + dto.getCode() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public Country modelFromDto(CountryDto dto) {
		if (dto == null) {
			return null;
		}
		Country model = new Country();
		model.setDisabled(dto.getDisabled());
		model.setCode(dto.getCode());
		model.setName(dto.getName());
		model.setCallingCode(dto.getCallingCode());
		if (dto.getTowns() != null) {
			model.setTowns(new ArrayList<>());
			dto.getTowns().stream().map(applicationContext.getBean(TownMapper.class)::entityFromIdOrModelFromDto)
					.forEach(model.getTowns()::add);
		}
		return model;
	}

}