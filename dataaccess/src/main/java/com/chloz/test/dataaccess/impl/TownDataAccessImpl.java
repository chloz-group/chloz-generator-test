package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.TownRepository;
import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.dataaccess.base.TownDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.TownQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TownDataAccessImpl extends TownDataAccessBaseImplBase implements TownDataAccess {

	private final TownRepository repository;
	public TownDataAccessImpl(TownRepository repository, TownQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}