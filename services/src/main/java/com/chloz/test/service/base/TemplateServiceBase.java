package com.chloz.test.service.base;

import com.chloz.test.domain.Template;
import com.chloz.test.service.SimpleDomainService;
import java.util.Optional;

public interface TemplateServiceBase extends SimpleDomainService<Template, Long> {

	Optional<Template> findByCode(String templateCode);

}