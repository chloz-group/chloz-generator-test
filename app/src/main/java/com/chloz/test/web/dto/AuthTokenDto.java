package com.chloz.test.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class AuthTokenDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;

}