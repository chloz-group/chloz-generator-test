package com.chloz.test.repository.base;

import com.chloz.test.domain.UserDevice;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean
public interface UserDeviceRepositoryBase extends SimpleDomainRepository<UserDevice, Long> {
}