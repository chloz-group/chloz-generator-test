package com.chloz.test.service.base;

import com.chloz.test.domain.Template;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleTemplateFilter;
import java.util.Optional;

public interface TemplateServiceBase extends FilterDomainService<Template, Long, SimpleTemplateFilter> {

	Optional<Template> findByCode(String templateCode);

}