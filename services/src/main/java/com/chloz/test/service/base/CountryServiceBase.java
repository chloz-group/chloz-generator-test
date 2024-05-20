package com.chloz.test.service.base;

import com.chloz.test.domain.Country;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleCountryFilter;
import java.util.*;

public interface CountryServiceBase extends FilterDomainService<Country, String, SimpleCountryFilter> {

	Optional<Country> findById(String code);

}