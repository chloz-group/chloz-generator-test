package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.ParamsRepository;
import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.dataaccess.base.ParamsDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.ParamsQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ParamsDataAccessImpl extends ParamsDataAccessBaseImplBase implements ParamsDataAccess {

	private final ParamsRepository repository;
	public ParamsDataAccessImpl(ParamsRepository repository, ParamsQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}