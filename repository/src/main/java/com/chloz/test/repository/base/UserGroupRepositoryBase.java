package com.chloz.test.repository.base;

import com.chloz.test.domain.UserGroup;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface UserGroupRepositoryBase extends SimpleDomainRepository<UserGroup, Long> {
}