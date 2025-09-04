package com.chloz.test.web.resource;

import com.chloz.test.domain.User;
import com.chloz.test.repository.DefaultDomainRepository;
import com.chloz.test.repository.UserRepository;
import com.chloz.test.service.dto.UserDto;
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
public class UserResourceIT extends AbstractDomainResourceIT<Long, User, UserDto> {

	@Autowired
	private UserRepository repository;
	@Override
	public DefaultDomainRepository<User, Long> getRepository() {
		return this.repository;
	}

	@Override
	public User generateEntity() {
		User entity = new User();
		entity.setLogin("nPPL3M3evqAvuAc7SPr");
		entity.setEmail("eeBJ");
		entity.setPhone("01t");
		entity.setPhoneChecked(false);
		entity.setAccountLocked(true);
		entity.setEmailChecked(true);
		entity.setActivated(false);
		entity.setAttempts(104);
		entity.setFirstName("ipHdmVNOkl");
		entity.setName("iI78h");
		entity.setLang("L8f");
		entity.setDisabled(false);
		return entity;
	}

	@Override
	public Long getEntityId(User entity) {
		return entity.getId();
	}

	@Override
	public String getApiBasePath() {
		return Constants.API_BASE_PATH + "/users";
	}

	@Override
	public ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, User entity) {
		return new ResultMatcher[]{jsonPath(jsonParentPath + "id", is(entity.getId().intValue())),
				jsonPath(jsonParentPath + "login", is(entity.getLogin())),
				jsonPath(jsonParentPath + "email", is(entity.getEmail())),
				jsonPath(jsonParentPath + "phone", is(entity.getPhone())),
				jsonPath(jsonParentPath + "phoneChecked", is(entity.getPhoneChecked())),
				jsonPath(jsonParentPath + "accountLocked", is(entity.getAccountLocked())),
				jsonPath(jsonParentPath + "emailChecked", is(entity.getEmailChecked())),
				jsonPath(jsonParentPath + "activated", is(entity.getActivated())),
				jsonPath(jsonParentPath + "attempts", is(entity.getAttempts().intValue())),
				jsonPath(jsonParentPath + "firstName", is(entity.getFirstName())),
				jsonPath(jsonParentPath + "name", is(entity.getName())),
				jsonPath(jsonParentPath + "lang", is(entity.getLang())),};
	}

	@Override
	public ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, UserDto dto, boolean onlyVerifyIdExistence) {
		return new ResultMatcher[]{
				onlyVerifyIdExistence
						? jsonPath(jsonParentPath + "id").exists()
						: jsonPath(jsonParentPath + "id", is(dto.getId().intValue())),
				jsonPath(jsonParentPath + "login", is(dto.getLogin())),
				jsonPath(jsonParentPath + "email", is(dto.getEmail())),
				jsonPath(jsonParentPath + "phone", is(dto.getPhone())),
				jsonPath(jsonParentPath + "phoneChecked", is(dto.getPhoneChecked())),
				jsonPath(jsonParentPath + "accountLocked", is(dto.getAccountLocked())),
				jsonPath(jsonParentPath + "emailChecked", is(dto.getEmailChecked())),
				jsonPath(jsonParentPath + "activated", is(dto.getActivated())),
				jsonPath(jsonParentPath + "attempts", is(dto.getAttempts().intValue())),
				jsonPath(jsonParentPath + "firstName", is(dto.getFirstName())),
				jsonPath(jsonParentPath + "name", is(dto.getName())),
				jsonPath(jsonParentPath + "lang", is(dto.getLang())),};
	}

	@Override
	public Long generateUnexistingEntityId() {
		return Long.valueOf(999);
	}

	@Override
	protected UserDto generateDtoForUpdate(User entity) {
		UserDto dto = this.generateDto();
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public UserDto generateDto() {
		UserDto dto = new UserDto();
		dto.setLogin("v");
		dto.setEmail("UQXq18wv2j0gNc");
		dto.setPhone("HtnxSZTEe8DixgIK");
		dto.setPhoneChecked(true);
		dto.setAccountLocked(false);
		dto.setEmailChecked(true);
		dto.setActivated(true);
		dto.setAttempts(329);
		dto.setFirstName("7cVRner1FDPKyGq");
		dto.setName("suUfkjEXkSd2");
		dto.setLang("");
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