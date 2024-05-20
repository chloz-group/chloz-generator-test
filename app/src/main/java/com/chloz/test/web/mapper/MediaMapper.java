package com.chloz.test.web.mapper;

import com.chloz.test.service.MediaService;
import com.chloz.test.web.mapper.base.MediaMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper extends MediaMapperBase {

	public MediaMapper(MediaService service, ApplicationContext applicationContext) {
		super(service, applicationContext);
	}

}