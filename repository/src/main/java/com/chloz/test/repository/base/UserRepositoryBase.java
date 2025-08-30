package com.chloz.test.repository.base;

import com.chloz.test.domain.User;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepositoryBase extends DefaultDomainRepository<User, Long> {

	Optional<User> findByLogin(String login);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);

}