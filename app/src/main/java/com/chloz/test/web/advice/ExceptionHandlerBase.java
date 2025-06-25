package com.chloz.test.web.advice;

import com.chloz.test.common.exception.ExceptionDetailsProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import java.net.URI;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class ExceptionHandlerBase implements ProblemHandling, SecurityAdviceTrait {

	private final MessageSource messageSource;

	protected boolean addErrorCode = true;
	public ExceptionHandlerBase(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public ProblemBuilder prepare(Throwable throwable, StatusType status, URI type) {
		ProblemBuilder builder = Problem.builder().withType(type).withTitle(status.getReasonPhrase()).withStatus(status)
				.withDetail(throwable.getMessage()).withCause(Optional.ofNullable(throwable.getCause())
						.filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null));
		Integer statusCode = null;
		String errorCode = null;
		Object[] messageArguments = null;
		ExceptionDetailsProvider edp;
		if (throwable instanceof ExceptionDetailsProvider a) {
			edp = a;
		} else {
			edp = parseExceptionMessage(throwable);
		}
		if (edp != null) {
			statusCode = edp.getStatusCode();
			errorCode = edp.getErrorCode();
			messageArguments = edp.getMessageArguments();
		}
		if (statusCode != null && statusCode != status.getStatusCode()) {
			Status s = Status.valueOf(statusCode);
			builder = builder.withStatus(s).withTitle(s.getReasonPhrase());
		}
		if (errorCode != null) {
			Locale locale = LocaleContextHolder.getLocale();
			String details = messageSource.getMessage("error." + errorCode, messageArguments, throwable.getMessage(),
					locale);
			builder = builder.withDetail(details);
			if (addErrorCode) {
				builder = builder.with("errorCode", errorCode);
			}
		}
		return builder;
	}

	protected ExceptionDetailsProvider parseExceptionMessage(Throwable throwable) {
		ErrorData errorData = this.parseErrorMessage(throwable.getMessage());
		if (errorData != null) {
			return new ExceptionDetailsProvider() {

				@Override
				public String getErrorCode() {
					return errorData.code;
				}

				@Override
				public Integer getStatusCode() {
					return errorData.status;
				}

				@Override
				public Object[] getMessageArguments() {
					return errorData.arguments;
				}

			};
		}
		return null;
	}

	/**
	 * Parse the error message. The error message is in the format
	 * #E#error_code(http_status_code,message_parameteres...)
	 * 
	 * @param input
	 * @return The parsed exception message
	 */
	protected ErrorData parseErrorMessage(String input) {
		if (input == null)
			return null;
		int markerIndex = input.indexOf("#E#");
		if (markerIndex == -1)
			return null;
		int startIndex = markerIndex + 3;
		String remainder = (startIndex >= input.length()) ? "" : input.substring(startIndex).trim();
		if (remainder.isEmpty())
			return null;
		String code = extractCode(remainder);
		if (code == null)
			return null;
		Integer status = null;
		String[] arguments = new String[0];
		String inside = null;
		if (remainder.startsWith(code + "(")) {
			inside = extractParenthesisContent(remainder);
		}
		if (inside != null && !inside.isEmpty()) {
			String[] parts = inside.split("::", -1);
			try {
				status = Integer.parseInt(parts[0]);
			} catch (NumberFormatException e) {
				// status si null in this case, already set to null up
			} finally {
				if (parts.length > 1) {
					arguments = Arrays.copyOfRange(parts, 1, parts.length);
				}
			}
		}
		return new ErrorData(code, status, arguments);
	}

	private String extractCode(String remainder) {
		int spaceIndex = remainder.indexOf(' ');
		int parenIndex = remainder.indexOf('(');
		int codeEnd = remainder.length();
		if (parenIndex != -1)
			codeEnd = parenIndex;
		else if (spaceIndex != -1)
			codeEnd = spaceIndex;
		if (spaceIndex != -1 && parenIndex != -1 && spaceIndex < parenIndex) {
			codeEnd = spaceIndex;
		}
		String code = remainder.substring(0, codeEnd).trim();
		if (code.isEmpty() || code.contains(" "))
			return null;
		return code;
	}

	private String extractParenthesisContent(String remainder) {
		int open = remainder.indexOf('(');
		int close = remainder.indexOf(')', open);
		if (open != -1 && close != -1 && close > open) {
			return remainder.substring(open + 1, close).trim();
		}
		return null;
	}
	@Getter
	@Setter
	protected static class ErrorData {

		private final String code;

		private final Integer status; // null si non précisé

		private final String[] arguments;
		public ErrorData(String code, Integer status, String[] arguments) {
			this.code = code;
			this.status = status;
			this.arguments = arguments;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			ErrorData other = (ErrorData) obj;
			return code.equals(other.code)
					&& ((status == null && other.status == null) || (status != null && status.equals(other.status)))
					&& Arrays.equals(arguments, other.arguments);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(new Object[]{code, status, Arrays.hashCode(arguments)});
		}

	}

}