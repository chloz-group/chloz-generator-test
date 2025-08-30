package com.chloz.test.repository;

import com.chloz.test.repository.base.ExtendedRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * The repository fragment where we define customs repository methods
 * 
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 */
@NoRepositoryBean
public interface ExtendedRepository<T, ID> extends ExtendedRepositoryBase<T, ID> {
}