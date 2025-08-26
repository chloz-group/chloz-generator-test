package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Params;
import java.util.Optional;

public interface ParamsDataAccessBase extends FilterDomainDataAccess<Params, Long, SimpleParamsFilter> {

	Optional<Params> findByParamKey(String paramKey);

}