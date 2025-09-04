package com.chloz.test.web.resource;

import com.chloz.test.domain.Media;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.MediaRepository;
import com.chloz.test.service.dto.MediaDto;
import com.chloz.test.web.Constants;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
@Disabled("Partial implementation. Not working for now.")
public class MediaResourceIT extends AbstractDomainResourceIT<Long, Media, MediaDto> {

	@Autowired
	private MediaRepository repository;
	@Override
	public DefaultDomainRepository<Media, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Media generateEntity() {
		Media entity = new Media();
		entity.setName("MyQZHgE");
		entity.setContentType("t8iiZQryK4SiIRk");
		entity.setKey("qT1x63Phk");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(Media entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/medias";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Media entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(entity.getName())),
				jsonPath(jsonParentPath + "contentType", is(entity.getContentType())),
				jsonPath(jsonParentPath + "key", is(entity.getKey())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, MediaDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(dto.getName())),
				jsonPath(jsonParentPath + "contentType", is(dto.getContentType())),
				jsonPath(jsonParentPath + "key", is(dto.getKey())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected MediaDto generateDtoForUpdate(Media entity) {
		MediaDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public MediaDto generateDto() {
		MediaDto dto = new MediaDto();
		dto.setName("wjqoMQNqfI9");
		dto.setContentType("RI5Qg6kSzcm");
		dto.setKey("nBN5pBOSZfu");
		return dto;
	}

	public boolean isInsertEnable() {
		return true;
	}

	public boolean isUpdateEnable() {
		return true;
	}

	public boolean isFilterEnable() {
		return true;
	}

}