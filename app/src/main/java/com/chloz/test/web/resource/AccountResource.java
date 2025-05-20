package com.chloz.test.web.resource;

import com.chloz.test.service.UserService;
import com.chloz.test.web.dto.*;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.resource.base.AccountResourceBase;
import com.chloz.test.web.security.jwt.TokenProvider;
import com.chloz.test.web.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping(path = Constants.API_BASE_PATH)
public class AccountResource extends AccountResourceBase {

	public AccountResource(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager,
			TokenProvider tokenProvider) {
		super(userService, userMapper, authenticationManager, tokenProvider);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public ResponseEntity<UserDto> registerAccount(@Valid @RequestBody UserRegistrationDto dto) {
		return super.registerAccount(dto);
	}

	@GetMapping("/authenticate")
	@Override
	public String isAuthenticated(HttpServletRequest request) {
		return super.isAuthenticated(request);
	}

	@GetMapping("/account")
	@Override
	public UserDto getAccount() {
		return super.getAccount();
	}

	@PostMapping("/account")
	@Override
	public void updateAccount(@Valid @RequestBody UserDto userDTO) {
		super.updateAccount(userDTO);
	}

	@PostMapping(path = "/account/change-password")
	@Override
	public void changePassword(@RequestBody PasswordChangeDto dto) {
		super.changePassword(dto);
	}

	@PostMapping("/authenticate")
	@Override
	public ResponseEntity<AuthTokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
		return super.authorize(loginDto);
	}

	@PostMapping("/request-authentication-code")
	@Override
	public void requestAuthenticationCode(@Valid @RequestBody AuthenticationCodeRequestDto lp) {
		super.requestAuthenticationCode(lp);
	}

}