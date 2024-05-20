package com.chloz.test.web.resource;

import com.chloz.test.service.UserService;
import com.chloz.test.web.dto.*;
import com.chloz.test.web.mapper.UserMapper;
import com.chloz.test.web.resource.base.AccountResourceBase;
import com.chloz.test.web.security.jwt.TokenProvider;
import com.chloz.test.web.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping(path = Constants.API_BASE_PATH)
public class AccountResource extends AccountResourceBase {

	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
	public AccountResource(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager,
			TokenProvider tokenProvider) {
		super(userService, userMapper, authenticationManager, tokenProvider);
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDto> registerAccount(@Valid @RequestBody UserRegistrationDto dto)
			throws URISyntaxException {
		return super.registerAccount(dto);
	}

	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		return super.isAuthenticated(request);
	}

	@GetMapping("/account")
	public UserDto getAccount() {
		return super.getAccount();
	}

	@PostMapping("/account")
	public void updateAccount(@Valid @RequestBody UserDto userDTO) {
		super.updateAccount(userDTO);
	}

	@PostMapping(path = "/account/change-password")
	public void changePassword(@RequestBody PasswordChangeDto dto) {
		super.changePassword(dto);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthTokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
		return super.authorize(loginDto);
	}

	@PostMapping("/request-authentication-code")
	public void requestAuthenticationCode(@Valid @RequestBody AuthenticationCodeRequestDto lp) {
		super.requestAuthenticationCode(lp);
	}

}