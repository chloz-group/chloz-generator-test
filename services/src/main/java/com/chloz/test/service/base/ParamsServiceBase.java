package com.chloz.test.service.base;

import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface ParamsServiceBase extends DefaultDomainService<Long, ParamsDto, SimpleParamsFilter> {

	Optional<ParamsDto> findById(Long id);

	ParamsDto updateFields(ParamsDto dto, String graph);

}