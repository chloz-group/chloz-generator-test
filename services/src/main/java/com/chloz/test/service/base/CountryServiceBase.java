package com.chloz.test.service.base;

import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface CountryServiceBase extends DefaultDomainService<String, CountryDto, SimpleCountryFilter> {

	Optional<CountryDto> findById(String code);

	CountryDto updateFields(CountryDto dto, String graph);

}