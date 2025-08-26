package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.domain.Template;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.service.mapper.TemplateMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

public class TemplateServiceBaseImplBase
		extends
			DefaultDomainServiceImpl<Template, Long, TemplateDto, SimpleTemplateFilter>
		implements
			TemplateServiceBase {

	private final TemplateDataAccess dataAccess;

	private final TemplateMapper mapper;
	public TemplateServiceBaseImplBase(TemplateDataAccess dataAccess, TemplateMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public Optional<TemplateDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public TemplateDto update(TemplateDto dto, String graph) {
		if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<TemplateDto> bulkUpdate(List<TemplateDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public TemplateDto updateFields(TemplateDto dto, String graph) {
		Optional<Template> opt = dataAccess.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		Template ent = opt.get();
		ent.setId(dto.getId());
		ent.setCode(dto.getCode());
		ent.setContent(dto.getContent());
		ent.setTitle(dto.getTitle());
		ent.setShortContent(dto.getShortContent());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}