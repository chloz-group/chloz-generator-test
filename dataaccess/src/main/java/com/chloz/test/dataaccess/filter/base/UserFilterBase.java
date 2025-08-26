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
public class UserFilterBase extends SimpleUserFilter implements IAdvancedFilter<UserFilter> {

	private List<UserFilter> and;

	private List<UserFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

	private MediaFilter picture;

	private UserGroupFilter groups;

}