package com.chloz.test.web.mapper.base;

import com.chloz.test.service.TemplateService;
import com.chloz.test.domain.Template;
import com.chloz.test.web.dto.TemplateDto;
import com.chloz.test.web.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TemplateMapperBase extends DomainMapper<Template, TemplateDto> {

	private final ApplicationContext applicationContext;

	private final TemplateService service;
	public TemplateMapperBase(TemplateService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public Template entityFromIdOrElseFromDto(TemplateDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Template with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public Template entityFromDto(TemplateDto dto) {
		if (dto == null) {
			return null;
		}
		Template ent = new Template();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Template with id " + dto.getId() + " does not exists"));
		}
		ent.setId(dto.getId());
		ent.setCode(dto.getCode());
		ent.setContent(dto.getContent());
		ent.setTitle(dto.getTitle());
		ent.setShortContent(dto.getShortContent());
		return ent;
	}

}