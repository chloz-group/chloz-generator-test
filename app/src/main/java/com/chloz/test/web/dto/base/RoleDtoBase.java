package com.chloz.test.web.dto.base;

import com.chloz.test.web.dto.AbstractAuditingEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name"})
public class RoleDtoBase extends AbstractAuditingEntityDto implements Serializable {

	@NotNull
	protected String name;

	protected String description;

}