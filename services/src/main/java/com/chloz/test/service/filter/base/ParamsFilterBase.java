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
public class ParamsFilterBase extends SimpleParamsFilter implements IAdvancedFilter<ParamsFilter> {

	private List<ParamsFilter> and;

	private List<ParamsFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}