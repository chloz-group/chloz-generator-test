package com.chloz.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SimpleDomainRepository<T, ID>
		extends
			JpaRepository<T, ID>,
			QuerydslPredicateExecutor<T>,
			DefaultJpaRepository<T, ID> {
}