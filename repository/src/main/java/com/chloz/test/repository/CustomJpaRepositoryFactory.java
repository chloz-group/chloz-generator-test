package com.chloz.test.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFragment;
import jakarta.persistence.EntityManager;

public class CustomJpaRepositoryFactory extends JpaRepositoryFactory {

	private final EntityManager entityManager;
	public CustomJpaRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	protected RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
		RepositoryFragments fragments = super.getRepositoryFragments(metadata);
		if (DefaultJpaRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
			JpaEntityInformation<?, Object> entityInformation = this.getEntityInformation(metadata.getDomainType());
			fragments = fragments.append(RepositoryFragment.implemented(DefaultJpaRepository.class,
					new DefaultJpaRepositoryImpl(entityInformation, entityManager)));
		}
		return fragments;
	}

}