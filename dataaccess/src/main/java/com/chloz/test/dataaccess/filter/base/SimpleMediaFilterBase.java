package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.filter.MediaFilter;
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
public class SimpleMediaFilterBase extends SimpleFilter {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private StringFilter name;

	private StringFilter path;

	private StringFilter contentType;

	private StringFilter key;

}