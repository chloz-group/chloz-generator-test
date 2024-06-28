package com.chloz.test.service.query.base;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import com.chloz.test.domain.QAbstractAuditingEntity;
import com.chloz.test.service.filter.FilterOperator;
import com.chloz.test.service.filter.IAdvancedFilter;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.filter.common.*;
import com.chloz.test.service.query.UserQueryBuilder;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;

public abstract class QueryBuilderBaseImplBase<T extends SimpleFilter, Q extends EntityPathBase>
		implements
			QueryBuilderBase<T, Q> {

	private final ApplicationContext applicationContext;
	public QueryBuilderBaseImplBase(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Predicate createPredicate(T filter, Q path) {
		List<Predicate> predicateList = new ArrayList<>();
		List<Predicate> fieldPredicates = createFilterFieldPredicates(filter, path);
		if (fieldPredicates != null && !fieldPredicates.isEmpty()) {
			predicateList.addAll(fieldPredicates);
		}
		Predicate predicate;
		if (filter.getOperator() == FilterOperator.or) {
			predicate = ExpressionUtils.anyOf(predicateList);
		} else {
			predicate = ExpressionUtils.allOf(predicateList);
		}
		if (filter instanceof IAdvancedFilter) {
			IAdvancedFilter<T> f = (IAdvancedFilter<T>) filter;
			if (f.getOr() != null && !f.getOr().isEmpty()) {
				for (T c : f.getOr()) {
					Predicate p = this.createPredicate(c, path);
					predicate = ExpressionUtils.or(predicate, p);
				}
			}
			if (f.getAnd() != null && !f.getAnd().isEmpty()) {
				for (T c : f.getAnd()) {
					Predicate p = this.createPredicate(c, path);
					predicate = ExpressionUtils.and(predicate, p);
				}
			}
		}
		return predicate;
	}

	/**
	 * Method that create predicates for filter field that are provided. The
	 * implementation should ignore the filter inherited from the
	 * {@link SimpleFilter} class and only focus on the filter defined in the
	 * inherited class
	 *
	 * @param filter
	 * @param path
	 * @return The predicates on fields
	 */
	protected abstract List<Predicate> createFilterFieldPredicates(T filter, Q path);

	/**
	 * Build predicate for a booleans
	 *
	 * @param expression
	 * @param filter
	 * @return
	 */
	protected List<Predicate> buildBooleanPredicate(BooleanPath expression, BooleanFilter filter) {
		return this.buildPredicate(expression, filter);
	}

	/**
	 * Build predicate for a enums
	 *
	 * @param expression
	 * @param filter
	 * @return
	 */
	protected <T extends Enum<T>> List<Predicate> buildEnumPredicate(EnumPath<T> expression, EnumFilter<T> filter) {
		return this.buildPredicate(expression, filter);
	}

	protected <A extends Number & Comparable<?>> List<Predicate> buildLongPredicate(NumberPath<A> expression,
			NumberFilter<A> filter) {
		return this.buildNumberPredicate(expression, filter);
	}

	protected <A extends Number & Comparable<?>> List<Predicate> buildIntegerPredicate(NumberPath<A> expression,
			NumberFilter<A> filter) {
		return this.buildNumberPredicate(expression, filter);
	}

	protected <A extends Number & Comparable<?>> List<Predicate> buildBigDecimalPredicate(NumberPath<A> expression,
			NumberFilter<A> filter) {
		return this.buildNumberPredicate(expression, filter);
	}

	protected <A extends Number & Comparable<?>> List<Predicate> buildShortPredicate(NumberPath<A> expression,
			NumberFilter<A> filter) {
		return this.buildNumberPredicate(expression, filter);
	}

	/**
	 * Build predicate for a numbers
	 *
	 * @param expression
	 * @param filter
	 * @return
	 */
	protected <A extends Number & Comparable<?>> List<Predicate> buildNumberPredicate(NumberPath<A> expression,
			NumberFilter<A> filter) {
		List<Predicate> list = this.buildPredicate(expression, filter);
		if (filter.getGoe() != null) {
			list.add(expression.goe(filter.getGoe()));
		}
		if (filter.getGt() != null) {
			list.add(expression.gt(filter.getGt()));
		}
		if (filter.getLoe() != null) {
			list.add(expression.loe(filter.getLoe()));
		}
		if (filter.getLt() != null) {
			list.add(expression.lt(filter.getLt()));
		}
		return list;
	}

	/**
	 * Build predicate for a numbers
	 *
	 * @param expression
	 * @param filter
	 * @return
	 */
	protected <A extends Comparable> List<Predicate> buildDateAndTimePredicate(DateTimePath<A> expression,
			DateAndTimeFilter<A> filter) {
		List<Predicate> list = this.buildPredicate(expression, filter);
		if (filter.getGoe() != null) {
			list.add(expression.goe(filter.getGoe()));
		}
		if (filter.getGt() != null) {
			list.add(expression.gt(filter.getGt()));
		}
		if (filter.getLoe() != null) {
			list.add(expression.loe(filter.getLoe()));
		}
		if (filter.getLt() != null) {
			list.add(expression.lt(filter.getLt()));
		}
		return list;
	}

	protected <A extends Comparable> List<Predicate> buildDatePredicate(DatePath<A> expression,
			DateAndTimeFilter<A> filter) {
		List<Predicate> list = this.buildPredicate(expression, filter);
		if (filter.getGoe() != null) {
			list.add(expression.goe(filter.getGoe()));
		}
		if (filter.getGt() != null) {
			list.add(expression.gt(filter.getGt()));
		}
		if (filter.getLoe() != null) {
			list.add(expression.loe(filter.getLoe()));
		}
		if (filter.getLt() != null) {
			list.add(expression.lt(filter.getLt()));
		}
		return list;
	}

	protected List<Predicate> buildStringPredicate(StringPath expression, StringFilter filter) {
		List<Predicate> list = this.buildPredicate(expression, filter);
		boolean ignoreCase = filter.getIgnoreCase() == null ? false : filter.getIgnoreCase();
		if (filter.getContains() != null) {
			if (ignoreCase)
				list.add(expression.containsIgnoreCase(filter.getContains()));
			else
				list.add(expression.contains(filter.getContains()));
		}
		if (filter.getNotContains() != null) {
			if (ignoreCase)
				list.add(expression.containsIgnoreCase(filter.getNotContains()).not());
			else
				list.add(expression.contains(filter.getNotContains()).not());
		}
		if (filter.getStartsWith() != null) {
			if (ignoreCase)
				list.add(expression.startsWithIgnoreCase(filter.getStartsWith()).not());
			else
				list.add(expression.startsWith(filter.getStartsWith()).not());
		}
		if (filter.getEndsWith() != null) {
			if (ignoreCase)
				list.add(expression.endsWithIgnoreCase(filter.getEndsWith()).not());
			else
				list.add(expression.endsWith(filter.getEndsWith()).not());
		}
		return list;
	}

	/**
	 * Build predicate for a filter
	 *
	 * @param expression
	 * @param filter
	 * @return
	 */
	private List<Predicate> buildPredicate(SimpleExpression expression, Filter filter) {
		List<Predicate> list = new ArrayList<>();
		if (filter.getIsNotNull() != null && filter.getIsNotNull()) {
			list.add(expression.isNotNull());
		}
		if (filter.getIsNull() != null && filter.getIsNull()) {
			list.add(expression.isNull());
		}
		if (filter.getEq() != null) {
			list.add(expression.eq(filter.getEq()));
		}
		if (filter.getNeq() != null) {
			list.add(expression.ne(filter.getNeq()));
		}
		if (filter.getIn() != null && !filter.getIn().isEmpty()) {
			list.add(expression.in(filter.getIn()));
		}
		if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
			list.add(expression.notIn(filter.getNotIn()));
		}
		return list;
	}

	// Todo : Creer tout les predicates (pour les Duration, Instant, OffsetDate,
	// LocalDate, UUID, ...,
	protected List<Predicate> createAbstractEntityPredicates(QAbstractAuditingEntity path, T filter) {
		List<Predicate> predicates = new ArrayList<>();
		// Deprecated code after use of Hibernate @SQLRestriction
		// predicates.add(path.deleted.isNull().or(path.deleted.isFalse()));
		if (filter.getDisabled() != null) {
			predicates.addAll(this.buildBooleanPredicate(path.disabled, filter.getDisabled()));
		}
		if (filter.getCreatedDate() != null) {
			predicates.addAll(this.buildDateAndTimePredicate(path.createdDate, filter.getCreatedDate()));
		}
		if (filter.getLastModifiedDate() != null) {
			predicates.addAll(this.buildDateAndTimePredicate(path.lastModifiedDate, filter.getLastModifiedDate()));
		}
		if (filter instanceof IAdvancedFilter) {
			IAdvancedFilter<T> f = (IAdvancedFilter<T>) filter;
			UserQueryBuilder userQueryBuilder = applicationContext.getBean(UserQueryBuilder.class);
			if (f.getCreatedBy() != null)
				predicates.add(userQueryBuilder.createPredicate(f.getCreatedBy(), path.createdBy));
			if (f.getLastModifiedBy() != null)
				predicates.add(userQueryBuilder.createPredicate(f.getLastModifiedBy(), path.lastModifiedBy));
		}
		return predicates;
	}

}