package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.dto.FileUploadDto;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.MediaResourceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/medias")
public class MediaResource extends MediaResourceBase {

	private static final String MEDIA_NOT_FOUND_MESSAGE = "Media not found";

	private final Logger logger = LoggerFactory.getLogger(MediaResource.class);

	private final MediaService service;
	public MediaResource(MediaService service) {
		super(service);
		this.service = service;
	}

	@GetMapping(path = "{id}")
	@Override
	public ResponseEntity<MediaDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<MediaDto> getPageByFilter(@ParameterObject SimpleMediaFilter filter,
			@ParameterObject Pageable pageable, @Nullable @RequestParam("graph") String graph) {
		return super.getPageByFilter(filter, pageable, graph);
	}

	// @PostMapping
	// TODOs : comment this because there is an issue when generating client on dart
	// using OpenAPI Generator
	// [SEVERE] json_serializable on lib/src/model/create_request.dart (cached):
	// Cannot populate the required constructor argument: file. It is assigned to a
	// field not meant to be used in fromJson.
	// package:****/src/model/create_request.dart:19:3
	public ResponseEntity<MediaDto> create(@NotNull @RequestParam("file") MultipartFile file,
			@Nullable @RequestParam("graph") String graph) throws IOException {
		MediaDto media = this.service.create(toFileUpload(file), graph);
		return ResponseEntity.status(HttpStatus.CREATED).body(media);
	}

	@PostMapping(path = "upload")
	public ResponseEntity<MediaDto> create(@Valid @RequestBody FileUploadDto fileUpload,
			@Nullable @RequestParam("graph") String graph) {
		MediaDto media = this.service.create(fileUpload, graph);
		return ResponseEntity.status(HttpStatus.CREATED).body(media);
	}

	// TODOs : comment this because there is an issue when generating client on dart
	// using OpenAPI Generator
	// [SEVERE] json_serializable on lib/src/model/create_request.dart (cached):
	// Cannot populate the required constructor argument: file. It is assigned to a
	// field not meant to be used in fromJson.
	// package:****/src/model/create_request.dart:19:3
	// @PutMapping(path = "{id}")
	public ResponseEntity<MediaDto> update(@NotNull @PathVariable("id") Long id,
			@NotNull @RequestParam("file") MultipartFile file, @Nullable @RequestParam("graph") String graph)
			throws IOException {
		MediaDto media = this.service.update(id, toFileUpload(file), graph);
		return ResponseEntity.status(HttpStatus.OK).body(media);
	}

	@PutMapping(path = "upload/{id}")
	public ResponseEntity<MediaDto> update(@NotNull @PathVariable("id") Long id,
			@Valid @RequestBody FileUploadDto fileUpload, @Nullable @RequestParam("graph") String graph) {
		MediaDto media = this.service.update(id, fileUpload, graph);
		return ResponseEntity.status(HttpStatus.OK).body(media);
	}

	@GetMapping(path = "download/{id}")
	public ResponseEntity<Resource> downloadFile(@NotNull @PathVariable("id") Long id, HttpServletRequest request)
			throws IOException {
		MediaDto media = service.getById(id, "*");
		if (media == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIA_NOT_FOUND_MESSAGE);
		}
		return this.download(this.service.getMediaContentById(id), media, request);
	}

	@GetMapping(path = "download-key/{key}")
	public ResponseEntity<Resource> downloadByKey(@NotNull @PathVariable("key") String key, HttpServletRequest request)
			throws IOException {
		Optional<MediaDto> opt = service.getMediaByKey(key, "*");
		if (!opt.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIA_NOT_FOUND_MESSAGE);
		}
		MediaDto media = opt.get();
		return this.download(this.service.getMediaContentByKey(key), media, request);
	}

	private ResponseEntity<Resource> download(InputStream is, MediaDto media, HttpServletRequest request)
			throws IOException {
		Resource resource = new InputStreamResource(is);
		String etag = "M" + media.getId()
				+ Optional.ofNullable(media.getLastModifiedDate()).map(dt -> dt.toInstant().getEpochSecond());
		etag = Base64.getEncoder().encodeToString(etag.getBytes(StandardCharsets.UTF_8));
		String contentType = media.getContentType();
		if (contentType == null) {
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				logger.warn("Could not determine the contentType of media : " + media.getId(), ex);
			}
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CACHE_CONTROL, "public, max-age=7776000").header(HttpHeaders.ETAG, etag)
				// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
				// media.getName() + "\"")
				.body(resource);
	}

	@Override
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteById(@NotNull @PathVariable("id") Long id) {
		return super.deleteById(id);
	}

	private FileUploadDto toFileUpload(MultipartFile file) throws IOException {
		return FileUploadDto.builder().contentType(file.getContentType()).contentStream(file.getInputStream())
				.fileName(file.getName()).build();
	}

}