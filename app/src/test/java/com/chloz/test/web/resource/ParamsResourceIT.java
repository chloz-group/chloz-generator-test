package com.chloz.test.web.resource;

import com.chloz.test.domain.Params;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.ParamsRepository;
import com.chloz.test.service.dto.ParamsDto;
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
public class ParamsResourceIT extends AbstractDomainResourceIT<Long, Params, ParamsDto> {

	@Autowired
	private ParamsRepository repository;
	@Override
	public DefaultDomainRepository<Params, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Params generateEntity() {
		Params entity = new Params();
		entity.setParamKey("Tms0UeRlbwT16urf95");
		entity.setStringValue("tZgA1g");
		entity.setNumberValue(97L);
		entity.setDecimalValue(null);
		entity.setBooleanValue(false);
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(Params entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/paramses";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Params entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "paramKey", is(entity.getParamKey())),
				jsonPath(jsonParentPath + "stringValue", is(entity.getStringValue())),
				jsonPath(jsonParentPath + "numberValue", is(entity.getNumberValue().intValue())),
				jsonPath(jsonParentPath + "decimalValue", is(entity.getDecimalValue().intValue())),
				jsonPath(jsonParentPath + "booleanValue", is(entity.getBooleanValue())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, ParamsDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "paramKey", is(dto.getParamKey())),
				jsonPath(jsonParentPath + "stringValue", is(dto.getStringValue())),
				jsonPath(jsonParentPath + "numberValue", is(dto.getNumberValue().intValue())),
				jsonPath(jsonParentPath + "decimalValue", is(dto.getDecimalValue().intValue())),
				jsonPath(jsonParentPath + "booleanValue", is(dto.getBooleanValue())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected ParamsDto generateDtoForUpdate(Params entity) {
		ParamsDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public ParamsDto generateDto() {
		ParamsDto dto = new ParamsDto();
		dto.setParamKey("U2DTr2zvGL");
		dto.setStringValue("K2z4sk");
		dto.setNumberValue(685L);
		dto.setDecimalValue(null);
		dto.setBooleanValue(false);
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