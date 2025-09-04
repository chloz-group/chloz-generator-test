package com.chloz.test.web.resource;

import com.chloz.test.domain.Role;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.RoleRepository;
import com.chloz.test.service.dto.RoleDto;
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
public class RoleResourceIT extends AbstractDomainResourceIT<String, Role, RoleDto> {

	@Autowired
	private RoleRepository repository;
	@Override
	public DefaultDomainRepository<Role, String> getRepository() {
		return this.repository;
	}

	@Override
	public Role generateEntity() {
		Role entity = new Role();
		entity.setDescription("TjzhQYc7ggXNCEaUNB");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public String getEntityId(Role entity) {
		return entity.getName();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/roles";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Role entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "name", is(entity.getName())),
				jsonPath(jsonParentPath + "description", is(entity.getDescription())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, RoleDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "name").exists()
						: jsonPath(jsonParentPath + "name", is(dto.getName())),
				jsonPath(jsonParentPath + "description", is(dto.getDescription())),};
	}

	@Override
	public String generateUnexistingEntityId() {
		return "adF50FadCf0LK";
	}

	@Override
	protected RoleDto generateDtoForUpdate(Role entity) {
		RoleDto dto = this.generateDto();
		dto.setName(entity.getName());
		return dto;
	}

	@Override
	public RoleDto generateDto() {
		RoleDto dto = new RoleDto();
		dto.setDescription("m8P7P");
		return dto;
	}

	public boolean isInsertEnable() {
		return false;
	}

	public boolean isUpdateEnable() {
		return false;
	}

	public boolean isFilterEnable() {
		return true;
	}

}