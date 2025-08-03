package com.chloz.test.repository.base;

import com.chloz.test.domain.Country;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CountryRepositoryBase extends SimpleDomainRepository<Country, String> {
}