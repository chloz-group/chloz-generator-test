package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.domain.Town;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.service.mapper.DomainMapper;
import com.chloz.test.service.mapper.CountryMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TownMapperBase extends DomainMapper<Town, TownDto> {

	private final ApplicationContext applicationContext;

	private final TownDataAccess dataAccess;
	public TownMapperBase(TownDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public Town entityFromIdOrModelFromDto(TownDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Town with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public Town modelFromDto(TownDto dto) {
		if (dto == null) {
			return null;
		}
		Town model = new Town();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCountry(applicationContext.getBean(CountryMapper.class).entityFromIdOrModelFromDto(dto.getCountry()));
		if (dto.getId() != null) {
			setCommonField(model, dataAccess.findById(dto.getId()));
		}
		return model;
	}

	@Override
	public void partialUpdate(Town ent, TownDto dto) {
		// set model simple fields
		if (dto.getId() != null)
			ent.setId(dto.getId());
		if (dto.getName() != null)
			ent.setName(dto.getName());
		// set model relations
		if (dto.getCountry() != null)
			ent.setCountry(
					applicationContext.getBean(CountryMapper.class).entityFromIdOrModelFromDto(dto.getCountry()));
	}

}