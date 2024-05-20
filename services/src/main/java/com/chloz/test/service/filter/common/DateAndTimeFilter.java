package com.chloz.test.service.filter.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Filter class for Comparable types, where less than / greater than / etc
 * relations could be interpreted. It can be added to a criteria class as a
 * member, to support the following query parameters:
 * 
 * <pre>
 *      fieldName.eq=42
 *      fieldName.neq=42
 *      fieldName.in=43,42
 *      fieldName.notIn=43,42
 *      fieldName.gt=41
 *      fieldName.lt=44
 *      fieldName.gte=42
 *      fieldName.lte=44
 * </pre>
 *
 * @param <T>
 *            the type of filter.
 * @see IntegerFilter
 * @see LongFilter
 * @see LocalDateFilter
 * @see InstantFilter
 * @see ShortFilter
 * @see ZonedDateTimeFilter
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "builder_")
public class DateAndTimeFilter<T extends Comparable> extends Filter<T> {

	private static final long serialVersionUID = 1L;

	private T gt;

	private T lt;

	private T goe;

	private T loe;

}