package com.chloz.test.service.impl;

import com.chloz.test.repository.MediaRepository;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.base.MediaServiceBaseImplBase;
import com.chloz.test.service.query.MediaQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MediaServiceImpl extends MediaServiceBaseImplBase implements MediaService {

	private final MediaRepository repository;
	public MediaServiceImpl(MediaRepository repository, MediaQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

}