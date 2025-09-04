package com.chloz.test.web.resource.base;

import com.chloz.test.common.exception.BusinessException;
import com.chloz.test.dataaccess.filter.SimpleCountryFilter;
import com.chloz.test.service.CountryService;
import com.chloz.test.service.dto.CountryDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.DefaultDomainResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

public class CountryResourceBase extends DefaultDomainResource<String, CountryDto, SimpleCountryFilter> {

	private final CountryService service;
	public CountryResourceBase(CountryService service) {
		super(service);
		this.service = service;
	}

	public ResponseEntity<CountryDto> update(@NotNull String code, @Valid CountryDto dto, String graph) {
		if (dto.getCode() != null && !Objects.equals(code, dto.getCode())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setCode(code);
		return super.update(dto, graph);
	}

	public ResponseEntity<CountryDto> partialUpdate(@NotNull String code, CountryDto dto, String graph) {
		if (dto.getCode() != null && !Objects.equals(code, dto.getCode())) {
			throw new BusinessException(Constants.ERROR_MESSAGE_OBJECT_ID_DIFFERENT_FROM_ID_PARAM, null, 400);
		}
		dto.setCode(code);
		return ResponseEntity.status(HttpStatus.OK).body(this.service.partialUpdate(dto, graph));
	}

}