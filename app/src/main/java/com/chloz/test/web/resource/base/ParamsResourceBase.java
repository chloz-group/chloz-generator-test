package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Params;
import com.chloz.test.service.ParamsService;
import com.chloz.test.web.Constants;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleParamsFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.ParamsDto;
import com.chloz.test.web.mapper.ParamsMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

public class ParamsResourceBase extends FilterDomainResource<Params, Long, ParamsDto, SimpleParamsFilter> {

	private final ParamsService service;

	private final ParamsMapper mapper;
	public ParamsResourceBase(ParamsService service, ParamsMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<ParamsDto> create(@Valid ParamsDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new Params cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<ParamsDto> update(@Valid ParamsDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<ParamsDto>> bulkUpdate(@Valid List<ParamsDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<ParamsDto> updateFields(@Valid ParamsDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Params> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
		}
		// set fields
		Params ent = opt.get();
		ent.setId(dto.getId());
		ent.setParamKey(dto.getParamKey());
		ent.setStringValue(dto.getStringValue());
		ent.setNumberValue(dto.getNumberValue());
		ent.setDecimalValue(dto.getDecimalValue());
		ent.setBooleanValue(dto.getBooleanValue());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Params model, ParamsDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(Params model, ParamsDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}