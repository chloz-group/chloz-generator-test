package com.chloz.test.web.dto.base;

import com.chloz.test.web.dto.AbstractAuditingEntityDto;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class ParamsDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected Long id;

	@NotNull
	protected String paramKey;

	protected String stringValue;

	protected Long numberValue;

	protected BigDecimal decimalValue;

	protected Boolean booleanValue;

}