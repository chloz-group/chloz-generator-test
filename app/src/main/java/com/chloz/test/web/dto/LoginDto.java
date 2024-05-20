package com.chloz.test.web.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class LoginDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 50)
	private String username;

	@NotNull
	@Size(min = 3, max = 100)
	private String password;

	private String smsCode;

	private String emailCode;

	private Boolean rememberMe;

}