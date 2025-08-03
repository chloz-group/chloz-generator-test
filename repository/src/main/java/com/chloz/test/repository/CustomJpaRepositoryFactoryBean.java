package com.chloz.test.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import jakarta.persistence.EntityManager;

public class CustomJpaRepositoryFactoryBean<R extends Repository<T, I>, T, I>
		extends
			JpaRepositoryFactoryBean<R, T, I> {

	/**
	 * Creates a new {@link JpaRepositoryFactoryBean} for the given repository
	 * interface.
	 *
	 * @param repositoryInterface
	 *            must not be {@literal null}.
	 */
	public CustomJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CustomJpaRepositoryFactory(entityManager);
	}

}