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
	public Town entityFromIdOrModelFromDto(TownDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId())
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
			service.findById(dto.getId()).ifPresent(ent -> {
				model.setCreatedBy(ent.getCreatedBy());
				model.setCreatedDate(ent.getCreatedDate());
				model.setLastModifiedBy(ent.getLastModifiedBy());
				model.setLastModifiedDate(ent.getLastModifiedDate());
			});
		}
		return model;
	}

}