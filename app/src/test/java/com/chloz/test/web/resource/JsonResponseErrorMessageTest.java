package com.chloz.test.web.resource;

import com.chloz.test.domain.User;
import com.chloz.test.service.UserService;
import com.chloz.test.web.dto.LoginDto;
import com.chloz.test.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JsonResponseErrorMessageTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	@BeforeEach
	void setup(WebApplicationContext wac) {
		// this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		Optional<User> opt = this.userService.findOneByLogin("user1");
		if (opt.isEmpty()) {
			User user = User.builder().login("user1").email("user1@test.org").name("User1").build();
			this.userService.createNewUser(user, "user1");
		}
	}

	@AfterEach
	void afterEach() {
	}

	@Test
	void testParseErrorCodeTranslateFr() throws Exception {
		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper
						.writeValueAsString(LoginDto.builder().username("user3").password("145555").build()))
				.header("Accept-Language", "fr")).andExpect(status().isUnauthorized())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.errorCode").value("A006"))
				.andExpect(jsonPath("$.detail").value("Compte utilisateur user3 introuvable"))
				.andExpect(jsonPath("$.status").value(401));
	}

	@Test
	void testParseErrorCodeTranslateEn() throws Exception {
		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper
						.writeValueAsString(LoginDto.builder().username("user3").password("145555").build()))
				.header("Accept-Language", "en")).andExpect(status().isUnauthorized())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.errorCode").value("A006"))
				.andExpect(jsonPath("$.detail").value("User account user3 not found"))
				.andExpect(jsonPath("$.status").value(401));
	}

	@Test
	void testBusinessExceptionFr() throws Exception {
		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						UserRegistrationDto.builder().login("u001").password("a").name("U001").build()))
				.queryParam("lang", "fr")).andExpect(status().isUnauthorized())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.errorCode").value("A002"))
				.andExpect(jsonPath("$.detail").value("Mot de passe invalide"))
				.andExpect(jsonPath("$.status").value(401));
	}

	@Test
	void testBusinessExceptionEn() throws Exception {
		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						UserRegistrationDto.builder().login("u001").password("a").name("U001").build()))
				.queryParam("lang", "en")).andExpect(status().isUnauthorized())
				.andExpect(content().contentType("application/problem+json"))
				.andExpect(jsonPath("$.errorCode").value("A002"))
				.andExpect(jsonPath("$.detail").value("Invalid password")).andExpect(jsonPath("$.status").value(401));
	}

}