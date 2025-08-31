package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class MediaResourceBase extends DefaultDomainResource<Long, MediaDto, SimpleMediaFilter> {

	private final MediaService service;
	public MediaResourceBase(MediaService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<MediaDto> update(Long id, @Valid MediaDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public ResponseEntity<MediaDto> partialUpdate(Long id, MediaDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}