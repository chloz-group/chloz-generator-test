package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.service.TemplateService;
import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class TemplateResourceBase extends DefaultDomainResource<Long, TemplateDto, SimpleTemplateFilter> {

	private final TemplateService service;
	public TemplateResourceBase(TemplateService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<TemplateDto> update(@NotNull Long id, @Valid TemplateDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public ResponseEntity<TemplateDto> partialUpdate(@NotNull Long id, TemplateDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}