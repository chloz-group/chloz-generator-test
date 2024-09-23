package com.chloz.test.web.mapper.base;

import com.chloz.test.service.TownService;
import com.chloz.test.domain.Town;
import com.chloz.test.web.dto.TownDto;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.mapper.CountryMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TownMapperBase extends DomainMapper<Town, TownDto> {

	private final ApplicationContext applicationContext;

	private final TownService service;
	public TownMapperBase(TownService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public Town entityFromIdOrElseFromDto(TownDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Town with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public Town entityFromDto(TownDto dto) {
		if (dto == null) {
			return null;
		}
		Town ent = new Town();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Town with id " + dto.getId() + " does not exists"));
		}
		ent.setDisabled(dto.getDisabled());
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setCountry(applicationContext.getBean(CountryMapper.class).entityFromIdOrElseFromDto(dto.getCountry()));
		return ent;
	}

}