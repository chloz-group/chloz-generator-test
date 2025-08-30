package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.domain.Template;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.service.mapper.TemplateMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
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
	@Transactional(readOnly = true)
	public Optional<TemplateDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected Long getIdFromDto(TemplateDto dto) {
		return dto.getId();
	}

}