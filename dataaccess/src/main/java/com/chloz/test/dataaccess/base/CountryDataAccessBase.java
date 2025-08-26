package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Country;
import java.util.Optional;

public interface CountryDataAccessBase extends FilterDomainDataAccess<Country, String, SimpleCountryFilter> {
}