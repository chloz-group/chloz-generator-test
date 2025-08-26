package com.chloz.test.dataaccess.query;

import com.chloz.test.domain.QCountry;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;

public interface CountryQueryBuilder extends QueryBuilder<SimpleCountryFilter, QCountry> {
}