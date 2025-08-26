package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.service.ParamsService;
import com.chloz.test.service.base.ParamsServiceBaseImplBase;
import com.chloz.test.service.mapper.ParamsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParamsServiceImpl extends ParamsServiceBaseImplBase implements ParamsService {

	private final ParamsDataAccess dataAccess;
	public ParamsServiceImpl(ParamsDataAccess dataAccess, ParamsMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}