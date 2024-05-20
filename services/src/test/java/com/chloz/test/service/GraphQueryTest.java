package com.chloz.test.service;

import com.chloz.test.domain.User;
import com.chloz.test.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = ServiceConfigurationTest.class)
public class GraphQueryTest {

	@Autowired
	private UserService userService;
	// @Test
	public void testGraph() {
		// List<UserGroup> groups = List.of(UserGroup.builder().id(1L).build(),
		// UserGroup.builder().id(2L).build());
		List<UserGroup> groups = null;
		User u = User.builder().id(1L).email("test@test.cm").groups(groups).login("test").password("paswd")
				.phone("00000000").build();
		userService.save(u);
		User user = userService.findById(1L, "id").get();
	}

}