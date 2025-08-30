package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.service.TemplateService;
import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class TemplateResourceBase extends DefaultDomainResource<Long, TemplateDto, SimpleTemplateFilter> {

	private final TemplateService service;
	public TemplateResourceBase(TemplateService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<TemplateDto> create(@Valid TemplateDto dto, String graph) {
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<TemplateDto> update(@Valid TemplateDto dto, String graph) {
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<TemplateDto>> bulkUpdate(@Valid List<TemplateDto> list, String graph) {
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<TemplateDto> partialUpdate(@Valid TemplateDto dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}