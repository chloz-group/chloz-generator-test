package com.chloz.test.service.mapper;

import com.chloz.test.dataaccess.MediaDataAccess;
import com.chloz.test.service.mapper.base.MediaMapperBase;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper extends MediaMapperBase {

	public MediaMapper(MediaDataAccess dataAccess, ApplicationContext applicationContext) {
		super(dataAccess, applicationContext);
	}

}