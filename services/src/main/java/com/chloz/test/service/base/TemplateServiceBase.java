package com.chloz.test.service.base;

import com.chloz.test.service.dto.TemplateDto;
import com.chloz.test.dataaccess.filter.SimpleTemplateFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface TemplateServiceBase extends DefaultDomainService<Long, TemplateDto, SimpleTemplateFilter> {

	Optional<TemplateDto> findById(Long id);

	TemplateDto updateFields(TemplateDto dto, String graph);

}