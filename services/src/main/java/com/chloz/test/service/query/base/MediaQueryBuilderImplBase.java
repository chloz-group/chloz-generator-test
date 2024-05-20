package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QMedia;
import com.chloz.test.service.filter.MediaFilter;
import com.chloz.test.service.filter.SimpleMediaFilter;
import com.chloz.test.service.query.MediaQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class MediaQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleMediaFilter, QMedia>
		implements
			MediaQueryBuilder {

	private final ApplicationContext applicationContext;
	public MediaQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleMediaFilter filter) {
		return this.createPredicate(filter, QMedia.media);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleMediaFilter filter, QMedia path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter.getPath() != null) {
			predicates.addAll(this.buildStringPredicate(path.path, filter.getPath()));
		}
		if (filter.getContentType() != null) {
			predicates.addAll(this.buildStringPredicate(path.contentType, filter.getContentType()));
		}
		if (filter.getKey() != null) {
			predicates.addAll(this.buildStringPredicate(path.key, filter.getKey()));
		}
		if (filter instanceof MediaFilter) {
			MediaFilter f = (MediaFilter) filter;
		}
		return predicates;
	}

}