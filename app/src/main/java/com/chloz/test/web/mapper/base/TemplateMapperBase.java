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
	public Template entityFromIdOrModelFromDto(TemplateDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId()).orElseThrow(
					() -> new NoSuchElementException("Template with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public Template modelFromDto(TemplateDto dto) {
		if (dto == null) {
			return null;
		}
		Template model = new Template();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setCode(dto.getCode());
		model.setContent(dto.getContent());
		model.setTitle(dto.getTitle());
		model.setShortContent(dto.getShortContent());
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