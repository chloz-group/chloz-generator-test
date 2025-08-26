package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.Media;
import com.chloz.test.repository.MediaRepository;
import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.MediaQueryBuilder;
import java.util.Optional;

public class MediaDataAccessBaseImplBase extends FilterDomainDataAccessImpl<Media, Long, SimpleMediaFilter>
		implements
			MediaDataAccessBase {

	private final MediaRepository repository;

	private final MediaQueryBuilder queryBuilder;
	public MediaDataAccessBaseImplBase(MediaRepository repository, MediaQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

}