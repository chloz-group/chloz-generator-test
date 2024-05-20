package com.chloz.test.web.resource;

import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.base.FilterDomainResourceBase;

public class FilterDomainResource<T, ID, DTO, F extends SimpleFilter> extends FilterDomainResourceBase<T, ID, DTO, F> {

	public <S extends FilterDomainService<T, ID, F>, M extends DomainMapper<T, DTO>> FilterDomainResource(S service,
			M mapper) {
		super(service, mapper);
	}

}