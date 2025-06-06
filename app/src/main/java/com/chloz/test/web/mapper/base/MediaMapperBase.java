package com.chloz.test.web.mapper.base;

import com.chloz.test.service.MediaService;
import com.chloz.test.domain.Media;
import com.chloz.test.web.dto.MediaDto;
import com.chloz.test.web.mapper.DomainMapper;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MediaMapperBase extends DomainMapper<Media, MediaDto> {

	private final ApplicationContext applicationContext;

	private final MediaService service;
	public MediaMapperBase(MediaService service, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.service = service;
	}

	@Override
	public Media entityFromIdOrModelFromDto(MediaDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId())
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