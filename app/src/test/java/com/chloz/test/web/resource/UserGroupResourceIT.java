package com.chloz.test.web.resource;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.UserGroupRepository;
import com.chloz.test.service.dto.UserGroupDto;
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
public class UserGroupResourceIT extends AbstractDomainResourceIT<Long, UserGroup, UserGroupDto> {

	@Autowired
	private UserGroupRepository repository;
	@Override
	public DefaultDomainRepository<UserGroup, Long> getRepository() {
		return this.repository;
	}

	@Override
	public UserGroup generateEntity() {
		UserGroup entity = new UserGroup();
		entity.setName("U2");
		entity.setDescription("3YIKBfdlA23axxNDzv");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(UserGroup entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/userGroups";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, UserGroup entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(entity.getName())),
				jsonPath(jsonParentPath + "description", is(entity.getDescription())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, UserGroupDto dto,
			boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(dto.getName())),
				jsonPath(jsonParentPath + "description", is(dto.getDescription())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected UserGroupDto generateDtoForUpdate(UserGroup entity) {
		UserGroupDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public UserGroupDto generateDto() {
		UserGroupDto dto = new UserGroupDto();
		dto.setName("R");
		dto.setDescription("PuGmSgTRbZmuGtB");
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