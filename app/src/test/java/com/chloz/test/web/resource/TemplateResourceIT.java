package com.chloz.test.web.resource;

import com.chloz.test.domain.Template;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.TemplateRepository;
import com.chloz.test.service.dto.TemplateDto;
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
public class TemplateResourceIT extends AbstractDomainResourceIT<Long, Template, TemplateDto> {

	@Autowired
	private TemplateRepository repository;
	@Override
	public DefaultDomainRepository<Template, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Template generateEntity() {
		Template entity = new Template();
		entity.setCode("dGsOlwj");
		entity.setContent(null);
		entity.setTitle("7OIY9S");
		entity.setShortContent("ZTq9bckxYPmh");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(Template entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/templates";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Template entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "code", is(entity.getCode())),
				jsonPath(jsonParentPath + "content", is(entity.getContent())),
				jsonPath(jsonParentPath + "title", is(entity.getTitle())),
				jsonPath(jsonParentPath + "shortContent", is(entity.getShortContent())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, TemplateDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "code", is(dto.getCode())),
				jsonPath(jsonParentPath + "content", is(dto.getContent())),
				jsonPath(jsonParentPath + "title", is(dto.getTitle())),
				jsonPath(jsonParentPath + "shortContent", is(dto.getShortContent())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected TemplateDto generateDtoForUpdate(Template entity) {
		TemplateDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public TemplateDto generateDto() {
		TemplateDto dto = new TemplateDto();
		dto.setCode("rwbM1fOMUK");
		dto.setContent(null);
		dto.setTitle("aG9");
		dto.setShortContent("NsPMt");
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