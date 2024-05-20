package com.chloz.test.web.dto.base;

import com.chloz.test.web.dto.AbstractAuditingEntityDto;
import com.chloz.test.web.dto.MediaDto;
import com.chloz.test.web.dto.RoleDto;
import com.chloz.test.web.dto.UserGroupDto;
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
public class UserDtoBase extends AbstractAuditingEntityDto implements Serializable {

	protected Long id;

	protected String login;

	protected String email;

	protected String phone;

	protected Boolean phoneChecked;

	protected Boolean accountLocked;

	protected Boolean emailChecked;

	protected Boolean activated;

	protected Integer attempts;

	protected String firstName;

	protected String name;

	protected String lang;

	protected List<RoleDto> roles;

	protected MediaDto picture;

	protected List<UserGroupDto> groups;

}