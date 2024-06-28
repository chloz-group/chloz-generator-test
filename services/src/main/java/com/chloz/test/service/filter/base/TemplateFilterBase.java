package com.chloz.test.service.filter.base;

import com.chloz.test.service.filter.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TemplateFilterBase extends SimpleTemplateFilter implements IAdvancedFilter<TemplateFilter> {

	private List<TemplateFilter> and;

	private List<TemplateFilter> or;

	private UserFilter createdBy;

	private UserFilter lastModifiedBy;

}