package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.filter.ParamsFilter;
import com.chloz.test.dataaccess.filter.common.BigDecimalFilter;
import com.chloz.test.dataaccess.filter.common.BooleanFilter;
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
public class SimpleParamsFilterBase extends SimpleFilter {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private StringFilter paramKey;

	private StringFilter stringValue;

	private LongFilter numberValue;

	private BigDecimalFilter decimalValue;

	private BooleanFilter booleanValue;

}