package com.chloz.test.service.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.UserDeviceRepository;
import com.chloz.test.service.impl.SimpleDomainServiceImpl;
import java.util.*;

public class UserDeviceServiceBaseImplBase extends SimpleDomainServiceImpl<UserDevice, Long>
		implements
			UserDeviceServiceBase {

	private final UserDeviceRepository repository;
	public UserDeviceServiceBaseImplBase(UserDeviceRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Optional<UserDevice> findById(Long id) {
		return repository.findById(id);
	}

}