package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Media;
import java.util.Optional;

public interface MediaDataAccessBase extends FilterDomainDataAccess<Media, Long, SimpleMediaFilter> {
}