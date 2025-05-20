package com.chloz.test.service;

import com.chloz.test.service.base.SimpleDomainServiceBase;

/**
 * Base service interface for all domain services
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
public interface SimpleDomainService<T, I> extends SimpleDomainServiceBase<T, I> {
}