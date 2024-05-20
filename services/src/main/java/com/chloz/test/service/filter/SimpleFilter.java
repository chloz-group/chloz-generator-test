package com.chloz.test.service.filter;

import com.chloz.test.service.filter.base.SimpleFilterBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The simple filter contains only simple filters on simple fields.
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SimpleFilter extends SimpleFilterBase {

	private static final long serialVersionUID = 1L;

}