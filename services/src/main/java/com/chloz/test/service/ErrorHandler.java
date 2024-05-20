package com.chloz.test.service;

import com.chloz.test.service.base.ErrorHandlerBase;
import com.chloz.test.service.messaging.MailMessagingService;
import com.chloz.test.service.messaging.SMSMessagingService;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler extends ErrorHandlerBase {

	public ErrorHandler(MailMessagingService mailMessagingService, SMSMessagingService smsMessagingService) {
		super(mailMessagingService, smsMessagingService);
	}

}