package com.chloz.test.web.resource;

import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.base.SimpleDomainResourceBase;

/**
 *
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <D>
 *            The DTO class for the entity
 */
public class SimpleDomainResource<T, I, D> extends SimpleDomainResourceBase<T, I, D> {

	public <S extends SimpleDomainService<T, I>, M extends DomainMapper<T, D>> SimpleDomainResource(S service,
			M mapper) {
		super(service, mapper);
	}

}