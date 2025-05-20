package com.chloz.test.service.base;

import com.chloz.test.service.Utils;
import com.chloz.test.service.messaging.DefaultMessagingService;
import com.chloz.test.service.messaging.MailMessagingService;
import com.chloz.test.service.messaging.SMSMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ErrorHandlerBase {

	private final MailMessagingService mailMessagingService;

	private final SMSMessagingService smsMessagingService;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${admin.emails:}")
	private String administratorsEmails;

	private Logger logger = LoggerFactory.getLogger(ErrorHandlerBase.class);

	public ErrorHandlerBase(MailMessagingService mailMessagingService, SMSMessagingService smsMessagingService) {
		this.mailMessagingService = mailMessagingService;
		this.smsMessagingService = smsMessagingService;
	}

	/**
	 * Handle this error and Notify the administrator by email or SMS that an error
	 * occur so that he can handle it as soon as possible
	 *
	 * @param exception
	 * @param operation
	 */
	@Async
	public void handlerError(Exception exception, boolean critical, String operation, String title, String message,
			Map<String, Object> params) {
		// Notify administrator
		String emailTitle = new StringBuilder().append("[").append(applicationName).append("] - ")
				.append(critical ? "[CRITICAL] - " : "").append("[").append(operation).append("] : ").append(title)
				.toString();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		if (exception != null)
			exception.printStackTrace(ps);
		ps.close();
		String lingneSeparator = "------------------------------------------------";
		String emailMessage = new StringBuilder().append(message != null ? message + "\n" : "").append(lingneSeparator)
				.append("PARAMETERS : \n").append(Utils.toString(params)).append(lingneSeparator)
				.append(lingneSeparator).append(bos.toByteArray()).toString();
		Map<String, Object> mailParams = new HashMap<>();
		mailParams.put(DefaultMessagingService.MAIL_PARAM_TO, administratorsEmails);
		try {
			this.mailMessagingService.sendMessage(emailMessage, emailTitle, Locale.ENGLISH, mailParams);
		} catch (Exception e) {
			logger.error("", e);
		}
		if (critical) {
			String smsMessage = new StringBuilder().append("[").append(applicationName).append("] - ")
					.append("[CRITICAL] - ").append("[").append(operation).append("] : ").append(title).toString();
			try {
				this.smsMessagingService.sendMessage(smsMessage, title, Locale.ENGLISH, mailParams);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

}