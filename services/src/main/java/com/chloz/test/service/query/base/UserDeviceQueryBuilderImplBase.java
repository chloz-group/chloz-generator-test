package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QUserDevice;
import com.chloz.test.service.filter.UserDeviceFilter;
import com.chloz.test.service.filter.SimpleUserDeviceFilter;
import com.chloz.test.service.query.UserDeviceQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class UserDeviceQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleUserDeviceFilter, QUserDevice>
		implements
			UserDeviceQueryBuilder {

	private final ApplicationContext applicationContext;
	public UserDeviceQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleUserDeviceFilter filter) {
		return this.createPredicate(filter, QUserDevice.userDevice);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleUserDeviceFilter filter, QUserDevice path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getToken() != null) {
			predicates.addAll(this.buildStringPredicate(path.token, filter.getToken()));
		}
		return predicates;
	}

}