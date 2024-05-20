package com.chloz.test.repository;

import com.chloz.test.domain.User;
import com.chloz.test.repository.base.UserRepositoryBase;
import java.util.Optional;

public interface UserRepository extends UserRepositoryBase {

	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByLogin(String login);

	Optional<User> findOneByPhone(String phone);

}