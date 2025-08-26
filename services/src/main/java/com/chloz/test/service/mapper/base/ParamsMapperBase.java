package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.domain.Params;
import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.service.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ParamsMapperBase extends DomainMapper<Params, ParamsDto> {

	private final ApplicationContext applicationContext;

	private final ParamsDataAccess dataAccess;
	public ParamsMapperBase(ParamsDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public Params entityFromIdOrModelFromDto(ParamsDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Params with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public Params modelFromDto(ParamsDto dto) {
		if (dto == null) {
			return null;
		}
		Params model = new Params();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setParamKey(dto.getParamKey());
		model.setStringValue(dto.getStringValue());
		model.setNumberValue(dto.getNumberValue());
		model.setDecimalValue(dto.getDecimalValue());
		model.setBooleanValue(dto.getBooleanValue());
		if (dto.getId() != null) {
			dataAccess.findById(dto.getId()).ifPresent(ent -> {
				model.setCreatedBy(ent.getCreatedBy());
				model.setCreatedDate(ent.getCreatedDate());
				model.setLastModifiedBy(ent.getLastModifiedBy());
				model.setLastModifiedDate(ent.getLastModifiedDate());
			});
		}
		return model;
	}

}