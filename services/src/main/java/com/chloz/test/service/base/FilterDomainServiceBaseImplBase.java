package com.chloz.test.service.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.impl.SimpleDomainServiceImpl;
import com.chloz.test.service.query.QueryBuilder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityGraph;
import java.util.List;

public class FilterDomainServiceBaseImplBase<T, I, F extends SimpleFilter> extends SimpleDomainServiceImpl<T, I>
		implements
			FilterDomainServiceBase<T, I, F> {

	private final QueryBuilder<F, ?> queryBuilder;

	private final SimpleDomainRepository<T, I> repository;

	private final Class<T> entityType;
	public FilterDomainServiceBaseImplBase(SimpleDomainRepository<T, I> repository, QueryBuilder<F, ?> queryBuilder) {
		super(repository);
		this.queryBuilder = queryBuilder;
		this.repository = repository;
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(), FilterDomainServiceBaseImplBase.class);
		this.entityType = types == null ? null : (Class<T>) types[0];
	}

	@Override
	public Page<T> findByFilter(F filter, Pageable pageable, String graph) {
		Predicate predicate = queryBuilder.createPredicate(filter);
		EntityGraph<T> entityGraph = this.createGraph(entityType, graph);
		return this.repository.findAll(predicate, entityGraph, pageable);
	}

	@Override
	public List<T> findByFilter(F filter, String graph) {
		Predicate predicate = queryBuilder.createPredicate(filter);
		EntityGraph<T> entityGraph = this.createGraph(entityType, graph);
		return this.repository.findAll(predicate, entityGraph);
	}

}