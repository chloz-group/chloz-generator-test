package com.chloz.test.repository.base;

import com.chloz.test.domain.Media;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MediaRepositoryBase extends SimpleDomainRepository<Media, Long> {
}