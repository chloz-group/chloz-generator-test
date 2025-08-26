package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.CountryRepository;
import com.chloz.test.dataaccess.CountryDataAccess;
import com.chloz.test.dataaccess.base.CountryDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.CountryQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CountryDataAccessImpl extends CountryDataAccessBaseImplBase implements CountryDataAccess {

	private final CountryRepository repository;
	public CountryDataAccessImpl(CountryRepository repository, CountryQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}