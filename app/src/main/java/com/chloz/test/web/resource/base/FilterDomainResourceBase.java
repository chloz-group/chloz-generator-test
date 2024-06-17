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
 * @param <T>
 *            the domain class
 * @param <F>
 *            the filter class for the domain
 */
public class FilterDomainResourceBase<T, ID, DTO, F extends SimpleFilter> extends SimpleDomainResource<T, ID, DTO> {

	private final FilterDomainService<T, ID, F> service;

	private final DomainMapper<T, DTO> mapper;
	public <S extends FilterDomainService<T, ID, F>, M extends DomainMapper<T, DTO>> FilterDomainResourceBase(S service,
			M mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	public PagedModel<DTO> getPageByFilter(F filter, Pageable pageable, String graph) {
		Page<T> page = this.service.findByFilter(filter, pageable, graph);
		List<DTO> dtoList = page.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return new PagedModel<>(new PageImpl<>(dtoList, pageable, page.getTotalElements()));
	}

	public List<DTO> getAllByFilter(F filter, String graph) {
		List<T> list = this.service.findByFilter(filter, graph);
		List<DTO> dtoList = list.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return dtoList;
	}

}