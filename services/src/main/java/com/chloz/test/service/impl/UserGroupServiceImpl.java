package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.UserGroupDataAccess;
import com.chloz.test.service.UserGroupService;
import com.chloz.test.service.base.UserGroupServiceBaseImplBase;
import com.chloz.test.service.mapper.UserGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserGroupServiceImpl extends UserGroupServiceBaseImplBase implements UserGroupService {

	private final UserGroupDataAccess dataAccess;
	public UserGroupServiceImpl(UserGroupDataAccess dataAccess, UserGroupMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}