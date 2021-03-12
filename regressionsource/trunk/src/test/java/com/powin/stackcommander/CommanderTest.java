package com.powin.stackcommander;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.powin.stackcommander.Commander;
import com.powin.stackcommander.Executor;

class CommanderTest {

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

//	@Test
//	void testGetCmdsToRun() {
//		fail("Not yet implemented");
//	}

	@ParameterizedTest
	@ValueSource(strings = { "DCIR.csv", "newTest.spt", "stackTest.spt", "StackTest3.csv", "stackTest4.spt",
			"DCIR2.csv", "stackTest.csv", "stackTest2.spt", "stackTest3.spt" })
	void testMain(final String p) throws IOException, ParseException {
		String [] path = {"src", "test", "resources", p};
		Commander.main(new String[] { String.join(File.separator, path) });
		final List<Executor> l = Commander.getCmdsToRun();
		assertTrue(l.size() > 0);
	}

	
	@Test
	void testSingle() throws IOException, ParseException {
		String [] path = {"src", "test", "resources", "StackTest3_2.csv"};
		Commander.main(new String[] {  String.join(File.separator, path)  });
		final List<Executor> l = Commander.getCmdsToRun();
		assertTrue(l.size() > 0);
		assertFalse(l.stream().anyMatch(e -> e.getStatus() == Executor.Status.NOT_READY),
				"A Command has reported a not ready status");
	}

//	@Test
//	void testProcessScript() {
//		fail("Not yet implemented");
//	}

}
