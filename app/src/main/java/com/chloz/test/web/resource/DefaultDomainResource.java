package com.chloz.test.web.resource;

import com.chloz.test.service.DefaultDomainService;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.web.resource.base.DefaultDomainResourceBase;

/**
 *
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 * @param <D>
 *            The DTO class for the entity
 */
public class DefaultDomainResource<I, D, F extends SimpleFilter> extends DefaultDomainResourceBase<I, D, F> {

	public <S extends DefaultDomainService<I, D, F>> DefaultDomainResource(S service) {
		super(service);
	}

}