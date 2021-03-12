package com.powin.modbusfiles.cycling;

import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpStatusEnum;
import com.powin.modbusfiles.apps.PowerApps;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Stack230Test {
	private static final String HOME_POWIN_DEVICE_20 = "/home/powin/device-20";
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int ONE_SECOND = 1000;
	private static final int MAX_POWER_PERCENT = 100;
	private static final String BALANCE_STRATEGY = "BALANCE_TO_AVERAGE";
	private final static Logger LOG = LogManager.getLogger(Stack230Test.class.getName());
	private static final int MAX_BALANCE_MINUTES = 120;
	private static Balancing cBalancing;
	private static StackStatusChecker cStackStatusChecker;
	private static LastCalls cLastCalls;
	private static FileHelper cReportFile;
	private static String cModbusHostName;
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static Modbus802 cBatteryBaseModelBlockMaster;
	private static int cModbusPort;
	private static int cModbusUnitId;
	private static boolean cEnableModbusLogging;
	private static FileHelper cCellVoltageReportFile;
	private int cArrayIndex;
	private int cStringIndex;
	private Reports cStringReport;
	private String cReportFolder;
	private String cStationCode;
	private int cMaxPermittedPower;
	private int maxChargeDischargeMinutes;
	private String balanceState="OFF";
	private  double namePlateKw;
    private boolean cIsBasicOp;
	/**
	 * Stop moving power
	 * 
	 * @param cIsBasicOp
	 * @throws ModbusException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	static int retryCount=0;
	public static Balancing getBalancing() {
		return cBalancing;
	}
	
	
	public static FileHelper getCellVoltageReportFile() {
		return cCellVoltageReportFile;
	}
	
	public static int getDCWatts() throws ModbusException {
        int dcWatts = 0;
        try {
        	dcWatts = cInverterThreePhaseBlockMaster.getDCWatts();
        } catch (ModbusException e) {
    		resetDevices();
        	dcWatts = cInverterThreePhaseBlockMaster.getDCWatts();
        }
        return dcWatts;
	}
	
	public static LastCalls getLastCalls() {
		return cLastCalls;
	}
	
	public static FileHelper getReportFile() {
		return cReportFile;
	}
	
	public static StackStatusChecker getStackStatusChecker() {
		return cStackStatusChecker;
	}
	
	public static int getWatts() throws ModbusException {
        int watts = 0;
        try {
        	watts = cInverterThreePhaseBlockMaster.getWatts();
        } catch (ModbusException e) {
    		resetDevices();
        	watts = cInverterThreePhaseBlockMaster.getWatts();
        }
        return watts;
	}

	protected static boolean isPCSConfigPresent() throws IOException, InterruptedException {
		File pcsConfigFile = new File(HOME_POWIN_DEVICE_20);
		if (!pcsConfigFile.exists()) {
			LOG.info("Reading PCS configuration from turtle.");
			CommonHelper.executeScriptFromResources("getPCSConfig.sh");
		}
		return pcsConfigFile.exists();
	}

	public static void resetDevices() {
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId,
				cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

	public static void setBalancing(Balancing balancing) {
		cBalancing = balancing;
	}

	public static void setCellVoltageReportFile(FileHelper reportFile) {
		cCellVoltageReportFile = reportFile;
	}

	public static void setLastCalls(LastCalls lastCalls) {
		cLastCalls = lastCalls;
	}

	public static void setReportFile(FileHelper reportFile) {
		cReportFile = reportFile;
	}

	public static void setStackStatusChecker(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker = stackStatusChecker;
	}

	/**
	 * Ctor
	 * @param arrayIndex      
	 * @param stringIndex
	 * @param maxChargeDischargeTime
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public Stack230Test(int arrayIndex, int stringIndex, int maxChargeDischargeTime)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		setArrayIndex(arrayIndex);
		setStringIndex(stringIndex);
		setBalancing(new Balancing(arrayIndex, stringIndex));
		setStackStatusChecker(new StackStatusChecker(getArrayIndex(), getStringIndex()));
		setLastCalls(new LastCalls());
		cStringReport = new Reports(cArrayIndex + "," + cStringIndex);
		setReportFolder("/home/powin");
		setModbusHostName(PowinProperty.TURTLEHOST.toString());
		setModbusPort(4502);
		setModBusUnitId(255);
		setModBusEnableLogging(false);
		setStationCode();
		setMaxChargeDischargeTime(maxChargeDischargeTime);
		resetDevices();
		if (!isPCSConfigPresent()) {
			LOG.error("No PCS configuration file is present!");
			System.exit(1);
		}
		JSONObject deviceConfig = JsonParserHelper.getJSONFromFile(HOME_POWIN_DEVICE_20);
		List<String> results = new ArrayList<>();
		 results = JsonParserHelper.getFieldJSONObject(deviceConfig, "nameplatekW", "", results);
		namePlateKw = 2000.0; //Double.parseDouble(results.get(0)+".0");
		LOG.info("Setting namePlateKw to " + namePlateKw);
		results = JsonParserHelper.getFieldJSONObject(deviceConfig, "maxAllowedkW", "", results);
		int maxPermittedPower = Integer.parseInt((String)results.get(0));
		LOG.info("Setting MaxPermitted Power to " + maxPermittedPower);
		setMaxPermittedPower(maxPermittedPower);
	}

	// TODO Specify this at the start of the test, pick up some configuration
	private void activateCellBalancing() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		cStringReport.getReportContents();
		LOG.info(cStringReport.getAvgCellGroupVoltage());

		switch (BALANCE_STRATEGY) {
		case "BALANCE_TO_AVERAGE":
			balanceToAverage();
			break;
		case "BALANCE_TO_PROVIDED":
			balanceToProvided(getAvgCellGroupVoltage());
			break;
		}
		cStringReport.getReportContents();
		LOG.info(cStringReport.getAvgCellGroupVoltage());

	}

	private void balanceToAverage() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		LOG.info("BalanceToAverage");
		getBalancing().balanceToAverage(String.valueOf(getArrayIndex()), String.valueOf(getStringIndex()));
	}

	private void balanceToProvided(int targetVoltage)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		LOG.info(String.format("BalanceToProvided voltage of %d", targetVoltage));
		getBalancing().balanceToProvided(String.valueOf(getArrayIndex()), String.valueOf(getStringIndex()),
				targetVoltage);
	}

	/**
	 *
	 */
	private void chargeDischargeSeconds(int targetP, boolean isCharging, int seconds, boolean isBasicOp)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {

		Instant startTime = Instant.now();
		Instant endTime = startTime.plusSeconds(seconds);
		int targetSoc = isCharging ? MAX_POWER_PERCENT : 0;
		int percent = kwToPercentage(targetP); 

		movePower(isBasicOp, targetP, targetSoc, percent);

		boolean isDone = false;
		int printInfoCounter = 0;
		int soc = 0;
		while (!isDone) {
			soc = Integer.parseInt(getStringReport().getStringSoc());
            //adjustPowerTowardsTarget(targetP, isBasicOp, targetSoc);
			
			Duration elapsed = Duration.between(Instant.now(), endTime); 
			isDone = elapsed.isZero() || elapsed.isNegative();
			isDone |= isCharging ? isChargingComplete(isBasicOp) : isDischargingComplete(isBasicOp);
            //isDone |= printInfoCounter == 3; // Set this to do a dry run.
			
			logPowerInfo(targetP, soc, printInfoCounter++);
			if (!isDone) {
				Thread.sleep(ONE_SECOND);
			} else {
				cReportFile.writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}
		}
	}

	protected void adjustPowerTowardsTarget(int targetP, boolean isBasicOp, int targetSoc) throws ModbusException {
		double power = Double.parseDouble((getStringReport().getStringPower()));
		int sign = targetP < 0 ? -1 : 1;
		if (Math.abs(power) > 0.0 && Math.abs(power - targetP) > 1) {
         if (Math.abs(power) > Math.abs(targetP)) {
        	 targetP = (Math.abs(targetP) - 1) * sign; 
         } else {
        	 targetP = (Math.abs(targetP) + 1) * sign;
		}
       
		 movePower(isBasicOp, targetP, targetSoc, kwToPercentage(targetP));
		}
	}

	public void chargeForMinutesBasicOp(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutes(powerDivider, minutes, true, true, String.format(" charging %s for %d minutes at P/%d", "BasicOps",
		minutes, powerDivider));
	}

	public void chargeForMinutesSunSpec(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutes(powerDivider, minutes, false, true, String.format(" charging %s for %d minutes at P/%d", "SunSpec",
		minutes, powerDivider));
	}

	private void chargeDischargeForMinutesPower(int kw, int minutes, boolean isBasicOp, boolean isCharging, String entryExit) throws IOException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for " , isCharging, minutes * SECONDS_PER_MINUTE);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, minutes * SECONDS_PER_MINUTE, isBasicOp);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	public void chargeForMinutesPowerBasicOp(int kw, int minutes) throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		 chargeDischargeForMinutesPower(kw, minutes, true, true, String.format(" charging %s for %d minutes at P/%d", "BasicOps",
						minutes, kw));
	}

	public void chargeForMinutesPowerSunSpec(int kw, int minutes)  throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		System.out.println("chargeForMinutesPowerSunspec");
		 chargeDischargeForMinutesPower(kw, minutes, false, true, String.format(" charging %s for %d minutes at P/%d", "SunSpec",
						minutes, kw));
	}


	private void chargeForSeconds(int powerDivider, int seconds, boolean isBasicOp, String entryExitMsg) throws IOException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		chargeDischargeSecondsPowerDivider(powerDivider, seconds, isBasicOp, true, entryExitMsg);
	}

	protected void chargeDischargeSecondsPowerDivider(int powerDivider, int seconds, boolean isBasicOp,
			boolean isCharging, String entryExitMsg) throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException {
		LOG.info("Start" + entryExitMsg);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, seconds);
		chargeDischargeSeconds(targetPowerkW, isCharging, seconds, isBasicOp);
		stopPower();
		LOG.info("Stop" + entryExitMsg);
	}
	
	public void chargeForSecondsBasicOp(int powerDivider, int seconds) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeForSeconds(powerDivider, seconds, true, String.format(" charging %s for %d seconds at P/%d", "BasicOps",
						seconds, powerDivider));
	}

	public void chargeForSecondsSunSpec(int powerDivider, int seconds) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeForSeconds(powerDivider, seconds, false, String.format(" charging %s for %d seconds at P/%d", "SunSpec",
						seconds, powerDivider));
	}

	protected void closeContactorsAndVerify()
			throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		if (!getStackStatusChecker().checkForContactorStatus(false, 90)) {
			LOG.info("Failed to close contactors, test abort!");
			System.exit(1); // Contactors are open and won't likely close again let's stop.
		}
		LOG.info("RAF closeContactorsAndVerify");
	}

	public void createNewCellVoltageReportFile(String folder, String fileName) throws IOException {
		setCellVoltageReportFile(FileHelper.createTimeStampedFile(folder, fileName, ".csv"));
	}



	protected void disablePowerApps() throws Exception {
		ModbusPowinBlock.getModbusPowinBlock().disableBasicOp();
		ModbusPowinBlock.getModbusPowinBlock().disableSunspec();
	}

	protected void chargeDischargeForMinutes(int powerDivider, int minutes, boolean isBasicOp, boolean isCharging,
			String entryExit) throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException {
		LOG.info("Start" + entryExit);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, minutes * SECONDS_PER_MINUTE);
		chargeDischargeSeconds(targetPowerkW, isCharging, minutes * SECONDS_PER_MINUTE, isBasicOp);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	public void dischargeForMinutesBasicOp(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutes(powerDivider, minutes, true, false, String.format(" discharging %s for %d minutes at P/%d", "BasicOps",
		minutes, powerDivider));
	}

	public void dischargeForMinutesSunSpec(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutes(powerDivider, minutes, false, false, String.format(" discharging %s for %d minutes at P/%d", "SunSpec",
		minutes, powerDivider));
	}
	
	public void dischargeForMinutesPowerBasicOp(int kw, int minutes) throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		 chargeDischargeForMinutesPower(kw, minutes, true, false, String.format(" discharging %s for %d minutes at %dkW", "BasicOps",
						minutes, kw));
	}

	public void dischargeForMinutesPowerSunSpec(int kw, int minutes)  throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		System.out.println("dischargeForMinutesPowerSunSpec");
		 chargeDischargeForMinutesPower(kw, minutes, false, false, String.format(" discharging %s for %d minutes at %dkW", "SunSpec",
						minutes, kw));
		
	}

	private void dischargeForSeconds(int powerDivider, int seconds, boolean isBasicOp, String entryExitMsg) throws IOException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		chargeDischargeSecondsPowerDivider(powerDivider, seconds, isBasicOp, false, entryExitMsg);
	}
	
	public void dischargeForSecondsBasicOp(int powerDivider, int seconds) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		dischargeForSeconds(powerDivider, seconds, true, String.format(" discharging %s for %d seconds at P/%d", "BasicOps",
						seconds, powerDivider));
	}

	public void dischargeForSecondsSunSpec(int powerDivider, int seconds) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		dischargeForSeconds(powerDivider, seconds, false, String.format(" discharging %s for %d seconds at P/%d", "SunSpec",
						seconds, powerDivider));
	}

	private void fullCharge(int powerDivider, boolean isBasicOp, boolean isCharging) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischarge(powerDivider, isBasicOp, isCharging, String.format(" full charging at P/%d", powerDivider));
	}

	protected void fullChargeDischarge(int powerDivider, boolean isBasicOp, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, maxChargeDischargeMinutes*SECONDS_PER_MINUTE);
		chargeDischargeSeconds(targetPowerkW, isCharging, maxChargeDischargeMinutes*SECONDS_PER_MINUTE, isBasicOp);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	public void fullChargeBasicOp(int powerDivider) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		fullCharge(powerDivider, true, true);
	}
	
	public void fullChargeSunSpec(int powerDivider) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		fullCharge(powerDivider, false, true);
	}

	protected void fullChargeDischargePower(int kw, boolean isBasicOp, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for " , isCharging, maxChargeDischargeMinutes * SECONDS_PER_MINUTE);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, maxChargeDischargeMinutes*SECONDS_PER_MINUTE, isBasicOp);
     	stopPower();
		LOG.info("Stop" + entryExit);
	}


	private void fullChargePower(int kw, boolean isBasicOp, String entryExit) throws IOException, InterruptedException,
	ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischargePower(kw, isBasicOp, true, entryExit);
	}

	public void fullChargePowerBasicOp(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		fullChargePower(kw, true, String.format(" full charging at P = %d kw", kw));
	}
	
	public void fullChargePowerSunSpec(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException { 
        System.out.println("fullChargePowerSunSpec");
		fullChargePower(kw, false, String.format(" full charging at P = %d kw", kw));
	}

	private void fullDischarge(int powerDivider, boolean isBasicOp) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischarge(powerDivider, isBasicOp, false, String.format(" full discharging at P/%d", powerDivider));
	}

	public void fullDischargeBasicOp(int powerDivider) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		fullDischarge(powerDivider, true);
	}

	public void fullDischargeSunSpec(int powerDivider) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		fullDischarge(powerDivider, false);
	}

	private void fullDischargePower(int kw, boolean isBasicOp, String entryExit) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischargePower(kw, isBasicOp, false, entryExit);
	}

	public void fullDischargePowerBasicOp(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException { 
		fullDischargePower(kw, true, String.format(" full discharging at P = %d kw", kw));
	}
	
	public void fullDischargePowerSunSpec(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		System.out.println("fullDischargePowerSunSpec");
		fullDischargePower(kw, false, String.format(" full discharging at P = %d kw", kw));
	}

    public int getArrayIndex() {
		return cArrayIndex;
	}
	
	private int getAvgCellGroupVoltage() {
		getStringReport().getReportContents();
		return Integer.valueOf(getStringReport().getMaxCellGroupVoltage());
	}

	public int getMaxPermittedPower() {
		return cMaxPermittedPower;
	}

	public String getModbusHostName() {
		return cModbusHostName;
	}

	public String getReportFolder() {
		return cReportFolder;
	}

	public String getStationCode() {
		return cStationCode;
	}

	public int getStringIndex() {
		return cStringIndex;
	}

	public Reports getStringReport() {
		return cStringReport;
	}
	
	private double getTargetP(boolean isCharging, int powerDivider) {
		double powerkW = isCharging ? Math.abs(namePlateKw / powerDivider) * -1
				: Math.abs(namePlateKw / powerDivider);
		return Math.min(powerkW, getMaxPermittedPower());
	}

	private boolean isBasicOpComplete() throws ModbusException {
		boolean isBasicOpComplete = (getModbusPowinBlock().getStatusEnum() == BasicOpStatusEnum.Done);
        if (isBasicOpComplete) {
        	LOG.info("DONE returned by BasicOps.");
        }
        return isBasicOpComplete;
	}

	public boolean isChargingComplete(boolean isBasicOps) throws IOException, ModbusException {
		//System.out.println("isChargingComplete");		
		boolean ret = isHighVoltageAlarmPresent();
		if (isBasicOps) {
			ret |= isBasicOpComplete();
		}
		return ret;
	}

	public boolean isDischargingComplete(boolean isBasicOps) throws IOException, ModbusException {
		//System.out.println("isDischargingComplete");
		int soc = Integer.parseInt(getStringReport().getStringSoc());
		boolean ret = isLowVoltageAlarmPresent() || soc < 1;
		if (isBasicOps) {
			ret |= isBasicOpComplete();
		}
		return ret;
	}
	private boolean isHighVoltageAlarmPresent() throws IOException {
		return isNotificationPresent(Arrays.asList("1001", "2534"));
	}
	
	private boolean isLowVoltageAlarmPresent() throws IOException {
		return isNotificationPresent(Arrays.asList("1004", "2534"));
	}

	private boolean isNotificationPresent(List<String> notifications) throws IOException {
		boolean ret = false;
		for (String notification : notifications) {
			ret |= isNotificationPresent(notification);
			if (ret) {
				LOG.info(notification + " is present.");
			}
		}
		return ret;
	}

	private boolean isNotificationPresent(String notification) throws IOException {
		Notifications notifications = new Notifications(cArrayIndex + "," + cStringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf(notification) != -1;
	}

	protected int kwToPercentage(int kw) {
		return (int) Math.floor(kw/namePlateKw * 100);
	}

	private void logPowerInfo(int targetP, int targetSoc, int printInfoCounter) throws IOException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException, InterruptedException {
		if (printInfoCounter % 5 == 0) {
			printInfoInConsole();
			saveDataToCsv(targetSoc, targetP);
		}
	}

	void movePower(boolean isBasicOp, int targetP, int targetSoc, int percent) throws ModbusException {
		
		targetP = Math.min(getMaxPermittedPower(), targetP);
		
		if (isBasicOp) {
			LOG.info(String.format("BasicOpPriorityPower %d, %d", targetP, targetSoc));
            ModbusPowinBlock.getModbusPowinBlock().runSimpleBasicOpCommand(BigDecimal.valueOf(targetP).movePointRight(3));
		} else {
			setPAsPercent(BigDecimal.valueOf(percent));
		}
	}

	public void printInfoInConsole() throws IOException {
		getStringReport().getReportContents();
		String cellVoltageMaximum = getStringReport().getMaxCellGroupVoltage();
		String cellVoltageMinimum = getStringReport().getMinCellGroupVoltage();
		String soc = getStringReport().getStringSoc();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		String stackPowerKW = getStringReport().getStringPower();
		LOG.info(String.format("vMax: %s vMin: %s SOC: %s DcbusV: %s StackKW: %s", cellVoltageMaximum, cellVoltageMinimum, soc,
				dcbusVolt, stackPowerKW));

	}

	public void restMinutes(int minutes) throws IOException, KeyManagementException, NoSuchAlgorithmException,
	ParseException, InterruptedException, ModbusException {
		System.out.println("restMinutes");
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Start resting for " + minutes + " minutes.");
		LOG.info("Start resting for " + minutes + " minutes.");

		int writeCsvCounter = 0;
		Instant startTime = Instant.now();
		Instant endTime =  startTime.plusSeconds(minutes*SECONDS_PER_MINUTE);
		boolean isDone = false;
		while(!isDone) {
			writeCsvCounter++;
			if (writeCsvCounter % 5 == 0) {
				saveDataToCsv(0, 0);
				printInfoInConsole();
			}
			Duration elapsed = Duration.between(Instant.now(), endTime); 
			isDone = elapsed.isZero() || elapsed.isNegative();
			if (!isDone) {
				Thread.sleep(1000);
			} else {
				cReportFile.writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}
		}
		LOG.info("Stop resting for " + minutes + " minutes.");
	}

	public boolean restUntilBalanced(int minutes, int toleranceMv, boolean isBasicOp) throws IOException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException, ModbusException {
		System.out.println("restUntilBalanced");
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Start resting until all cells are balanced");
		LOG.info("Start resting until all cells are balanced or " + minutes + " minutes have elapsed.");

		stopPower();

		activateCellBalancing();
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(minutes * SECONDS_PER_MINUTE);
        endTime = (0 == minutes) ? startTime.plusSeconds(MAX_BALANCE_MINUTES * SECONDS_PER_MINUTE) : endTime;
		boolean isBalanceToProvided = false;
		for (int i = 0; i < 60; i++) {
			String content = cLastCalls.getLastCallsContent();

			if (content.contains("BALANCE_TO_PROVIDED") && (content.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON")
					|| content.contains("BATTERY_PACK_CHARGE_BALANCING_ON"))) {
				LOG.info("PASS: Balance Strategy is Set using " + BALANCE_STRATEGY);
				isBalanceToProvided = true;
				balanceState="ON";
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceToProvided) {
			LOG.info("Failed to set balance to average, test abort.");
			return false;
		}

		boolean isCgcBalanced = false;
		getStringReport().getReportContents();
		int cellVoltageMaximum = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
		int cellVoltageMinimum = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
		int writeCsvCounter = 0;
		boolean isDone = false;
		while(!isDone) {
			writeCsvCounter++;
			if (writeCsvCounter % 5 == 0) {
				saveDataToCsv(0, 0);
				printInfoInConsole();
			}
			Duration elapsed = Duration.between(Instant.now(), endTime); 
			isDone = elapsed.isZero() || elapsed.isNegative();
			if (!isDone) {
				Thread.sleep(1000);
			} else {
				cReportFile.writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}
			
			getStringReport().getReportContents();
			cellVoltageMaximum = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
			cellVoltageMinimum = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
			if (cellVoltageMaximum - cellVoltageMinimum <= toleranceMv) {
				isCgcBalanced = true;
				cReportFile.writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",All CGCs are balanced");
				LOG.info("All CGCs are balanced.");
				break;
			}
		}

		if (isCgcBalanced == false) {
			cReportFile.writeToCSV(
					DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Not all CGCs are balanced, but time is up.");
			LOG.info("Not all CGCs are balanced, but time is up.");
		}
		getBalancing().balanceStop(cArrayIndex, cStringIndex);
		balanceState="OFF";
		return true;
	}

	/**
     * TODO move to a string utils class
     * Reverse rotate
     * moves the last string in a csv string to the front
     * s1,s2,s3 -> s3,s1,s2
     * @param string is a csv string 
     * @return csv string
     */
	protected String rrotCSV(String csvString) {
		String [] ret = csvString.split(",");
		String t=ret[ret.length-1];
		for (int i=ret.length-1; i>0; --i) {
			ret[i]=ret[i-1];
		}
		ret[0] = t;
		
		return String.join(",", ret);
	}
	
	public void runCellBalancingBasicOpTest() throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		activateCellBalancing();
		fullDischargeBasicOp(4);
		fullChargeBasicOp(4);
		Scanner s = new Scanner(System.in);
		System.out.print("Connect external Current Clamp and press any key to continue.");
		s.nextLine();
		chargeForMinutesBasicOp(4, 24);

	}

	public void runCellBalancingSunSpecTest() throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		stopBalancingAndVerify();
		fullDischargeSunSpec(4);
		fullChargeSunSpec(4);
		Scanner s = new Scanner(System.in);
		System.out.print("Connect external Current Clamp and press any key to continue.");
		s.nextLine();
		chargeForMinutesBasicOp(4, 24);

	}

	public void runDirectCurrentInternalResistanceTest() throws IOException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException, ModbusException, ParseException {
		createNewReportFile("DCIR");
		fullDischargeSunSpec(4);
		activateCellBalancing();
		if (!restUntilBalanced(60, 10, false))
			return;
		if (!stopBalancingAndVerify())
			return;
		fullChargeSunSpec(4);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 12);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 12);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 12);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 12);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		dischargeForMinutesSunSpec(4, 24);
		restMinutes(60);
		dischargeForSecondsSunSpec(3, 30);
		restMinutes(10);
		chargeForSecondsSunSpec(3, 30);
		restMinutes(10);
	}

	public void runOpenCircuitVoltageCharacterizationTest() throws IOException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException, ModbusException, ParseException {
		createNewReportFile("OCVTest");

		fullDischargeSunSpec(4);

		if (!restUntilBalanced(120, 10, false))
			return;

		if (!stopBalancingAndVerify())
			return;

		fullChargeSunSpec(4);

		restMinutes(120);

		while (!isDischargingComplete(false)) {
			dischargeForMinutesSunSpec(4, 10);
			restMinutes(60);
		}
	}

	public void runRateCharacterizationBasicOp(int powerDivider) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		runRateCharacterizationTest(powerDivider, true);
	}

	public void runRateCharacterizationSpecialBasicOp(int kw) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		runRateCharacterizationSpecialTest(kw, true);
	}

	public void runRateCharacterizationSpecialSunSpec(int kw) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		runRateCharacterizationSpecialTest(kw, false);
	}

	protected void runSoCBalancingTest2(int kw, boolean isBasicOp) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("SoCBalancing");
		closeContactorsAndVerify();
		activateCellBalancing();
		cycleFifteenTimes(kw);
		stopBalancing();
		cycleFifteenTimes(kw);
		cycleFifteenTimesDischargeBalancingOnly(kw);
		stopPower();
	}

	protected void runSoCBalancingTest(int kw, boolean isBasicOp) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("SoCBalancing");
		closeContactorsAndVerify();
		activateCellBalancing();
		cycleFifteenTimes(kw);
		stopBalancing();
		cycleFifteenTimes(kw);
		cycleFifteenTimesDischargeBalancingOnly(kw);
		stopPower();
	}

	private void cycleFifteenTimes(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		for (int i = 0; i < 15; ++i) {
		  fullChargePowerBasicOp(kw);
		  fullDischargePowerBasicOp(kw);
		}
	}

	private void cycleFifteenTimesDischargeBalancingOnly(int kw) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		for (int i = 0; i < 15; ++i) {
			stopBalancing();
			fullChargePowerBasicOp(kw);
			activateCellBalancing();
			fullDischargePowerBasicOp(kw);
		}
	}

    /**
     * OCVTEST
     * @param kw
     * @param isBasicOp
     * @throws IOException
     * @throws InterruptedException
     * @throws ModbusException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
	protected void runOpenCircuitVoltageTest(int kw, boolean isBasicOp) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("OCVTestSpecial");
		closeContactorsAndVerify();
        maxChargeDischargeMinutes = 1;
		/* 1 */ fullDischargePowerSunSpec(kw);
		/* 2 - 4 */ restUntilBalanced(1, 10, isBasicOp);
		/* 5 */ fullChargePowerSunSpec(kw);
		/* 6 */ restMinutes(1);
		while (!isDischargingComplete(isBasicOp)) {
			/* 7 */ dischargeForMinutesPowerSunSpec(kw, 1);
			/* 8 */ restMinutes(1);
			/* 9 */ }
		/* 10 */ chargeForMinutesPowerSunSpec(kw, 1);
		/* 11 */ restMinutes(1);
		/* 12 */ fullDischargePowerSunSpec(kw);
		/* 13 */ restMinutes(1);
		while (!isChargingComplete(isBasicOp)) {
			/* 14 */ chargeForMinutesPowerSunSpec(kw, 1);
			/* 15 */ restMinutes(1);
			/* 16 */ }

	}

	// TODO This is a one off don't leave this active
	protected void runRateCharacterizationSpecialTest(int kw, boolean isBasicOp) throws IOException,
			InterruptedException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("CyclingAtSpecial");
		int restMinutes = 60;
//		restMinutes(restMinutes);
		
		fullDischargePowerSunSpec(kw);
//		fullDischargePowerBasicOp(kw);
//		fullDischargePower(kw, isBasicOp, String.format(" full discharging at P = %d kw", kw));
		
		restMinutes(restMinutes);
		
		setPowerApp(PowerApps.BasicOp);

//		int balMinutes = 120;
//		if (!restUntilBalanced(balMinutes, 10, isBasicOp))
//			return;
        fullChargePowerBasicOp(77);
//        fullChargePowerSunSpec(77);
//		fullChargeDischargePower(kw, isBasicOp, true, String.format(" full charging at P = %d kw", kw));

		restMinutes(restMinutes);

		fullDischargePowerBasicOp(77);
//		fullDischargePowerSunSpec(77);
//		fullDischargePower(kw, isBasicOp, String.format(" full discharging at P = %d kw", kw));

		restMinutes(restMinutes);

//		int chargeMinutes = 25;
//		chargeForMinutesPowerSunSpec(kw, chargeMinutes);
//		chargeDischargeForMinutesPower(kw, chargeMinutes, isBasicOp, true, String.format(" charging %s for %d minutes at P/%d", (isBasicOp ? "BasicOps" : "SunSpec"),
//				chargeMinutes, kw));

//		restMinutes(restMinutes);
	}


	//TODO this will go away
	private void runRateCharacterizationTest(int powerDivider, boolean isBasicOp) throws IOException,
			InterruptedException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("CyclingAtP" + powerDivider);

		fullDischarge(4, isBasicOp);

		int balMinutes = 120;

		if (!restUntilBalanced(balMinutes, 10, isBasicOp))
			return;

		fullCharge(powerDivider, isBasicOp, true);

		int restMinutes = 60;
		restMinutes(restMinutes);

		fullDischarge(powerDivider, isBasicOp);

		restMinutes(restMinutes);

		int chargeMinutes = 24;
		chargeDischargeForMinutes(4, chargeMinutes, isBasicOp, true, String.format(" charging %s for %d minutes at P/%d", (isBasicOp ? "BasicOps" : "SunSpec"),
		chargeMinutes, powerDivider));

		restMinutes(restMinutes);
	}

	
	public void runRateCharacterizationTestSunSpec(int powerDivider) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException {
		runRateCharacterizationTest(powerDivider, false);
	}
	
	public void runSOEResetUsingBasicOpTest() throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		createNewReportFile("SOEReset");
		fullDischargeBasicOp(4);
		chargeForMinutesBasicOp(4, 120);
		restMinutes(10);
		saveDataToCsv(Integer.parseInt(getStringReport().getStringSoc()),
				Integer.parseInt(getStringReport().getStringPower()));
		// Todo Turn Off BMS
		restMinutes(10);
		// Todo Turn On BMS
		saveDataToCsv(Integer.parseInt(getStringReport().getStringSoc()),
				Integer.parseInt(getStringReport().getStringPower()));

	}

	public void runValidateStepTimingsTest() throws IOException, KeyManagementException, NoSuchAlgorithmException,
			InterruptedException, ModbusException, ParseException {
		createNewReportFile("ValidateStepTimings");

		chargeForMinutesSunSpec(3, 5);
        
		restMinutes(3);
        
		dischargeForMinutesSunSpec(3, 4);

		restMinutes(3);

	}

	public void saveCellVoltageDataToCsv(List<List<String>> cells) throws InterruptedException, IOException {
		for (List<String> row : cells) {
			row.add(0,  DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			cCellVoltageReportFile.writeToCSV(String.join(",", (String [])row.toArray(new String[row.size()])));	
		}
		cCellVoltageReportFile.writeToCSV("--------------------------------------------------------------");
	}
	public void createNewReportFile(String fileName) throws IOException {
		createNewCellVoltageReportFile(getReportFolder(), "cellVoltageReport");
		setReportFile(FileHelper.createTimeStampedFile(getReportFolder(), fileName, ".csv"));
//		getReportFile().writeToCSV(
//				"TimeStamp, StepTime, PackDeltaV, CellDeltaV, CellMinV, CellAvgV, CellMaxV, CellMinT, BPID, CGID,  CellAvgT, CellMaxT, BPID, CGID, DCBusV, StackCurrent, StackPower, PCSPowerAC, SOC, ContactorStatus, BalancingState, Notifications");
//		String celsius = "\u00B0C";
//		getReportFile().writeToCSV(" , , mV, mV, mV, mV, mV, " + celsius + ", , , " + celsius + ", " + celsius
//				+ ", , , V, A, kW, kW, %, , ,");
		getReportFile().writeToCSV(
				"TimeStamp, StepTime, CellDeltaV, CellMinV, CellAvgV, CellMaxV, CellAvgT, DCBusV, MeasuredStackV, CalculatedStackV, StackCurrent, StackPower, PCSPowerAC, SOC, ContactorStatus, BalancingState, Notifications");
		String celsius = "\u00B0C";
		getReportFile().writeToCSV(" , , mV, mV, mV, mV, " + celsius + ", V, V, V, A, kW, kW, %, , ,");
		
	}

	public void saveDataToCsv(int targetSoc, int targetP) throws ModbusException, IOException, InterruptedException {

		getStringReport().getReportContents();
		List<List<String>> cells = getStringReport().getCellVoltageArrayReport();
		String packDeltaV = calculatePackDeltaV(cells);
		String cellVoltMax = getStringReport().getMaxCellGroupVoltage();
		String cellVoltMin = getStringReport().getMinCellGroupVoltage();
		String cellVoltAvg = getStringReport().getAvgCellGroupVoltage();
		String cellVoltDelta = String.valueOf(Integer.valueOf(cellVoltMax) - Integer.valueOf(cellVoltMin));
		// String cellTempMax = cStringReport.getMaxCellGroupTemperature();
		// String cellTempMin = cStringReport.getMinCellGroupTemperature();
		// String cellGroupAvgTemp = cStringReport.getAvgCellGroupTemperature();
		String negContactorStatus = getStringReport().getStringNegativeContactorStatus();
		String posContactorStatus = getStringReport().getStringPositiveContactorStatus();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		if (!(Boolean.valueOf(negContactorStatus) && Boolean.valueOf(posContactorStatus))) {
			dcbusVolt = "0"; // don't show hysteresis;
		}
		// String stackVolt = cStringReport.getMeasuredStringVoltage();
		String stackCurrent = getStringReport().getStringCurrent();
		String stackPowerKW = getStringReport().getStringPower();
		String soc = getStringReport().getStringSoc();
		String pcsACPower = String.valueOf(getWatts() / 1000);
		// String pcsDCPower = String.valueOf(getDCWatts());
		String contactorStatus = negContactorStatus + "/" + posContactorStatus;
		// getLastCalls().getLastCallsContent();
		// String bopStatus = getLastCalls().getBOPStatus();

		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);

		String notificationString = "";
		if (notificationList != null && notificationList.size() > 0) {
			for (int i = 0; i < notificationList.size(); i++)
				notificationString += notificationList.get(i) + " ";
		}

		Map<String, String> stringReport_vt_ids = getStringReport().getStringReport_vt_id();

//		String[] fields = { DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), "", // StepTime
//				packDeltaV, cellVoltDelta, cellVoltMin, // rrotCSV(stringReport_vt_ids.get("minVoltageWithId"),
//				cellVoltAvg, cellVoltMax, // rrotCSV(stringReport_vt_ids.get("maxVoltageWithId"),
//				rrotCSV(stringReport_vt_ids.get("minTemperatureWithId")), stringReport_vt_ids.get("avgCellTemperature"),
//				rrotCSV(stringReport_vt_ids.get("maxTemperatureWithId")), dcbusVolt, stackCurrent, stackPowerKW,
//				pcsACPower, soc, contactorStatus, balanceState, notificationString };
		String[] fields = { DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), "", // StepTime
				packDeltaV, 
				cellVoltDelta, 
				cellVoltMin, 
				cellVoltAvg, 
				cellVoltMax, 
				stringReport_vt_ids.get("avgCellTemperature"),
				dcbusVolt, 
			    getMeasuredStackVoltage(),
			    getCalculatedStringVoltage(),
				stackCurrent,
				stackPowerKW,
				pcsACPower, 
				soc, 
				contactorStatus, 
				balanceState, 
				notificationString };
		getReportFile().writeToCSV(String.join(",", fields));
		saveCellVoltageDataToCsv(cells);
	}
	protected String getCalculatedStringVoltage() {
		return getStringReport().getCalculatedStringVoltage();
	}

	protected String getMeasuredStackVoltage() {
		return getStringReport().getMeasuredStringVoltage();
	}
	private String calculatePackDeltaV(List<List<String>> cells) {
		List<Integer> rowSums = new ArrayList<>();
		for (List<String> row : cells) {
			rowSums.add(row.stream().map(Integer::valueOf).reduce(Integer::sum).get());
		}
		Integer max = rowSums.stream().mapToInt(v -> v).max().getAsInt();
		Integer min = rowSums.stream().mapToInt(v -> v).min().getAsInt();
		return String.valueOf(max-min);
	}

	public void setArrayIndex(int arrayIndex) {
		cArrayIndex = arrayIndex;
	}

	public void setPowerApp(PowerApps powerApp) throws ModbusException {
		this.cIsBasicOp = powerApp == PowerApps.BasicOp;
		if (cIsBasicOp) {
			ModbusPowinBlock.getModbusPowinBlock().disableApp("SunspecPowerCommand");
			ModbusPowinBlock.getModbusPowinBlock().enableApp("BasicOp");
		} else {
			ModbusPowinBlock.getModbusPowinBlock().disableBasicOp();
            ModbusPowinBlock.getModbusPowinBlock().enableApp("SunspecPowerCommand");			
		}
	}

	private void setMaxChargeDischargeTime(int maxChargeDischargeMinutes) {
		this.maxChargeDischargeMinutes = maxChargeDischargeMinutes;

	}

	public void setMaxPermittedPower(int maxPermittedPower) {
		cMaxPermittedPower = maxPermittedPower;
	}

	public void setModBusEnableLogging(boolean enableModbusLogging) {
		cEnableModbusLogging = enableModbusLogging;
	}

	public void setModbusHostName(String modbusHostName) {
		cModbusHostName = modbusHostName;
	}

	public void setModbusPort(int modbusPort) {
		cModbusPort = modbusPort;
	}

	public void setModBusUnitId(int modbusUnitId) {
		cModbusUnitId = modbusUnitId;
	}

	public void setPAsPercent(BigDecimal percent) throws ModbusException {
		Modbus123 cImmediateControlsBlockMaster = new Modbus123(getModbusHostName(), 4502, 255, false);
		cImmediateControlsBlockMaster.setWMaxLimPct(percent);
	}
	
	public void setReportFolder(String reportFolder) {
		cReportFolder = reportFolder;
	}

	public void setStationCode() {
		LastCalls lc;
			lc = new LastCalls();
			cStationCode = lc.getStationCode();
			LOG.info("Station Code is: {}", cStationCode);
	}

	public void setStringIndex(int stringIndex) {
		cStringIndex = stringIndex;
	}

	public void setStringReport(Reports stringReport) {
		cStringReport = stringReport;
	}

	protected void stopBalancing() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		balanceState="OFF";
		getBalancing().balanceStop(getArrayIndex(), getStringIndex());
		LOG.info("Balance stopped.");
		cReportFile.writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Balance stopped");
	}

	public boolean stopBalancingAndVerify()
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Try to stop balancing.");
		cReportFile.writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Try to stop balancing");
		boolean isBalanceStopped = false;
		getBalancing().balanceStop(getArrayIndex(), getStringIndex());
		int timeOut = 60;
		cLastCalls = new LastCalls();

		for (int i = 0; i < timeOut; i++) {
			String content = cLastCalls.getLastCallsContent();
			if (content.contains("BALANCING_OFF")) {
				LOG.info("PASS: Balance stopped.");
				cReportFile.writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",PASS: Balance stopped");
				isBalanceStopped = true;
				balanceState="OFF";
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceStopped) {
			LOG.info("FAIL: Balance did not stop.");
			cReportFile.writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",FAIL: Balance did not stop");
			return false;
		}
		return true;
	}

	public void stopPower()		{
		LOG.info("Stopping power with " + (cIsBasicOp ? PowerApps.BasicOp : PowerApps.SunspecPowerCommand));
		
		try {
			if (cIsBasicOp) {
				ModbusPowinBlock.getModbusPowinBlock().setBasicOpPriorityPower(0, 0);
			} else {
				setPAsPercent(BigDecimal.ZERO);
			}
			//disablePowerApps();
			//cBalancing.balanceStop(getArrayIndex(), getStringIndex());
			//cBalancing.openContactors(cArrayIndex, cStringIndex);
		} catch (Exception e) {
			retryCount++;
			if (retryCount > 0 && retryCount < 3) {
				resetDevices();
				stopPower();
			} else if (retryCount >= 3) {
				LOG.error("Unable to Stop Power after retry " + retryCount);
			}
		}
		retryCount=0;
	}

	protected void writeReportHeader(String units, boolean isCharging, int seconds) throws IOException {
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		cReportFile.writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Start " + (isCharging ? "charging" : "discharging")
					+ units	+ seconds + " seconds.");
	}
	
	public static  String createSemaphore() throws IOException, InterruptedException {
		String filename = "./.semaphore.txt";
		CommonHelper.executeProcess("/bin/touch", filename);
		CommonHelper.setSystemFilePermissons(filename, "755");
		return filename;
	}
	
	public static  String deleteSemaphore() throws IOException, InterruptedException {
		String filename = "./.semaphore.txt";
		CommonHelper.executeProcess("/bin/rm", "-f", filename);
		return filename;
	}

	public static void main(String[] args) {
		try {
			Stack230Test mStack230Test = new Stack230Test(1, 1, 360);
			Stack230Test.getBalancing().balanceStop(PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());
			LOG.info("Setting station code: " + args[0]);
            mStack230Test.runSoCBalancingTest(112, true);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			LOG.error(e.getStackTrace().toString());
		}

	}


}
