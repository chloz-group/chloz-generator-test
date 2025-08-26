package com.chloz.test.dataaccess.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.OffsetDateTime;

/**
 * Filter class for {@link OffsetDateTime} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class OffsetDateTimeFilter extends DateAndTimeFilter<OffsetDateTime> {

	private static final long serialVersionUID = 1L;

}