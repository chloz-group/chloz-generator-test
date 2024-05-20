package com.chloz.test.service.impl;

import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.service.base.SimpleDomainServiceBaseImplBase;

public abstract class SimpleDomainServiceImpl<T, ID> extends SimpleDomainServiceBaseImplBase<T, ID>
		implements
			SimpleDomainService<T, ID> {

	public SimpleDomainServiceImpl(SimpleDomainRepository<T, ID> repository) {
		super(repository);
	}

}