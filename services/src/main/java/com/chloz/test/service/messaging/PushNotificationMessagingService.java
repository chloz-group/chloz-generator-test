package com.chloz.test.service.messaging;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.chloz.test.service.UserDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class PushNotificationMessagingService implements MessagingService, InitializingBean {

	private final UserDeviceService userDeviceService;

	private final Logger logger = LoggerFactory.getLogger(PushNotificationMessagingService.class);

	@Value("${fcm.GOOGLE_APPLICATION_CREDENTIALS:}")
	private String googleApplicationCredentials;
	public PushNotificationMessagingService(UserDeviceService userDeviceService) {
		this.userDeviceService = userDeviceService;
	}

	@Override
	public boolean sendMessage(String message, String title, Locale locale, Map<String, Object> messageParams)
			throws IOException {
		String topic = (String) messageParams.get(DefaultMessagingService.PUSH_PARAM_TOPIC);
		Message.Builder builder = Message.builder();
		MulticastMessage.Builder multiBuilder = MulticastMessage.builder();
		if (message != null || title != null) {
			Notification notif = Notification.builder().setBody(message).setTitle(title).build();
			builder.setNotification(notif);
			builder.setTopic(topic);
			multiBuilder.setNotification(notif);
		}
		List<String> devices = (List<String>) messageParams.get(DefaultMessagingService.PUSH_PARAM_DEVICES);
		Map<String, String> data = getAllData(messageParams);
		if (topic == null && (devices == null || devices.isEmpty())) {
			logger.warn("Would not sent the notification : one of token or topic must be specified");
			return false;
		}
		if (devices == null || devices.isEmpty()) {
			if (data != null && !data.isEmpty()) {
				builder.putAllData(data);
			}
			Message msg = builder.build();
			try {
				FirebaseMessaging.getInstance().send(msg);
			} catch (FirebaseMessagingException e) {
				throw new IllegalStateException(e);
			}
		} else if (devices.size() == 1) {
			builder.setToken(devices.get(0));
			if (data != null && !data.isEmpty()) {
				builder.putAllData(data);
			}
			Message msg = builder.build();
			try {
				FirebaseMessaging.getInstance().send(msg);
			} catch (FirebaseMessagingException e) {
				if (isInvalidToken(e)) {
					logger.warn("Deleting token {} on error {e}", devices.get(0), e);
					userDeviceService.deleteToken(devices.get(0));
				} else {
					throw new IllegalStateException(e);
				}
			}
		} else {
			multiBuilder.addAllTokens(devices);
			if (data != null && !data.isEmpty())
				builder.putAllData(data);
			MulticastMessage msg = multiBuilder.build();
			try {
				BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(msg);
				if (response.getFailureCount() > 0) {
					List<SendResponse> responses = response.getResponses();
					List<String> failedTokens = new ArrayList<>();
					for (int i = 0; i < responses.size(); i++) {
						FirebaseMessagingException e = responses.get(i).getException();
						if (!responses.get(i).isSuccessful() && isInvalidToken(e)) {
							// The order of responses corresponds to the order of the registration tokens.
							failedTokens.add(devices.get(i));
						}
					}
					failedTokens.forEach(s -> {
						logger.debug("Deleting token {}", s);
						userDeviceService.deleteToken(s);
						logger.warn("Deleted token {}", s);
					});
				}
			} catch (FirebaseMessagingException e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
	}

	private Map<String, String> getAllData(Map<String, Object> messageParams) {
		Map<String, String> data = new HashMap<>();
		List<String> excludedParams = List.of(DefaultMessagingService.PUSH_PARAM_DEVICES,
				DefaultMessagingService.PUSH_PARAM_TOPIC);
		messageParams.keySet().stream().filter(s -> !excludedParams.contains(s))
				.forEach(k -> data.put(k, messageParams.get(k).toString()));
		return data;
	}

	private boolean isInvalidToken(FirebaseMessagingException e) {
		return e.getMessagingErrorCode() == MessagingErrorCode.INVALID_ARGUMENT
				|| e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.isBlank(this.googleApplicationCredentials)) {
			logger.info("Initializing Firebase FCM ...");
			FileInputStream serviceAccount = new FileInputStream(this.googleApplicationCredentials);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);
			logger.info("Firebase FCM initialized");
		}
	}

}