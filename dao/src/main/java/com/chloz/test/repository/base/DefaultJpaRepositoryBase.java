package com.chloz.test.repository.base;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import jakarta.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

/**
 * The base class for the default repository
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface DefaultJpaRepositoryBase<T, ID> {

	/**
	 * Will Remove the entity physically from the datastore
	 *
	 * @param entity
	 */
	void deletePermanently(T entity);

	/**
	 * Remove the entities permanently from the database
	 *
	 * @param entities
	 */
	void deleteAllPermanently(Iterable<? extends T> entities);

	/**
	 * Return the entity matching the given ID or {@link Optional#empty()} if none
	 * was found. The attributes fetched from the store are the one provided by the
	 * entity graph argument
	 *
	 * @param id
	 *            the entity ID.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return
	 */
	Optional<T> findOneById(ID id, EntityGraph<T> graph);

	/**
	 * Returns a single entity matching the given {@link Predicate} or
	 * {@link Optional#empty()} if none was found.
	 *
	 * @param predicate
	 *            must not be {@literal null}.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return a single entity matching the given {@link Predicate} or
	 *         {@link Optional#empty()} if none was found.
	 * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
	 *             if the predicate yields more than one result.
	 */
	Optional<T> findOne(Predicate predicate, EntityGraph<T> graph);

	/**
	 * Returns a {@link Page} of entities matching the given {@link Predicate}. In
	 * case no match could be found, an empty {@link Page} is returned.
	 *
	 * @param predicate
	 *            must not be {@literal null}.
	 * @param pageable
	 *            may be {@link Pageable#unpaged()}, must not be {@literal null}.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return a {@link Page} of entities matching the given {@link Predicate}.
	 */
	Page<T> findAll(Predicate predicate, EntityGraph<T> graph, Pageable pageable);

	/**
	 * Returns all entities matching the given {@link Predicate}. In case no match
	 * could be found an empty {@link List} is returned.
	 *
	 * @param predicate
	 *            must not be {@literal null}.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return all entities matching the given {@link Predicate}.
	 */
	List<T> findAll(Predicate predicate, EntityGraph<T> graph);

	/**
	 * Returns all entities. In case none found an empty {@link List} is returned.
	 *
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return all entities.
	 */
	List<T> findAll(EntityGraph<T> graph);

	/**
	 * Returns a {@link Page} of all entities. In case none found, an empty
	 * {@link Page} is returned.
	 *
	 * @param pageable
	 *            may be {@link Pageable#unpaged()}, must not be {@literal null}.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return a {@link Page} of entities matching the given {@link Predicate}.
	 */
	Page<T> findAll(EntityGraph<T> graph, Pageable pageable);

	/**
	 * Change the entity enable state
	 * @param ids list of ids for witch the enable
	 * @param value the state to set
	 */
	void updateEnableStatus(List<ID> ids, Boolean value);

}