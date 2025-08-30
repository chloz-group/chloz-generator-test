package com.chloz.test.repository.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.DefaultDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface UserDeviceRepositoryBase extends DefaultDomainRepository<UserDevice, Long> {

	Optional<UserDevice> findByToken(String token);

}