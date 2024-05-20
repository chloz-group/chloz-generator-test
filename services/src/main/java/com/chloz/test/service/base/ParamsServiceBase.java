package com.chloz.test.service.base;

import com.chloz.test.domain.Params;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleParamsFilter;
import java.util.*;

public interface ParamsServiceBase extends FilterDomainService<Params, Long, SimpleParamsFilter> {

	Optional<Params> findById(Long id);

}