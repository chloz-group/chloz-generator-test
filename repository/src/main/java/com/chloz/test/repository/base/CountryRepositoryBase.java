package com.chloz.test.repository.base;

import com.chloz.test.domain.Country;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface CountryRepositoryBase extends DefaultDomainRepository<Country, String> {
}