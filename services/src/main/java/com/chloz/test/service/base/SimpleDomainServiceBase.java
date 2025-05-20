package com.chloz.test.service.base;

import com.querydsl.core.types.Predicate;
import com.chloz.test.service.DefaultService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

/**
 * A Service class for an entity to do common CRUD operations
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
public interface SimpleDomainServiceBase<T, I> extends DefaultService {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 *
	 * @param entity
	 *            must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given {@literal entity} is {@literal null}.
	 * @throws OptimisticLockingFailureException
	 *             when the entity uses optimistic locking and has a version
	 *             attribute with a different value from that found in the
	 *             persistence store. Also thrown if the entity is assumed to be
	 *             present but does not exist in the database.
	 */
	<S extends T> S save(S entity);

	/**
	 * Saves all given entities.
	 *
	 * @param entities
	 *            must not be {@literal null} nor must it contain {@literal null}.
	 * @return the saved entities; will never be {@literal null}. The returned
	 *         {@literal Iterable} will have the same size as the
	 *         {@literal Iterable} passed as an argument.
	 * @throws IllegalArgumentException
	 *             in case the given {@link Iterable entities} or one of its
	 *             entities is {@literal null}.
	 * @throws OptimisticLockingFailureException
	 *             when at least one entity uses optimistic locking and has a
	 *             version attribute with a different value from that found in the
	 *             persistence store. Also thrown if at least one entity is assumed
	 *             to be present but does not exist in the database.
	 */
	<S extends T> Iterable<S> saveAll(Iterable<S> entities);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none
	 *         found.
	 * @throws IllegalArgumentException
	 *             if {@literal id} is {@literal null}.
	 */
	Optional<T> findById(I id);

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists,
	 *         {@literal false} otherwise.
	 * @throws IllegalArgumentException
	 *             if {@literal id} is {@literal null}.
	 */
	boolean existsById(I id);

	/**
	 * Deletes the entity with the given id.
	 * <p>
	 * If the entity is not found in the persistence store it is silently ignored.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given {@literal id} is {@literal null}
	 */
	void deleteById(I id);

	/**
	 * Deletes entities with the given list of id.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 */
	void deleteAllById(Iterable<I> ids);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity
	 *            must not be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given entity is {@literal null}.
	 * @throws OptimisticLockingFailureException
	 *             when the entity uses optimistic locking and has a version
	 *             attribute with a different value from that found in the
	 *             persistence store. Also thrown if the entity is assumed to be
	 *             present but does not exist in the database.
	 */
	void delete(T entity);

	/**
	 * Remove the entities permanently from the database
	 *
	 * @param entities
	 */
	void deleteAll(Iterable<? extends T> entities);

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
	Optional<T> findById(I id, EntityGraph<T> graph);

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
	Optional<T> findById(I id, String graph);

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

	@Transactional(readOnly = true)
	Page<T> findAll(Predicate predicate, String graph, Pageable pageable);

	@Transactional(readOnly = true)
	Page<T> findAll(String graph, Pageable pageable);

	/**
	 * Returns all entities matching the given {@link Predicate}. In case no match
	 * could be found an empty {@link Iterable} is returned.
	 *
	 * @param predicate
	 *            must not be {@literal null}.
	 * @param graph
	 *            the entityGraph for returned result.
	 * @return all entities matching the given {@link Predicate}.
	 */
	List<T> findAll(Predicate predicate, EntityGraph<T> graph);

	@Transactional(readOnly = true)
	List<T> findAll(Predicate predicate, String graph);

	@Transactional(readOnly = true)
	List<T> findAllUsingGraph(String graph);

	/**
	 * Returns the number of instances matching the given {@link Predicate}.
	 *
	 * @param predicate
	 *            the {@link Predicate} to count instances for, must not be
	 *            {@literal null}.
	 * @return the number of instances matching the {@link Predicate}.
	 */
	long count(Predicate predicate);

	/**
	 * Checks whether the data store contains elements that match the given
	 * {@link Predicate}.
	 *
	 * @param predicate
	 *            the {@link Predicate} to use for the existence check, must not be
	 *            {@literal null}.
	 * @return {@literal true} if the data store contains elements that match the
	 *         given {@link Predicate}.
	 */
	boolean exists(Predicate predicate);

	/**
	 * Update the status of the entity to either enable it or disable it
	 */
	void updateEnableStatus(@NotNull List<I> ids, @NotNull Boolean value);

}