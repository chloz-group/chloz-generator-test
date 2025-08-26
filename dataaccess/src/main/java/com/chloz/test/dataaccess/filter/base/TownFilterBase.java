package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TownFilterBase extends SimpleTownFilter implements IAdvancedFilter<TownFilter> {

	private List<TownFilter> and;

	private List<TownFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

	private CountryFilter country;

}