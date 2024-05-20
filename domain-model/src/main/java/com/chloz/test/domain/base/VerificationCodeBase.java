package com.chloz.test.domain.base;

import com.chloz.test.domain.AbstractAuditingEntity;
import com.chloz.test.domain.Connection;
import com.chloz.test.domain.User;
import com.chloz.test.domain.enums.VerificationType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "code"})
public class VerificationCodeBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	/**
	 * code
	 */
	@NotNull
	@Column(name = "code", length = 10, nullable = false)
	private String code;

	/**
	 * expiryDate
	 */
	@NotNull
	@Column(name = "expiry_date", nullable = false)
	private Instant expiryDate;

	/**
	 * codeUsed
	 */
	@NotNull
	@Column(name = "code_used", nullable = false)
	private Boolean codeUsed;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "used_for")
	private User usedFor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "connection_id")
	private Connection connection;

	/**
	 * connectionMean
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "verification_type", nullable = false, length = 15)
	private VerificationType verificationType;
	public boolean isCodeUsed() {
		return codeUsed == null ? false : codeUsed;
	}

}