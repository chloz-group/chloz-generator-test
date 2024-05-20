package com.chloz.test.service.base;

import com.chloz.test.domain.Country;
import com.chloz.test.repository.CountryRepository;
import com.chloz.test.service.filter.SimpleCountryFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.CountryQueryBuilder;
import java.util.*;

public class CountryServiceBaseImplBase extends FilterDomainServiceImpl<Country, String, SimpleCountryFilter>
		implements
			CountryServiceBase {

	private final CountryRepository repository;
	public CountryServiceBaseImplBase(CountryRepository repository, CountryQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Country> findById(String code) {
		return repository.findById(code);
	}

}