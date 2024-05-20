package com.chloz.test.web.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class PasswordChangeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String currentPassword;

	@NotNull
	private String newPassword;

}