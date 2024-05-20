package com.chloz.test.service.base;

import com.chloz.test.domain.Media;
import com.chloz.test.repository.MediaRepository;
import com.chloz.test.service.filter.SimpleMediaFilter;
import com.chloz.test.service.impl.FilterDomainServiceImpl;
import com.chloz.test.service.query.MediaQueryBuilder;
import java.util.*;

public class MediaServiceBaseImplBase extends FilterDomainServiceImpl<Media, Long, SimpleMediaFilter>
		implements
			MediaServiceBase {

	private final MediaRepository repository;
	public MediaServiceBaseImplBase(MediaRepository repository, MediaQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
	}

	@Override
	public Optional<Media> findById(Long id) {
		return repository.findById(id);
	}

}