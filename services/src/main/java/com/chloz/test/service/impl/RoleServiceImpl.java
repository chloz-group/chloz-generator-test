package com.chloz.test.service.impl;

import com.chloz.test.repository.RoleRepository;
import com.chloz.test.service.RoleService;
import com.chloz.test.service.base.RoleServiceBaseImplBase;
import com.chloz.test.service.query.RoleQueryBuilder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends RoleServiceBaseImplBase implements RoleService {

	private final RoleRepository repository;
	public RoleServiceImpl(RoleRepository repository, RoleQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}