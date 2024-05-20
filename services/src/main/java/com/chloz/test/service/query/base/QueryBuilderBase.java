package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.chloz.test.service.filter.SimpleFilter;

public interface QueryBuilderBase<T extends SimpleFilter, Q extends EntityPathBase> {

	/**
	 * Create the predicates using the default path
	 *
	 * @param filter
	 * @return
	 */
	public Predicate createPredicate(T filter);

	/**
	 * Create the predicates using the path
	 *
	 * @param filter
	 * @param path
	 * @return
	 */
	public Predicate createPredicate(T filter, Q path);

}