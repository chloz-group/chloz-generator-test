package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.RoleDataAccess;
import com.chloz.test.service.RoleService;
import com.chloz.test.service.base.RoleServiceBaseImplBase;
import com.chloz.test.service.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends RoleServiceBaseImplBase implements RoleService {

	private final RoleDataAccess dataAccess;
	public RoleServiceImpl(RoleDataAccess dataAccess, RoleMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}