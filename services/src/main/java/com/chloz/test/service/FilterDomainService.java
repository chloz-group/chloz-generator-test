package com.chloz.test.service;

import com.chloz.test.service.base.FilterDomainServiceBase;
import com.chloz.test.service.filter.SimpleFilter;

/**
 * Base service interface for all domain services that include a filter
 */
public interface FilterDomainService<T, ID, F extends SimpleFilter> extends FilterDomainServiceBase<T, ID, F> {
}