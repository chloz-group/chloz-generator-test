package com.chloz.test.repository;

import com.chloz.test.repository.base.DefaultJpaRepositoryBaseImplBase;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

@Transactional
public class DefaultJpaRepositoryImpl<T, ID> extends DefaultJpaRepositoryBaseImplBase<T, ID>
		implements
			DefaultJpaRepository<T, ID> {

	public DefaultJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
	}

}