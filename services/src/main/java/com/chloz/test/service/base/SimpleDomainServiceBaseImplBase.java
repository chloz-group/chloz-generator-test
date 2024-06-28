package com.chloz.test.service.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.domain.AbstractAuditingEntity;
import com.chloz.test.repository.SimpleDomainRepository;
import com.chloz.test.service.GraphBuilder;
import com.chloz.test.service.impl.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

public abstract class SimpleDomainServiceBaseImplBase<T, ID> extends DefaultServiceImpl
		implements
			SimpleDomainServiceBase<T, ID> {

	private final SimpleDomainRepository<T, ID> repository;

	private final Class<T> entityType;

	private final Class<ID> idType;

	private final boolean logicalDeletion;

	@Autowired
	private GraphBuilder entityGraphBuilder;
	protected SimpleDomainServiceBaseImplBase(SimpleDomainRepository<T, ID> repository) {
		this.repository = repository;
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(), SimpleDomainServiceBaseImplBase.class);
		this.entityType = (Class<T>) types[0];
		this.idType = (Class<ID>) types[1];
		this.logicalDeletion = AbstractAuditingEntity.class.isAssignableFrom(entityType);
	}

	@Override
	public <S extends T> S save(S entity) {
		return this.repository.save(entity);
	}

	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		return this.repository.saveAll(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id) {
		return filterDeleted(this.repository.findById(id));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(ID id) {
		return this.findById(id).isPresent();
	}

	@Override
	public void deleteById(ID id) {
		// Deprecated code after use of Hibernate @SQLRestriction
		// this.repository.findById(id).ifPresent(t -> delete(t));
		this.repository.deleteById(id);
	}

	@Override
	public void delete(T entity) {
		this.repository.delete(entity);
		// Deprecated code after use of Hibernate @SQLRestriction
		/*
		 * if (!logicalDeletion) { this.repository.delete(entity); } else {
		 * AbstractAuditingEntity ent = (AbstractAuditingEntity) entity;
		 * ent.setDeleted(true); ent.setDisabled(true); this.repository.save(entity); }
		 */
	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(this::delete);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id, EntityGraph<T> graph) {
		return this.filterDeleted(this.repository.findOneById(id, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id, String graph) {
		return this.findById(id, this.createGraph(this.entityType, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findOne(Predicate predicate, EntityGraph<T> graph) {
		return this.filterDeleted(this.repository.findOne(predicate, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Predicate predicate, EntityGraph<T> graph, Pageable pageable) {
		return this.repository.findAll(predicate, graph, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Predicate predicate, String graph, Pageable pageable) {
		return this.findAll(predicate, this.createGraph(this.entityType, graph), pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(String graph, Pageable pageable) {
		return this.repository.findAll(this.createGraph(this.entityType, graph), pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll(Predicate predicate, EntityGraph<T> graph) {
		return this.repository.findAll(predicate, graph);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll(Predicate predicate, String graph) {
		return this.repository.findAll(predicate, this.createGraph(this.entityType, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAllUsingGraph(String graph) {
		return this.repository.findAll(this.createGraph(this.entityType, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public long count(Predicate predicate) {
		return this.repository.count(predicate);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Predicate predicate) {
		return this.repository.exists(predicate);
	}

	private Optional<T> filterDeleted(Optional<T> optional) {
		// Deprecated code after use of Hibernate @SQLRestriction
		/*
		 * Optional<T> opt = optional; if (this.logicalDeletion) { opt =
		 * optional.filter(t -> !((AbstractAuditingEntity) t).isDeleted()); }
		 */
		return optional;
	}

	protected EntityGraph<T> createGraph(Class<T> clazz, String graph) {
		return this.entityGraphBuilder.createEntityGraph(clazz, graph);
	}

}