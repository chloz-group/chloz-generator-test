package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.UserDeviceRepository;
import com.chloz.test.dataaccess.UserDeviceDataAccess;
import com.chloz.test.dataaccess.base.UserDeviceDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.UserDeviceQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDeviceDataAccessImpl extends UserDeviceDataAccessBaseImplBase implements UserDeviceDataAccess {

	private final UserDeviceRepository repository;
	public UserDeviceDataAccessImpl(UserDeviceRepository repository, UserDeviceQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}