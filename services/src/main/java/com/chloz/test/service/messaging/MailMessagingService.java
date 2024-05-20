package com.chloz.test.service.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class MailMessagingService implements MessagingService {

	private final JavaMailSender emailSender;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.mail.username:}")
	private String userEmail;
	public MailMessagingService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Override
	public boolean sendMessage(String message, String title, Locale locale, Map<String, Object> messageParams)
			throws Exception {
		String emailsStr = messageParams.get(DefaultMessagingService.MAIL_PARAM_TO).toString();
		List<String> tos = Arrays.asList(emailsStr.split(","));
		String contentType = messageParams.getOrDefault(DefaultMessagingService.MAIL_PARAM_CONTENT_TYPE, "text/html")
				.toString();
		List<DataHandler> paramAttachment = (List<DataHandler>) messageParams
				.get(DefaultMessagingService.PARAM_ATTACHMENTS);
		sendMessage(tos, this.userEmail, title, message, contentType, paramAttachment);
		return true;
	}

	private void sendMessage(List<String> tos, String from, String subject, String bodyText, String contentType,
			List<DataHandler> attachments) throws MessagingException, IOException {
		MimeMessage mimeMessage = createEmailWithAttachments(from, tos, subject, bodyText, contentType, attachments);
		this.emailSender.send(mimeMessage);
	}

	public MimeMessage createEmailWithAttachments(String from, List<String> tos, String subject, String bodyText,
			String contentType, List<DataHandler> attachments) throws MessagingException, UnsupportedEncodingException {
		MimeMessage email = emailSender.createMimeMessage();
		email.setFrom(new InternetAddress(from, this.applicationName));
		for (String to : tos) {
			email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
		}
		email.setSubject(subject);
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(bodyText, contentType);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
		if (attachments != null) {
			for (int i = 0, n = attachments.size(); i < n; i++) {
				DataHandler dh = attachments.get(i);
				mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setDataHandler(dh);
				mimeBodyPart.setFileName(dh.getName());
				multipart.addBodyPart(mimeBodyPart);
			}
		}
		email.setContent(multipart);
		return email;
	}

}