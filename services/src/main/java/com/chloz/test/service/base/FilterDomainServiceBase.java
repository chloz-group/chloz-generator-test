package com.chloz.test.service.base;

import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.service.filter.SimpleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * A service class for an entity that include complex filter using the
 * corresponding filter object of the entity.
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 */
public interface FilterDomainServiceBase<T, I, F extends SimpleFilter> extends SimpleDomainService<T, I> {

	public Page<T> findByFilter(F filter, Pageable pageable, String graph);

	public List<T> findByFilter(F filter, String graph);

}