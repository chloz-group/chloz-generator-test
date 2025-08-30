package com.chloz.test.service.mapper.base;

import com.chloz.test.dataaccess.MediaDataAccess;
import com.chloz.test.domain.Media;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.service.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MediaMapperBase extends DomainMapper<Media, MediaDto> {

	private final ApplicationContext applicationContext;

	private final MediaDataAccess dataAccess;
	public MediaMapperBase(MediaDataAccess dataAccess, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.dataAccess = dataAccess;
	}

	@Override
	public Media entityFromIdOrModelFromDto(MediaDto dto) {
		if (dto != null && dto.getId() != null) {
			return dataAccess.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Media with id " + dto.getId() + " does not exists"));
		}
		return this.modelFromDto(dto);
	}

	@Override
	public Media modelFromDto(MediaDto dto) {
		if (dto == null) {
			return null;
		}
		Media model = new Media();
		model.setDisabled(dto.getDisabled());
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setContentType(dto.getContentType());
		model.setKey(dto.getKey());
		if (dto.getId() != null) {
			setCommonField(model, dataAccess.findById(dto.getId()));
		}
		return model;
	}

	@Override
	public void partialUpdate(Media ent, MediaDto dto) {
		// set model simple fields
		if (dto.getId() != null)
			ent.setId(dto.getId());
		if (dto.getName() != null)
			ent.setName(dto.getName());
		if (dto.getContentType() != null)
			ent.setContentType(dto.getContentType());
		if (dto.getKey() != null)
			ent.setKey(dto.getKey());
		// set model relations
	}

}