package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.repository.UserGroupRepository;
import com.chloz.test.dataaccess.filter.SimpleUserGroupFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.UserGroupQueryBuilder;
import java.util.Optional;

public class UserGroupDataAccessBaseImplBase extends FilterDomainDataAccessImpl<UserGroup, Long, SimpleUserGroupFilter>
		implements
			UserGroupDataAccessBase {

	private final UserGroupRepository repository;

	private final UserGroupQueryBuilder queryBuilder;
	public UserGroupDataAccessBaseImplBase(UserGroupRepository repository, UserGroupQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

}