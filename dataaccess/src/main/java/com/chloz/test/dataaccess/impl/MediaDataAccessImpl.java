package com.chloz.test.dataaccess.impl;

import com.chloz.test.repository.MediaRepository;
import com.chloz.test.dataaccess.MediaDataAccess;
import com.chloz.test.dataaccess.base.MediaDataAccessBaseImplBase;
import com.chloz.test.dataaccess.query.MediaQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MediaDataAccessImpl extends MediaDataAccessBaseImplBase implements MediaDataAccess {

	private final MediaRepository repository;
	public MediaDataAccessImpl(MediaRepository repository, MediaQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}