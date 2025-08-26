package com.chloz.test.service.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.chloz.test.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAuditingEntityDtoBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UserDto createdBy;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private OffsetDateTime createdDate;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UserDto lastModifiedBy;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private OffsetDateTime lastModifiedDate;

	private Boolean disabled;

}