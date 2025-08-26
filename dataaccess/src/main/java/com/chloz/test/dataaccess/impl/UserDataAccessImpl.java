package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.UserRepository;
import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.dataaccess.base.UserDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.UserQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDataAccessImpl extends UserDataAccessBaseImplBase implements UserDataAccess {

	private final UserRepository repository;
	public UserDataAccessImpl(UserRepository repository, UserQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}