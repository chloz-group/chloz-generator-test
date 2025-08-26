package com.chloz.test.service;

/**
 * Application constants.
 */
public final class Constants {

	public static final String ERROR_MESSAGE_OBJECT_NOT_FOUND = "#E#S003 Object not found";

	/**
	 * Minimum Password length
	 */
	public static final int PASSWORD_MIN_LENGTH = 3;

	/**
	 * Maximum Password length
	 */
	public static final int PASSWORD_MAX_LENGTH = 100;

	/**
	 * Maximum number of login attempts
	 */
	public static final Integer MAX_LOGIN_ATTEMPTS = 3;

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