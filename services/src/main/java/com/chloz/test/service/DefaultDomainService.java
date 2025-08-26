package com.chloz.test.service;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.service.base.DefaultDomainServiceBase;

/**
 * The base class for an entity to do common CRUD operations
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <D>
 *            The DTO class for the entity
 * @param <F>
 *            The filter class for the entity
 */
public interface DefaultDomainService<I, D, F extends SimpleFilter> extends DefaultDomainServiceBase<I, D, F> {
}