package com.chloz.test.web.resource;

import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.base.SimpleDomainResourceBase;

public class SimpleDomainResource<T, ID, DTO> extends SimpleDomainResourceBase<T, ID, DTO> {

	public <S extends SimpleDomainService<T, ID>, M extends DomainMapper<T, DTO>> SimpleDomainResource(S service,
			M mapper) {
		super(service, mapper);
	}

}