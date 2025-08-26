package com.chloz.test.web.resource;

import com.chloz.test.service.AccountService;
import com.chloz.test.service.dto.UserDto;
import com.chloz.test.service.dto.UserRegistrationDto;
import com.chloz.test.service.dto.PasswordChangeDto;
import com.chloz.test.service.dto.AuthTokenDto;
import com.chloz.test.service.dto.LoginDto;
import com.chloz.test.service.dto.AuthenticationCodeRequestDto;
import com.chloz.test.web.Constants;
import com.chloz.test.web.resource.base.AccountResourceBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping(path = Constants.API_BASE_PATH)
public class AccountResource extends AccountResourceBase {

	public AccountResource(AccountService accountService) {
		super(accountService);
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