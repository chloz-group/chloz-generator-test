package com.chloz.test.repository.base;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface TemplateRepositoryBase extends SimpleDomainRepository<Template, Long> {

	public Optional<Template> findByCode(String code);

}