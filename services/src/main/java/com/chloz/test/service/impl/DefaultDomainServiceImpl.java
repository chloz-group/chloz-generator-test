package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.DefaultDomainDataAccess;
import com.chloz.test.service.base.DefaultDomainServiceBaseImplBase;
import com.chloz.test.service.DefaultDomainService;
import com.chloz.test.service.mapper.DomainMapper;

public abstract class DefaultDomainServiceImpl<T, I, D, F extends SimpleFilter>
		extends
			DefaultDomainServiceBaseImplBase<T, I, D, F>
		implements
			DefaultDomainService<I, D, F> {

	public DefaultDomainServiceImpl(DefaultDomainDataAccess<T, I> dataAccess, DomainMapper<T, D> domainMapper) {
		super(dataAccess, domainMapper);
	}

}