package com.chloz.test.web.resource;

import com.chloz.test.domain.Town;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.TownRepository;
import com.chloz.test.service.dto.TownDto;
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
public class TownResourceIT extends AbstractDomainResourceIT<Long, Town, TownDto> {

	@Autowired
	private TownRepository repository;
	@Override
	public DefaultDomainRepository<Town, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Town generateEntity() {
		Town entity = new Town();
		entity.setName("7NutU3TdplHVsRVqa");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(Town entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/towns";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Town entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(entity.getName())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, TownDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "name", is(dto.getName())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected TownDto generateDtoForUpdate(Town entity) {
		TownDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public TownDto generateDto() {
		TownDto dto = new TownDto();
		dto.setName("jL5RLV");
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