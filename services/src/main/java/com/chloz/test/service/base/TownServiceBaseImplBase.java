package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleTownFilter;
import com.chloz.test.dataaccess.TownDataAccess;
import com.chloz.test.domain.Town;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.TownDto;
import com.chloz.test.service.mapper.TownMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

public class TownServiceBaseImplBase extends DefaultDomainServiceImpl<Town, Long, TownDto, SimpleTownFilter>
		implements
			TownServiceBase {

	private final TownDataAccess dataAccess;

	private final TownMapper mapper;
	public TownServiceBaseImplBase(TownDataAccess dataAccess, TownMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public Optional<TownDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public TownDto update(TownDto dto, String graph) {
		if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<TownDto> bulkUpdate(List<TownDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public TownDto updateFields(TownDto dto, String graph) {
		Optional<Town> opt = dataAccess.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		Town ent = opt.get();
		ent.setId(dto.getId());
		ent.setName(dto.getName());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}