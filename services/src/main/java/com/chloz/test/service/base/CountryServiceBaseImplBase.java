package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.domain.Country;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.service.mapper.CountryMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

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
	public Optional<CountryDto> findById(String code) {
		return dataAccess.findById(code).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public CountryDto update(CountryDto dto, String graph) {
		if (dto.getCode() == null || dataAccess.findById(dto.getCode()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<CountryDto> bulkUpdate(List<CountryDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getCode() == null || dataAccess.findById(dto.getCode()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public CountryDto updateFields(CountryDto dto, String graph) {
		Optional<Country> opt = dataAccess.findById(dto.getCode());
		if (dto.getCode() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		Country ent = opt.get();
		ent.setCode(dto.getCode());
		ent.setName(dto.getName());
		ent.setCallingCode(dto.getCallingCode());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}