package com.chloz.test.service.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * A Service class for an entity to do common CRUD operations
 * 
 * @param <I>
 *            The class of the entity id field
 * @param <D>
 *            The DTO class for the entity
 * @param <F>
 *            The filter class for the entity
 */
public interface DefaultDomainServiceBase<I, D, F extends SimpleFilter> {

	/**
	 * Saves a given object.
	 *
	 * @param dto
	 *            must not be {@literal null}.
	 * @param graph
	 *            The graph of fields in the returned dto.
	 *
	 * @return the saved dto with the field specify in the graph param; will never
	 *         be {@literal null}.
	 */
	public D create(D dto, String graph);

	/**
	 * Saves a list of objects.
	 *
	 * @param list
	 *            The list of object, must not be {@literal null}.
	 * @param graph
	 *            The graph of fields in the returned dto.
	 *
	 * @return the saved dto list with the field specify in the graph param.
	 */
	public List<D> bulkCreate(List<D> list, String graph);

	/**
	 * Update a given object.
	 *
	 * @param dto
	 *            must not be {@literal null}.
	 * @param graph
	 *            The graph of fields in the returned dto.
	 *
	 * @return the updated dto with the field specify in the graph param; will never
	 *         be {@literal null}.
	 */
	public D update(D dto, String graph);

	/**
	 * Partial Update a given object with, only setting not null properties.
	 *
	 * @param dto
	 *            must not be {@literal null}.
	 * @param graph
	 *            The graph of fields in the returned dto.
	 *
	 * @return the updated dto with the field specify in the graph param;
	 */
	public D partialUpdate(D dto, String graph);

	/**
	 * update a list of objects.
	 *
	 * @param list
	 *            The list of object, must not be {@literal null}.
	 * @param graph
	 *            The graph of fields in the returned dto.
	 *
	 * @return the saved dto list with the field specify in the graph param.
	 */
	public List<D> bulkUpdate(List<D> list, String graph);

	public Page<D> getAllPaginate(Pageable pageable, String graph);

	public D getById(I id, String graph);

	public void updateEnableStatus(List<I> ids, Boolean value);

	public void deleteById(I id);

	public void deleteAllById(List<I> ids);

	public Page<D> getPageByFilter(F filter, Pageable pageable, String graph);

	public List<D> getAllByFilter(F filter, String graph);

}