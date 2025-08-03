package com.chloz.test.repository.base;

import com.chloz.test.domain.User;
import com.chloz.test.domain.VerificationCode;
import com.chloz.test.domain.enums.VerificationType;
import com.chloz.test.repository.SimpleDomainRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;

@NoRepositoryBean
public interface VerificationCodeRepositoryBase extends SimpleDomainRepository<VerificationCode, Long> {

	@Query("select verificationCode from VerificationCode verificationCode where verificationCode.usedFor.login = ?#{principal.username}")
	List<VerificationCode> findByUsedForIsCurrentUser();

	List<VerificationCode> findByUsedForAndVerificationTypeOrderByExpiryDateDesc(User user,
			VerificationType verificationType);

}