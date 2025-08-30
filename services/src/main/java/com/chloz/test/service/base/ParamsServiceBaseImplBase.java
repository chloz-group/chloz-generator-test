package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.domain.Params;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.service.mapper.ParamsMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class ParamsServiceBaseImplBase extends DefaultDomainServiceImpl<Params, Long, ParamsDto, SimpleParamsFilter>
		implements
			ParamsServiceBase {

	private final ParamsDataAccess dataAccess;

	private final ParamsMapper mapper;
	public ParamsServiceBaseImplBase(ParamsDataAccess dataAccess, ParamsMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ParamsDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected Long getIdFromDto(ParamsDto dto) {
		return dto.getId();
	}

}