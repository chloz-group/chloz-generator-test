package com.chloz.test.service.query;

import com.chloz.test.domain.QUser;
import com.chloz.test.service.filter.SimpleUserFilter;

public interface UserQueryBuilder extends QueryBuilder<SimpleUserFilter, QUser> {
}