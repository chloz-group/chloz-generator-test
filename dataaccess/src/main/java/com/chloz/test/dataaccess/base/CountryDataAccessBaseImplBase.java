package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Country;
import com.chloz.test.repository.CountryRepository;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.CountryQueryBuilder;
import java.util.Optional;

public class CountryDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Country, String, SimpleCountryFilter>
		implements
			CountryDataAccessBase {

	private final CountryRepository repository;

	private final CountryQueryBuilder queryBuilder;
	public CountryDataAccessBaseImplBase(CountryRepository repository, CountryQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

}