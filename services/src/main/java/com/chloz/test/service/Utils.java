package com.chloz.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Map;

public class Utils {

	private static final ObjectMapper mapper = new ObjectMapper();
	private Utils() {
	}

	public static String toString(Map<String, Object> map) {
		if (map == null)
			return null;
		String s;
		try {
			s = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			s = map.toString();
		}
		return s;
	}

	public static String generateVerificationCode(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

}