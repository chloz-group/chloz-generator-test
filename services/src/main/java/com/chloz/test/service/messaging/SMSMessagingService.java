package com.chloz.test.service.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class SMSMessagingService implements MessagingService {

	private final Logger log = LoggerFactory.getLogger(SMSMessagingService.class);

	private static final String STATUS_SUCCESS = "success";

	@Value("${sms.username}")
	private String username;

	@Value("${sms.password}")
	private String password;

	@Value("${sms.senderName}")
	private String senderName;

	@Value("${sms.apiAddress}")
	private URI apiAddress;
	public SMSMessagingService() {
		//empty constructor
	}

	@Override
	public boolean sendMessage(String message, String title, Locale locale, Map<String, Object> messageParams)
			throws Exception {
		String phoneStr = messageParams.get(DefaultMessagingService.SMS_PARAM_TO).toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		StringBuilder body = new StringBuilder().append("username=").append(encode(username)).append("&password=")
				.append(encode(password)).append("&sender=").append(encode(senderName)).append("&message=")
				.append(encode(message)).append("&recipients=")
				.append(encode(StringUtils.join(cleanPhoneNumber(phoneStr), ",")));
		HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
		ResponseEntity<ApiResult> responseEntity = this.sendRequest(request);
		ApiResult result = responseEntity.getBody();
		if(result == null ){
			return false;
		}
		log.info("Sms sending to {} result is {}. The message is {}.", phoneStr, result, message);
		return responseEntity.getStatusCode() == HttpStatus.OK && STATUS_SUCCESS.equals(result.getStatus());
	}

	/**
	 * Return the phone number that will not have + or 00 in the front
	 *
	 * @param phone
	 *            the phone number or the phone number list separated by a coma
	 * @return The cleaned phone list
	 */
	protected List<String> cleanPhoneNumber(String phone) {
		String[] phones = phone.split(",");
		List<String> resList = new ArrayList<>();
		for (String num : phones) {
			String res = num;
			if (num.startsWith("00")) {
				res = res.replaceFirst("00", "");
			} else if (num.startsWith("+")) {
				res = res.replaceFirst("\\+", "");
			}
			resList.add(res);
		}
		return resList;
	}

	private String encode(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, StandardCharsets.UTF_8.toString());
	}

	protected ResponseEntity<ApiResult> sendRequest(HttpEntity<String> request)
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial((KeyStore) null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", csf).register("http", new PlainConnectionSocketFactory()).build();
		BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
				socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate.postForEntity(apiAddress, request, ApiResult.class);
	}
	@Getter
	@Setter
	@ToString
	@Builder
	protected static class ApiResult {

		private String status;

		private String code;

		private String message;

	}

}