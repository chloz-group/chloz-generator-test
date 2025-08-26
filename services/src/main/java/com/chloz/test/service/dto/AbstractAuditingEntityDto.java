package com.chloz.test.service.dto;

import com.chloz.test.service.dto.base.AbstractAuditingEntityDtoBase;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class AbstractAuditingEntityDto extends AbstractAuditingEntityDtoBase {
}