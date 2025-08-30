package com.chloz.test.dataaccess.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.dataaccess.GraphBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public class DefaultDomainDataAccessBaseImplBase<T, I> implements DefaultDomainDataAccessBase<T, I> {

	private final DefaultDomainRepository<T, I> repository;

	private final Class<T> entityType;

	@Autowired
	private GraphBuilder entityGraphBuilder;
	protected DefaultDomainDataAccessBaseImplBase(DefaultDomainRepository<T, I> repository) {
		this.repository = repository;
		Class<?>[] types = GenericTypeResolver.resolveTypeArguments(getClass(),
				DefaultDomainDataAccessBaseImplBase.class);
		this.entityType = types == null ? null : (Class<T>) types[0];
	}

	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		return this.repository.save(entity);
	}

	@Override
	@Transactional
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		return this.repository.saveAll(entities);
	}

	@Override
	public Optional<T> findById(I id) {
		return this.repository.findById(id);
	}

	@Override
	public boolean existsById(I id) {
		return this.repository.findById(id).isPresent();
	}

	@Override
	@Transactional
	public void deleteById(I id) {
		this.repository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteAllById(Iterable<I> ids) {
		ids.forEach(this::deleteById);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		this.repository.delete(entity);
	}

	@Override
	@Transactional
	public void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(this::delete);
	}

	@Override
	public Optional<T> findById(I id, EntityGraph<T> graph) {
		return this.repository.findOneById(id, graph);
	}

	@Override
	public Optional<T> findById(I id, String graph) {
		return this.repository.findOneById(id, this.createGraph(this.entityType, graph));
	}

	@Override
	public Optional<T> findOne(Predicate predicate, EntityGraph<T> graph) {
		return this.repository.findOne(predicate, graph);
	}

	@Override
	public Page<T> findAll(Predicate predicate, EntityGraph<T> graph, Pageable pageable) {
		return this.repository.findAll(predicate, graph, pageable);
	}

	@Override
	public Page<T> findAll(Predicate predicate, String graph, Pageable pageable) {
		return this.repository.findAll(predicate, this.createGraph(this.entityType, graph), pageable);
	}

	@Override
	public Page<T> findAll(String graph, Pageable pageable) {
		return this.repository.findAll(this.createGraph(this.entityType, graph), pageable);
	}

	@Override
	public List<T> findAll(Predicate predicate, EntityGraph<T> graph) {
		return this.repository.findAll(predicate, graph);
	}

	@Override
	public List<T> findAll(Predicate predicate, String graph) {
		return this.repository.findAll(predicate, this.createGraph(this.entityType, graph));
	}

	@Override
	public List<T> findAllUsingGraph(String graph) {
		return this.repository.findAll(this.createGraph(this.entityType, graph));
	}

	@Override
	public long count(Predicate predicate) {
		return this.repository.count(predicate);
	}

	@Override
	public boolean exists(Predicate predicate) {
		return this.repository.exists(predicate);
	}

	@Override
	@Transactional
	public void updateEnableStatus(List<I> ids, Boolean value) {
		this.repository.updateEnableStatus(ids, value);
	}

	protected EntityGraph<T> createGraph(Class<T> clazz, String graph) {
		return this.entityGraphBuilder.createEntityGraph(clazz, graph);
	}

}