package com.chloz.test.web.dto.base;

import com.chloz.test.web.dto.AbstractAuditingEntityDto;
import com.chloz.test.web.dto.TownDto;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"code"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"code"})
public class CountryDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected String code;

	protected String name;

	protected String callingCode;

	protected List<TownDto> towns;

}