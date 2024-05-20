package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.Instant;

/**
 * Filter class for {@link Instant} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class InstantFilter extends DateAndTimeFilter<Instant> {

	private static final long serialVersionUID = 1L;

}