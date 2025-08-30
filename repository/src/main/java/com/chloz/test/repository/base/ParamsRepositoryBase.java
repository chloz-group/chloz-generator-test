package com.chloz.test.repository.base;

import com.chloz.test.domain.Params;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface ParamsRepositoryBase extends DefaultDomainRepository<Params, Long> {

	Optional<Params> findByParamKey(String paramKey);

}