package com.chloz.test.service.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleParamsFilter;
import com.chloz.test.dataaccess.ParamsDataAccess;
import com.chloz.test.domain.Params;
import com.chloz.test.service.Constants;
import com.chloz.test.service.dto.ParamsDto;
import com.chloz.test.service.mapper.ParamsMapper;
import com.chloz.test.service.impl.DefaultDomainServiceImpl;
import java.util.*;

public class ParamsServiceBaseImplBase extends DefaultDomainServiceImpl<Params, Long, ParamsDto, SimpleParamsFilter>
		implements
			ParamsServiceBase {

	private final ParamsDataAccess dataAccess;

	private final ParamsMapper mapper;
	public ParamsServiceBaseImplBase(ParamsDataAccess dataAccess, ParamsMapper mapper) {
		super(dataAccess, mapper);
		this.dataAccess = dataAccess;
		this.mapper = mapper;
	}

	@Override
	public Optional<ParamsDto> findById(Long id) {
		return dataAccess.findById(id).map(v -> mapper.mapToDto(v, "*"));
	}

	@Override
	public ParamsDto update(ParamsDto dto, String graph) {
		if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		return super.update(dto, graph);
	}

	@Override
	public List<ParamsDto> bulkUpdate(List<ParamsDto> list, String graph) {
		list.forEach(dto -> {
			if (dto.getId() == null || dataAccess.findById(dto.getId()).isEmpty()) {
				throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
			}
		});
		return super.bulkUpdate(list, graph);
	}

	@Override
	public ParamsDto updateFields(ParamsDto dto, String graph) {
		Optional<Params> opt = dataAccess.findById(dto.getId());
		if (dto.getId() == null || opt.isEmpty()) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_NOT_FOUND, null, 404);
		}
		// set fields
		Params ent = opt.get();
		ent.setId(dto.getId());
		ent.setParamKey(dto.getParamKey());
		ent.setStringValue(dto.getStringValue());
		ent.setNumberValue(dto.getNumberValue());
		ent.setDecimalValue(dto.getDecimalValue());
		ent.setBooleanValue(dto.getBooleanValue());
		// end of set fields
		ent = this.dataAccess.save(ent);
		return mapper.mapToDto(ent, graph);
	}

}