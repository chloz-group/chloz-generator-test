package com.chloz.test.service.impl;

import com.chloz.test.repository.CountryRepository;
import com.chloz.test.service.CountryService;
import com.chloz.test.service.base.CountryServiceBaseImplBase;
import com.chloz.test.service.query.CountryQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryServiceImpl extends CountryServiceBaseImplBase implements CountryService {

	private final CountryRepository repository;
	public CountryServiceImpl(CountryRepository repository, CountryQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}