package com.chloz.test.service.filter.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Class for filtering attributes with {@link String} type.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StringFilter extends Filter<String> {

	private static final long serialVersionUID = 1L;

	private String contains;

	private String notContains;

	private String startsWith;

	private String endsWith;

	private Boolean ignoreCase;

}