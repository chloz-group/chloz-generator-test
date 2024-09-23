package com.chloz.test.service.impl;

import com.chloz.test.domain.QUserDevice;
import com.chloz.test.domain.User;
import com.chloz.test.domain.UserDevice;
import com.chloz.test.domain.base.UserDeviceBase;
import com.chloz.test.repository.UserDeviceRepository;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.service.base.UserDeviceServiceBaseImplBase;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDeviceServiceImpl extends UserDeviceServiceBaseImplBase implements UserDeviceService {

	private final UserDeviceRepository repository;
	public UserDeviceServiceImpl(UserDeviceRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public void deleteToken(String token) {
		this.repository.findByToken(token).ifPresent(userDevice -> {
			repository.delete(userDevice);
			// Deprecated code after use of Hibernate @SQLRestriction
			/*
			 * userDevice.setDisabled(true); userDevice.setDeleted(true);
			 * repository.save(userDevice);
			 */
		});
	}

	@Override
	public Optional<UserDevice> findByToken(String token) {
		return this.repository.findByToken(token);
	}

	@Override
	public List<UserDevice> findDevicesOwnedByUsers(List<User> users) {
		List<Long> ids = users.stream().map(User::getId).toList();
		QUserDevice root = QUserDevice.userDevice;
		// Deprecated code after use of Hibernate @SQLRestriction
		Predicate predicate = ExpressionUtils.or(root.deleted.isNull(), root.deleted.isFalse());
		predicate = ExpressionUtils.and(predicate, ExpressionUtils.or(root.disabled.isNull(), root.disabled.isFalse()));
		predicate = ExpressionUtils.and(predicate, root.createdBy._super.id.in(ids));
		return this.findAll(predicate, "*");
	}

}