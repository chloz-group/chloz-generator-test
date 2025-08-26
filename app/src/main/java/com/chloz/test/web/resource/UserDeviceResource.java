package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.filter.SimpleUserDeviceFilter;
import com.chloz.test.dataaccess.filter.UserDeviceFilter;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.service.dto.UserDeviceDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.UserDeviceResourceBase;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/userDevices")
public class UserDeviceResource extends UserDeviceResourceBase {

	public UserDeviceResource(UserDeviceService service) {
		super(service);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<UserDeviceDto> getById(@NotNull @PathVariable("id") Long id,
			@Nullable @RequestParam("graph") String graph) {
		return super.getById(id, graph);
	}

	@Override
	@GetMapping
	public PagedModel<UserDeviceDto> getAllPaginate(@ParameterObject Pageable pageable,
			@Nullable @RequestParam("graph") String graph) {
		return super.getAllPaginate(pageable, graph);
	}

	@Override
	@PostMapping
	public ResponseEntity<UserDeviceDto> create(@Valid @RequestBody UserDeviceDto dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.create(dto, graph);
	}

	@Override
	@PostMapping(path = "bulk")
	public ResponseEntity<List<UserDeviceDto>> bulkCreate(@Valid @RequestBody List<UserDeviceDto> dto,
			@Nullable @RequestParam("graph") String graph) {
		return super.bulkCreate(dto, graph);
	}

}