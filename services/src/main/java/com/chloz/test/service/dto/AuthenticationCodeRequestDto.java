package com.chloz.test.service.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class AuthenticationCodeRequestDto {

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private boolean generateNewCode;

}