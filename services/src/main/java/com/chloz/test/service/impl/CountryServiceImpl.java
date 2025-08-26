package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.service.CountryService;
import com.chloz.test.service.base.CountryServiceBaseImplBase;
import com.chloz.test.service.mapper.CountryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryServiceImpl extends CountryServiceBaseImplBase implements CountryService {

	private final CountryDataAccess dataAccess;
	public CountryServiceImpl(CountryDataAccess dataAccess, CountryMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}