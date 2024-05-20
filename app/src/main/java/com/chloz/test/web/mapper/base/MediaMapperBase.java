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
	public Media entityFromIdOrElseFromDto(MediaDto dto) {
		if (dto != null && dto.getId() != null) {
			return service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Media with id " + dto.getId() + " does not exists"));
		}
		return this.entityFromDto(dto);
	}

	@Override
	public Media entityFromDto(MediaDto dto) {
		if (dto == null) {
			return null;
		}
		Media ent = new Media();
		if (dto.getId() != null) {
			ent = service.findById(dto.getId())
					.orElseThrow(() -> new NoSuchElementException("Media with id " + dto.getId() + " does not exists"));
		}
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setContentType(dto.getContentType());
		ent.setKey(dto.getKey());
		return ent;
	}

}