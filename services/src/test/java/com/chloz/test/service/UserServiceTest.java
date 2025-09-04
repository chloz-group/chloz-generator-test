package com.chloz.test.service;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.domain.User;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Disabled("Partial implementation. Not working for now.")
public class UserServiceTest extends AbstractDomainServiceTest<User, Long, UserDto> {

	@Mock
	protected UserDataAccess dataAccess;

	@Mock
	protected UserMapper mapper;

	protected UserService service;
	@BeforeEach
	void setUp() {
		// service = new UserServiceImpl(dataAccess, mapper);
		this.setService(service);
		this.setDataAccess(this.dataAccess);
		this.setMapper(mapper);
	}

	@Override
	protected UserDto createDto() {
		UserDto dto = new UserDto();
		dto.setId(1L);
		dto.setName("Douala");
		return dto;
	}

	@Override
	protected User createEntity() {
		User user = new User();
		user.setId(1L);
		user.setName("Douala");
		return user;
	}

	@Override
	protected Long getEntityId(User entity) {
		return entity.getId();
	}

	@Override
	protected void assertDtoMatchesEntity(UserDto dto, User entity) {
		assertThat(dto.getId()).isEqualTo(entity.getId());
		assertThat(dto.getName()).isEqualTo(entity.getName());
	}

}