package com.chloz.test.service.impl;

import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.base.FilterDomainServiceBaseImplBase;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.query.QueryBuilder;

public class FilterDomainServiceImpl<T, ID, F extends SimpleFilter> extends FilterDomainServiceBaseImplBase<T, ID, F>
		implements
			FilterDomainService<T, ID, F> {

	public FilterDomainServiceImpl(SimpleDomainRepository<T, ID> repository, QueryBuilder<F, ?> queryBuilder) {
		super(repository, queryBuilder);
	}

}