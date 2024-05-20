package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Town;
import com.chloz.test.service.TownService;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleTownFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.TownDto;
import com.chloz.test.web.mapper.TownMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class TownResourceBase extends FilterDomainResource<Town, Long, TownDto, SimpleTownFilter> {

	private final TownService service;

	private final TownMapper mapper;
	public TownResourceBase(TownService service, TownMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<TownDto> create(TownDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new Town cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<TownDto> update(TownDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		return super.update(dto, graph);
	}

	public ResponseEntity<TownDto> updateFields(TownDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Town> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		// set fields
		Town ent = opt.get();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Town model, TownDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(Town model, TownDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}