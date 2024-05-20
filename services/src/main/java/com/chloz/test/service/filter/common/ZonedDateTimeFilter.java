package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.ZonedDateTime;

/**
 * Filter class for {@link ZonedDateTime} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ZonedDateTimeFilter extends DateAndTimeFilter<ZonedDateTime> {

	private static final long serialVersionUID = 1L;

}