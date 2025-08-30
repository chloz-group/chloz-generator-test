package com.chloz.test.repository;

import com.chloz.test.repository.base.ExtendedRepositoryBaseImplBase;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

@Transactional(readOnly = true)
@Repository
public class ExtendedRepositoryImpl<T, ID> extends ExtendedRepositoryBaseImplBase<T, ID>
		implements
			ExtendedRepository<T, ID> {

	public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
	}

}