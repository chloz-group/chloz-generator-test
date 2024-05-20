package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QTown;
import com.chloz.test.service.filter.TownFilter;
import com.chloz.test.service.filter.SimpleTownFilter;
import com.chloz.test.service.query.TownQueryBuilder;
import com.chloz.test.service.query.CountryQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class TownQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleTownFilter, QTown>
		implements
			TownQueryBuilder {

	private final ApplicationContext applicationContext;
	public TownQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleTownFilter filter) {
		return this.createPredicate(filter, QTown.town);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleTownFilter filter, QTown path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter instanceof TownFilter) {
			TownFilter f = (TownFilter) filter;
			if (f.getCountry() != null) {
				predicates.add(applicationContext.getBean(CountryQueryBuilder.class).createPredicate(f.getCountry(),
						path.country));
			}
		}
		return predicates;
	}

}