package com.chloz.test.dataaccess;

import com.chloz.test.dataaccess.base.FilterDomainDataAccessBase;
import com.chloz.test.dataaccess.filter.SimpleFilter;

/**
 * Base data access interface for all domain DataAcess that include a filter
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 */
public interface FilterDomainDataAccess<T, I, F extends SimpleFilter> extends FilterDomainDataAccessBase<T, I, F> {
}