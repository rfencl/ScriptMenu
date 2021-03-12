package com.powin.stackcommander;

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
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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

//CodeReview: Remove all unnecessary commented code. Makes code look neater. Also indent the code 
public class StackCommandDriver implements IStackCommandExecutor {
	private final static Logger LOG = LogManager.getLogger(StackCommandDriver.class.getName());
	protected static final String HOME_POWIN_DEVICE_20 = "/home/powin/device-20";
	protected static final String HOME_POWIN_CONFIGURATION = "/home/powin/configuration.json";
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int ONE_SECOND = 1000;
	private static final int MAX_POWER_PERCENT = 100;
	private static final String BALANCE_STRATEGY = "BALANCE_TO_PROVIDED";
	private static final int MAX_BALANCE_MINUTES = 120;
	private static Balancing cBalancing;
	private static StackStatusChecker cStackStatusChecker;
	private static LastCalls cLastCalls;
	private static FileHelper cReportFile;
	private static String cModbusHostName;
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static int cModbusPort;
	private static int cModbusUnitId;
	private static boolean cEnableModbusLogging;
	private static FileHelper cCellVoltageReportFile;
	private int cArrayIndex;
	private int cStringIndex;
	private Reports cStringReport;
	private String cReportFolder;
	private String cStationCode; // TODO where is this used?
	private int cMaxPermittedPower;
	private int maxChargeDischargeMinutes;
	private String balanceState = "OFF";
	private double namePlateKw;
	private boolean cIsBasicOp = false;
	private Condition condition = Condition.Undefined;

	static {
    	cModbusHostName = PowinProperty.TURTLEHOST.toString();
		LOG.info("Setting cModbusHostName = {}", cModbusHostName);
	}
	static int retryCount = 0;
	private static StackCommandDriver scd;
	private static Modbus802 cBatteryBaseModelBlockMaster;

	public static Balancing getBalancing() {
		return cBalancing;
	}

	public static FileHelper getCellVoltageReportFile() {
		return cCellVoltageReportFile;
	}

	public static LastCalls getLastCalls() {
		return cLastCalls;
	}

	public FileHelper getReportFile() throws IOException {
		if (null == cReportFile) {
			createNewReportFile("StackCommandExec");
		}
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

//	//CodeReview: Combine these 2 methods isPCSConfigPresent(). isConfigJSONPresent to something like this
//	/*protected static boolean isConfigPresent(String fileName){
//		File configJSON = new File(fileName);
//		if (!configJSON.exists()) {
//			LOG.info("Reading configuration json from turtle.");
//			if(fileName.equals(HOME_POWIN_DEVICE_20)) {
//				CommonHelper.getDevice20();;
//			}else if(fileName.equals(HOME_POWIN_CONFIGURATION)) {
//				CommonHelper.getConfigurationJSON();
//			}
//			
//		}
//		return configJSON.exists();
//	}*/
// //Resolved:

	protected static boolean isRemoteFilePresent(String localFilename, String scriptFilename) {
		File pcsConfigFile = new File(localFilename);
		if (!pcsConfigFile.exists()) {
			LOG.info("Reading {} from turtle.", (new File(localFilename)).getName());
			CommonHelper.executeScriptFromResources(scriptFilename);
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
	
	// CodeReview: This series of catch statement can be removed and instead just
	// catch generic exception
	// and print stacktrace ()
	// Resolved
	public void setPowerApp(PowerApps powerApp) {
		this.cIsBasicOp = powerApp == PowerApps.BasicOp;
			closeContactorsAndVerify();
			enableApp();
	}

	public static void setReportFile(FileHelper reportFile) {
		cReportFile = reportFile;
	}

	public static void setStackStatusChecker(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker = stackStatusChecker;
	}

	/**
	 * Ctor
	 * 
	 * @param arrayIndex
	 * @param stringIndex
	 * @param maxChargeDischargeTime
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public StackCommandDriver(int arrayIndex, int stringIndex, int maxChargeDischargeTime)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		setArrayIndex(arrayIndex);
		setStringIndex(stringIndex);
		setBalancing(new Balancing(getArrayIndex(), getStringIndex()));
		setStackStatusChecker(new StackStatusChecker(getArrayIndex(), getStringIndex()));
		setLastCalls(new LastCalls());
		cStringReport = new Reports(cArrayIndex + "," + cStringIndex);
		setReportFolder(PowinProperty.REPORTFOLDER.toString());

		setModbusPort(4502);
		setModBusUnitId(255);
		setModBusEnableLogging(false);
		setMaxChargeDischargeTime(maxChargeDischargeTime);
		resetDevices();
		getPCSConfig();
		setConfiguredStationCode();
	}

	protected void setConfiguredStationCode()
			throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
//		if (!isConfigJSONPresent()) {
//			LOG.error("No PCS configuration file is present!");
//			System.exit(1);
//		}
//		JSONObject deviceConfig = JsonParserHelper.getJSONFromFile(HOME_POWIN_CONFIGURATION);
//		List<String> results = JsonParserHelper.getFieldJSONObject(deviceConfig, "blockConfiguration|stationCode", "", new ArrayList<>());
		LastCalls lc = new LastCalls();
		setStationCode(lc.getStationCode());
	}

	protected void getPCSConfig() throws IOException, InterruptedException {
		if (!isRemoteFilePresent(HOME_POWIN_DEVICE_20, "getPCSConfig.sh")) {
			LOG.error("No PCS configuration file is present!");
			System.exit(1);
		}
		JSONObject deviceConfig = JsonParserHelper.getJSONFromFile(HOME_POWIN_DEVICE_20);
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(deviceConfig, "nameplatekW", "", results);
		namePlateKw = Double.parseDouble(results.get(0) + ".0");
		LOG.info("Setting namePlateKw to " + namePlateKw);
		results = JsonParserHelper.getFieldJSONObject(deviceConfig, "maxAllowedkW", "", results);
		int maxPermittedPower = Integer.parseInt((String) results.get(0));
		LOG.info("Setting MaxPermitted Power to " + maxPermittedPower);
		setMaxPermittedPower(maxPermittedPower);
	}

	// TODO Specify this at the start of the test, pick up some configuration
	public void activateCellBalancing() throws IOException, KeyManagementException, NoSuchAlgorithmException {
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
	private void chargeDischargeSeconds(int targetP, boolean isCharging, int seconds) throws IOException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {

		// CodeReview: USe jodaTime package with DateTime object or DateTimeUtils.
		Instant startTime = Instant.now();
		Instant endTime = startTime.plusSeconds(seconds);
		int targetSoc = isCharging ? MAX_POWER_PERCENT : 0;
		double percent = kwToPercentage(targetP);

		movePower(targetP, targetSoc, percent);

		boolean isDone = false;
		int printInfoCounter = 0;
		int soc = 0;
		while (!isDone) {
			soc = Integer.parseInt(getStringReport().getStringSoc());
			logPowerInfo(targetP, soc, printInfoCounter++);

			Duration elapsed = Duration.between(Instant.now(), endTime);

			isDone = elapsed.isZero() || elapsed.isNegative();
			isDone |= isCharging ? isChargingComplete() : isDischargingComplete();
			isDone |= checkCondition();

			if (!isDone) {
				Thread.sleep(5 * ONE_SECOND);
			} else {
				getReportFile().writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}
		}
	}

	/**
	 * Checks for a condition to end the current command.
	 * 
	 * @return
	 */
	private boolean checkCondition() {
		int soc = Integer.parseInt(getStringReport().getStringSoc());
		boolean ret = false;
		Condition condition = getCondition();
		int soCValue = condition.getSoCValue();
		switch (condition) {
		case SoCLT:
			ret = soc < soCValue;
			break;
		case SoCGT:
			ret = soc > soCValue;
			break;
		case SoCEQ:
			ret = soc == soCValue;
		case Undefined:
			break;
		default:
			break;
		}
		if (ret) {
			LOG.info("The condtion {} {} has been met", condition.toString(), condition.getSoCValue());
		}
		return ret;
	}

	private Condition getCondition() {
		return this.condition;
	}


	protected double kwToPercentage(int kw) {
		LOG.debug("kw = " + kw);
		double percentage = kw / namePlateKw * 100;
		LOG.info("Calc {}% from {}kW", percentage, kw);
		return percentage;
	}

	private void chargeDischargeForHoursPower(int kw, int hours, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for ", isCharging, hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	private void chargeDischargeForMinutesPower(int kw, int minutes, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for ", isCharging, minutes * SECONDS_PER_MINUTE);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, minutes * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	private void chargeDischargeForSecondsPower(int kw, int seconds, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for ", isCharging, seconds);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, seconds);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	protected void closeContactorsAndVerify() {
					Contactors.closeContactors(cArrayIndex, cStringIndex);
		if (!getStackStatusChecker().checkForContactorStatus(false, 90)) {
			LOG.info("Failed to close contactors, test abort!");
			System.exit(1); // Contacters are open and won't likely close again let's stop.
		}
	}

	protected void disablePowerApps() throws Exception {
		ModbusPowinBlock.getModbusPowinBlock().disableBasicOp();
		ModbusPowinBlock.getModbusPowinBlock().disableSunspec();
	}
	// --------------------------------------------------------------------------------------------------------------------
	// Charge / Discharge DURATION
	// --------------------------------------------------------------------------------------------------------------------

	public void dischargeForDuration(Power power, com.powin.stackcommander.Duration duration)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException,
			InterruptedException {
		if (power.isUnitKW()) {
			if (duration.getUnits().equals(com.powin.stackcommander.Duration.HOURS)) {
				dischargeForHoursPower(power.getPower(), calcHours(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.MINUTES)) {
				dischargeForMinutesPower(power.getPower(), calcMinutes(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.SECONDS)) {
				dischargeForSecondsPower(power.getPower(), (int) (duration.getTime().getSeconds()));
			}

		} else {
			if (duration.getUnits().equals(com.powin.stackcommander.Duration.HOURS)) {
				dischargeForHoursPD(power.getPower(), calcHours(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.MINUTES)) {
				dischargeForMinutesPD(power.getPower(), calcMinutes(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.SECONDS)) {
				dischargeForSecondsPD(power.getPower(), (int) (duration.getTime().getSeconds()));
			}
		}

	}

	private int calcMinutes(com.powin.stackcommander.Duration duration) {
		return (int) (duration.getTime().getSeconds() / SECONDS_PER_MINUTE);
	}

	private int calcHours(com.powin.stackcommander.Duration duration) {
		return (int) (duration.getTime().getSeconds() / SECONDS_PER_MINUTE / MINUTES_PER_HOUR);
	}

	public void chargeForDuration(Power power, com.powin.stackcommander.Duration duration)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException,
			InterruptedException {
		if (power.isUnitKW()) {
			if (duration.getUnits().equals(com.powin.stackcommander.Duration.HOURS)) {
				chargeForHoursPower(power.getPower(), calcHours(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.MINUTES)) {
				chargeForMinutesPower(power.getPower(), calcMinutes(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.SECONDS)) {
				chargeForSecondsPower(power.getPower(), (int) (duration.getTime().getSeconds()));
			}
		} else {
			if (duration.getUnits().equals(com.powin.stackcommander.Duration.HOURS)) {
				chargeForHoursPD(power.getPower(), calcHours(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.MINUTES)) {
				chargeForMinutesPD(power.getPower(), calcMinutes(duration));
			} else if (duration.getUnits().equals(com.powin.stackcommander.Duration.SECONDS)) {
				chargeForSecondsPD(power.getPower(), (int) (duration.getTime().getSeconds()));
			}
		}

	}
	// --------------------------------------------------------------------------------------------------------------------
	// Charge / Discharge Power HMS
	// --------------------------------------------------------------------------------------------------------------------

	public void chargeForHoursPower(int kw, int hours) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForHoursPower(kw, hours, true, String.format(" charging for %d hours at %d kW", hours, kw));
	}

	public void chargeForMinutesPower(int kw, int minutes) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutesPower(kw, minutes, true,
				String.format(" charging for %d minutes at %d kW", minutes, kw));
	}

	public void chargeForSecondsPower(int kw, int seconds) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForSecondsPower(kw, seconds, true,
				String.format(" charging for %d seconds at %d kW", seconds, kw));
	}

	public void dischargeForHoursPower(int kw, int hours) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForHoursPower(kw, hours, false, String.format(" discharging for %d hours at %d kW", hours, kw));
	}

	public void dischargeForMinutesPower(int kw, int minutes) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutesPower(kw, minutes, false,
				String.format(" discharging for %d minutes at %d kW", minutes, kw));
	}

	public void dischargeForSecondsPower(int kw, int seconds) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForSecondsPower(kw, seconds, false,
				String.format(" discharging for %d seconds at %d kW", seconds, kw));
	}

//--------------------------------------------------------------------------------------------------------------------
//       Charge / Discharge PD HMS
//--------------------------------------------------------------------------------------------------------------------
	public void chargeForHoursPD(int powerDivider, int hours) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutesPD(powerDivider, hours, true,
				String.format(" charging for %d hours at P/%d", hours, powerDivider));
	}

	public void chargeForMinutesPD(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutesPD(powerDivider, minutes, true,
				String.format(" charging for %d minutes at P/%d", minutes, powerDivider));
	}

	public void chargeForSecondsPD(int powerDivider, int seconds) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeSecondsPD(powerDivider, seconds, true,
				String.format(" charging for %d seconds at P/%d", seconds, powerDivider));
	}

	public void dischargeForHoursPD(int powerDivider, int hours) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForHoursPD(powerDivider, hours, false,
				String.format(" discharging for %d Hours at P/%d", hours, powerDivider));
	}

	public void dischargeForMinutesPD(int powerDivider, int minutes) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		chargeDischargeForMinutesPD(powerDivider, minutes, false,
				String.format(" discharging for %d minutes at P/%d", minutes, powerDivider));
	}

	private void dischargeForSecondsPD(int powerDivider, int seconds) throws IOException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		chargeDischargeSecondsPD(powerDivider, seconds, false,
				String.format(" discharging for %d seconds at P/%d", seconds, powerDivider));
	}

	// CodeReview: chargeDischargeForHoursPD, chargeDischargeForMinutesPD just needs
	// to call chargeDischargeSeconds.
	// Everything else is same and can be handled by single method
	protected void chargeDischargeForHoursPD(int powerDivider, int hours, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		chargeDischargeSeconds(targetPowerkW, isCharging, hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	protected void chargeDischargeForMinutesPD(int powerDivider, int minutes, boolean isCharging, String entryExit)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExit);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, minutes * SECONDS_PER_MINUTE);
		chargeDischargeSeconds(targetPowerkW, isCharging, minutes * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	protected void chargeDischargeSecondsPD(int powerDivider, int seconds, boolean isCharging, String entryExitMsg)
			throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException,
			InterruptedException {
		LOG.info("Start" + entryExitMsg);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging, seconds);
		chargeDischargeSeconds(targetPowerkW, isCharging, seconds);
		stopPower();
		LOG.info("Stop" + entryExitMsg);
	}

	// ------------------------------------------------------------------------------------------------
	// FULL Charge / Discharge
	// ------------------------------------------------------------------------------------------------

	public void fullCharge(Power power) throws IOException, InterruptedException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException {
		if (power.isUnitKW()) {
			fullChargePower(power.getPower());
		} else {
			fullChargePD(power.getPower());
		}

	}

	public void fullDischarge(Power power) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		if (power.isUnitKW())
			fullDischargePower(power.getPower());
		else
			fullDischargePD(power.getPower());
	}

	void fullChargePower(int kw) throws IOException, InterruptedException, ModbusException, KeyManagementException,
			NoSuchAlgorithmException, ParseException {
		fullChargeDischargePower(kw, true, String.format(" full charging at P = %d kw", kw));
	}

	private void fullChargePD(int powerDivider) throws IOException, InterruptedException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischargePD(powerDivider, true, String.format(" full charging at P/%d", powerDivider));
	}

	void fullDischargePower(int kw) throws IOException, InterruptedException, ModbusException, KeyManagementException,
			NoSuchAlgorithmException, ParseException {
		fullChargeDischargePower(kw, false, String.format(" full discharging at %d kW", kw));
	}

	private void fullDischargePD(int powerDivider) throws IOException, InterruptedException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException {
		fullChargeDischargePD(powerDivider, false, String.format(" full discharging at P/%d", powerDivider));
	}

	protected void fullChargeDischargePower(int kw, boolean isCharging, String entryExit) throws IOException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		LOG.info("Start" + entryExit);
		writeReportHeader(" at " + kw + " kW for ", isCharging, maxChargeDischargeMinutes * SECONDS_PER_MINUTE);
		kw = isCharging ? -kw : kw;
		chargeDischargeSeconds(kw, isCharging, maxChargeDischargeMinutes * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}

	protected void fullChargeDischargePD(int powerDivider, boolean isCharging, String entryExit) throws IOException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		LOG.info("Start" + entryExit);
		int targetPowerkW = (int) getTargetP(isCharging, powerDivider);
		writeReportHeader(" at P/" + powerDivider + " for ", isCharging,
				maxChargeDischargeMinutes * SECONDS_PER_MINUTE);
		chargeDischargeSeconds(targetPowerkW, isCharging, maxChargeDischargeMinutes * SECONDS_PER_MINUTE);
		stopPower();
		LOG.info("Stop" + entryExit);
	}
	// ------------------------------------------------------------------------------------------------
	// Charge / Discharge Until Condition
	// ------------------------------------------------------------------------------------------------

	@Override
	public void chargeUntilCondition(Power power, Condition condition) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		this.condition = condition;
		LOG.info("Condition {} has been set. Calling fullCharge()", condition);
		fullCharge(power);

	}

	@Override
	public void dischargeUntilCondition(Power power, Condition condition) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		this.condition = condition;
		LOG.info("Condition {} has been set. Calling fullDischarge()", condition);
		fullDischarge(power);
	}

	// ------------------------------------------------------------------------------------------------
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
		double powerkW = isCharging ? Math.abs(namePlateKw / powerDivider) * -1 : Math.abs(namePlateKw / powerDivider);
		return Math.min(powerkW, getMaxPermittedPower());
	}

	private boolean isBasicOpComplete() throws ModbusException {
		boolean mIsBasicOpComplete = (getModbusPowinBlock().getStatusEnum() == BasicOpStatusEnum.Done);
		if (mIsBasicOpComplete) {
			LOG.info("DONE returned by BasicOps.");
		}
		return mIsBasicOpComplete;
	}

	public boolean isChargingComplete() throws IOException, ModbusException {
		boolean ret = isHighVoltageAlarmPresent();
		if (cIsBasicOp) {
			ret |= isBasicOpComplete();
		}
		return ret;
	}

	public boolean isDischargingComplete() throws IOException, ModbusException {
		int soc = Integer.parseInt(getStringReport().getStringSoc());
		boolean ret = isLowVoltageAlarmPresent() || soc < 1;
		if (cIsBasicOp) {
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

	// TODO Move this to Reporting
	private void logPowerInfo(int targetP, int targetSoc, int printInfoCounter) throws IOException,
			KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException, InterruptedException {
		// if (printInfoCounter % 5 == 0) {
		checkSemaphore();
		printInfoInConsole();
		saveDataToCsv(targetSoc, targetP);
		// }
	}

	public void enableApp()  {
		if (cIsBasicOp) {
			ModbusPowinBlock.getModbusPowinBlock().enableBasicOp();
		} else {
			ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		}
	}

	void movePower(int targetP, int targetSoc, double percent) throws ModbusException {
		targetP = Math.min(getMaxPermittedPower(), targetP);
		// LOG.info("Setting power to " + targetP);
		if (cIsBasicOp) {
//			assert cModbusPowinBlock.getAppStatus("BasicOp") == 1 : "BasicOp is not enabled";
//			cModbusPowinBlock.disableApp("SunspecPowerCommand");
//			cModbusPowinBlock.enableApp("BasicOp");
			LOG.info(String.format("BasicOpPriorityPower %d, %d", targetP, targetSoc));
			if (targetSoc == 100) {
				ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, targetP);
			} else {
				ModbusPowinBlock.getModbusPowinBlock().setBasicOpPriorityPower(targetP, targetSoc);
			}
			// cModbusPowinBlock.getBasicOpPower();
		} else {
//			cModbusPowinBlock.disableBasicOp();
//                       cModbusPowinBlock.enableApp("SunspecPowerCommand");	
			LOG.info("Setting power to {}%", percent);
			setPAsPercent(BigDecimal.valueOf(percent));
		}
	}

	public void restForDuration(com.powin.stackcommander.Duration duration) throws IOException, KeyManagementException,
			NoSuchAlgorithmException, ParseException, InterruptedException, ModbusException {
		stopPower();
		if (duration.getUnits().equals("Minutes") || duration.getUnits().equals("min")) {
			restMinutes(duration.getTime().toMinutes());
		} else if (duration.getUnits().equals("Seconds") || duration.getUnits().equals("s")) {
			restSeconds(duration.getTime().getSeconds());
		} else if (duration.getUnits().equals("Hours") || duration.getUnits().equals("h")) {
			restHours(duration.getTime().toHours());
		}

		LOG.info("Resting for " + duration.getTime());
	}

	public void restHours(long hours) throws IOException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException, ModbusException {
		writeLogInfo(hours, "Hours");
		p_restSeconds(hours * 60 * 60);
		LOG.info("Stop resting for " + hours + " Hours.");
	}

	public void restMinutes(long minutes) throws IOException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException, ModbusException {
		writeLogInfo(minutes, "Minutes");
		p_restSeconds(minutes * 60);
		LOG.info("Stop resting for " + minutes + " Minutes.");
	}

	public void restSeconds(long seconds) throws IOException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException, ModbusException {
		writeLogInfo(seconds, "Seconds");
		p_restSeconds(seconds);
		LOG.info("Stop resting for " + seconds + " Seconds.");
	}

	private void p_restSeconds(long seconds) throws IOException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, ModbusException, InterruptedException {
		int writeCsvCounter = 0;
		Instant startTime = Instant.now();
		Instant endTime = startTime.plusSeconds(seconds);
		boolean isDone = false;
		while (!isDone) {
			writeCsvCounter++;
			logPowerInfo(0, 0, writeCsvCounter);
			Duration elapsed = Duration.between(Instant.now(), endTime);
			isDone = elapsed.isZero() || elapsed.isNegative();
			if (!isDone) {
				Thread.sleep(1000);
			} else {
				getReportFile().writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}
		}
	}

	private void writeLogInfo(long period, String speriod) throws IOException {
		getReportFile().writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		getReportFile().writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Start resting for " + period + " " + speriod + ".");
		LOG.info("Start resting for " + period + " " + speriod + ".");
	}

	public boolean restUntilBalanced(int minutes, int toleranceMv) throws IOException, KeyManagementException,
			NoSuchAlgorithmException, ParseException, InterruptedException, ModbusException {

		getReportFile().writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		getReportFile().writeToCSV(
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
				balanceState = "ON";
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
		while (!isDone) {
			writeCsvCounter++;
			logPowerInfo(0, 0, writeCsvCounter);
			Duration elapsed = Duration.between(Instant.now(), endTime);
			isDone = elapsed.isZero() || elapsed.isNegative();
			if (!isDone) {
				Thread.sleep(1000);
			} else {
				getReportFile().writeToCSV(",Elapsed Time: " + Duration.between(startTime, Instant.now()).toString());
			}

			getStringReport().getReportContents();
			cellVoltageMaximum = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
			cellVoltageMinimum = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
			if (cellVoltageMaximum - cellVoltageMinimum <= toleranceMv) {
				isCgcBalanced = true;
				getReportFile().writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",All CGCs are balanced");
				LOG.info("All CGCs are balanced.");
				break;
			}
		}

		if (isCgcBalanced == false) {
			getReportFile().writeToCSV(
					DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Not all CGCs are balanced, but time is up.");
			LOG.info("Not all CGCs are balanced, but time is up.");
		}
		getBalancing().balanceStop(cArrayIndex, cStringIndex);
		balanceState = "OFF";
		return true;
	}

	/**
	 * TODO move to a string utils class Reverse rotate moves the last string in a
	 * csv string to the front s1,s2,s3 -> s3,s1,s2
	 * 
	 * @param string is a csv string
	 * @return csv string
	 */
	protected static String rrotCSV(String csvString) {
		String[] ret = csvString.split(",");
		String t = ret[ret.length - 1];
		for (int i = ret.length - 1; i > 0; --i) {
			ret[i] = ret[i - 1];
		}
		ret[0] = t;

		return String.join(",", ret);
	}

	public void printInfoInConsole() throws IOException {
		getStringReport().getReportContents();
		String cellVoltageMaximum = getStringReport().getMaxCellGroupVoltage();
		String cellVoltageMinimum = getStringReport().getMinCellGroupVoltage();
		String soc = getStringReport().getStringSoc();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		String stackPowerKW = getStringReport().getStringPower();
		LOG.info(String.format("vMax: %s vMin: %s SOC: %s DcbusV: %s StackKW: %s", cellVoltageMaximum,
				cellVoltageMinimum, soc, dcbusVolt, stackPowerKW));
	}

	public void saveCellVoltageDataToCsv(List<List<String>> cells) throws InterruptedException, IOException {
		for (List<String> row : cells) {
			row.add(0, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			cCellVoltageReportFile.writeToCSV(String.join(",", (String[]) row.toArray(new String[row.size()])));
		}
		cCellVoltageReportFile.writeToCSV("--------------------------------------------------------------");
	}

	// TODO move this to Reporting

	public void createNewCellVoltageReportFile(String fileName) throws IOException {
		setCellVoltageReportFile(FileHelper.createTimeStampedFile(getReportFolder(), fileName, ".csv"));
	}

	/**
	 * Create a new file for this run.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void createNewReportFile(String fileName) throws IOException {
		// CodeReview: reportFolder can be accessed directly. No need to pass it as
		// parameter
		// Resolved
		createNewCellVoltageReportFile("cellVoltageReport");
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
				cellVoltDelta, cellVoltMin, cellVoltAvg, cellVoltMax, stringReport_vt_ids.get("avgCellTemperature"),
				dcbusVolt, getMeasuredStackVoltage(), getCalculatedStringVoltage(), stackCurrent, stackPowerKW,
				pcsACPower, soc, contactorStatus, balanceState, notificationString };
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
		return String.valueOf(max - min);
	}

	public void setArrayIndex(int arrayIndex) {
		cArrayIndex = arrayIndex;
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

	public void setStationCode(String stationCode) {
		cStationCode = stationCode;
	}

	public void setStringIndex(int stringIndex) {
		cStringIndex = stringIndex;
	}

	public void setStringReport(Reports stringReport) {
		cStringReport = stringReport;
	}

	public void stopBalancing() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		balanceState = "OFF";
		getBalancing().balanceStop(getArrayIndex(), getStringIndex());
	}

	public boolean stopBalancingAndVerify()
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Try to stop balancing.");
		getReportFile().writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Try to stop balancing");
		boolean isBalanceStopped = false;
		getBalancing().balanceStop(getArrayIndex(), getStringIndex());
		int timeOut = 60;
		cLastCalls = new LastCalls();

		for (int i = 0; i < timeOut; i++) {
			String content = cLastCalls.getLastCallsContent();
			if (content.contains("BALANCING_OFF")) {
				LOG.info("PASS: Balance stopped.");
				getReportFile().writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",PASS: Balance stopped");
				isBalanceStopped = true;
				balanceState = "OFF";
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceStopped) {
			LOG.info("FAIL: Balance did not stop.");
			getReportFile().writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",FAIL: Balance did not stop");
			return false;
		}
		return true;
	}

	// TODO Move to TestBase class
	public void stopPower() {
		LOG.info("Stopping power with " + (cIsBasicOp ? "BasicOp" : "SunSpec"));

		try {
			if (cIsBasicOp) {
				ModbusPowinBlock.getModbusPowinBlock().setBasicOpPriorityPower(0, 0);
				// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(0));
			} else {
				setPAsPercent(BigDecimal.ZERO);
			}
			// disablePowerApps();
			// cBalancing.balanceStop(getArrayIndex(), getStringIndex());
			// cBalancing.openContactors(cArrayIndex, cStringIndex);
		} catch (Exception e) {
			retryCount++;
			if (retryCount > 0 && retryCount < 3) {
				resetDevices();
				stopPower();
			} else if (retryCount >= 3) {
				LOG.error("Unable to Stop Power after retry " + retryCount);
			}
		}
		retryCount = 0;
	}

	protected void writeReportHeader(String units, boolean isCharging, int seconds) throws IOException {

		getReportFile().writeToCSV(
				DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",******************************************");
		getReportFile().writeToCSV(DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + ",Start "
				+ (isCharging ? "charging" : "discharging") + units + seconds + " seconds.");
	}

	/**
	 * Check to see if there is condition for this command. Currently Charge and
	 * Discharge commands can move to a particular SoC condition.
	 * 
	 * @param condition
	 * @return
	 */
	private static boolean isConditionSet(Condition condition) {
		return Condition.Undefined != condition && condition.getSoCValue() >= 0;
	}

	// --------------------------------------------------------
	// Semaphore Management - Controls execution externally
	// --------------------------------------------------------
	public static String createSemaphore() throws IOException, InterruptedException {
		String filename = "./.semaphore.txt";
		CommonHelper.executeProcess("/bin/touch", filename);
		CommonHelper.setSystemFilePermissons(filename, "755");
		return filename;
	}

	public static String deleteSemaphore() throws IOException, InterruptedException {
		String filename = "./.semaphore.txt";
		CommonHelper.executeProcess("/bin/rm", "-f", filename);
		return filename;
	}

	private void checkSemaphore() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		if (!(new File("./.semaphore.txt")).exists()) {
			LOG.info("The signal to stop the application has been raised");
			stopPower();
			Contactors.openContactors(cArrayIndex, cStringIndex);
			System.exit(0);
		}
	}
	// ------------- End Semaphore Management --------------

	public static void main(String[] args) {
		try {
			int arrayID = Integer.valueOf(args[0]);
			int stringID = Integer.valueOf(args[1]);
			scd = new StackCommandDriver(arrayID, stringID, 360);
			StackCommandDriver.getBalancing().balanceStop(arrayID, stringID);
			if (args.length < 3) {
				LOG.error("Usage parameters are: ArrayID StringID scriptfile");
				deleteSemaphore();
				System.exit(1);
			}

			createSemaphore();
			List<String> lines = FileHelper.readFileToList(args[2]).stream().filter(e -> !e.isEmpty())
					.collect(Collectors.toList());
			List<Executor> executor = Commander.processLines(lines);
			executeScript(executor, scd);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		try {
			deleteSemaphore();
		} catch (IOException | InterruptedException e) {
			LOG.error("Unable to delete Semaphore file");
			e.printStackTrace();
		}
	}

	public static void executeScript(List<Executor> executor, IStackCommandExecutor scd)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException, ModbusException,
			ParseException, java.text.ParseException {
		int i = 0;
		repeatLabel: for (; i < executor.size(); ++i) {
			LOG.info("Execution Line# {}", i);
			Executor exec = executor.get(i);
			LOG.info("Executing {}", exec.toString());
			Command command = exec.getCommand();
			Condition condition = exec.getCondition();
			List<Directive> directives = exec.getDirective();
			Power power = exec.getPower();
			com.powin.stackcommander.Duration duration = exec.getDuration();

			switch (command) {
			case Title:
				String reportName = directives.get(0).getTitle();
				if (StringUtils.isAllEmpty(reportName)) {
					reportName = "StackCommandExec";
				}
				scd.createNewReportFile(reportName);
			case Using:
				switch (directives.get(0)) {
				case DirectP:
					scd.setPowerApp(PowerApps.SunspecPowerCommand);
					break;
				case BasicOp:
					scd.setPowerApp(PowerApps.BasicOp);
					break;
				default:
					break;
				}
				break;
			case FullDischarge:
				if (null != power) {
					scd.fullDischarge(power);
				}
				break;
			case Balancing:
				if (directives.contains(Directive.ON)) {
					scd.activateCellBalancing();
				} else if (directives.contains(Directive.OFF)) {
					scd.stopBalancing();
				}

				break;
			case FullCharge:
				if (directives.contains(Directive.at)) {
					scd.fullCharge(power);
				}
				break;
			case Charge:
				if (directives.contains(Directive.at) && directives.contains(Directive.FOR)) {
					scd.chargeForDuration(power, duration);
				} else if (directives.contains(Directive.FULL)) {
					scd.fullCharge(power);
				} else if (isConditionSet(condition)) {
					scd.chargeUntilCondition(power, condition);
				} else if (!duration.getUnits().isEmpty()) {
					scd.chargeForDuration(power, duration);
				}

				break;
			case Discharge:
				if (directives.contains(Directive.at) && directives.contains(Directive.FOR)) {
					scd.dischargeForDuration(power, duration);
				} else if (directives.contains(Directive.FULL)) {
					scd.fullDischarge(power);
				} else if (isConditionSet(condition)) {
					scd.dischargeUntilCondition(power, condition);
				} else if (!duration.getUnits().isEmpty()) {
					scd.dischargeForDuration(power, duration);
				}
				break;
			case Repeat:
				i = directives.get(0).getRepeatStep() - 3;
				continue repeatLabel;
			case Record:
				break;
			case Turnoff:
				break;
			case Restart:
				break;
			case Rest:
				if (directives.contains(Directive.Until)) {
					scd.restUntilBalanced(480, 10);
				} else {
					scd.restForDuration(duration);
				}
			case Comment:
				break;
			default:
				LOG.error("Command {} not found", command);
				break;
			}
		}

	}

}
