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
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name"})
public class RoleBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@Column(name = "name", length = 50, unique = true)
	@NotNull
	protected String name;

	@Column(name = "description", length = 255)
	protected String description;

}