package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.RoleRepository;
import com.chloz.test.dataaccess.RoleDataAccess;
import com.chloz.test.dataaccess.base.RoleDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.RoleQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RoleDataAccessImpl extends RoleDataAccessBaseImplBase implements RoleDataAccess {

	private final RoleRepository repository;
	public RoleDataAccessImpl(RoleRepository repository, RoleQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}