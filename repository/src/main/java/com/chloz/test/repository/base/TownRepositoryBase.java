package com.chloz.test.repository.base;

import com.chloz.test.domain.Town;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface TownRepositoryBase extends DefaultDomainRepository<Town, Long> {
}