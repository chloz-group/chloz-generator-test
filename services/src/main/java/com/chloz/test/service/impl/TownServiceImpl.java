package com.chloz.test.service.impl;

import com.chloz.test.repository.TownRepository;
import com.chloz.test.service.TownService;
import com.chloz.test.service.base.TownServiceBaseImplBase;
import com.chloz.test.service.query.TownQueryBuilder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TownServiceImpl extends TownServiceBaseImplBase implements TownService {

	private final TownRepository repository;
	public TownServiceImpl(TownRepository repository, TownQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}