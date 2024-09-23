package com.chloz.test.web.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotBlank
	private String fileName;

	@NotNull
	@NotBlank
	private String base64Content;

	@NotNull
	@NotBlank
	private String contentType;

	@Builder.Default
	private Boolean optimizeImage = true;

}