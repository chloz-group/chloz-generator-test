package com.chloz.test.service.base;

import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.service.filter.SimpleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FilterDomainServiceBase<T, ID, F extends SimpleFilter> extends SimpleDomainService<T, ID> {

	public Page<T> findByFilter(F filter, Pageable pageable, String graph);

	public List<T> findByFilter(F filter, String graph);

}