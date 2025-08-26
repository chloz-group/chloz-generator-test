package com.chloz.test.domain.base;

import com.querydsl.core.annotations.QueryInit;
import com.chloz.test.domain.AbstractAuditingEntity;
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
public class UserDeviceBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "token", length = 512, unique = true)
	protected String token;

}