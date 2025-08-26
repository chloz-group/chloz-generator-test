package com.chloz.test.dataaccess.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QRole;
import com.chloz.test.dataaccess.filter.RoleFilter;
import com.chloz.test.dataaccess.filter.SimpleRoleFilter;
import com.chloz.test.dataaccess.query.RoleQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class RoleQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleRoleFilter, QRole>
		implements
			RoleQueryBuilder {

	private final ApplicationContext applicationContext;
	public RoleQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleRoleFilter filter) {
		return this.createPredicate(filter, QRole.role);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleRoleFilter filter, QRole path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter.getDescription() != null) {
			predicates.addAll(this.buildStringPredicate(path.description, filter.getDescription()));
		}
		return predicates;
	}

}