package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.FilterOperator;
import com.chloz.test.dataaccess.filter.common.BooleanFilter;
import com.chloz.test.dataaccess.filter.common.OffsetDateTimeFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class SimpleFilterBase implements Serializable {

	/**
	 * The operator to apply between the filters
	 */
	@Builder.Default
	private FilterOperator operator = FilterOperator.AND;

	private OffsetDateTimeFilter createdDate;

	private OffsetDateTimeFilter lastModifiedDate;

	private BooleanFilter disabled;

}