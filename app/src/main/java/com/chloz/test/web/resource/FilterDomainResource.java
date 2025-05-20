package com.chloz.test.web.resource;

import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.base.FilterDomainResourceBase;

/**
 *
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 * @param <D>
 *            The DTO class for the entity
 */
public class FilterDomainResource<T, I, D, F extends SimpleFilter> extends FilterDomainResourceBase<T, I, D, F> {

	public <S extends FilterDomainService<T, I, F>, M extends DomainMapper<T, D>> FilterDomainResource(S service,
			M mapper) {
		super(service, mapper);
	}

}