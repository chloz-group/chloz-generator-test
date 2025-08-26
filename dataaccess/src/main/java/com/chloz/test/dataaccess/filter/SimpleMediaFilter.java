package com.chloz.test.dataaccess.filter;

import com.chloz.test.dataaccess.filter.base.SimpleMediaFilterBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SimpleMediaFilter extends SimpleMediaFilterBase {

	private static final long serialVersionUID = 1L;

}