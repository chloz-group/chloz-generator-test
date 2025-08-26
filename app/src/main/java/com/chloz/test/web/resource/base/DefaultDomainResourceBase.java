package com.chloz.test.web.resource.base;

import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.service.DefaultDomainService;
import com.chloz.test.web.utils.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

/**
 *
 * @param <I>
 *            The class of the entity id field
 * @param <F>
 *            The Filter class for the entity
 * @param <D>
 *            The DTO class for the entity
 */
public abstract class DefaultDomainResourceBase<I, D, F extends SimpleFilter> {

	private final DefaultDomainService<I, D, F> service;
	public <S extends DefaultDomainService<I, D, F>> DefaultDomainResourceBase(S service) {
		this.service = service;
	}

	public ResponseEntity<D> create(@Valid D dto, String graph) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(dto, graph));
	}

	public ResponseEntity<List<D>> bulkCreate(@Valid List<D> list, String graph) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.bulkCreate(list, graph));
	}

	public ResponseEntity<D> update(@Valid D dto, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(dto, graph));
	}

	public ResponseEntity<List<D>> bulkUpdate(@Valid List<D> list, String graph) {
		return ResponseEntity.status(HttpStatus.OK).body(service.bulkUpdate(list, graph));
	}

	public PagedModel<D> getAllPaginate(Pageable pageable, String graph) {
		Page<D> page = this.service.getAllPaginate(pageable, graph);
		return new PagedModel<>(page);
	}

	public ResponseEntity<D> getById(@NotNull I id, String graph) {
		Optional<D> opt = Optional.ofNullable(this.service.getById(id, graph));
		return ResponseUtil.wrapOrNotFound(opt);
	}

	public ResponseEntity<Void> updateEnableStatus(@NotNull List<I> ids, @NotNull Boolean value) {
		this.service.updateEnableStatus(ids, value);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Void> deleteById(@NotNull I id) {
		this.service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Void> deleteAllById(@NotNull List<I> ids) {
		this.service.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}

	public PagedModel<D> getPageByFilter(F filter, Pageable pageable, String graph) {
		Page<D> page = this.service.getPageByFilter(filter, pageable, graph);
		return new PagedModel<>(page);
	}

	public List<D> getAllByFilter(F filter, String graph) {
		List<D> list = this.service.getAllByFilter(filter, graph);
		return list;
	}

}