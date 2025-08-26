package com.chloz.test.service.impl;

import com.chloz.test.dataaccess.MediaDataAccess;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.base.MediaServiceBaseImplBase;
import com.chloz.test.service.mapper.MediaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MediaServiceImpl extends MediaServiceBaseImplBase implements MediaService {

	private final MediaDataAccess dataAccess;
	public MediaServiceImpl(MediaDataAccess dataAccess, MediaMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
	}

}