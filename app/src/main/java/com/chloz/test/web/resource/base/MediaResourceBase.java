package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Media;
import com.chloz.test.service.MediaService;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleMediaFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.MediaDto;
import com.chloz.test.web.mapper.MediaMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class MediaResourceBase extends FilterDomainResource<Media, Long, MediaDto, SimpleMediaFilter> {

	private final MediaService service;

	private final MediaMapper mapper;
	public MediaResourceBase(MediaService service, MediaMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<MediaDto> create(@Valid MediaDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new Media cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<MediaDto> update(@Valid MediaDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		return super.update(dto, graph);
	}

	public ResponseEntity<MediaDto> updateFields(@Valid MediaDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Media> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		// set fields
		Media ent = opt.get();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		ent.setContentType(dto.getContentType());
		ent.setKey(dto.getKey());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Media model, MediaDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(Media model, MediaDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}