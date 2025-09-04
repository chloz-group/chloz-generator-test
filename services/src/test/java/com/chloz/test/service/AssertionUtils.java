package com.chloz.test.service;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {

	public static void assertObjectEquals(Object actual, Object expected) {
		// Todo
		assertThat(actual).isEqualTo(expected);
	}

}