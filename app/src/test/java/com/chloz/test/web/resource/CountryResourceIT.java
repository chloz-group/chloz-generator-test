package com.chloz.test.web.resource;

import com.chloz.test.domain.Country;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.CountryRepository;
import com.chloz.test.service.dto.CountryDto;
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
public class CountryResourceIT extends AbstractDomainResourceIT<String, Country, CountryDto> {

	@Autowired
	private CountryRepository repository;
	@Override
	public DefaultDomainRepository<Country, String> getRepository() {
		return this.repository;
	}

	@Override
	public Country generateEntity() {
		Country entity = new Country();
		entity.setName("mONsHBarYpru3XYOED5");
		entity.setCallingCode("PJB2FtjwILBChRY");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public String getEntityId(Country entity) {
		return entity.getCode();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/countries";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, Country entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "code", is(entity.getCode())),
				jsonPath(jsonParentPath + "name", is(entity.getName())),
				jsonPath(jsonParentPath + "callingCode", is(entity.getCallingCode())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, CountryDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "code").exists()
						: jsonPath(jsonParentPath + "code", is(dto.getCode())),
				jsonPath(jsonParentPath + "name", is(dto.getName())),
				jsonPath(jsonParentPath + "callingCode", is(dto.getCallingCode())),};
	}

	@Override
	public String generateUnexistingEntityId() {
		return "adF50FadCf0LK";
	}

	@Override
	protected CountryDto generateDtoForUpdate(Country entity) {
		CountryDto dto = this.generateDto();
		dto.setCode(entity.getCode());
		return dto;
	}

	@Override
	public CountryDto generateDto() {
		CountryDto dto = new CountryDto();
		dto.setName("iefxbimWzXLA");
		dto.setCallingCode("U");
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