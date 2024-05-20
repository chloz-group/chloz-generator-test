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
public class RoleFilterBase extends SimpleRoleFilter implements IAdvancedFilter<RoleFilter> {

	private List<RoleFilter> and;

	private List<RoleFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}