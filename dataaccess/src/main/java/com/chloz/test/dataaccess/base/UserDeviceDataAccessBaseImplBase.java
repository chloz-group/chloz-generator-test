package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.UserDeviceRepository;
import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.UserDeviceQueryBuilder;
import java.util.Optional;

public class UserDeviceDataAccessBaseImplBase
		extends
			FilterDomainDataAccessImpl<UserDevice, Long, SimpleUserDeviceFilter>
		implements
			UserDeviceDataAccessBase {

	private final UserDeviceRepository repository;

	private final UserDeviceQueryBuilder queryBuilder;
	public UserDeviceDataAccessBaseImplBase(UserDeviceRepository repository, UserDeviceQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Optional<UserDevice> findByToken(String token) {
		return this.repository.findByToken(token);
	};

}