package com.chloz.test.service.filter.base;

import com.chloz.test.service.filter.SimpleFilter;
import com.chloz.test.service.filter.CountryFilter;
import com.chloz.test.service.filter.common.StringFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SimpleCountryFilterBase extends SimpleFilter {

	private static final long serialVersionUID = 1L;

	private StringFilter code;

	private StringFilter name;

	private StringFilter callingCode;

}