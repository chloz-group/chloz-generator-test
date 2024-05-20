package com.chloz.test.service;

import com.chloz.test.service.base.CountryServiceBase;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CountryService extends CountryServiceBase {
}