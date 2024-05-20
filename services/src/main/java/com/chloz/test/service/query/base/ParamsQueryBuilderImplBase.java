package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QParams;
import com.chloz.test.service.filter.ParamsFilter;
import com.chloz.test.service.filter.SimpleParamsFilter;
import com.chloz.test.service.query.ParamsQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class ParamsQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleParamsFilter, QParams>
		implements
			ParamsQueryBuilder {

	private final ApplicationContext applicationContext;
	public ParamsQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleParamsFilter filter) {
		return this.createPredicate(filter, QParams.params);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleParamsFilter filter, QParams path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getParamKey() != null) {
			predicates.addAll(this.buildStringPredicate(path.paramKey, filter.getParamKey()));
		}
		if (filter.getStringValue() != null) {
			predicates.addAll(this.buildStringPredicate(path.stringValue, filter.getStringValue()));
		}
		if (filter.getNumberValue() != null) {
			predicates.addAll(this.buildLongPredicate(path.numberValue, filter.getNumberValue()));
		}
		if (filter.getDecimalValue() != null) {
			predicates.addAll(this.buildBigDecimalPredicate(path.decimalValue, filter.getDecimalValue()));
		}
		if (filter.getBooleanValue() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.booleanValue, filter.getBooleanValue()));
		}
		if (filter instanceof ParamsFilter) {
			ParamsFilter f = (ParamsFilter) filter;
		}
		return predicates;
	}

}