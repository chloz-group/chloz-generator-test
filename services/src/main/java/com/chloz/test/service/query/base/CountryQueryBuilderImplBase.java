package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QCountry;
import com.chloz.test.service.filter.CountryFilter;
import com.chloz.test.service.filter.SimpleCountryFilter;
import com.chloz.test.service.query.CountryQueryBuilder;
import com.chloz.test.service.query.TownQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class CountryQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleCountryFilter, QCountry>
		implements
			CountryQueryBuilder {

	private final ApplicationContext applicationContext;
	public CountryQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleCountryFilter filter) {
		return this.createPredicate(filter, QCountry.country);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleCountryFilter filter, QCountry path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getCode() != null) {
			predicates.addAll(this.buildStringPredicate(path.code, filter.getCode()));
		}
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter.getCallingCode() != null) {
			predicates.addAll(this.buildStringPredicate(path.callingCode, filter.getCallingCode()));
		}
		if (filter instanceof CountryFilter f) {
			if (f.getTowns() != null) {
				predicates.add(applicationContext.getBean(TownQueryBuilder.class).createPredicate(f.getTowns(),
						path.towns.any()));
			}
		}
		return predicates;
	}

}