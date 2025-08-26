package com.chloz.test.dataaccess.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

/**
 * Filter class for {@link BigDecimal} type attributes.
 *
 * @see DateAndTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class BigDecimalFilter extends NumberFilter<BigDecimal> {

	private static final long serialVersionUID = 1L;

}