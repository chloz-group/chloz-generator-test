package com.chloz.test.repository.base;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface TemplateRepositoryBase extends DefaultDomainRepository<Template, Long> {

	Optional<Template> findByCode(String code);

}