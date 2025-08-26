package com.chloz.test.dataaccess.filter.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.dataaccess.filter.UserFilter;
import com.chloz.test.dataaccess.filter.common.BooleanFilter;
import com.chloz.test.dataaccess.filter.common.IntegerFilter;
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
public class SimpleUserFilterBase extends SimpleFilter {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private StringFilter login;

	private StringFilter email;

	private StringFilter phone;

	private BooleanFilter phoneChecked;

	private BooleanFilter accountLocked;

	private BooleanFilter emailChecked;

	private BooleanFilter activated;

	private IntegerFilter attempts;

	private StringFilter firstName;

	private StringFilter name;

	private StringFilter lang;

}