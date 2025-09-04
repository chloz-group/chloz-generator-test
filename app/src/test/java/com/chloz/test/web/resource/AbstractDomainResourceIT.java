package com.chloz.test.web.resource;

import com.chloz.test.repository.DefaultDomainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base class for to test a domain resource api
 *
 * @param <I>
 *            The entity id type
 * @param <T>
 *            The Entity Type
 * @param <D>
 *            The Dto type of the entity
 */
@Disabled("Partial implementation. Not working for now.")
public abstract class AbstractDomainResourceIT<I, T, D> {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	protected T createdEntity;
	public abstract DefaultDomainRepository<T, I> getRepository();

	public abstract boolean isInsertEnable();

	public abstract boolean isUpdateEnable();

	public abstract boolean isFilterEnable();

	/**
	 * Generate a new entity object
	 *
	 * @return
	 */
	public abstract T generateEntity();

	public abstract D generateDto();

	public abstract I getEntityId(T entity);

	public abstract ResultMatcher[] expectAllEntityJsonPath(String jsonParentPath, T entity);

	public abstract ResultMatcher[] expectAllDtoJsonPath(String jsonParentPath, D dto, boolean onlyVerifyIdExistence);

	public abstract String getApiBasePath();

	public abstract I generateUnexistingEntityId();

	protected abstract D generateDtoForUpdate(T createdEntity);

	@BeforeEach
	void setUp() {
		this.createdEntity = this.getRepository().save(this.generateEntity());
	}

	@AfterEach
	void cleanUp() {
		this.getRepository().delete(createdEntity);
	}

	@Test
	void testGetById_success() throws Exception {
		mockMvc.perform(get(getApiBasePath() + "/{id}", getEntityId(createdEntity)).param("graph", "*")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpectAll(this.expectAllEntityJsonPath("$.", createdEntity));
	}

	@Test
	void testGetById_notFound() throws Exception {
		mockMvc.perform(get(getApiBasePath() + "/{id}", generateUnexistingEntityId()).param("graph", "*")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	void testCreate() throws Exception {
		if (!isInsertEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		D dto = generateDto();
		mockMvc.perform(post(getApiBasePath()).param("graph", "*").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$").exists()).andExpect(jsonPath("$.*").isNotEmpty())
				.andExpect(jsonPath("$[?(@ != null)]").exists());
		assertThat(repositoryCountBefore + 1).isEqualTo(this.getRepository().count());
	}

	@Test
	void testUpdate() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		D dto = generateDtoForUpdate(createdEntity);
		mockMvc.perform(put(getApiBasePath() + "/{id}", getEntityId(createdEntity)).param("graph", "*")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk()).andExpectAll(expectAllDtoJsonPath("$.", dto, false));
		assertThat(repositoryCountBefore).isEqualTo(this.getRepository().count());
	}

	@Test
	void testPartialUpdate() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		D dto = generateDtoForUpdate(createdEntity);
		mockMvc.perform(patch(getApiBasePath() + "/{id}", getEntityId(createdEntity)).param("graph", "*")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk()).andExpect(jsonPath("$").exists()).andExpect(jsonPath("$.*").isNotEmpty())
				.andExpect(jsonPath("$[?(@ != null)]").exists());
		// Todo : write the code to check that fields not provided in the DTO where not
		// updated
		assertThat(repositoryCountBefore).isEqualTo(this.getRepository().count());
	}

	@Test
	void testBulkCreate() throws Exception {
		if (!isInsertEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		D dto1 = generateDto();
		D dto2 = generateDto();
		mockMvc.perform(post(getApiBasePath() + "/bulk").param("graph", "*").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(List.of(dto1, dto2)))).andExpect(status().isCreated())
				.andExpect(jsonPath("$", hasSize(2))).andExpectAll(expectAllDtoJsonPath("$[0].", dto1, true))
				.andExpectAll(expectAllDtoJsonPath("$[1].", dto2, true));
		assertThat(repositoryCountBefore + 2).isEqualTo(this.getRepository().count());
	}

	@Test
	void testBulkUpdate() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		D dto = generateDtoForUpdate(this.createdEntity);
		mockMvc.perform(put(getApiBasePath() + "/bulk").param("graph", "*").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(List.of(dto)))).andExpect(status().isOk())
				.andExpectAll(expectAllDtoJsonPath("$[0].", dto, false));
		assertThat(repositoryCountBefore).isEqualTo(this.getRepository().count());
	}

	@Test
	void testUpdateEnableStatus() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		mockMvc.perform(
				post(getApiBasePath() + "/enable-status/{ids}", getEntityId(createdEntity)).param("status", "false"))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteById() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		long repositoryCountBefore = this.getRepository().count();
		mockMvc.perform(delete(getApiBasePath() + "/{id}", getEntityId(createdEntity)))
				.andExpect(status().isNoContent());
		assertThat(repositoryCountBefore - 1).isEqualTo(this.getRepository().count());
	}

	@Test
	void testDeleteAllById() throws Exception {
		if (!isUpdateEnable()) {
			return;
		}
		T other = generateEntity();
		other = getRepository().save(other);
		String ids = getEntityId(createdEntity) + "," + getEntityId(other);
		long repositoryCountBefore = this.getRepository().count();
		mockMvc.perform(delete(getApiBasePath() + "/bulk/{ids}", ids)).andExpect(status().isNoContent());
		assertThat(repositoryCountBefore - 2).isEqualTo(this.getRepository().count());
	}

	@Test
	void testSearch() throws Exception {
		if (!isFilterEnable()) {
			return;
		}
		long repositoryCount = this.getRepository().count();
		String filterJson = "{}"; // empty filter
		mockMvc.perform(post(getApiBasePath() + "/search").param("graph", "*").contentType(MediaType.APPLICATION_JSON)
				.content(filterJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize((int) repositoryCount)));
	}

}