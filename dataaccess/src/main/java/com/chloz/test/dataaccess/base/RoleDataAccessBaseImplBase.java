package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Role;
import com.chloz.test.repository.RoleRepository;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.RoleQueryBuilder;
import java.util.Optional;

public class RoleDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Role, String, SimpleRoleFilter>
		implements
			RoleDataAccessBase {

	private final RoleRepository repository;

	private final RoleQueryBuilder queryBuilder;
	public RoleDataAccessBaseImplBase(RoleRepository repository, RoleQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Optional<Role> findByName(String name) {
		return this.repository.findByName(name);
	};

}