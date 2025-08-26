package com.chloz.test.dataaccess.impl;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.query.QueryBuilder;
import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.dataaccess.base.FilterDomainDataAccessBaseImplBase;
import com.chloz.test.dataaccess.FilterDomainDataAccess;

/**
 * Base DataAccess interface for all domain DataAccess
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
public abstract class FilterDomainDataAccessImpl<T, I, F extends SimpleFilter>
		extends
			FilterDomainDataAccessBaseImplBase<T, I, F>
		implements
			FilterDomainDataAccess<T, I, F> {

	protected FilterDomainDataAccessImpl(SimpleDomainRepository<T, I> repository, QueryBuilder<F, ?> queryBuilder) {
		super(repository, queryBuilder);
	}

}