package com.chloz.test.service.impl;

import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.service.base.SimpleDomainServiceBaseImplBase;

public abstract class SimpleDomainServiceImpl<T, I> extends SimpleDomainServiceBaseImplBase<T, I>
		implements
			SimpleDomainService<T, I> {

	protected SimpleDomainServiceImpl(SimpleDomainRepository<T, I> repository) {
		super(repository);
	}

}