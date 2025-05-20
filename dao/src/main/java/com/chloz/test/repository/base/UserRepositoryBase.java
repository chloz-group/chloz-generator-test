package com.chloz.test.repository.base;

import com.chloz.test.domain.User;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryBase extends SimpleDomainRepository<User, Long> {
}