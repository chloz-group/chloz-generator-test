package com.chloz.test.dataaccess.query.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.QUser;
import com.chloz.test.dataaccess.filter.UserFilter;
import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.dataaccess.query.UserQueryBuilder;
import com.chloz.test.dataaccess.query.MediaQueryBuilder;
import com.chloz.test.dataaccess.query.UserGroupQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class UserQueryBuilderImplBase extends QueryBuilderBaseImplBase<SimpleUserFilter, QUser>
		implements
			UserQueryBuilder {

	private final ApplicationContext applicationContext;
	public UserQueryBuilderImplBase(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(SimpleUserFilter filter) {
		return this.createPredicate(filter, QUser.user);
	}

	@Override
	protected List<Predicate> createFilterFieldPredicates(SimpleUserFilter filter, QUser path) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(this.createAbstractEntityPredicates(path._super._super, filter));
		if (filter.getId() != null) {
			predicates.addAll(this.buildLongPredicate(path.id, filter.getId()));
		}
		if (filter.getLogin() != null) {
			predicates.addAll(this.buildStringPredicate(path.login, filter.getLogin()));
		}
		if (filter.getEmail() != null) {
			predicates.addAll(this.buildStringPredicate(path.email, filter.getEmail()));
		}
		if (filter.getPhone() != null) {
			predicates.addAll(this.buildStringPredicate(path.phone, filter.getPhone()));
		}
		if (filter.getPhoneChecked() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.phoneChecked, filter.getPhoneChecked()));
		}
		if (filter.getAccountLocked() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.accountLocked, filter.getAccountLocked()));
		}
		if (filter.getEmailChecked() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.emailChecked, filter.getEmailChecked()));
		}
		if (filter.getActivated() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.activated, filter.getActivated()));
		}
		if (filter.getAttempts() != null) {
			predicates.addAll(this.buildIntegerPredicate(path.attempts, filter.getAttempts()));
		}
		if (filter.getFirstName() != null) {
			predicates.addAll(this.buildStringPredicate(path.firstName, filter.getFirstName()));
		}
		if (filter.getName() != null) {
			predicates.addAll(this.buildStringPredicate(path.name, filter.getName()));
		}
		if (filter.getLang() != null) {
			predicates.addAll(this.buildStringPredicate(path.lang, filter.getLang()));
		}
		if (filter instanceof UserFilter f) {
			if (f.getPicture() != null) {
				predicates.add(applicationContext.getBean(MediaQueryBuilder.class).createPredicate(f.getPicture(),
						path.picture));
			}
			if (f.getGroups() != null) {
				predicates.add(applicationContext.getBean(UserGroupQueryBuilder.class).createPredicate(f.getGroups(),
						path.groups.any()));
			}
		}
		return predicates;
	}

}