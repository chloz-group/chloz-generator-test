package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Params;
import com.chloz.test.repository.ParamsRepository;
import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.ParamsQueryBuilder;
import java.util.Optional;

public class ParamsDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Params, Long, SimpleParamsFilter>
		implements
			ParamsDataAccessBase {

	private final ParamsRepository repository;

	private final ParamsQueryBuilder queryBuilder;
	public ParamsDataAccessBaseImplBase(ParamsRepository repository, ParamsQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Optional<Params> findByParamKey(String paramKey) {
		return this.repository.findByParamKey(paramKey);
	};

}