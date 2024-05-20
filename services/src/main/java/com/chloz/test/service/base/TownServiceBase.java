package com.chloz.test.service.base;

import com.chloz.test.domain.Town;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleTownFilter;
import java.util.*;

public interface TownServiceBase extends FilterDomainService<Town, Long, SimpleTownFilter> {

	Optional<Town> findById(Long id);

}