package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.service.ParamsService;
import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class ParamsResourceBase extends DefaultDomainResource<Long, ParamsDto, SimpleParamsFilter> {

	private final ParamsService service;
	public ParamsResourceBase(ParamsService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<ParamsDto> create(@Valid ParamsDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<ParamsDto> update(@Valid ParamsDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<ParamsDto>> bulkUpdate(@Valid List<ParamsDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<ParamsDto> updateFields(@Valid ParamsDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.updateFields(dto, graph));
	}

}