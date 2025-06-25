package com.chloz.test.common.exception;

/**
 * Interface that can be implemented by Exception to provide details. The
 * details are used to render a user-friendly message to the client side.
 */
public interface ExceptionDetailsProvider {

	/**
	 * The error code for this exception. It may be provided to the client if
	 * needed. The error code will also be used to retrieve the localized message
	 * from bundles.
	 *
	 * @return The error code
	 */
	default String getErrorCode() {
		return null;
	}

	/**
	 * Returns the array of arguments to be used when resolving the error message.
	 * <p>
	 * These arguments can be inserted into a parameterized message pattern defined
	 * in a localization file using placeholders like <code>{0}</code>,
	 * <code>{1}</code>, etc.
	 * </p>
	 * <p>
	 * For example, for an error code <code>"CLIENT_NOT_FOUND"</code>, the message
	 * pattern could be <code>"Client with ID {0} was not found."</code>, and this
	 * method could return <code>new Object[] { 123 }</code>.
	 * </p>
	 *
	 * @return an array of message arguments
	 */
	default Object[] getMessageArguments() {
		return new Object[]{};
	}

	/**
	 * Returns the HTTP status code to be returned with this error.
	 * <p>
	 * This status code should follow standard HTTP semantics, such as:
	 * <ul>
	 * <li>400 for bad requests</li>
	 * <li>404 for not found resources</li>
	 * <li>409 for conflicts</li>
	 * <li>500 for internal server errors</li>
	 * </ul>
	 * </p>
	 * If null, the application will try to look elsewhere for the status code.
	 *
	 * @return an integer representing the HTTP status code (e.g. 400, 404, 500)
	 */
	default Integer getStatusCode() {
		return null;
	}

}