package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.dataaccess.base.DefaultDomainDataAccessBaseImplBase;
import com.chloz.test.dataaccess.DefaultDomainDataAccess;

/**
 * Base DataAccess interface for all domain DataAccess
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
public class DefaultDomainDataAccessImpl<T, I> extends DefaultDomainDataAccessBaseImplBase<T, I>
		implements
			DefaultDomainDataAccess<T, I> {

	protected DefaultDomainDataAccessImpl(SimpleDomainRepository<T, I> repository) {
		super(repository);
	}

}