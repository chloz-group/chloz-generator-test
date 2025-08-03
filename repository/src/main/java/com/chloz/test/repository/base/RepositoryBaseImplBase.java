package com.chloz.test.repository.base;

import com.chloz.test.domain.AbstractAuditingEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

public class RepositoryBaseImplBase<T, ID> extends SimpleJpaRepository<T, ID> {

	private final JpaEntityInformation<T, ID> entityInformation;

	private final boolean applyCustomCode;
	public RepositoryBaseImplBase(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.applyCustomCode = AbstractAuditingEntity.class.isAssignableFrom(entityInformation.getJavaType());
	}

	@Override
	@Transactional
	public void delete(T entity) {
		super.delete(entity);
		//// Deprecated code after use of Hibernate @SQLRestriction
		/*
		 * if (!applyCustomCode) { super.delete(entity); } else { if
		 * (entityInformation.isNew(entity)) { return; } AbstractAuditingEntity ent =
		 * (AbstractAuditingEntity) entity; ent.setDeleted(true); ent.setDisabled(true);
		 * super.save(entity); }
		 */
	}

}