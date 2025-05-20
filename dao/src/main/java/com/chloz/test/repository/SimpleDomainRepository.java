package com.chloz.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * A common repositoty class for an entity to do common CRUD operations
 * @param <T> The entity class
 * @param <I> The class of the entity id field
 */
@NoRepositoryBean
public interface SimpleDomainRepository<T, I>
		extends
			JpaRepository<T, I>,
			QuerydslPredicateExecutor<T>,
			DefaultJpaRepository<T, I> {
}