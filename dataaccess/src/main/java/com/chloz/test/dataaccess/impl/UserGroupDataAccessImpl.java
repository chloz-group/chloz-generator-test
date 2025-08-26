package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.UserGroupRepository;
import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.dataaccess.base.UserGroupDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.UserGroupQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserGroupDataAccessImpl extends UserGroupDataAccessBaseImplBase implements UserGroupDataAccess {

	private final UserGroupRepository repository;
	public UserGroupDataAccessImpl(UserGroupRepository repository, UserGroupQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}