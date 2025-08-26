package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.MediaFilter;
import com.chloz.test.dataaccess.MediaDataAccess;
import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.dataaccess.filter.common.StringFilter;
import com.chloz.test.domain.Media;
import com.chloz.test.service.dto.FileUploadDto;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import com.chloz.test.service.mapper.MediaMapper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

public class MediaServiceBaseImplBase extends DefaultDomainServiceImpl<Media, Long, MediaDto, SimpleMediaFilter>
		implements
			MediaServiceBase {

	private static final String MEDIA_NOT_FOUND_MESSAGE = "Media not found";

	private final Logger logger = LoggerFactory.getLogger(MediaServiceBaseImplBase.class);

	@Value("${media.storageLocation:}")
	private String storageLocation;

	private final MediaDataAccess dataAccess;

	private final MediaMapper mapper;

	private Path rootDir;

	// The target width for the optimized image
	private static final int optimizedImagedWith = 300;

	// The target height for the optimized image
	private static final int optimizedImageHeight = 300;
	public MediaServiceBaseImplBase(MediaDataAccess dataAccess, MediaMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
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

	@Override
	public MediaDto create(FileUploadDto fileUpload, String graph) {
		Media media = this.saveMedia(new Media(), fileUpload);
		return mapper.mapToDto(media, graph);
	}

	private Media saveMedia(Media media, FileUploadDto fileUpload) {
		if (fileUpload == null || fileUpload.getFileName() == null
				|| (fileUpload.getBase64Content() == null && fileUpload.getContentStream() == null)
				|| fileUpload.getContentType() == null) {
			throw new IllegalArgumentException(
					"Illegal data provided, fileName, base64Content and contentType are all required");
		}
		// try to parse contentType to make sur it's a valid content type
		ContentType.parse(fileUpload.getContentType());
		MediaType.parseMediaType(fileUpload.getContentType());
		InputStream is = fileUpload.getContentStream();
		if (is == null)
			is = new ByteArrayInputStream(Base64.getDecoder().decode(fileUpload.getBase64Content()));
		return this.saveMedia(media, is, fileUpload.getFileName(), fileUpload.getContentType(),
				fileUpload.getOptimizeImage());
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
			throw new IllegalStateException("Unable to read the media content", e);
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
			throw new IllegalStateException("Could not upload the file", e);
		}
		Media res = this.dataAccess.save(media);
		logger.info("Media saved at {}", destination);
		return res;
	}

	@Override
	public MediaDto update(Long id, FileUploadDto fileUpload, String graph) {
		Media media = this.dataAccess.findById(id)
				.orElseThrow(() -> new BusinessException(MEDIA_NOT_FOUND_MESSAGE, null, 404));
		media = this.saveMedia(media, fileUpload);
		return mapper.mapToDto(media, graph);
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream getMediaContentById(Long id) throws IOException {
		Optional<Media> opt = dataAccess.findById(id);
		if (!opt.isPresent()) {
			throw new BusinessException(MEDIA_NOT_FOUND_MESSAGE, null, 404);
		}
		Media media = opt.get();
		return Files.newInputStream(this.rootDir.resolve(media.getPath()));
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream getMediaContentByKey(String key) throws IOException {
		Optional<Media> opt = dataAccess
				.findByFilter(MediaFilter.builder().key(StringFilter.builder().eq(key).build()).build(), "*").stream()
				.findFirst();
		if (!opt.isPresent()) {
			throw new BusinessException(MEDIA_NOT_FOUND_MESSAGE, null, 404);
		}
		Media media = opt.get();
		return Files.newInputStream(this.rootDir.resolve(media.getPath()));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<MediaDto> getMediaByKey(String key, String graph) {
		Optional<Media> opt = dataAccess
				.findByFilter(MediaFilter.builder().key(StringFilter.builder().eq(key).build()).build(), graph).stream()
				.findFirst();
		return opt.map(m -> mapper.mapToDto(m, graph));
	}

	@Override
	public MediaDto updateFields(MediaDto dto, String graph) {
		// Todo : Not implemented
		return dto;
	}

}