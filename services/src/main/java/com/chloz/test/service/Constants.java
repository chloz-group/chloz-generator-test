package com.chloz.test.service;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM_ACCOUNT = "system";

	public static final String ANONYMOUS_USER = "anonymoususer";

	public static final String EMAIL_CHECK_ENABLED_PARAM = "email.check.enabled";

	public static final String PHONE_CHECK_ENABLED_PARAM = "phone.check.enabled";

	public static final String TEMPLATE_MESSAGE_USER_ACTIVATION_CODE = "user/activationCode";
	private Constants() {
	}

}