package com.chloz.test.web;

public class Constants {

	public static final String API_BASE_PATH = "";

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

	public static final String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * Spring profile used to enable swagger
	 */
	public static final String SPRING_PROFILE_SWAGGER = "swagger";

	public static final String ERROR_MESSAGE_OBJECT_NOT_FOUND = "#E#S003 Object not found";
	private Constants() {
	}

}