package com.powin.stackcommander;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
//import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerApps;
import com.powin.modbusfiles.utilities.FileHelper;


public class StackCommandDriverTest {
	private final static Logger LOG = LogManager.getLogger(StackCommandDriverTest.class.getName());
	private static final int ENABLED = 1;
	private static final int DISABLED = 0;
	private static int arrayIndex = 1;
	private static int stringIndex = 1;

	
	private IStackCommandExecutor classUnderTest = new StackCommandDriverStubb();

//	@Rule
//	public LogAppenderResource appender = new LogAppenderResource(LogManager.getLogger(StackCommandDriverStubb.class.getName()));

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		 LOG.info("What is going on?");
	}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		classUnderTest.setPowerApp(PowerApps.SunspecPowerCommand);
	}
	
	@After
	public void tearDown() throws Exception {
		//cut.stopPower();
		assertTrue(StackCommandDriver.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
	@Test
	public void testExecuteScript() throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException, java.text.ParseException {
		String [] path = {"src", "test", "resources", "stackTest.csv"};
		String filename = String.join(File.separator, path); 
		List<String> lines = FileHelper.readFileToList(filename);
		List<Executor> executor = Commander.processLines(lines);
		StackCommandDriver.executeScript(executor, classUnderTest);
		List<String> called = ((StackCommandDriverStubb)classUnderTest).getCalled();
		assertEquals(5, called.size());

	}
	/**
	 * Test both conditions, if the file exists don't bother copying it again
	 * if it doesn't then grab it.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void isPCSConfigPresent() throws IOException, InterruptedException {
		assertTrue(StackCommandDriver.isRemoteFilePresent(StackCommandDriver.HOME_POWIN_DEVICE_20, "getPCSConfig.sh"));
		(new File(StackCommandDriver.HOME_POWIN_DEVICE_20)).delete();
		assertTrue(StackCommandDriver.isRemoteFilePresent(StackCommandDriver.HOME_POWIN_DEVICE_20, "getPCSConfig.sh"));
	}
	
	@Test
	public void isConfigJSONPresent() throws IOException, InterruptedException {
		assertTrue(StackCommandDriver.isRemoteFilePresent(StackCommandDriver.HOME_POWIN_CONFIGURATION, "getConfigurationJSON.sh"));
		(new File(StackCommandDriver.HOME_POWIN_CONFIGURATION)).delete();
		assertTrue(StackCommandDriver.isRemoteFilePresent(StackCommandDriver.HOME_POWIN_CONFIGURATION, "getConfigurationJSON.sh"));
	}
	

}
