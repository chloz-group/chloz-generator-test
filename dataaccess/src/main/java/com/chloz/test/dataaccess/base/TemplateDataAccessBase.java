package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Template;
import java.util.Optional;

public interface TemplateDataAccessBase extends FilterDomainDataAccess<Template, Long, SimpleTemplateFilter> {

	Optional<Template> findByCode(String code);

}