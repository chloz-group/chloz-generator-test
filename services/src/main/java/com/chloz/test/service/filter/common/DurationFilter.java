package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.Duration;

/**
 * Filter class for {@link Duration} type attributes.
 *
 * @see Filter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class DurationFilter extends DateAndTimeFilter<Duration> {

	private static final long serialVersionUID = 1L;

}