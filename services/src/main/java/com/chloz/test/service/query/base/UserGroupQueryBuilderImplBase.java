package com.chloz.test.service.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QUserGroup;
import com.chloz.test.service.filter.UserGroupFilter;
import com.chloz.test.service.filter.SimpleUserGroupFilter;
import com.chloz.test.service.query.UserGroupQueryBuilder;
import com.chloz.test.service.query.UserQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class UserGroupQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleUserGroupFilter, QUserGroup>
		implements
			UserGroupQueryBuilder {

	private final ApplicationContext applicationContext;
	public UserGroupQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleUserGroupFilter filter) {
		return this.createPredicate(filter, QUserGroup.userGroup);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleUserGroupFilter filter, QUserGroup path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter.getDescription() != null) {
			predicates.addAll(this.buildStringPredicate(path.description, filter.getDescription()));
		}
		if (filter instanceof UserGroupFilter f) {
			if (f.getUsers() != null) {
				predicates.add(applicationContext.getBean(UserQueryBuilder.class).createPredicate(f.getUsers(),
						path.users.any()));
			}
		}
		return predicates;
	}

}