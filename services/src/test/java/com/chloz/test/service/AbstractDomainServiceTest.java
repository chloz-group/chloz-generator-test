package com.chloz.test.service;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.DefaultDomainDataAccess;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.service.mapper.DomainMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Template réutilisable pour tester les services CRUD + bulk + enable-status +
 * graph
 */
@Disabled("Partial implementation. Not working for now.")
public abstract class AbstractDomainServiceTest<E, I, D> {

	public void setService(DefaultDomainService<I, D, ? extends SimpleFilter> service) {
		this.service = service;
	}

	public void setDataAccess(DefaultDomainDataAccess<E, I> dataAccess) {
		this.dataAccess = dataAccess;
	}

	public void setMapper(DomainMapper<E, D> mapper) {
		this.mapper = mapper;
	}
	private DefaultDomainService<I, D, ? extends SimpleFilter> service;

	private DefaultDomainDataAccess<E, I> dataAccess;

	private DomainMapper<E, D> mapper;
	protected abstract D createDto();

	protected abstract E createEntity();

	protected abstract I getEntityId(E entity);

	protected abstract void assertDtoMatchesEntity(D dto, E entity);

	@Test
	void testCreate() {
		E entity = createEntity();
		when(dataAccess.save(any())).thenReturn(entity);
		D dto = createDto();
		when(mapper.mapToDto(entity, "*")).thenReturn(dto);
		D result = service.create(dto, "*");
		assertDtoMatchesEntity(result, entity);
		verify(dataAccess, times(1)).save(any());
	}

	@Test
	void testUpdate() {
		E entity = createEntity();
		I id = getEntityId(entity);
		when(dataAccess.findById(id)).thenReturn(Optional.of(entity));
		when(dataAccess.save(any())).thenReturn(entity);
		D dto = createDto();
		when(mapper.mapToDto(entity, "*")).thenReturn(dto);
		D result = service.update(dto, "*");
		assertDtoMatchesEntity(result, entity);
		verify(dataAccess, times(1)).save(any());
	}

	@Test
	void update_shouldThrowWhenIdNullOrNotFound() {
		D input = createDto();
		when(dataAccess.findById(any())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.update(input, "*")).isInstanceOf(BusinessException.class)
				.hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
	}

	@Test
	void testPartialUpdate() {
		E entity = createEntity();
		I id = getEntityId(entity);
		D dto = createDto();
		when(dataAccess.findById(id)).thenReturn(Optional.of(entity));
		when(dataAccess.save(any())).thenReturn(entity);
		when(mapper.mapToDto(entity, "*")).thenReturn(dto);
		D result = service.partialUpdate(dto, "*");
		assertThat(result).isNotNull();
		verify(dataAccess, times(1)).save(any());
	}

	@Test
	void partialUpdate_shouldThrowWhenNotFound() {
		D patch = createDto();
		when(dataAccess.findById(any())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.partialUpdate(patch, "*")).isInstanceOf(BusinessException.class)
				.hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
	}

	@Test
	void testGetById() {
		E entity = createEntity();
		I id = getEntityId(entity);
		D dto = createDto();
		when(dataAccess.findById(id, "*")).thenReturn(Optional.of(entity));
		when(mapper.mapToDto(entity, "*")).thenReturn(dto);
		D result = service.getById(id, "*");
		assertDtoMatchesEntity(result, entity);
	}

	@Test
	void testDeleteById() {
		E entity = createEntity();
		I id = getEntityId(entity);
		service.deleteById(id);
		verify(dataAccess, times(1)).deleteById(id);
	}

	// -------------------- Bulk --------------------
	@Test
	void bulkUpdate_shouldValidateEachDtoAndThrowOnMissingAny() {
		D dto1 = createDto();
		D dto2 = createDto();
		when(dataAccess.findById(any())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.bulkUpdate(List.of(dto1, dto2), "*")).isInstanceOf(BusinessException.class)
				.hasMessage(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND);
	}

	@Test
	void testBulkCreate() {
		E entity = createEntity();
		when(dataAccess.saveAll(any())).thenReturn(List.of(entity));
		List<D> dtos = List.of(createDto());
		List<D> result = service.bulkCreate(dtos, "*");
		assertThat(result).hasSize(1);
	}

	@Test
	void testBulkUpdate() {
		E entity = createEntity();
		when(dataAccess.findById(any())).thenReturn(Optional.of(entity));
		when(dataAccess.saveAll(any())).thenReturn(List.of(entity));
		List<D> dtos = List.of(createDto());
		List<D> result = service.bulkUpdate(dtos, "*");
		assertThat(result).hasSize(1);
	}

	// -------------------- Enable-Status --------------------
	@Test
	void testUpdateEnableStatus() {
		E entity = createEntity();
		I id = getEntityId(entity);
		service.updateEnableStatus(List.of(id), true);
		verify(dataAccess, times(1)).updateEnableStatus(List.of(id), true);
	}

}