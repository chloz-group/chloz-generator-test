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
public class MediaFilterBase extends SimpleMediaFilter implements IAdvancedFilter<MediaFilter> {

	private List<MediaFilter> and;

	private List<MediaFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}