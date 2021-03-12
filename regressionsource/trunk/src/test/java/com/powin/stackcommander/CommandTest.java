package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.powin.stackcommander.Command;

class CommandTest {

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
		assertEquals(Command.fromString("Balancing"), Command.Balancing);
	}

	@Test
	void testToRegEx() {
		assertEquals(
				"Title|Using|Fully? discharge|Balancing|Balance|Fully? charge|Charge|Discharge|Record|Turn off|Restart|Rest|Repeat|Comment",
				Command.toRegEx(false));
	}

	@Test
	void testToString() {
		assertEquals(Command.Balancing.toString(), "Balancing|Balance");
	}

}
