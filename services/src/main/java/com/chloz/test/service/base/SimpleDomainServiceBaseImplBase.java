package com.chloz.test.service.base;

import com.querydsl.core.types.Predicate;
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

public abstract class SimpleDomainServiceBaseImplBase<T, I> extends DefaultServiceImpl
		implements
			SimpleDomainServiceBase<T, I> {

	private final SimpleDomainRepository<T, I> repository;

	private final Class<T> entityType;

	@Autowired
	private GraphBuilder entityGraphBuilder;
	protected SimpleDomainServiceBaseImplBase(SimpleDomainRepository<T, I> repository) {
		this.repository = repository;
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(), SimpleDomainServiceBaseImplBase.class);
		this.entityType = types == null ? null : (Class<T>) types[0];
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
	public Optional<T> findById(I id) {
		return this.repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(I id) {
		return this.repository.findById(id).isPresent();
	}

	@Override
	public void deleteById(I id) {
		this.repository.deleteById(id);
	}

	@Override
	public void deleteAllById(Iterable<I> ids) {
		ids.forEach(this::deleteById);
	}

	@Override
	public void delete(T entity) {
		this.repository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(this::delete);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(I id, EntityGraph<T> graph) {
		return this.repository.findOneById(id, graph);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(I id, String graph) {
		return this.repository.findOneById(id, this.createGraph(this.entityType, graph));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findOne(Predicate predicate, EntityGraph<T> graph) {
		return this.repository.findOne(predicate, graph);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Predicate predicate, EntityGraph<T> graph, Pageable pageable) {
		return this.repository.findAll(predicate, graph, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Predicate predicate, String graph, Pageable pageable) {
		return this.repository.findAll(predicate, this.createGraph(this.entityType, graph), pageable);
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

	@Override
	public void updateEnableStatus(List<I> ids, Boolean value) {
		this.repository.updateEnableStatus(ids, value);
	}

	protected EntityGraph<T> createGraph(Class<T> clazz, String graph) {
		return this.entityGraphBuilder.createEntityGraph(clazz, graph);
	}

}