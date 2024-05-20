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
	public Params entityFromIdOrElseFromDto(ParamsDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Params with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public Params entityFromDto(ParamsDto dto) {
		if (dto == null) {
			return null;
		}
		Params ent = new Params();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Params with id " + dto.getId() + " does not exists"));
		}
		ent.setId(dto.getId());
		ent.setParamKey(dto.getParamKey());
		ent.setStringValue(dto.getStringValue());
		ent.setNumberValue(dto.getNumberValue());
		ent.setDecimalValue(dto.getDecimalValue());
		ent.setBooleanValue(dto.getBooleanValue());
		return ent;
	}

}