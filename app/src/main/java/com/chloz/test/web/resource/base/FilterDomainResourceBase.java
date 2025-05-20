package com.chloz.test.web.resource.base;

import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.SimpleDomainResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import java.util.List;

/**
 * @param <T> The entity class
 * @param <I> The class of the entity id field
 * @param <F> The Filter class for the entity
 * @param <D> The DTO class for the entity
 */
public class FilterDomainResourceBase<T, I, D, F extends SimpleFilter> extends SimpleDomainResource<T, I, D> {

	private final FilterDomainService<T, I, F> service;

	private final DomainMapper<T, D> mapper;
	public <S extends FilterDomainService<T, I, F>, M extends DomainMapper<T, D>> FilterDomainResourceBase(S service,
																										   M mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	public PagedModel<D> getPageByFilter(F filter, Pageable pageable, String graph) {
		Page<T> page = this.service.findByFilter(filter, pageable, graph);
		List<D> dtoList = page.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return new PagedModel<>(new PageImpl<>(dtoList, pageable, page.getTotalElements()));
	}

	public List<D> getAllByFilter(F filter, String graph) {
		List<T> list = this.service.findByFilter(filter, graph);
		return list.stream().map(t -> mapper.mapToDto(t, graph)).toList();
	}

}