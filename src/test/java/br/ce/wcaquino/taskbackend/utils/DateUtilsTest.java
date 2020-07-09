package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testFutureDate() {
		LocalDate date = LocalDate.of(2030, 1, 1);
		assertTrue(DateUtils.isEqualOrFutureDate(date));
	}

	
	@Test
	public void testPasteDate () {
		LocalDate date = LocalDate.of(2010, 1, 1);
		assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
	
	@Test
	public void testActualDate() {
		LocalDate date = LocalDate.now();
		assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
}
