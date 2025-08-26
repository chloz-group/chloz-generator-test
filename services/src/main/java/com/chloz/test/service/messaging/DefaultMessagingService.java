package com.chloz.test.service.messaging;

import com.chloz.test.dataaccess.TemplateDataAccess;
import com.chloz.test.domain.Template;
import com.chloz.test.service.ErrorHandler;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@Service
@Transactional
public class DefaultMessagingService {

	private static final Logger log = LoggerFactory.getLogger(DefaultMessagingService.class);

	/**
	 * A COMMA Separated list of emails addresses where to send the message
	 */
	public static final String MAIL_PARAM_TO = "email.to";

	/**
	 * A COMMA separated list of phone numbers that will receive the message
	 */
	public static final String SMS_PARAM_TO = "sms.recipients";

	/**
	 * The content type of the email
	 */
	public static final String MAIL_PARAM_CONTENT_TYPE = "email.contentType";

	/**
	 * Attachments is supposed to be a list of DataHandler
	 */
	public static final String PARAM_ATTACHMENTS = "attachments";

	/**
	 * A list of device tokens where to send the message
	 */
	public static final String PUSH_PARAM_DEVICES = "devices.to";

	/**
	 * The topic in which to send the message
	 */
	public static final String PUSH_PARAM_TOPIC = "message.topic";

	private static final freemarker.template.Version VERSION = Configuration.VERSION_2_3_30;

	private final TemplateDataAccess templateDataAccess;

	private final MailMessagingService mailService;

	private final SMSMessagingService smsService;

	private final PushNotificationMessagingService pushNotificationService;

	private final ErrorHandler errorHandler;

	private final Configuration configuration;
	public DefaultMessagingService(TemplateDataAccess templateDataAccess, MailMessagingService mailService,
			SMSMessagingService smsService, PushNotificationMessagingService pushNotificationService,
			ErrorHandler errorHandler) {
		this.templateDataAccess = templateDataAccess;
		this.mailService = mailService;
		this.smsService = smsService;
		this.pushNotificationService = pushNotificationService;
		this.errorHandler = errorHandler;
		this.configuration = this.getConfiguration();
	}

	private Configuration getConfiguration() {
		Configuration cfg = new Configuration(VERSION);
		TemplateLoader templateLoader = new StringTemplateLoader() {

			@Override
			public Object findTemplateSource(String name) {
				this.putTemplate(name, name);
				return super.findTemplateSource(name);
			}

		};
		cfg.setTemplateLoader(templateLoader);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setLocalizedLookup(false);
		// Do not fall back to higher scopes when reading a null loop variable:
		cfg.setFallbackOnNullLoopVariable(false);
		DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(VERSION);
		builder.setExposeFields(true);
		DefaultObjectWrapper objectWrapper = builder.build();
		cfg.setObjectWrapper(objectWrapper);
		return cfg;
	}

	/**
	 * Envoi le message aux differents types indiquer. Le message provient du
	 * template dont le code est referencer.
	 *
	 * @param types
	 *            liste des types de provider pour lequel le message doit etre
	 *            envoyer
	 * @param templateCode
	 *            le code du template qui contient le message a envoyer
	 * @param templateParams
	 *            les parametres a envoyer dans le template pour avoir le message
	 *            definitif
	 * @param title
	 *            le titre du message. Si le titre n'est pas fourni dans l'appel de
	 *            la methode, alors il sera recuperer du template
	 * @param messageParams
	 *            les parametres du message (Exemple : les destinataires, ...)
	 * @return true si le message est envoyer et false sinon
	 * @throws IOException
	 */
	public boolean sendMessageFromTemplate(Set<MessageType> types, Locale locale, String title, String templateCode,
			Map<String, Object> templateParams, Map<String, Object> messageParams) throws Exception {
		if (types == null || types.isEmpty() || templateCode == null) {
			throw new IllegalArgumentException("Invalid parameters provided");
		}
		Optional<Template> opt = this.transformMessage(locale, title, templateCode, templateParams);
		if (!opt.isPresent()) {
			return false;
		}
		Template template = opt.get();
		boolean res = true;
		for (MessageType t : types) {
			if (res) {
				MessagingService messagingService = getServiceOfType(t);
				String content = template.getContent();
				if (t == MessageType.SMS && template.getShortContent() != null) {
					content = template.getShortContent();
				}
				try {
					res = messagingService.sendMessage(content, template.getTitle(), locale, messageParams);
				} catch (Exception e) {
					boolean critical = t == MessageType.SMS;
					String message = content;
					this.errorHandler.handlerError(e, critical, t.name() + "_SEND", "Message sending error", message,
							messageParams);
					throw e;
				}
			}
		}
		return res;
	}

	@Async
	public void sendMessageFromTemplateAsynchronous(Set<MessageType> types, Locale locale, String title,
			String templateCode, Map<String, Object> templateParams, Map<String, Object> messageParams) {
		try {
			boolean ok = this.sendMessageFromTemplate(types, locale, title, templateCode, templateParams,
					messageParams);
			if (!ok) {
				log.error("Couldn't send the template message [{}], Params={}", templateCode, templateParams);
			}
		} catch (Exception e) {
			log.error("Couldn't send the template message [{}]", templateCode);
			log.error("", e);
		}
	}

	public MessagingService getServiceOfType(MessageType t) {
		MessagingService res = null;
		switch (t) {
			case PUSH :
				res = this.pushNotificationService;
				break;
			case SMS :
				res = this.smsService;
				break;
			default :
				res = this.mailService;
				break;
		}
		return res;
	}

	public Optional<Template> transformMessage(Locale locale, String title, String templateCode,
			Map<String, Object> templateParams) {
		Optional<Template> opt = this.templateDataAccess.findByCode(templateCode);
		if (!opt.isPresent()) {
			return Optional.empty();
		}
		Template template = opt.get();
		Template res = new Template();
		res.setTitle(template.getTitle());
		res.setContent(template.getContent());
		res.setShortContent(template.getShortContent());
		// search the corresponding internationalized template if exists
		Template localizedTemplate = null;
		if (locale != null) {
			String i10nCode = templateCode + ".i10n." + locale.getLanguage().toLowerCase();
			localizedTemplate = this.templateDataAccess.findByCode(i10nCode).orElse(null);
			if (localizedTemplate != null) {
				res.setTitle(Optional.ofNullable(localizedTemplate.getTitle()).orElse(res.getTitle()));
				res.setContent(Optional.ofNullable(localizedTemplate.getContent()).orElse(res.getContent()));
				res.setShortContent(
						Optional.ofNullable(localizedTemplate.getShortContent()).orElse(res.getShortContent()));
			}
		}
		if (title != null)
			res.setTitle(title);
		res.setContent(this.processTemplate(res.getContent(), templateParams));
		if (res.getTitle() != null)
			res.setTitle(this.processTemplate(res.getTitle(), templateParams));
		if (res.getShortContent() != null)
			res.setShortContent(this.processTemplate(res.getShortContent(), templateParams));
		return Optional.of(res);
	}

	private String processTemplate(String content, Map<String, Object> templateParams) {
		try (StringWriter out = new StringWriter()) {
			freemarker.template.Template template = this.configuration.getTemplate(content);
			template.process(templateParams, out);
			return out.toString();
		} catch (IOException | TemplateException e) {
			LoggerFactory.getLogger(getClass()).warn(null, e);
		}
		return null;
	}

}