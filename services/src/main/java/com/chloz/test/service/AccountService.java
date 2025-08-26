package com.chloz.test.service;

import com.chloz.test.service.dto.*;

/**
 * Service class for managing user's account and authentication
 */
public interface AccountService {

	public UserDto registerAccount(UserRegistrationDto dto);

	public UserDto getAccount();

	public void updateAccount(UserDto userDTO);

	public void changePassword(PasswordChangeDto passwordChangeDto);

	public AuthTokenDto authorize(LoginDto loginDto);

	public void requestAuthenticationCode(AuthenticationCodeRequestDto lp);

}