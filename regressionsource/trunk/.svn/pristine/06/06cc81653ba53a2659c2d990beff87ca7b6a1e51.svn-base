package com.powin.modbusfiles.utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.utilities.FileHelper;

public class FileHelperIntegrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testReadFileAsString() {
		String path = "src/test/resources/"+"Stack225Gen2_2SafetyAndNotificationConfig.json";
		String s = FileHelper.readFileAsString(path);
		assertThat(s.length(), is(11455));
	}

}
