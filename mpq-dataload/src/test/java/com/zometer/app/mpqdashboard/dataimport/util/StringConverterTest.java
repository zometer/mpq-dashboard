package com.zometer.app.mpqdashboard.dataimport.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.zometer.app.mpqdashboard.dataimport.util.StringConverter;

@DisplayName("com.zometer.sandbox.app.mpq.dataimport.util.StringConverter")
class StringConverterTest {

	@Nested
	@DisplayName("convert(String, Class<?>)")
	class SuccessfulUsage {

		@Test
		@DisplayName("(String, String.class)")
		public void testConvertStringToString() {
			String value = StringConverter.convert("foo", String.class);

			assertNotNull(value, "Null value not expected");
			assertEquals("foo", value);
		}

		@Test
		@DisplayName("(String, Integer.class)")
		public void testConvertIntegerToString() {
			int value = StringConverter.convert("7", Integer.class);

			assertNotNull(value, "Null value not expected");
			assertEquals(7, value);
		}

		@Test
		@DisplayName("(String, Double.class)")
		public void testConvertFloatToString() {
			double value = StringConverter.convert("3.14", Double.class);

			assertNotNull(value, "Null value not expected");
			assertEquals(3.14, value);
		}

		@Nested
		@DisplayName("Error Cases")
		class ErrorCases {

			@Test
			@DisplayName("Non-numeric types cannot be converted.")
			public void testConvertDateToStringThrowsException() {
				Throwable e = assertThrows(IllegalArgumentException.class, () -> {
					StringConverter.convert("2018-03-09", Date.class);
				});

				assertAll("Exception",
					() -> assertNotNull(e.getMessage(), "null message"),
					() -> assertThat(e.getMessage(), containsString("java.util.Date")),
					() -> assertThat(e.getMessage(), containsString("cannot be converted"))
				);
			}
			
			@Test
			@DisplayName("Non-numeric types cannot be converted.")
			public void testConvertStrToStringThrowsException() {
				Throwable e = assertThrows(IllegalArgumentException.class, () -> {
					StringConverter.convert("2018-03-09", Date.class);
				});

				assertAll("Exception",
					() -> assertNotNull(e.getMessage(), "null message"),
					() -> assertThat(e.getMessage(), containsString("java.util.Date")),
					() -> assertThat(e.getMessage(), containsString("cannot be converted"))
				);
			}
		}

	}

}
