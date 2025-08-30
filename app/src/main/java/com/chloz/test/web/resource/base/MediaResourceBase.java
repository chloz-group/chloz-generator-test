package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class MediaResourceBase extends DefaultDomainResource<Long, MediaDto, SimpleMediaFilter> {

	private final MediaService service;
	public MediaResourceBase(MediaService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<MediaDto> create(@Valid MediaDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<MediaDto> update(@Valid MediaDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<MediaDto>> bulkUpdate(@Valid List<MediaDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<MediaDto> partialUpdate(@Valid MediaDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}