package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.service.TownService;
import com.chloz.test.service.base.TownServiceBaseImplBase;
import com.chloz.test.service.mapper.TownMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TownServiceImpl extends TownServiceBaseImplBase implements TownService {

	private final TownDataAccess dataAccess;
	public TownServiceImpl(TownDataAccess dataAccess, TownMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}