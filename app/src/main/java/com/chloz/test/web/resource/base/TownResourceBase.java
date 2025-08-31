package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.service.TownService;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class TownResourceBase extends DefaultDomainResource<Long, TownDto, SimpleTownFilter> {

	private final TownService service;
	public TownResourceBase(TownService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<TownDto> update(Long id, @Valid TownDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return super.update(dto, graph);
	}

	public ResponseEntity<TownDto> partialUpdate(Long id, TownDto dto, String graph) {
		if (dto.getId() != null && !Objects.equals(id, dto.getId())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setId(id);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}