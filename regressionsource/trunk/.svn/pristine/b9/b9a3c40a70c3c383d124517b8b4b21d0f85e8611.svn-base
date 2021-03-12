package com.powin.modbusfiles.utilities;

import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.reports.stackTypeIntegrationTest;


public class CommonHelperIntegrationTest {
//    /**
//     * Test that the shell script works on a dev environment
//     * @throws IOException
//     * @throws InterruptedException
//     */
//	//@Test
//	public void testGetDevice20() throws IOException, InterruptedException {
//		CommonHelper.getDevice20();
//		assertTrue((new File("/home/powin/device-20")).exists());
//	}
//	//@Test
//	public void testGetConfigurationJSON() throws IOException, InterruptedException {
//		CommonHelper.getConfigurationJSON();
//		assertTrue((new File("/home/powin/configuration.json")).exists());
//	}
//	
//	// @Test
//	public void testGetTurtleLog() throws IOException, InterruptedException {
//		CommonHelper.getTurtleLog("turtleLog");
//	}
//	
	private final static Logger LOG = LogManager.getLogger(stackTypeIntegrationTest.class.getName());
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
	public void test() throws InstantiationException, IllegalAccessException {
		LOG.info(CommonHelper.testLimit(1).CELL_HIGH_VOLTAGE_DELTA_ALARM_SET);
	}
	
	@Test
	public void testReadConfigurationData() throws Exception {
		String pathToFiles ="/com/powin/qilin/configuration/";
		try (InputStream input = this.getClass().getResourceAsStream(pathToFiles + SystemInfo.getStackType().stackConfigurationName + ".json")) {
			String contents = IOUtils.toString(input, StandardCharsets.UTF_8);
			System.out.println(contents);
		}
	} 
		
		@Test
		public void testReadConfigurationData230() throws Exception {
			String pathToFiles ="/com/powin/qilin/configuration/";
			try (InputStream input = this.getClass().getResourceAsStream(pathToFiles + StackType.STACK_230_GEN22 + ".json")) {
				String contents = IOUtils.toString(input, StandardCharsets.UTF_8);
				System.out.println(contents);
			}

		}

}
