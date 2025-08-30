package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.domain.Town;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.service.mapper.TownMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class TownServiceBaseImplBase extends DefaultDomainServiceImpl<Town, Long, TownDto, SimpleTownFilter>
		implements
			TownServiceBase {

	private final TownDataAccess dataAccess;

	private final TownMapper mapper;
	public TownServiceBaseImplBase(TownDataAccess dataAccess, TownMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TownDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected Long getIdFromDto(TownDto dto) {
		return dto.getId();
	}

}