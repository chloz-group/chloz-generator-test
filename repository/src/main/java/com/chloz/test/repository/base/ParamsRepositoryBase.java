package com.chloz.test.repository.base;

import com.chloz.test.domain.Params;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ParamsRepositoryBase extends SimpleDomainRepository<Params, Long> {
}