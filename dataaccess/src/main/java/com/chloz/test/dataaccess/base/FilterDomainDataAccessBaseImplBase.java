package com.chloz.test.dataaccess.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.impl.DefaultDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.QueryBuilder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityGraph;
import java.util.List;

public class FilterDomainDataAccessBaseImplBase<T, I, F extends SimpleFilter> extends DefaultDomainDataAccessImpl<T, I>
		implements
			FilterDomainDataAccessBase<T, I, F> {

	private final QueryBuilder<F, ?> queryBuilder;

	private final SimpleDomainRepository<T, I> repository;

	private final Class<T> entityType;
	public FilterDomainDataAccessBaseImplBase(SimpleDomainRepository<T, I> repository,
			QueryBuilder<F, ?> queryBuilder) {
		super(repository);
		this.queryBuilder = queryBuilder;
		this.repository = repository;
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(),
				FilterDomainDataAccessBaseImplBase.class);
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