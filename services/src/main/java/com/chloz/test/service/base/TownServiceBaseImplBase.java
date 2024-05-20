package com.chloz.test.service.base;

import com.chloz.test.domain.Town;
import com.chloz.test.repository.TownRepository;
import com.chloz.test.service.filter.SimpleTownFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.TownQueryBuilder;
import java.util.*;

public class TownServiceBaseImplBase extends FilterDomainServiceImpl<Town, Long, SimpleTownFilter>
		implements
			TownServiceBase {

	private final TownRepository repository;
	public TownServiceBaseImplBase(TownRepository repository, TownQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Town> findById(Long id) {
		return repository.findById(id);
	}

}