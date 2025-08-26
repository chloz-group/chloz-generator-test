package com.chloz.test.service.mapper;

import com.chloz.test.domain.User;
import com.chloz.test.domain.UserGroup;
import com.chloz.test.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;
	@Test
	public void testMap() {
		User user = User.builder().id(1L).email("test@test.cm")
				.groups(List.of(UserGroup.builder().id(1l).disabled(false).build(),
						UserGroup.builder().id(2l).disabled(true).build()))
				.login("test").password("paswd").phone("00000000").build();
		UserDto dto = userMapper.mapToDto(user, "id");
		assertEquals(user.getId(), dto.getId());
		assertNull(dto.getEmail());
		assertNull(dto.getGroups());
		assertNull(dto.getLogin());
		assertNull(dto.getPhone());
		///
		dto = userMapper.mapToDto(user, "id,groups");
		assertEquals(user.getId(), dto.getId());
		assertNull(dto.getEmail());
		assertEquals(2, dto.getGroups().size());
		UserDto finalDto = dto;
		assertTrue(() -> finalDto.getGroups().stream().allMatch(g -> g.getDisabled() == null));
		assertNull(dto.getLogin());
		assertNull(dto.getPhone());
		//
		dto = userMapper.mapToDto(user, "id,groups,groups.disabled");
		assertEquals(user.getId(), dto.getId());
		assertNull(dto.getEmail());
		assertEquals(2, dto.getGroups().size());
		UserDto finalDto2 = dto;
		assertTrue(() -> finalDto2.getGroups().stream().allMatch(g -> g.getId() == null));
		assertTrue(() -> finalDto2.getGroups().stream().allMatch(g -> g.getDisabled() != null));
		assertNull(dto.getLogin());
		assertNull(dto.getPhone());
		dto = userMapper.mapToDto(user, "email,login");
		assertNull(dto.getId());
		assertNotNull(dto.getEmail());
		assertNotNull(dto.getLogin());
		assertNull(dto.getPhone());
	}

}