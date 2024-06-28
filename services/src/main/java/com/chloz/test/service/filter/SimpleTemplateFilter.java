package com.chloz.test.service.filter;

import com.chloz.test.service.filter.base.SimpleTemplateFilterBase;
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
public class SimpleTemplateFilter extends SimpleTemplateFilterBase {

	private static final long serialVersionUID = 1L;

}