package com.chloz.test.service.base;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.repository.UserGroupRepository;
import com.chloz.test.service.filter.SimpleUserGroupFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.UserGroupQueryBuilder;
import java.util.*;

public class UserGroupServiceBaseImplBase extends FilterDomainServiceImpl<UserGroup, Long, SimpleUserGroupFilter>
		implements
			UserGroupServiceBase {

	private final UserGroupRepository repository;
	public UserGroupServiceBaseImplBase(UserGroupRepository repository, UserGroupQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<UserGroup> findById(Long id) {
		return repository.findById(id);
	}

}