package com.chloz.test.repository;

import com.chloz.test.repository.base.DefaultJpaRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultJpaRepository<T, ID> extends DefaultJpaRepositoryBase<T, ID> {
}