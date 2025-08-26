package com.chloz.test.service.dto.base;

import com.chloz.test.service.dto.AbstractAuditingEntityDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class UserDeviceDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected Long id;

	protected String token;

}