package com.chloz.test.web.resource;

import com.chloz.test.domain.Media;
import com.chloz.test.service.MediaService;
import com.chloz.test.service.filter.MediaFilter;
import com.chloz.test.service.filter.SimpleMediaFilter;
import com.chloz.test.service.filter.common.StringFilter;
import com.chloz.test.web.Constants;
import com.chloz.test.web.dto.FileUploadDto;
import com.chloz.test.web.dto.MediaDto;
import com.chloz.test.web.mapper.MediaMapper;
import com.chloz.test.web.resource.base.MediaResourceBase;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import javax.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/medias")
public class MediaResource extends MediaResourceBase {

	private final Logger logger = LoggerFactory.getLogger(MediaResource.class);

	@Value("${media.storageLocation:}")
	private String storageLocation;

	private final MediaService service;

	private final MediaMapper mapper;

	private Path rootDir;

	// The target width for the optimized image
	private final int optimizedImagedWith = 300;

	// The target height for the optimized image
	private final int optimizedImageHeight = 300;
	public MediaResource(MediaService service, MediaMapper mapper) {
		super(service, mapper);
		this.service = service;
		this.mapper = mapper;
	}

	@PostConstruct
	private void init() throws IOException {
		this.rootDir = Path.of(this.storageLocation);
		if (Files.notExists(rootDir)) {
			Files.createDirectories(this.rootDir);
			logger.info("Directory {} created", this.rootDir);
		}
	}

	@GetMapping(path = "{id}")
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

	@PostMapping
	public ResponseEntity<MediaDto> create(@NotNull @RequestParam("file") MultipartFile file,
			@Nullable @RequestParam("graph") String graph) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("No file uploaded");
		}
		Media media = this.saveMedia(new Media(), file, true);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(media, graph));
	}

	@PostMapping(path = "upload")
	public ResponseEntity<MediaDto> create(@Valid @RequestBody FileUploadDto fileUpload,
			@Nullable @RequestParam("graph") String graph) {
		Media media = this.saveMedia(new Media(), fileUpload);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(media, graph));
	}

	private Media saveMedia(Media media, FileUploadDto fileUpload) {
		if (fileUpload == null || fileUpload.getFileName() == null || fileUpload.getBase64Content() == null
				|| fileUpload.getContentType() == null) {
			throw new IllegalArgumentException(
					"Illegal data provided, fileName, base64Content and contentType are all required");
		}
		// try to parse contentType to make sur it's a valid content type
		ContentType.parse(fileUpload.getContentType());
		InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(fileUpload.getBase64Content()));
		return this.saveMedia(media, is, fileUpload.getFileName(), fileUpload.getContentType(),
				fileUpload.getOptimizeImage());
	}

	private Media saveMedia(Media media, MultipartFile file, boolean optimizeImage) {
		try {
			return this.saveMedia(media, file.getInputStream(), file.getOriginalFilename(), file.getContentType(),
					optimizeImage);
		} catch (IOException e) {
			throw new RuntimeException("Could not upload the file", e);
		}
	}

	private Media saveMedia(Media media, InputStream stream, String fileName, String contentType,
			boolean optimizeImage) {
		media.setContentType(contentType);
		media.setName(fileName);
		String key = media.getKey();
		if (key == null) {
			key = DigestUtils.md5Hex(System.currentTimeMillis() + fileName);
		}
		byte[] data;
		try {
			data = IOUtils.toByteArray(stream);
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to read the media content", e);
		}
		// Optimize image
		try {
			boolean isImage = contentType != null && contentType.toLowerCase().startsWith("image/");
			if (isImage && optimizeImage) {
				logger.info("Optimizing the image");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				Thumbnails.of(bis).size(optimizedImagedWith, optimizedImageHeight).toOutputStream(bos);
				data = bos.toByteArray();
				bis.close();
				bos.close();
			}
		} catch (IOException e) {
			logger.warn("Could not optimize the image", e);
		}
		//
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		LocalDate date = LocalDate.now();
		Path relativePath = Path.of("" + date.getYear(), StringUtils.leftPad("" + date.getMonthValue(), 2, "0"),
				StringUtils.leftPad("" + date.getDayOfMonth(), 2, "0"), key + "-" + media.getName());
		media.setPath(relativePath.toString());
		media.setKey(key);
		Path destination = this.rootDir.resolve(relativePath);
		try {
			Files.createDirectories(destination);
			Files.copy(is, destination, StandardCopyOption.REPLACE_EXISTING);
			is.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not upload the file", e);
		}
		Media res = this.service.save(media);
		logger.info("Media saved at {}", destination);
		return res;
	}

	@PostMapping(path = "{id}")
	public ResponseEntity<MediaDto> update(@NotNull @PathVariable("id") Long id,
			@NotNull @RequestParam("file") MultipartFile file, @Nullable @RequestParam("graph") String graph) {
		Media media = this.service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));
		media = this.saveMedia(media, file, true);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(media, graph));
	}

	@PutMapping(path = "upload/{id}")
	public ResponseEntity<MediaDto> update(@NotNull @PathVariable("id") Long id,
			@Valid @RequestBody FileUploadDto fileUpload, @Nullable @RequestParam("graph") String graph) {
		Media media = this.service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));
		media = this.saveMedia(media, fileUpload);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(media, graph));
	}

	@Transactional(readOnly = true)
	@RequestMapping(path = "download/{id}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@NotNull @PathVariable("id") Long id, HttpServletRequest request)
			throws Exception {
		Optional<Media> opt = service.findById(id);
		if (!opt.isPresent() || opt.get().isDeleted()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found");
		}
		Media media = opt.get();
		return this.download(media, request);
	}

	@Transactional(readOnly = true)
	@RequestMapping(path = "download-key/{key}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadByKey(@NotNull @PathVariable("key") String key, HttpServletRequest request)
			throws Exception {
		Optional<Media> opt = service
				.findByFilter(MediaFilter.builder().key(StringFilter.builder().eq(key).build()).build(), "*").stream()
				.findFirst();
		if (!opt.isPresent() || opt.get().isDeleted()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found");
		}
		Media media = opt.get();
		return this.download(media, request);
	}

	private ResponseEntity<Resource> download(Media media, HttpServletRequest request) throws IOException {
		InputStream is = Files.newInputStream(this.rootDir.resolve(media.getPath()));
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

}