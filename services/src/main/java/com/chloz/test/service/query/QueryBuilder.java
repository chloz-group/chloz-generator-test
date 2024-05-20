package com.chloz.test.service.query;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.query.base.QueryBuilderBase;

public interface QueryBuilder<T extends SimpleFilter, Q extends EntityPathBase> extends QueryBuilderBase<T, Q> {
}