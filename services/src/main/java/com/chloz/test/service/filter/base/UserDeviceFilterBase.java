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
public class UserDeviceFilterBase extends SimpleUserDeviceFilter implements IAdvancedFilter<UserDeviceFilter> {

	private List<UserDeviceFilter> and;

	private List<UserDeviceFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}