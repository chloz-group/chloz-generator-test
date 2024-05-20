package com.chloz.test.service.filter.base;

import com.chloz.test.service.filter.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CountryFilterBase extends SimpleCountryFilter implements IAdvancedFilter<CountryFilter> {

	private List<CountryFilter> and;

	private List<CountryFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

	private TownFilter towns;

}