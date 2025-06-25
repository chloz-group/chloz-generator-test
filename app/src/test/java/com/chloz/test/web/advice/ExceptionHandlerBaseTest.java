package com.chloz.test.web.advice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExceptionHandlerBaseTest {

	@Autowired
	private MessageSource messageSource;

	private ExceptionHandlerBase exceptionHandlerBase;
	@BeforeEach
	void setUp() {
		this.exceptionHandlerBase = new ExceptionHandlerBase(messageSource);
	}

	@Test
	void testValidFullInput() {
		String input = "#E#ERR001(404::File not found::Line 42)";
		var expected = new ExceptionHandlerBase.ErrorData("ERR001", 404, new String[]{"File not found", "Line 42"});
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testOnlyCodeAndStatus() {
		String input = "#E#ERR002(500)";
		var expected = new ExceptionHandlerBase.ErrorData("ERR002", 500, new String[0]);
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testCodeWithNoStatusOrArguments() {
		String input = "#E#ERR003()";
		var expected = new ExceptionHandlerBase.ErrorData("ERR003", null, new String[0]);
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testBadCodeWithInvalidStatus() {
		String input = "#E#BAD CODE(400)";
		var expected = new ExceptionHandlerBase.ErrorData("BAD", null, new String[0]);
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testCodeWithNoStatusOrArgumentsAndBrackets() {
		String input = "#E#ERR004";
		var expected = new ExceptionHandlerBase.ErrorData("ERR004", null, new String[0]);
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "#E#(404::Something)", // MissingCode
			"Some random message without marker",})
	void testMissingCode(String input) {
		assertNull(exceptionHandlerBase.parseErrorMessage(input));
	}

	@Test
	void testNonIntegerStatus() {
		String input = "#E#ERR004(NOT_FOUND::File)";
		var expected = new ExceptionHandlerBase.ErrorData("ERR004", null, new String[]{"File"});
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testExtraContentAfter() {
		String input = "#E#ERR005(100::extra)::should be ignored";
		var expected = new ExceptionHandlerBase.ErrorData("ERR005", 100, new String[]{"extra"});
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

	@Test
	void testExtraTextIgnored() {
		String input = "#E#CODEN(100::x::y) some irrelevant info here";
		var expected = new ExceptionHandlerBase.ErrorData("CODEN", 100, new String[]{"x", "y"});
		assertEquals(expected, exceptionHandlerBase.parseErrorMessage(input));
	}

	@Test
	void testNullInput() {
		assertNull(exceptionHandlerBase.parseErrorMessage(null));
	}

	@Test
	void testStatusMissingButArgumentsPresent() {
		String input = "#E#ERR006(::arg1::arg2)";
		var expected = new ExceptionHandlerBase.ErrorData("ERR006", null, new String[]{"arg1", "arg2"});
		var result = exceptionHandlerBase.parseErrorMessage(input);
		assertEquals(expected, result);
	}

}