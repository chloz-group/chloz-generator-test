package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.service.TownService;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class TownResourceBase extends DefaultDomainResource<Long, TownDto, SimpleTownFilter> {

	private final TownService service;
	public TownResourceBase(TownService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<TownDto> create(@Valid TownDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<TownDto> update(@Valid TownDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<TownDto>> bulkUpdate(@Valid List<TownDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<TownDto> partialUpdate(@Valid TownDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}