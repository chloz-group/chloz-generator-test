package com.chloz.test.dataaccess.base;

import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.dataaccess.FilterDomainDataAccess;
import com.chloz.test.domain.User;
import java.util.Optional;

public interface UserDataAccessBase extends FilterDomainDataAccess<User, Long, SimpleUserFilter> {

	Optional<User> findByLogin(String login);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);

}