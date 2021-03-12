package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.powin.stackcommander.Power;

class PowerTest {

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
	void testFromString() {
		Power p = Power.fromString("48kW");
		assertEquals(48, p.getPower());
		p = Power.fromString("P/3");
		assertEquals(3, p.getPower());
	}

	@Test
	void testPowerkW() {
		final Power p = new Power("48kW");
		assertEquals(48, p.getPower());
	}

	@Test
	void testPowerP() {
		final Power p = new Power("P/5");
		assertEquals(5, p.getPower());
	}

	@Test
	void testToString() {
		Power p = new Power("P/5");
		assertEquals("P/5", p.toString());
		p = new Power("35kW");
		assertEquals("35kW", p.toString());
	}

}
