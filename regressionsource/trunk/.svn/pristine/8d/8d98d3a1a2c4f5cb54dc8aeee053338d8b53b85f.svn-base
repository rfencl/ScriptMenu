package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.powin.stackcommander.Command;
import com.powin.stackcommander.Directive;
import com.powin.stackcommander.Executor;
import com.powin.stackcommander.StackScriptParser;

class StackScriptParserTest {
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	StackScriptParser cut;

	@BeforeEach
	void setUp() throws Exception {
		cut = new StackScriptParser();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@EnumSource(value = Command.class, names = { "Undefined", "Repeat" }, mode = EnumSource.Mode.EXCLUDE)
	void testCommonC(final Command command) {
		final String s = command.toString().replace("y?", "y");
		final Executor executor = cut.parse(Arrays.asList(s)).get(0);
		assertEquals(command, executor.getCommand());
	}

//	@ParameterizedTest
//	@EnumSource(value = Directive.class, names = { "step", "Undefined" }, mode = EnumSource.Mode.EXCLUDE)
//	void testCommonD(final Directive command) {
//		final Executor executor = cut.parse(Arrays.asList(command.toString())).get(0);
//		assertEquals(Arrays.asList(command), executor.getDirective());
//	}

	@ParameterizedTest
	@ValueSource(strings = { "Fully discharge at 58kW", "Fully discharge at P/3" })
	void testParsePowerP(final String p) {
		final Executor executor = cut.parse(Arrays.asList(p)).get(0);
		assertEquals(p, "Fully discharge at " + executor.getPowerString());
	}

	@Test
	void testParseRepeat() {
		final Command c = Command.Repeat;
		final String s = c.toString() + " step 1";
		final Executor executor = cut.parse(Arrays.asList(s)).get(0);
		assertEquals(c, executor.getCommand());
	}

	@Test
	void testParseUsingBasicOp() {
		final Executor executor = cut.parse(Arrays.asList("Using BasicOp:".replace(":", ""))).get(0);
		assertEquals(Arrays.asList(Directive.BasicOp), executor.getDirective());
	}

	@Test
	void testParseUsingDirectP() {
		final Executor executor = cut.parse(Arrays.asList("Using DirectP:".replace(":", ""))).get(0);
		assertEquals(Arrays.asList(Directive.DirectP), executor.getDirective());
	}

}
