package com.chloz.test.web.dto;

import com.chloz.test.web.dto.base.CountryDtoBase;
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