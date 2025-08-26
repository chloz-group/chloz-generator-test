package com.chloz.test.repository.base;

import com.chloz.test.domain.User;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepositoryBase extends SimpleDomainRepository<User, Long> {

	Optional<User> findByLogin(String login);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);

}