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
public class ParamsFilterBase extends SimpleParamsFilter implements IAdvancedFilter<ParamsFilter> {

	private List<ParamsFilter> and;

	private List<ParamsFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}