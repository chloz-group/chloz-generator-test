package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.Town;
import java.util.Optional;

public interface TownDataAccessBase extends FilterDomainDataAccess<Town, Long, SimpleTownFilter> {
}