package com.chloz.test.service.impl;

import com.chloz.test.repository.ParamsRepository;
import com.chloz.test.service.ParamsService;
import com.chloz.test.service.base.ParamsServiceBaseImplBase;
import com.chloz.test.service.query.ParamsQueryBuilder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ParamsServiceImpl extends ParamsServiceBaseImplBase implements ParamsService {

	private final ParamsRepository repository;
	public ParamsServiceImpl(ParamsRepository repository, ParamsQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}