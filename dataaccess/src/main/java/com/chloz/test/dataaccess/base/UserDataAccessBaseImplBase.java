package com.chloz.test.dataaccess.base;

import com.chloz.test.domain.User;
import com.chloz.test.repository.UserRepository;
import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.dataaccess.impl.FilterDomainDataAccessImpl;
import com.chloz.test.dataaccess.query.UserQueryBuilder;
import java.util.Optional;

public class UserDataAccessBaseImplBase extends FilterDomainDataAccessImpl<User, Long, SimpleUserFilter>
		implements
			UserDataAccessBase {

	private final UserRepository repository;

	private final UserQueryBuilder queryBuilder;
	public UserDataAccessBaseImplBase(UserRepository repository, UserQueryBuilder queryBuilder) {
		super(repository, queryBuilder);
		this.repository = repository;
		this.queryBuilder = queryBuilder;
	}

	@Override
	public Optional<User> findByLogin(String login) {
		return this.repository.findByLogin(login);
	};

	@Override
	public Optional<User> findByEmail(String email) {
		return this.repository.findByEmail(email);
	};

	@Override
	public Optional<User> findByPhone(String phone) {
		return this.repository.findByPhone(phone);
	};

}