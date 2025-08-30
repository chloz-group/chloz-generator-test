package com.chloz.test.repository.base;

import com.chloz.test.domain.Role;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface RoleRepositoryBase extends DefaultDomainRepository<Role, String> {

	Optional<Role> findByName(String name);

}