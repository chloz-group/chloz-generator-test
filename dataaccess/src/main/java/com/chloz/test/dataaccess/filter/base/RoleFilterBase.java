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
public class RoleFilterBase extends SimpleRoleFilter implements IAdvancedFilter<RoleFilter> {

	private List<RoleFilter> and;

	private List<RoleFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}