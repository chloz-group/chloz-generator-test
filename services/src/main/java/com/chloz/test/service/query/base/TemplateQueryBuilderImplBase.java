package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QTemplate;
import com.chloz.test.service.filter.TemplateFilter;
import com.chloz.test.service.filter.SimpleTemplateFilter;
import com.chloz.test.service.query.TemplateQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class TemplateQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleTemplateFilter, QTemplate>
		implements
			TemplateQueryBuilder {

	private final ApplicationContext applicationContext;
	public TemplateQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleTemplateFilter filter) {
		return this.createPredicate(filter, QTemplate.template);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleTemplateFilter filter, QTemplate path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getCode() != null) {
			predicates.addAll(this.buildStringPredicate(path.code, filter.getCode()));
		}
		if (filter.getContent() != null) {
			predicates.addAll(this.buildStringPredicate(path.content, filter.getContent()));
		}
		if (filter.getTitle() != null) {
			predicates.addAll(this.buildStringPredicate(path.title, filter.getTitle()));
		}
		if (filter.getShortContent() != null) {
			predicates.addAll(this.buildStringPredicate(path.shortContent, filter.getShortContent()));
		}
		return predicates;
	}

}