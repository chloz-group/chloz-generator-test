package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Town;
import com.chloz.test.repository.TownRepository;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.TownQueryBuilder;
import java.util.Optional;

public class TownDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Town, Long, SimpleTownFilter>
		implements
			TownDataAccessBase {

	private final TownRepository repository;

	private final TownQueryBuilder queryBuilder;
	public TownDataAccessBaseImplBase(TownRepository repository, TownQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

}