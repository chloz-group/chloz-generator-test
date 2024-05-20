package com.chloz.test.service.base;

import com.chloz.test.domain.Media;
import com.chloz.test.service.FilterDomainService;
import com.chloz.test.service.filter.SimpleMediaFilter;
import java.util.*;

public interface MediaServiceBase extends FilterDomainService<Media, Long, SimpleMediaFilter> {

	Optional<Media> findById(Long id);

}