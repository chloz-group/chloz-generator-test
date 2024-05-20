package com.chloz.test.web.dto;

import com.chloz.test.web.dto.base.AbstractAuditingEntityDtoBase;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class AbstractAuditingEntityDto extends AbstractAuditingEntityDtoBase {
}