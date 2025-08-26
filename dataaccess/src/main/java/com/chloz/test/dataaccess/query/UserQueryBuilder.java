package com.chloz.test.dataaccess.query;

import com.chloz.test.domain.QUser;
import com.chloz.test.dataaccess.filter.SimpleUserFilter;

public interface UserQueryBuilder extends QueryBuilder<SimpleUserFilter, QUser> {
}