package com.chloz.test.web.mapper.base;

import com.chloz.test.service.ParamsService;
import com.chloz.test.domain.Params;
import com.chloz.test.web.dto.ParamsDto;
import com.chloz.test.web.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ParamsMapperBase extends DomainMapper<Params, ParamsDto> {

	private final ApplicationContext applicationContext;

	private final ParamsService service;
	public ParamsMapperBase(ParamsService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public Params entityFromIdOrModelFromDto(ParamsDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
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
		return model;
	}

}