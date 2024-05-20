package com.chloz.test.repository;

import com.chloz.test.repository.base.RepositoryBaseImplBase;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import jakarta.persistence.EntityManager;

public class RepositoryBaseImpl<T, ID> extends RepositoryBaseImplBase<T, ID> {

	public RepositoryBaseImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

}