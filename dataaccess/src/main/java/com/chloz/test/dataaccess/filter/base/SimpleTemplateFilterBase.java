package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.filter.TemplateFilter;
import com.chloz.test.dataaccess.filter.common.LongFilter;
import com.chloz.test.dataaccess.filter.common.StringFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SimpleTemplateFilterBase extends SimpleFilter {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private StringFilter code;

	private StringFilter content;

	private StringFilter title;

	private StringFilter shortContent;

}