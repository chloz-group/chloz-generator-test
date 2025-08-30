package com.chloz.test.service.base;

import com.chloz.test.service.dto.TownDto;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.service.DefaultDomainService;
import java.util.*;

public interface TownServiceBase extends DefaultDomainService<Long, TownDto, SimpleTownFilter> {

	Optional<TownDto> findById(Long id);

}