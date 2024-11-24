package com.chloz.test.web.resource.base;

import com.chloz.test.domain.Template;
import com.chloz.test.service.TemplateService;
import com.chloz.test.web.exception.BadRequestException;
import com.chloz.test.service.filter.SimpleTemplateFilter;
import com.chloz.test.web.resource.FilterDomainResource;
import com.chloz.test.web.dto.TemplateDto;
import com.chloz.test.web.mapper.TemplateMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

public class TemplateResourceBase extends FilterDomainResource<Template, Long, TemplateDto, SimpleTemplateFilter> {

	private final TemplateService service;

	private final TemplateMapper mapper;
	public TemplateResourceBase(TemplateService service, TemplateMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<TemplateDto> create(@Valid TemplateDto dto, String graph) {
		if (dto.getId() != null) {
			throw new BadRequestException("A new Template cannot already have the id field");
		}
		return super.create(dto, graph);
	}

	@Override
	public ResponseEntity<TemplateDto> update(@Valid TemplateDto dto, String graph) {
		if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		return super.update(dto, graph);
	}

	@Override
	public ResponseEntity<List<TemplateDto>> bulkUpdate(@Valid List<TemplateDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || service.findById(dto.getId()).isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
			}
		});
		return super.bulkUpdate(list, graph);
	}

	public ResponseEntity<TemplateDto> updateFields(@Valid TemplateDto dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		Optional<Template> opt = service.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found");
		}
		// set fields
		Template ent = opt.get();
		ent.setId(dto.getId());
		ent.setCode(dto.getCode());
		ent.setContent(dto.getContent());
		ent.setTitle(dto.getTitle());
		ent.setShortContent(dto.getShortContent());
		// end of set fields
		this.handleModelBeforeUpdate(ent, dto);
		ent = this.service.save(ent);
		this.handleModelAfterUpdate(ent, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(ent, graph));
	}

	@Override
	protected void handleModelBeforeCreate(Template model, TemplateDto dto) {
		super.handleModelBeforeCreate(model, dto);
	}

	@Override
	protected void handleModelBeforeUpdate(Template model, TemplateDto dto) {
		super.handleModelBeforeUpdate(model, dto);
	}

}