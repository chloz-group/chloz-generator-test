package com.chloz.test.web.resource;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.UserDeviceRepository;
import com.chloz.test.service.dto.UserDeviceDto;
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
public class UserDeviceResourceIT extends AbstractDomainResourceIT<Long, UserDevice, UserDeviceDto> {

	@Autowired
	private UserDeviceRepository repository;
	@Override
	public DefaultDomainRepository<UserDevice, Long> getRepository() {
		return this.repository;
	}

	@Override
	public UserDevice generateEntity() {
		UserDevice entity = new UserDevice();
		entity.setToken("LEbcsh2OOBtFhvV");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(UserDevice entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/userDevices";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, UserDevice entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "token", is(entity.getToken())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, UserDeviceDto dto,
			boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "token", is(dto.getToken())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected UserDeviceDto generateDtoForUpdate(UserDevice entity) {
		UserDeviceDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public UserDeviceDto generateDto() {
		UserDeviceDto dto = new UserDeviceDto();
		dto.setToken("YbQxo1R5ZnjgRxaAUD");
		return dto;
	}

	public boolean isInsertEnable() {
		return true;
	}

	public boolean isUpdateEnable() {
		return false;
	}

	public boolean isFilterEnable() {
		return false;
	}

}