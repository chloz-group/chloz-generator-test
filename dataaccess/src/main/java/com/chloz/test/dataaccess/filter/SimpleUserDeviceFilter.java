package com.chloz.test.dataaccess.filter;

import com.chloz.test.dataaccess.filter.base.SimpleUserDeviceFilterBase;
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
public class SimpleUserDeviceFilter extends SimpleUserDeviceFilterBase {

	private static final long serialVersionUID = 1L;

}