package com.chloz.test.web.resource;

import com.chloz.test.dataaccess.UserDataAccess;
import com.chloz.test.domain.User;
import com.chloz.test.domain.base.UserDeviceBase;
import com.chloz.test.service.dto.NotificationDto;
import com.chloz.test.service.UserDeviceService;
import com.chloz.test.service.messaging.DefaultMessagingService;
import com.chloz.test.service.messaging.PushNotificationMessagingService;
import com.chloz.test.web.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = Constants.API_BASE_PATH + "/notification")
public class NotificationResource {

	private final UserDataAccess userDataAccess;

	private final UserDeviceService userDeviceService;

	private final PushNotificationMessagingService messagingService;
	public NotificationResource(UserDataAccess userDataAccess, UserDeviceService userDeviceService,
			PushNotificationMessagingService messagingService) {
		this.userDataAccess = userDataAccess;
		this.userDeviceService = userDeviceService;
		this.messagingService = messagingService;
	}

	@PostMapping(path = "send-to-users")
	public ResponseEntity<String> sendToUsers(@Valid @RequestBody NotificationDto dto) throws IOException {
		List<String> tokens = null;
		if (dto.getUserFilter() != null) {
			List<User> users = userDataAccess.findByFilter(dto.getUserFilter(), "*");
			if (users != null && !users.isEmpty()) {
				tokens = this.userDeviceService.findDevicesOwnedByUsers(users).stream().map(UserDeviceBase::getToken)
						.toList();
			}
		}
		Map<String, Object> params = new HashMap<>();
		if (dto.getData() != null) {
			params.putAll(dto.getData());
		}
		params.put(DefaultMessagingService.PUSH_PARAM_DEVICES, tokens);
		if (dto.getTopic() != null) {
			params.put(DefaultMessagingService.PUSH_PARAM_TOPIC, dto.getTopic());
		}
		messagingService.sendMessage(dto.getMessage(), dto.getTitle(), null, params);
		return ResponseEntity.ok("");
	}

}