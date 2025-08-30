package com.chloz.test.repository.base;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import jakarta.persistence.Query;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

/**
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
@Transactional(readOnly = true)
@Repository
public class ExtendedRepositoryBaseImplBase<T, I> extends SimpleJpaRepository<T, I>
		implements
			ExtendedRepositoryBase<T, I> {

	private static final String ENTITY_GRAPH_TYPE = "jakarta.persistence.fetchgraph";

	private final EntityManager em;

	private final Querydsl querydsl;

	private final EntityPath<T> path;

	private final JpaEntityInformation<T, ?> entityInformation;
	public ExtendedRepositoryBaseImplBase(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
		this.entityInformation = entityInformation;
		this.path = SimpleEntityPathResolver.INSTANCE.createPath(entityInformation.getJavaType());
		this.querydsl = new Querydsl(em, new PathBuilder<T>(path.getType(), path.getMetadata()));
	}

	@Override
	@Transactional
	public void hardDeleteById(I id) {
		SingularAttribute<? super T, ?> idAttr = entityInformation.getIdAttribute();
		if (idAttr != null) {
			String idFieldName = idAttr.getName();
			Query query = em.createQuery(
					"DELETE FROM " + entityInformation.getEntityName() + " ent WHERE ent." + idFieldName + " = :id");
			query.setParameter("id", id);
			// Execute the update and check if it succeeded
			query.executeUpdate();
		} else {
			em.remove(em.find(path.getType(), id));
		}
	}

	@Override
	public Optional<T> findOneById(I id, EntityGraph<T> graph) {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ENTITY_GRAPH_TYPE, graph);
		T t = em.find(path.getType(), id, properties);
		return Optional.ofNullable(t);
	}

	@Override
	public Optional<T> findOne(Predicate predicate, EntityGraph<T> graph) {
		try {
			return Optional.ofNullable(createQuery(graph, predicate).select(path).limit(2).fetchOne());
		} catch (NonUniqueResultException ex) {
			throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
		}
	}

	@Override
	public Page<T> findAll(Predicate predicate, EntityGraph<T> graph, Pageable pageable) {
		Assert.notNull(pageable, "Pageable must not be null");
		final JPQLQuery<?> countQuery = createCountQuery(predicate);
		JPQLQuery<T> query = querydsl.applyPagination(pageable, createQuery(graph, predicate).select(path));
		return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
	}

	@Override
	public List<T> findAll(Predicate predicate, EntityGraph<T> graph) {
		return createQuery(graph, predicate).select(path).fetch();
	}

	@Override
	public List<T> findAll(EntityGraph<T> graph) {
		return this.findAll(null, graph);
	}

	@Override
	public Page<T> findAll(EntityGraph<T> graph, Pageable pageable) {
		return this.findAll(null, graph, pageable);
	}

	/**
	 * Creates a new {@link JPQLQuery} for the given {@link Predicate}.
	 *
	 * @param predicate
	 * @return the Querydsl {@link JPQLQuery}.
	 */
	protected AbstractJPAQuery<?, ?> createQuery(EntityGraph<T> graph, Predicate... predicate) {
		AbstractJPAQuery<?, ?> query = null;
		if (graph != null) {
			query = doCreateQuery(QueryHints.of(ENTITY_GRAPH_TYPE, graph), predicate);
		} else {
			query = doCreateQuery(QueryHints.of(), predicate);
		}
		return query;
	}

	/**
	 * Creates a new {@link JPQLQuery} count query for the given {@link Predicate}.
	 *
	 * @param predicate,
	 *            can be {@literal null}.
	 * @return the Querydsl count {@link JPQLQuery}.
	 */
	protected JPQLQuery<?> createCountQuery(@Nullable Predicate... predicate) {
		return doCreateQuery(QueryHints.of(), predicate);
	}

	private AbstractJPAQuery<?, ?> doCreateQuery(QueryHints hints, @Nullable Predicate... predicate) {
		AbstractJPAQuery<?, ?> query = querydsl.createQuery(path);
		if (predicate != null) {
			query = query.where(predicate);
		}
		hints.forEach(query::setHint);
		return query;
	}

	@Override
	@Transactional
	public void updateEnableStatus(List<I> ids, Boolean value) {
		SingularAttribute<? super T, ?> idAttr = entityInformation.getIdAttribute();
		if (idAttr != null) {
			String idFieldName = idAttr.getName();
			Query query = em.createQuery("UPDATE " + entityInformation.getEntityName()
					+ " ent SET ent.disabled = :disabled WHERE ent." + idFieldName + " IN :id");
			query.setParameter("disabled", !value);
			query.setParameter("id", ids);
			// Execute the update and check if it succeeded
			query.executeUpdate();
		}
	}

}