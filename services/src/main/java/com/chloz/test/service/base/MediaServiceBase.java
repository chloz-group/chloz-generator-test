package com.chloz.test.service.base;

import com.chloz.test.dataaccess.filter.SimpleMediaFilter;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.service.dto.FileUploadDto;
import com.chloz.test.service.DefaultDomainService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface MediaServiceBase extends DefaultDomainService<Long, MediaDto, SimpleMediaFilter> {

	public MediaDto create(FileUploadDto fileUpload, String graph);

	public Optional<MediaDto> getMediaByKey(String key, String graph);

	public MediaDto update(Long id, FileUploadDto fileUpload, String graph);

	public InputStream getMediaContentById(Long id) throws IOException;

	public InputStream getMediaContentByKey(String key) throws IOException;

}