package com.chloz.test.dataaccess.query;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.query.base.QueryBuilderBase;

public interface QueryBuilder<T extends SimpleFilter, Q extends EntityPathBase> extends QueryBuilderBase<T, Q> {
}