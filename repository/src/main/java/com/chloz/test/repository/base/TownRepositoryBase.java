package com.chloz.test.repository.base;

import com.chloz.test.domain.Town;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TownRepositoryBase extends SimpleDomainRepository<Town, Long> {
}