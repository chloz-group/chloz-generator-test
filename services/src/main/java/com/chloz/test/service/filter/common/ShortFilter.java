package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Filter class for {@link Short} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ShortFilter extends NumberFilter<Short> {

	private static final long serialVersionUID = 1L;

}