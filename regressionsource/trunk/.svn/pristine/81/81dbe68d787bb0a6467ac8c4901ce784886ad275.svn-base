package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.powin.stackcommander.Directive;

class DirectiveTest {

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
	void testToRegEx() {
		assertEquals("DirectP:?|BasicOp:?|step|at|ON|On|OFF|Off|for|or|Full|SOE value|BMS|Until", Directive.toRegEx());
	}

}
