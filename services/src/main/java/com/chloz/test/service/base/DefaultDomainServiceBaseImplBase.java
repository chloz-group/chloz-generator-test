package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.DefaultDomainDataAccess;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.dataaccess.filter.SimpleFilter;
import com.chloz.test.service.exception.BadRequestException;
import com.chloz.test.service.mapper.DomainMapper;
import com.chloz.test.service.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T>
 *            The entity class
 * @param <I>
 *            The class of the entity id field
 * @param <D>
 *            The DTO class for the entity
 * @param <F>
 *            The filter class for the entity
 */
@Transactional
public abstract class DefaultDomainServiceBaseImplBase<T, I, D, F extends SimpleFilter>
		implements
			DefaultDomainServiceBase<I, D, F> {

	private final DomainMapper<T, D> mapper;

	private final DefaultDomainDataAccess<T, I> dataAccess;
	protected <S extends DefaultDomainDataAccess<T, I>, M extends DomainMapper<T, D>> DefaultDomainServiceBaseImplBase(
			S dataAccess, M mapper) {
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public D create(D dto, String graph) {
		T model = this.mapper.modelFromDto(dto);
		model = this.dataAccess.save(model);
		return mapper.mapToDto(model, graph);
	}

	@Override
	public List<D> bulkCreate(List<D> list, String graph) {
		List<T> toSaveList = new ArrayList<>();
		for (D dto : list) {
			T model = this.mapper.modelFromDto(dto);
			toSaveList.add(model);
		}
		Iterable<T> savedList = this.dataAccess.saveAll(toSaveList);
		Iterator<T> it = savedList.iterator();
		List<D> res = new ArrayList<>(list.size());
		while (it.hasNext()) {
			T model = it.next();
			res.add(mapper.mapToDto(model, graph));
		}
		return res;
	}

	public D update(D dto, String graph) {
		I id = this.getIdFromDto(dto);
		if (id == null || dataAccess.findById(id).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		T model = this.mapper.modelFromDto(dto);
		model = this.dataAccess.save(model);
		return mapper.mapToDto(model, graph);
	}

	@Override
	public D partialUpdate(D dto, String graph) {
		Optional<T> opt = this.dataAccess.findById(this.getIdFromDto(dto));
		if (opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		T model = opt.get();
		this.mapper.partialUpdate(model, dto);
		return mapper.mapToDto(this.dataAccess.save(model), graph);
	}

	@Override
	public List<D> bulkUpdate(List<D> list, String graph) {
		list.forEach(dto -> {
			I id = this.getIdFromDto(dto);
			if (id == null || dataAccess.findById(id).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		List<T> toSaveList = new ArrayList<>();
		for (D dto : list) {
			T model = this.mapper.modelFromDto(dto);
			toSaveList.add(model);
		}
		Iterable<T> savedList = this.dataAccess.saveAll(toSaveList);
		Iterator<T> it = savedList.iterator();
		List<D> res = new ArrayList<>(list.size());
		while (it.hasNext()) {
			T model = it.next();
			res.add(mapper.mapToDto(model, graph));
		}
		return res;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<D> getAllPaginate(Pageable pageable, String graph) {
		Page<T> page = this.dataAccess.findAll(graph, pageable);
		List<D> dtoList = page.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return new PageImpl<D>(dtoList, pageable, page.getTotalElements());
	}

	@Transactional(readOnly = true)
	@Override
	public D getById(I id, String graph) {
		Optional<T> model = this.dataAccess.findById(id, graph);
		return model.map(t -> mapper.mapToDto(t, graph)).orElse(null);
	}

	@Override
	public void updateEnableStatus(List<I> ids, Boolean value) {
		this.dataAccess.updateEnableStatus(ids, value);
	}

	@Override
	public void deleteById(I id) {
		this.dataAccess.deleteById(id);
	}

	@Override
	public void deleteAllById(List<I> ids) {
		this.dataAccess.deleteAllById(ids);
	}

	@Override
	public Page<D> getPageByFilter(F filter, Pageable pageable, String graph) {
		if (!(this.dataAccess instanceof FilterDomainDataAccess))
			throw new BadRequestException("Not implemented method");
		Page<T> page = ((FilterDomainDataAccess) this.dataAccess).findByFilter(filter, pageable, graph);
		List<D> dtoList = page.stream().map(t -> mapper.mapToDto(t, graph)).toList();
		return new PageImpl<>(dtoList, pageable, page.getTotalElements());
	}

	@Override
	public List<D> getAllByFilter(F filter, String graph) {
		if (!(this.dataAccess instanceof FilterDomainDataAccess))
			throw new BadRequestException("Not implemented method");
		List<T> list = ((FilterDomainDataAccess) this.dataAccess).findByFilter(filter, graph);
		return list.stream().map(t -> mapper.mapToDto(t, graph)).toList();
	}

	/**
	 * Extract the Id field value from the DTO
	 * 
	 * @param dto
	 * @return The id field from the Dto
	 */
	protected abstract I getIdFromDto(D dto);

}