package com.chloz.test.service.base;

import com.chloz.test.domain.Params;
import com.chloz.test.repository.ParamsRepository;
import com.chloz.test.service.filter.SimpleParamsFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.ParamsQueryBuilder;
import java.util.*;

public class ParamsServiceBaseImplBase extends FilterDomainServiceImpl<Params, Long, SimpleParamsFilter>
		implements
			ParamsServiceBase {

	private final ParamsRepository repository;
	public ParamsServiceBaseImplBase(ParamsRepository repository, ParamsQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Params> findById(Long id) {
		return repository.findById(id);
	}

}