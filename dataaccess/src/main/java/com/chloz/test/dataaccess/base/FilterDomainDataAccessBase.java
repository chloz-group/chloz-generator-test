package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.DefaultDomainDataAccess;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * A data class for an entity that include complex filter using the
 * corresponding filter object of the entity.
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 */
public interface FilterDomainDataAccessBase<T, I, F extends SimpleFilter> extends DefaultDomainDataAccess<T, I> {

	public Page<T> findByFilter(F filter, Pageable pageable, String graph);

	public List<T> findByFilter(F filter, String graph);

}