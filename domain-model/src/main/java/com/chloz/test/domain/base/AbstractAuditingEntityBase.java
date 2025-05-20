package com.chloz.test.domain.base;

import com.chloz.test.domain.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Base abstract class for entities which will hold definitions for created,
 * last modified, created by, last modified by attributes.
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAuditingEntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedBy
	@JoinColumn(name = "CREATED_BY")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@Builder.Default
	private OffsetDateTime createdDate = OffsetDateTime.now();

	@LastModifiedBy
	@JoinColumn(name = "LAST_MODIFIED_BY")
	@ManyToOne(fetch = FetchType.LAZY)
	private User lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@Builder.Default
	private OffsetDateTime lastModifiedDate = OffsetDateTime.now();

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Builder.Default
	private Boolean deleted = false;

	@Builder.Default
	private Boolean disabled = false;
	/**
	 * @deprecated (Deprecated code after use of Hibernate @SQLRestriction)
	 */
	@Deprecated(forRemoval = true)
	public boolean isDeleted() {
		return deleted == null ? false : deleted;
	}

	public boolean isDisabled() {
		return disabled == null ? false : disabled;
	}

	public boolean isEnable() {
		return !this.isDisabled();
	}

	@PrePersist
	protected void prePersit() {
		if (this.disabled == null) {
			this.disabled = false;
		}
	}

}