package com.chloz.test.service.dto.base;

import com.chloz.test.service.dto.AbstractAuditingEntityDto;
import com.chloz.test.service.dto.RoleDto;
import com.chloz.test.service.dto.UserDto;
import java.util.List;
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
public class UserGroupDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected Long id;

	protected String name;

	protected String description;

	protected List<UserDto> users;

	protected List<RoleDto> roles;

}