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
	public Country entityFromIdOrElseFromDto(CountryDto dto) {
		if (dto != null && dto.getCode() != null) {
			return service.findById(dto.getCode()).orElseThrow(
					() -> new NoSuchElementException("Country with code " + dto.getCode() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public Country entityFromDto(CountryDto dto) {
		if (dto == null) {
			return null;
		}
		Country ent = new Country();
		if (dto.getCode() != null) {
			ent = service.findById(dto.getCode()).orElseThrow(
					() -> new NoSuchElementException("Country with code " + dto.getCode() + " does not exists"));
		}
		ent.setDisabled(dto.getDisabled());
		ent.setCode(dto.getCode());
		ent.setName(dto.getName());
		ent.setCallingCode(dto.getCallingCode());
		if (dto.getTowns() != null) {
			if (ent.getTowns() != null)
				ent.getTowns().clear();
			else
				ent.setTowns(new ArrayList<>());
			dto.getTowns().stream().map(applicationContext.getBean(TownMapper.class)::entityFromIdOrElseFromDto)
					.forEach(ent.getTowns()::add);
		}
		return ent;
	}

}