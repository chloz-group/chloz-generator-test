package com.chloz.test.service.base;

import com.chloz.test.domain.Role;
import com.chloz.test.repository.RoleRepository;
import com.chloz.test.service.filter.SimpleRoleFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.RoleQueryBuilder;
import java.util.*;

public class RoleServiceBaseImplBase extends FilterDomainServiceImpl<Role, String, SimpleRoleFilter>
		implements
			RoleServiceBase {

	private final RoleRepository repository;
	public RoleServiceBaseImplBase(RoleRepository repository, RoleQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Role> findById(String name) {
		return repository.findById(name);
	}

}