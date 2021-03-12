package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.powin.stackcommander.Duration;

class DurationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDurationMinutes() {
		final Duration d = new Duration("24min", true);
		assertEquals("24min", d.toString());
	}

	@Test
	void testDurationSeconds() {
		final Duration d = new Duration("24s", true);
		assertEquals("24s", d.toString());
	}

	@Test
	void testFromString() {
		final Duration d = Duration.fromString("24min");
		assertEquals(24L, d.getTime().getSeconds() / 60);
	}

}
