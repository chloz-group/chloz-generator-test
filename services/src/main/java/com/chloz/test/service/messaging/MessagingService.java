package com.chloz.test.service.messaging;

import java.util.Locale;
import java.util.Map;

/**
 * Define the contract implemented by each messaging service
 */
public interface MessagingService {

	/**
	 * sent the message
	 *
	 * @param message
	 *            the message to send
	 * @param messageParams
	 *            other parameters of the message
	 * @param locale
	 *            the locale to use
	 * @param title
	 *            the message title
	 * @return true if message successfully send false otherwise
	 * @throws Exception
	 */
	public boolean sendMessage(String message, String title, Locale locale, Map<String, Object> messageParams)
			throws Exception;

}