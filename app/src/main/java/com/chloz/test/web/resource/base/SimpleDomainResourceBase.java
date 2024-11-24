package com.chloz.test.web.resource.base;

import com.chloz.test.service.SimpleDomainService;
import com.chloz.test.web.mapper.DomainMapper;
import com.chloz.test.web.resource.DefaultResource;
import com.chloz.test.web.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Transactional
public class SimpleDomainResourceBase<T, ID, DTO> extends DefaultResource {

	private final DomainMapper<T, DTO> mapper;

	private final SimpleDomainService<T, ID> service;
	public <S extends SimpleDomainService<T, ID>, M extends DomainMapper<T, DTO>> SimpleDomainResourceBase(S service,
			M mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public ResponseEntity<DTO> create(@Valid DTO dto, String graph) {
		this.handleDtoBeforeCreate(dto);
		T model = this.mapper.entityFromDto(dto);
		this.handleModelBeforeCreate(model, dto);
		model = this.service.save(model);
		this.handleModelAfterCreate(model, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(model, graph));
	}

	public ResponseEntity<List<DTO>> bulkCreate(@Valid List<DTO> list, String graph) {
		List<T> toSaveList = new ArrayList<>();
		for (DTO dto : list) {
			this.handleDtoBeforeCreate(dto);
			T model = this.mapper.entityFromDto(dto);
			this.handleModelBeforeCreate(model, dto);
			toSaveList.add(model);
		}
		Iterable<T> savedList = this.service.saveAll(toSaveList);
		Iterator<T> it = savedList.iterator();
		List<DTO> res = new ArrayList<>(list.size());
		while (it.hasNext()) {
			T model = it.next();
			this.handleModelAfterCreate(model, null);
			res.add(mapper.mapToDto(model, graph));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	public ResponseEntity<DTO> update(@Valid DTO dto, String graph) {
		this.handleDtoBeforeUpdate(dto);
		T model = this.mapper.entityFromDto(dto);
		this.handleModelBeforeUpdate(model, dto);
		model = this.service.save(model);
		this.handleModelAfterUpdate(model, dto);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.mapToDto(model, graph));
	}

	public ResponseEntity<List<DTO>> bulkUpdate(@Valid List<DTO> list, String graph) {
		List<T> toSaveList = new ArrayList<>();
		for (DTO dto : list) {
			this.handleDtoBeforeUpdate(dto);
			T model = this.mapper.entityFromDto(dto);
			this.handleModelBeforeUpdate(model, dto);
			toSaveList.add(model);
		}
		Iterable<T> savedList = this.service.saveAll(toSaveList);
		Iterator<T> it = savedList.iterator();
		List<DTO> res = new ArrayList<>(list.size());
		while (it.hasNext()) {
			T model = it.next();
			this.handleModelAfterUpdate(model, null);
			res.add(mapper.mapToDto(model, graph));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	public PagedModel<DTO> getAllPaginate(Pageable pageable, String graph) {
		Page<T> page = this.service.findAll(graph, pageable);
		List<DTO> dtoList = page.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return new PagedModel<>(new PageImpl<>(dtoList, pageable, page.getTotalElements()));
	}

	public ResponseEntity<DTO> getById(@NotNull ID id, String graph) {
		Optional<T> model = this.service.findById(id, graph);
		return ResponseUtil.wrapOrNotFound(model.map(t -> mapper.mapToDto(t, graph)));
	}

	public ResponseEntity<Void> updateEnableStatus(@NotNull List<ID> ids, @NotNull Boolean value) {
		this.service.updateEnableStatus(ids, value);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Void> deleteById(@NotNull ID id) {
		this.service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Void> deleteAllById(@NotNull List<ID> ids) {
		this.service.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Handle the DTO before it's saved. Do some work as validating the object,
	 * etc...
	 *
	 * @param dto
	 */
	protected void handleDtoBeforeCreate(DTO dto) {
		// Method to be implemented in sub class
	}

	/**
	 * Handle the DTO before it's saved. Do some work as validating the object,
	 * etc...
	 *
	 * @param dto
	 */
	protected void handleDtoBeforeUpdate(DTO dto) {
		// Method to be implemented in sub class
	}

	/**
	 * Do some work on the model before it's saved.
	 *
	 * @param dto
	 * @param model
	 */
	protected void handleModelBeforeCreate(T model, DTO dto) {
	}

	/**
	 * Do some work on the model before it's updated.
	 *
	 * @param dto
	 * @param model
	 */
	protected void handleModelBeforeUpdate(T model, DTO dto) {
	}

	/**
	 * Do some work on the model after it has been created.
	 * 
	 * @param model
	 * @param dto
	 */
	protected void handleModelAfterCreate(T model, DTO dto) {
	}

	/**
	 * Do some work on the model after it has been updated.
	 * 
	 * @param model
	 * @param dto
	 */
	protected void handleModelAfterUpdate(T model, DTO dto) {
	}

}