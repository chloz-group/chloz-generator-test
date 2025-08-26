package com.chloz.test.repository.base;

import com.chloz.test.domain.Role;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface RoleRepositoryBase extends SimpleDomainRepository<Role, String> {

	Optional<Role> findByName(String name);

}