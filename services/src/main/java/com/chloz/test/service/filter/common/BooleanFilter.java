package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Class for filtering attributes with {@link Boolean} type.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class BooleanFilter extends Filter<Boolean> {

	private static final long serialVersionUID = 1L;

}