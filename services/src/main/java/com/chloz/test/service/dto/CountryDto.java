package com.chloz.test.service.dto;

import com.chloz.test.service.dto.base.CountryDtoBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CountryDto extends CountryDtoBase {

	private static final long serialVersionUID = 1L;

}