package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.domain.Country;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.service.mapper.DomainMapper;
import com.chloz.test.service.mapper.TownMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CountryMapperBase extends DomainMapper<Country, CountryDto> {

	private final ApplicationContext applicationContext;

	private final CountryDataAccess dataAccess;
	public CountryMapperBase(CountryDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public Country entityFromIdOrModelFromDto(CountryDto dto) {
		if (dto != null && dto.getCode() != null) {
			return dataAccess.findById(dto.getCode()).orElseThrow(
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
					.forEach(v -> {
						model.getTowns().add(v);
						if (v.getCountry() != null) {
							v.setCountry(model);
						}
					});
		}
		if (dto.getCode() != null) {
			dataAccess.findById(dto.getCode()).ifPresent(ent -> {
				model.setCreatedBy(ent.getCreatedBy());
				model.setCreatedDate(ent.getCreatedDate());
				model.setLastModifiedBy(ent.getLastModifiedBy());
				model.setLastModifiedDate(ent.getLastModifiedDate());
			});
		}
		return model;
	}

}