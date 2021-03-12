package com.powin.modbusfiles.cycling;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerApps;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.CommonHelper;

// TODO Set to Ignore before checking in.


public class Stack230TestIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Stack230TestIntegrationTest.class.getName());
	private static final int ENABLED = 1; 
	private static final int DISABLED = 0;
	private static Stack230Test cut;
	private static int arrayIndex = 1;
	private static int stringIndex = 1;
	public static final boolean WAIT = true;
	public static final boolean NO_WAIT = false;
	public static final boolean SHOW_ALERT = true;
	public static final boolean NO_ALERT = false;
	public static final boolean CAPTURE = true;
	public static final boolean NO_CAPTURE = false;
//	@Rule
//	public LogAppenderResource appender = new LogAppenderResource(LogManager.getLogger(Stack230Test.class.getName()));

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cut = new Stack230Test(arrayIndex, stringIndex, 360);
		cut.setPowerApp(PowerApps.SunspecPowerCommand);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//cut.stopBalancing();
	}
	
	@After
	public void tearDown() throws Exception {
		//cut.stopPower();
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
	
	private void rateCharacterizationBasicOpsCommon(int i) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.runRateCharacterizationBasicOp(4);
		//String logMsg = appender.getOutput();
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	private void rateCharacterizationSunspecCommon(int i) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, 		
InterruptedException, ModbusException, ParseException {
		cut.runRateCharacterizationTestSunSpec(i);
		//String logMsg = appender.getOutput();
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
    
	@Test
	public void stopPower() throws IOException, InterruptedException {
         cut.stopPower();		
 		 //assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	/**
	 * This is the one
	 */
	@Test
	public void runSoCBalancing2() throws KeyManagementException,
	NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.setPowerApp(PowerApps.BasicOp);
		cut.runSoCBalancingTest2(112, true);
	}

	@Test
	public void runSoCBalancing() throws KeyManagementException,
	NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.setPowerApp(PowerApps.BasicOp);
		cut.runSoCBalancingTest(112, true);
	}

	@Test
	public void runOpenCircuitVoltageCharacterizationSpecialSunSpec() throws KeyManagementException,
	NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.setPowerApp(PowerApps.SunspecPowerCommand);
		cut.runOpenCircuitVoltageTest(56, false);
	}

	@Test
	public void runRateCharacterizationSpecialSunSpec() throws KeyManagementException,
	NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.setPowerApp(PowerApps.SunspecPowerCommand);
		cut.runRateCharacterizationSpecialTest(58, false);
	}
	
	
	@Test
	public void runRateCharacterizationSpecialBasicOp() throws KeyManagementException,
	NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		cut.setPowerApp(PowerApps.BasicOp);
		cut.runRateCharacterizationSpecialTest(58, false);
	}
	
	@Test
	public void setPAsPercent() throws Exception {
		cut.setPAsPercent(BigDecimal.valueOf(2.5));
	}


	@Test
	public void testCreateSemaphore() throws Exception {
		String filename = Stack230Test.createSemaphore();
		assertTrue((new File(filename)).exists());
	}
	
	@Test
	public void testDeleteSemaphore() throws Exception {
		String filename = Stack230Test.deleteSemaphore();
		assertTrue(!(new File(filename)).exists());
		
	}

	@Test
	public void testChargeForMinutesBasicOp() throws Exception {
		cut.createNewReportFile("ChargeBasicOpsForMinutes");
		cut.chargeForMinutesBasicOp(3, 5);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
	
	@Test
	public void testChargeForMinutesSunspec() throws Exception {
		cut.createNewReportFile("ChargeSunSpecForMinutes");
		cut.chargeForMinutesSunSpec(3, 5);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}

	@Test
	public void testChargeForMinutesPowerBasicOp() throws Exception {
		cut.createNewReportFile("ChargeBasicOpsForMinutes");
		cut.chargeForMinutesPowerBasicOp(20, 5);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}

	@Test
	public void testChargeForMinutesPowerSunspec() throws Exception {
		cut.createNewReportFile("ChargeSunSpecForMinutes");
		cut.chargeForMinutesPowerSunSpec(58, 24);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	@Test
	public void testDisChargeForMinutesPowerSunspec() throws Exception {
		cut.createNewReportFile("ChargeSunSpecForMinutes");
		cut.dischargeForMinutesPowerSunSpec(20, 5);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
	@Test
	public void testEnableBasicOp() throws Exception {
		
		ModbusPowinBlock.getModbusPowinBlock().disableSunspec();
		ModbusPowinBlock.getModbusPowinBlock().enableBasicOp();
		assertThat(ModbusPowinBlock.getModbusPowinBlock().getAppStatus("SunspecPowerCommand"), is(DISABLED));
		assertThat(ModbusPowinBlock.getModbusPowinBlock().getAppStatus("BasicOp"), is(ENABLED));
	}
	
	@Test
	public void testEnableSunSpec() throws Exception {
		ModbusPowinBlock.getModbusPowinBlock().disableBasicOp();
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		assertThat(ModbusPowinBlock.getModbusPowinBlock().getAppStatus("testDisChargeForMinutesPowerSunspecSunspecPowerCommand"), is(ENABLED));
		assertThat(ModbusPowinBlock.getModbusPowinBlock().getAppStatus("BasicOp"), is(DISABLED));
	}
	
	@Test
	public void testFullChargePowerBasicOp() throws Exception {
		cut.createNewReportFile("FullChargePowerBasicOp");
		cut.fullChargePowerBasicOp(47);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	@Test
	public void testFullChargePowerSunSpec() throws Exception {
		cut.createNewReportFile("FullChargePower"
				+ "SunSpec");
		cut.fullChargePowerSunSpec(46);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}

	@Test
	public void testFullDischargePowerBasicOp() throws Exception {
		cut.createNewReportFile("FullDischargeSpecialBasicOp");
		cut.fullDischargePowerBasicOp(35);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}

	@Test
	public void testFullDischargePowerSunSpec() throws Exception {
		cut.createNewReportFile("FullDischargeSpecialSunSpec");
		cut.fullDischargePowerSunSpec(58);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
	@Test
	public void testIsChargingComplete() throws IOException, ModbusException {
		cut.isChargingComplete(true);
	}

	@Test
	public void testKwToPercentage() throws Exception {
		assertThat(cut.kwToPercentage(-77), is(-62));
		assertThat(cut.kwToPercentage(-61), is(-49));
	}

	@Test
	public void testMovePower() throws Exception {
		Contactors.closeContactors(cut.getArrayIndex(), cut.getStringIndex());
		if (!cut.getStackStatusChecker().checkForContactorStatus(false, 90)) {
			LOG.info("Failed to close contactors, test abort!");
			return; // TODO: what happens here, we don't really stop.
		}
		cut.movePower(false, 0, 0, (int)Math.floor(-77/4));
	}

	@Test
	public void testNotificationsPresent() throws IOException {
		StackStatusChecker stackStatusChecker = Stack230Test.getStackStatusChecker();
		Notifications notifications = new Notifications(
				Integer.toString(cut.getArrayIndex()) + "," + Integer.toString(cut.getStringIndex()));
		List<String> notificationList = notifications.getNotificationsInfo();
        LOG.info(notificationList.toString());
	}

	
	@Test
	public void testPCSFileExists() throws IOException, InterruptedException {
		assertTrue(Stack230Test.isPCSConfigPresent());
	}
	
	@Test
	public void testRestUntilBalancedSunSpec() throws Exception {
		cut.createNewReportFile("RestUntilBalanced");
		cut.restUntilBalanced(60, 10, false);
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	
    @Test
    public void testRrotCSV() throws Exception {
    	String t = "is,a,test,this";
    	assertThat(cut.rrotCSV(t), is("this,is,a,test"));
    }
	
	@Test
	public void testRunFullChargeTest() throws Exception {
		cut.createNewReportFile("FullChargeTestTest");
		cut.fullChargeSunSpec(4);
	}
	

	@Test
	public void testRunFullDischargeTest() throws Exception {
		cut.createNewReportFile("FullDischargeTestTest");
		cut.fullDischargeSunSpec(4);
	}
	@Test
	public void testRunOpenCircuitVoltageCharacterizationTest() throws Exception {
		cut.runOpenCircuitVoltageCharacterizationTest();
	}

	@Test
	public void testRunRateCharacterizationTestBasicOpsPD3() throws Exception{
		rateCharacterizationBasicOpsCommon(3);
	}

	@Test
	public void testRunRateCharacterizationTestBasicOpsPD4() throws Exception{
		rateCharacterizationBasicOpsCommon(4);
	}
	@Test
	public void testRunRateCharacterizationTestBasicOpsPD5testFullChargePowerSunSpec() throws Exception{
		rateCharacterizationBasicOpsCommon(5);
	}
	@Test
	public void testRunRateCharacterizationTestSunSpecPD3() throws Exception{
		rateCharacterizationSunspecCommon(3);
	}
	@Test
	public void testRunRateCharacterizationTestSunSpecPD4() throws Exception{
		rateCharacterizationSunspecCommon(4);
	}
	
	@Test
	public void testRunRateCharacterizationTestSunSpecPD5() throws Exception{
		rateCharacterizationSunspecCommon(5);
	}

	@Test
	public void testRunSOEReseUsingBasicOpTest() throws Exception {
		cut.runSOEResetUsingBasicOpTest();
	}

	@Test
	public void testRunValidateStepTimingsTest() throws Exception {
		cut.runValidateStepTimingsTest();
		Contactors.openContactors(arrayIndex, stringIndex);
		assertTrue(Stack230Test.getStackStatusChecker().checkForContactorStatus(true, 60));
	}
	/**testFullChargePowerSunSpec
	 * Write the individual cell voltages to a file 
	 */
	@Test
	public void testWriteCellVoltageReport() throws Exception {
		cut.getStringReport().getReportContents();
		List<List<String>> cells = cut.getStringReport().getCellVoltageArrayReport();
		cut.createNewCellVoltageReportFile(cut.getReportFolder(), "cellVoltageReport");
		cut.saveCellVoltageDataToCsv(cells);
	}
	
	@Test
	public void testRunBash() {
         //gnome-terminal -x sh -c 'printf "New terminal emulator is opened for input\n"; exec bash'
		 //CommonHelper.executeProcess(WAIT, NO_CAPTURE, "gnome-terminal");
		// CommonHelper.executeProcess(WAIT, NO_CAPTURE, "sh", "-c", "echo powin | sudo -S yum install zenity -y &> /dev/null");
		 String text = "Before you run a test make sure that all the ports are configured. Port 4502 is commonly missed.";
		 List<String> result = CommonHelper.executeProcess(WAIT, CAPTURE, "sh", "-c", "echo powin | sudo -S firewall-cmd --list-ports");
		 assertEquals(result.get(0).split(" ").length, 4);
		 LOG.info(result.toString());
	}
}
