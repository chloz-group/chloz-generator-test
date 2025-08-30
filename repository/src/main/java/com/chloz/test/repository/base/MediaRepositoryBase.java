package com.chloz.test.repository.base;

import com.chloz.test.domain.Media;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface MediaRepositoryBase extends DefaultDomainRepository<Media, Long> {
}