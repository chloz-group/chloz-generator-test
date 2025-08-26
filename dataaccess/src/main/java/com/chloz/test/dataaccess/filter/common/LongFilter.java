package com.chloz.test.dataaccess.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Filter class for {@link Long} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class LongFilter extends NumberFilter<Long> {

	private static final long serialVersionUID = 1L;

}