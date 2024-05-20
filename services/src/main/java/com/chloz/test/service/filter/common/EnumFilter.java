package com.chloz.test.service.filter.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Class for filtering attributes with {@link Enum} type.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(builderMethodName = "builder_")
public class EnumFilter<T extends Enum<T>> extends Filter<T> {

	private static final long serialVersionUID = 1L;

}