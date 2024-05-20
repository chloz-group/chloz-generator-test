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
public class UserGroupFilterBase extends SimpleUserGroupFilter implements IAdvancedFilter<UserGroupFilter> {

	private List<UserGroupFilter> and;

	private List<UserGroupFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

	private UserFilter users;

}