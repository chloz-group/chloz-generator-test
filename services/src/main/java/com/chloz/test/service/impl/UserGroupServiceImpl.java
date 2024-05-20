package com.chloz.test.service.impl;

import com.chloz.test.repository.UserGroupRepository;
import com.chloz.test.service.UserGroupService;
import com.chloz.test.service.base.UserGroupServiceBaseImplBase;
import com.chloz.test.service.query.UserGroupQueryBuilder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserGroupServiceImpl extends UserGroupServiceBaseImplBase implements UserGroupService {

	private final UserGroupRepository repository;
	public UserGroupServiceImpl(UserGroupRepository repository, UserGroupQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}