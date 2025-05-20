package com.chloz.test.service;

import com.chloz.test.service.base.FilterDomainServiceBase;
import com.chloz.test.service.filter.SimpleFilter;

/**
 * Base service interface for all domain services that include a filter
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 */
public interface FilterDomainService<T, I, F extends SimpleFilter> extends FilterDomainServiceBase<T, I, F> {
}