package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.domain.Template;
import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.service.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TemplateMapperBase extends DomainMapper<Template, TemplateDto> {

	private final ApplicationContext applicationContext;

	private final TemplateDataAccess dataAccess;
	public TemplateMapperBase(TemplateDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public Template entityFromIdOrModelFromDto(TemplateDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId()).orElseThrow(
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
			setCommonField(model, dataAccess.findById(dto.getId()));
		}
		return model;
	}

	@Override
	public void partialUpdate(Template ent, TemplateDto dto) {
		// set model simple fields
		if (dto.getId() != null)
			ent.setId(dto.getId());
		if (dto.getCode() != null)
			ent.setCode(dto.getCode());
		if (dto.getContent() != null)
			ent.setContent(dto.getContent());
		if (dto.getTitle() != null)
			ent.setTitle(dto.getTitle());
		if (dto.getShortContent() != null)
			ent.setShortContent(dto.getShortContent());
		// set model relations
	}

}