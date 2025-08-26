package com.chloz.test.service.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.InputStream;
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

	private String base64Content;

	private InputStream contentStream;

	@NotNull
	@NotBlank
	private String contentType;

	@Builder.Default
	private Boolean optimizeImage = true;

}