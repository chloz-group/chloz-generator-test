package com.chloz.test.service.query;

import com.chloz.test.domain.QCountry;
import com.chloz.test.service.filter.SimpleCountryFilter;

public interface CountryQueryBuilder extends QueryBuilder<SimpleCountryFilter, QCountry> {
}