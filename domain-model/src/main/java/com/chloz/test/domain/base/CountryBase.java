package com.chloz.test.domain.base;

import com.querydsl.core.annotations.QueryInit;
import com.chloz.test.domain.AbstractAuditingEntity;
import com.chloz.test.domain.Town;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = {"code"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"code"})
public class CountryBase extends AbstractAuditingEntity implements Serializable {

	@Id
	@Column(name = "code", length = 2)
	protected String code;

	@Column(name = "name", length = 255)
	protected String name;

	@Column(name = "calling_code", length = 20)
	protected String callingCode;

	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	@QueryInit("*.*")
	protected List<Town> towns;

}