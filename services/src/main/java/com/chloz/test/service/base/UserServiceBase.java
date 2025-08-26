package com.chloz.test.service.base;

import com.chloz.test.dataaccess.filter.SimpleUserFilter;
import com.chloz.test.domain.User;
import com.chloz.test.domain.VerificationCode;
import com.chloz.test.domain.enums.VerificationType;
import com.chloz.test.service.DefaultDomainService;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.messaging.MessageType;
import java.util.List;
import java.util.Optional;

public interface UserServiceBase extends DefaultDomainService<Long, UserDto, SimpleUserFilter> {

	User registerNewUser(User user, String password);

	void changePassword(User user, String currentClearTextPassword, String newPassword);

	void changePassword(User user, String newPassword);

	void changePassword(Long id, String password);

	void sendActivationCodes(User user, MessageType messageType);

	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByLogin(String login);

	Optional<User> findOneByPhone(String phone);

	void createVerificationCodes(User user);

	public List<VerificationCode> findUserVerificationCodesOrderByExpiryDateDesc(User user,
			VerificationType verificationType);

	void saveVerificationCode(VerificationCode verificationCode);

}