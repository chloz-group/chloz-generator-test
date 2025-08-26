package com.chloz.test.dataaccess;

import com.chloz.test.dataaccess.base.DefaultDomainDataAccessBase;

/**
 * Base DataAccess interface for all domain DataAccess
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
public interface DefaultDomainDataAccess<T, I> extends DefaultDomainDataAccessBase<T, I> {
}