package com.chloz.test.domain.base;

import com.querydsl.core.annotations.QueryInit;
import com.chloz.test.domain.AbstractAuditingEntity;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class ParamsBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "param_key", length = 50, unique = true)
	@NotNull
	protected String paramKey;

	@Column(name = "string_value", length = 255)
	protected String stringValue;

	@Column(name = "number_value")
	protected Long numberValue;

	@Column(name = "decimal_value")
	protected BigDecimal decimalValue;

	@Column(name = "boolean_value")
	protected Boolean booleanValue;

}