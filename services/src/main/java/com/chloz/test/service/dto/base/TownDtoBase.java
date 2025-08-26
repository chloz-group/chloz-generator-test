package com.chloz.test.service.dto.base;

import com.chloz.test.service.dto.AbstractAuditingEntityDto;
import com.chloz.test.service.dto.CountryDto;
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
public class TownDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected Long id;

	protected String name;

	protected CountryDto country;

}