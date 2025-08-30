package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.domain.Country;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.service.mapper.CountryMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class CountryServiceBaseImplBase
		extends
			DefaultDomainServiceImpl<Country, String, CountryDto, SimpleCountryFilter>
		implements
			CountryServiceBase {

	private final CountryDataAccess dataAccess;

	private final CountryMapper mapper;
	public CountryServiceBaseImplBase(CountryDataAccess dataAccess, CountryMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CountryDto> findById(String code) {
		return dataAccess.findById(code).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	protected String getIdFromDto(CountryDto dto) {
		return dto.getCode();
	}

}