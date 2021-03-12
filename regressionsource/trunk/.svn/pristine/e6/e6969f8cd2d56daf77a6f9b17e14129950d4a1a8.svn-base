package com.powin.modbusfiles.stackoperations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StringReport;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Balancing {
	final static Logger LOG = LogManager.getLogger(Balancing.class.getName());
	private static HttpHelper cHttpHelper;
	private static String turtleURL;
	private String cArrayIndex;
	private String cStringIndex;
	private LastCalls cLastCalls;
	private String cReportFolder;
	private Reports cStringReport;
	private static FileHelper cCellVoltageReportFile;
	private static FileHelper cReportFile;
	private static String cModbusHostName;
	private String balanceState = "OFF";
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static Modbus802 cBatteryBaseModelBlockMaster;
	private static ModbusPowinBlock cModbusPowinBlock;
	private static int cModbusPort;
	private static int cModbusUnitId;
	private static boolean cEnableModbusLogging;
	private static int cRestMilliSeconds;

	private static int cStackCount = 2;

	public Balancing(String arrayIndex, String stringIndex) {
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
		setReportFolder(PowinProperty.REPORTFOLDER.toString());
		turtleURL = PowinProperty.TURTLE_URL.toString();
		cRestMilliSeconds = 40000;
	}

	public Balancing(int arrayIndex, int stringIndex) {
		this(String.valueOf(arrayIndex), String.valueOf(stringIndex));
	}

	// TODO move reports section to a Reporting class.
	public void createNewCellVoltageReportFile(String folder, String fileName) throws IOException {
		setCellVoltageReportFile(FileHelper.createTimeStampedFile(folder, fileName, ".csv"));
	}

	public Reports getStringReport() {
		if (null == cStringReport) {
			cStringReport = new Reports(cArrayIndex + "," + cStringIndex);
		}
		return cStringReport;
	}

	public static FileHelper getCellVoltageReportFile() {
		return cCellVoltageReportFile;
	}

	public void setReportFolder(String reportFolder) {
		cReportFolder = reportFolder;
	}

	public String getReportFolder() {
		return cReportFolder;
	}

	protected String rrotCSV(String csvString) {
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

	public void createNewReportFile(String fileName) throws IOException {
		createNewCellVoltageReportFile(getReportFolder(), "cellVoltageReport");
		setReportFile(FileHelper.createTimeStampedFile(getReportFolder(), fileName, ".csv"));
		getReportFile().writeToCSV(
				"TimeStamp, StepTime, PackDeltaV, CellDeltaV, CellMinV, CellAvgV, CellMaxV, CellMinT, BPID, CGID,  CellAvgT, CellMaxT, BPID, CGID, DCBusV, StackCurrent, StackPower, PCSPowerAC, SOC, ContactorStatus, BalancingState, Notifications");
		String celsius = "\u00B0C";
		getReportFile().writeToCSV(" , , mV, mV, mV, mV, mV, " + celsius + ", , , " + celsius + ", " + celsius
				+ ", , , V, A, kW, kW, %, , ,");
	}

	public static FileHelper getReportFile() {
		return cReportFile;
	}

	public static void setReportFile(FileHelper reportFile) {
		cReportFile = reportFile;
	}

	public static void resetDevices() {
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId,
				cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cModbusPowinBlock = ModbusPowinBlock.getModbusPowinBlock();
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

	public static void setCellVoltageReportFile(FileHelper reportFile) {
		cCellVoltageReportFile = reportFile;
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

		Notifications notifications = new Notifications(cArrayIndex + "," + cStringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);

		String notificationString = "";
		if (notificationList != null && notificationList.size() > 0) {
			for (int i = 0; i < notificationList.size(); i++)
				notificationString += notificationList.get(i) + " ";
		}

		Map<String, String> stringReport_vt_ids = getStringReport().getStringReport_vt_id();

		String[] fields = { DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), "", // StepTime
				packDeltaV, cellVoltDelta, cellVoltMin, // rrotCSV(stringReport_vt_ids.get("minVoltageWithId"),
				cellVoltAvg, cellVoltMax, // rrotCSV(stringReport_vt_ids.get("maxVoltageWithId"),
				rrotCSV(stringReport_vt_ids.get("minTemperatureWithId")), stringReport_vt_ids.get("avgCellTemperature"),
				rrotCSV(stringReport_vt_ids.get("maxTemperatureWithId")), dcbusVolt, stackCurrent, stackPowerKW,
				pcsACPower, soc, contactorStatus, balanceState, notificationString };
		cReportFile.writeToCSV(String.join(",", fields));
		saveCellVoltageDataToCsv(cells);
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

	// ----------------------------
	// URLS
	//
	static String getTurtleUrl() {
		return turtleURL;
	}
	// ---------------------------
	// https://powinenergy.atlassian.net/wiki/spaces/PEES/pages/671580849/BMS+EMS+Local+Diagnostic+URLs
	// TODO move these to a Diagnostics class
	// ---------------------------

	private String getStopBalancingURL(String arrayIndex, String stringIndex) throws IOException {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "stop");
	}

	private String getStopBalancingURL(String arrayIndex) {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, "balance", "stop");
	}

	/*
	 * Warning this command has no safeties.
	 */
	private String getChargeURL(String arrayIndex, String stringIndex, String batteryPackIndex, String cellGroupIndex)
			throws IOException {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, batteryPackIndex,
				cellGroupIndex, "balance", "up");
	}

	/*
	 * Warning this command has no safeties.
	 */
	private String getDischargeURL(String arrayIndex, String stringIndex, String batteryPackIndex,
			String cellGroupIndex) throws IOException {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, batteryPackIndex,
				cellGroupIndex, "balance", "down");
	}

	private String getBalanceURL(String arrayIndex, String stringIndex)  {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "avg");
	}

	private String getBalanceToProvidedURL(String arrayIndex, String stringIndex, int providedMv) throws IOException {
		return getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "provided",
				Integer.toString(providedMv));
	}

	static void executeCommand(String URL)	 {
		try {
			LOG.debug("Executing request to URL: {}", URL);
			cHttpHelper = new HttpHelper(URL);
			cHttpHelper.getConnection();
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException("Error executing command.");
		}
	}

	// --------------------------------------------
	// Commands
	// --------------------------------------------
	public void balanceCgCharging(String arrayIndex, String stringIndex, String batteryPackIndex, String cellGroupIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		String chargeUrl = getChargeURL(arrayIndex, stringIndex, batteryPackIndex, cellGroupIndex);
		executeCommand(chargeUrl);
	}

	public void balanceCgDischarging(String arrayIndex, String stringIndex, String batteryPackIndex,
			String cellGroupIndex) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		String dischargeUrl = getDischargeURL(arrayIndex, stringIndex, batteryPackIndex, cellGroupIndex);
		executeCommand(dischargeUrl);
	}

	public void balanceStop(int arrayIndex, int stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		balanceStop(String.valueOf(arrayIndex), String.valueOf(stringIndex));
	}

	public void balanceStop(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		LOG.info("Stop Balancing Stack#" + stringIndex);
		String stopUrl = getStopBalancingURL(arrayIndex, stringIndex);
		executeCommand(stopUrl);

	}

	public void balanceStop(String arrayIndex) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		List<StringReport> strList = SystemInfo.getStringReportList(arrayIndex, cStackCount);
		if (strList != null && strList.isEmpty() == false) {
			for (StringReport report : strList) {
				balanceStop(arrayIndex, Integer.toString(report.getStringIndex()));
			}
		}
	}

	public void balanceToAverage(String arrayIndex, String stringIndex) {
			String balanceUrl = getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "avg");
			executeCommand(balanceUrl);

	}

	public void balanceToAverage(String arrayIndex) {
		String balanceUrl = getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, "balance", "avg");
		executeCommand(balanceUrl);
	}

	public void balanceToProvided(String arrayIndex, String stringIndex, int providedMv)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		String balanceUrl = getBalanceToProvidedURL(arrayIndex, stringIndex, providedMv);
		executeCommand(balanceUrl);
	}

	public void balanceToProvided(String arrayIndex, int providedMv) {
		String balanceUrl = getTurtleUrl()
				+ String.join("/", "turtle", "qaqc", arrayIndex, "balance", "provided", Integer.toString(providedMv));
		executeCommand(balanceUrl);
	}

	public void balanceToHighest(String arrayIndex, String stringIndex) {
		String balanceUrl = getTurtleUrl()
				+ String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "highest");
		executeCommand(balanceUrl);
	}

	public void balanceToHighest(String arrayIndex) {
		String balanceUrl = getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, "balance", "highest");
		executeCommand(balanceUrl);
	}

	public void balanceToLowest(String arrayIndex, String stringIndex) {
		String balanceUrl = getTurtleUrl()
				+ String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "balance", "lowest");
		executeCommand(balanceUrl);
	}

	public void balanceToLowest(String arrayIndex) {
		String balanceUrl = getTurtleUrl() + String.join("/", "turtle", "qaqc", arrayIndex, "balance", "lowest");
		executeCommand(balanceUrl);
	}

	public void stopBalancingAndVerify(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Try to stop balancing.");
		boolean isBalanceStopped = false;
		balanceStop(arrayIndex, stringIndex);
		int timeOut = 60;
		cLastCalls = new LastCalls();

		for (int i = 0; i < timeOut; i++) {
			String content = cLastCalls.getLastCallsContent();
			if (content.contains("BALANCING_OFF")) {
				LOG.info("PASS: Balance stopped.");
				isBalanceStopped = true;
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceStopped) {
			LOG.info("FAIL: Balance did not stop.");
			return;
		}
	}

	public void startBalanceToAverageAndVerify(String arrayIndex, String stringIndex, int balanceTimeMinutes,
			int toleranceMv)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		int timeOut = 60;
		LOG.info("Try to start balancing.");
		Thread.sleep(1000);
		// Jim: I think we will need additional validation based on the actual cell
		// voltages after balancing.
		// For now keep a variable tolerance. Once I get the correct tolerance values
		// you can set that.
		balanceToAverage(arrayIndex, stringIndex);
		cLastCalls = new LastCalls();
		boolean isBalanceToProvided = false;
		for (int i = 0; i < timeOut; i++) {
			String content = cLastCalls.getLastCallsContent();
			if (content.contains("BALANCE_TO_PROVIDED") && (content.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON")
					|| content.contains("BATTERY_PACK_CHARGE_BALANCING_ON"))) {
				LOG.info("PASS: Balance to average set.");
				LOG.info("last calls content: " + content);
				isBalanceToProvided = true;
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceToProvided) {
			LOG.info("Failed to set balance to average, test abort.");
			return;
		}

		Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
		int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
		int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
		boolean isBalancePass = false;
		LOG.info("Start balancing to average for " + balanceTimeMinutes + " minutes.");
		for (int i = 0; i < balanceTimeMinutes * 30; i++) {
			strReport.getReportContents();
			cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
			cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
			LOG.info("Current minimum voltage=" + cellVoltageMinimum + "mV. Current maximum voltage="
					+ cellVoltageMaximum + "mV. Delta voltage=" + Math.abs(cellVoltageMaximum - cellVoltageMinimum)
					+ "mV.");
			Thread.sleep(2000);
			if (Math.abs(cellVoltageMaximum - cellVoltageMinimum) <= Math.abs(toleranceMv)) {
				isBalancePass = true;
				break;
			}
		}
		if (isBalancePass) {
			LOG.info("Balancing to average PASS.");
		} else {
			LOG.info("Balancing to average FAIL.");
		}

		LOG.info("The difference between maximun cell voltage and minimun cell voltage is "
				+ Math.abs(cellVoltageMaximum - cellVoltageMinimum) + "mV.");
		LOG.info("Voltage tolerance is " + toleranceMv + "mV.");

	}

	public void startBalanceToProvidedAndVerify(String arrayIndex, String stringIndex, int targetVoltage,
			int toleranceMv)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		int timeOut = 60;
		LOG.info("Try to start balance to provided voltage.");
		Thread.sleep(1000);
		balanceToProvided(arrayIndex, stringIndex, targetVoltage);
		cLastCalls = new LastCalls();
		String lastCallContent;
		boolean isBalanceToProvided = false;
		for (int i = 0; i < timeOut; i++) {
			lastCallContent = cLastCalls.getLastCallsContent();
			if (lastCallContent.contains("BALANCE_TO_PROVIDED")
					&& (lastCallContent.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON")
							|| lastCallContent.contains("BATTERY_PACK_CHARGE_BALANCING_ON"))) {
				LOG.info("PASS: Balance to provided set.");
				LOG.info("last calls content: " + lastCallContent);
				isBalanceToProvided = true;
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceToProvided) {
			LOG.info("Failed to set balance to provided voltage, test abort.");
			return;
		}

		Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
		int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
		int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
		boolean isBalancePass = false;
		LOG.info("Waiting for balancing to target voltage. Target voltage: " + targetVoltage);
		while (isBalancePass == false) {
			strReport.getReportContents();
			cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
			cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
			LOG.info(DateTime.now().toString() + ": Current minimum voltage=" + cellVoltageMinimum
					+ "mV. Current maximum voltage=" + cellVoltageMaximum + "mV. Target voltage=" + targetVoltage
					+ "mV.");

			lastCallContent = cLastCalls.getLastCallsContent();
			if (!lastCallContent.contains("BALANCE_TO_PROVIDED")
					&& !lastCallContent.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON")
					&& !lastCallContent.contains("BATTERY_PACK_CHARGE_BALANCING_ON")) {
				isBalancePass = true;
				break;
			}
			Thread.sleep(5000);
		}

		LOG.info("Balancing to provided voltage stopped.");

		if (Math.abs(cellVoltageMaximum - cellVoltageMinimum) <= Math.abs(toleranceMv)) {
			LOG.info("Balancing to provided PASS.");
		} else {
			LOG.info("Balancing to provided FAIL.");
		}

		LOG.info("The difference between maximun cell voltage and minimun cell voltage is "
				+ Math.abs(cellVoltageMaximum - cellVoltageMinimum) + "mV.");
		LOG.info("Voltage tolerance is " + toleranceMv + "mV.");
	}

	public void startBalanceToProvidedAndVerify(String arrayIndex, String stringIndex, boolean isCharging)
			throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		Reports strReport = new Reports(arrayIndex + "," + stringIndex);
		strReport.getReportContents();
		int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
		int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());

		int timeOut = 60;

		if (isCharging) {
			LOG.info("Try to start balancing to provided voltage(charging).");
			balanceToProvided(arrayIndex, stringIndex, cellVoltageMaximum + 30);
		} else {
			LOG.info("Try to start balancing to provided voltage(discharging).");
			balanceToProvided(arrayIndex, stringIndex, cellVoltageMinimum - 30);
		}
		Thread.sleep(1000);

		boolean isBalanceStarted = false;
		for (int i = 0; i < timeOut; i++) {
			String content = cLastCalls.getLastCallsContent();

			if (content.contains("BALANCE_TO_PROVIDED")) {
				if ((isCharging && content.contains("BATTERY_PACK_CHARGE_BALANCING_ON"))
						|| (!isCharging && content.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON"))) {
					isBalanceStarted = true;
					break;
				}
			}

			Thread.sleep(1000);
		}

		if (isBalanceStarted) {
			LOG.info("PASS: Balance to provided set.");
		} else {
			LOG.info("Failed to set balance to provided, test abort.");
		}
	};
	// TESTS

	public void testDisableBalancing(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Start testing disable balancing.");
		cLastCalls = new LastCalls();
		startBalanceToAverageAndVerify(arrayIndex, stringIndex, 10, 10);
		stopBalancingAndVerify(arrayIndex, stringIndex);
	}

	public void testBalanceSingleStackToAverage(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Start testing balance a single stack to average.");
		cLastCalls = new LastCalls();
		stopBalancingAndVerify(arrayIndex, stringIndex);
		startBalanceToAverageAndVerify(arrayIndex, stringIndex, 10, 10);
	}

	public void testBalanceToProvidedMv(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		LOG.info("Start testing balance to provided voltage.");
		cLastCalls = new LastCalls();
		stopBalancingAndVerify(arrayIndex, stringIndex);
		startBalanceToProvidedAndVerify(arrayIndex, stringIndex, true);

		Thread.sleep(1000);
		stopBalancingAndVerify(arrayIndex, stringIndex);
		startBalanceToProvidedAndVerify(arrayIndex, stringIndex, false);
	}

	public boolean stopBalancingOneStackAndVerify(String arrayIndex, String stringIndex) {
		LOG.info("Try to Stop Balancing Stack#" + stringIndex);
		try {
			balanceStop(arrayIndex, stringIndex);
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e1) {
			e1.printStackTrace();
		}
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingState(arrayIndex, stringIndex, "BALANCING_OFF")) {
			LOG.info("PASS: Stopped balancing Stack#" + stringIndex);
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in Stack#" + stringIndex + " stopped balancing.");
			return false;
		}
	}

	public boolean stopBalancingArrayAndVerify(String arrayIndex) {
		LOG.info("Try to Stop Balancing Entire Array");
		try {
			balanceStop(arrayIndex);
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e1) {
			LOG.info("Failed to Stop Balancing Entire Array.");
			return false;
		}
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingState(arrayIndex, "", "BALANCING_OFF")) {
			LOG.info("PASS: Stopped balancing the entire Array.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks stopped balancing.");
			return false;
		}
	}

	public boolean startBalanceOneStackToAverageAndVerify(String arrayIndex, String stringIndex) {
		LOG.info("Try to set Balance Stack#" + stringIndex + " to Average");
		balanceToAverage(arrayIndex, stringIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, stringIndex, "BALANCE_TO_AVERAGE")) {
			LOG.info("PASS: Stack#" + stringIndex + "is set to Balance to Average.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Stack are set to Balance to Average.");
			return false;
		}
	}

	public boolean startBalanceArrayToAverageAndVerify(String arrayIndex) {
		LOG.info("Try to set Balance Entire Array to Average");
		balanceToAverage(arrayIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, "", "BALANCE_TO_AVERAGE")) {
			LOG.info("PASS: The entire Array is set to Balance to Average.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Array are set to Balance to Average.");
			return false;
		}
	}

	public boolean startBalanceOneStackToProvidedAndVerify(String arrayIndex, String stringIndex, int providedMv) {
		LOG.info("Try to set Balance Stack#" + stringIndex + " to " + providedMv);
		try {
			balanceToProvided(arrayIndex, stringIndex, providedMv);
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e1) {
			e1.printStackTrace();
		}
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, stringIndex, "BALANCE_TO_PROVIDED")) {
			LOG.info("PASS: Stack#" + stringIndex + "is set to Balance to provided voltage.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Stack are set to Balance to provided voltage.");
			return false;
		}
	}

	public boolean startBalanceArrayToProvidedAndVerify(String arrayIndex, int providedMv) {
		LOG.info("Try to set Balance Entire Array to " + providedMv);
		balanceToProvided(arrayIndex, providedMv);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, "", "BALANCE_TO_PROVIDED")) {
			LOG.info("PASS: The entire Array is set to Balance to provided voltage.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Array are set to Balance to provided voltage.");
			return false;
		}
	}

	public boolean startBalanceOneStackToHighestAndVerify(String arrayIndex, String stringIndex) {
		LOG.info("Try to set Balance Stack#" + stringIndex + " to Highest");
		balanceToHighest(arrayIndex, stringIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, stringIndex, "BALANCE_TO_HIGHEST")) {
			LOG.info("PASS: Stack#" + stringIndex + "is set to Balance to Highest.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Stack are set to Balance to Highest.");
			return false;
		}
	}

	public boolean startBalanceArrayToHighestAndVerify(String arrayIndex) {
		LOG.info("Try to set Balance Entire Array to Highest");
		balanceToHighest(arrayIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, "", "BALANCE_TO_HIGHEST")) {
			LOG.info("PASS: The entire Array is set to Balance to Average.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Array are set to Balance to Highest.");
			return false;
		}
	}

	public boolean startBalanceOneStackToLowestAndVerify(String arrayIndex, String stringIndex) {
		LOG.info("Try to set Balance Stack#" + stringIndex + " to Highest");
		balanceToLowest(arrayIndex, stringIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, stringIndex, "BALANCE_TO_LOWEST")) {
			LOG.info("PASS: Stack#" + stringIndex + "is set to Balance to Lowest.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Stack are set to Balance to Lowest.");
			return false;
		}
	}

	public boolean startBalanceArrayToLowestAndVerify(String arrayIndex) {
		LOG.info("Try to set Balance Entire Array to Lowest");
		balanceToLowest(arrayIndex);
		LOG.info("Command sent, waiting for the command takes effect.");
		try {
			Thread.sleep(cRestMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verifyBalancingMode(arrayIndex, "", "BALANCE_TO_Lowest")) {
			LOG.info("PASS: The entire Array is set to Balance to Average.");
			return true;
		} else {
			LOG.info("FAIL: Not all BatteryPacks in the Array are set to Balance to Lowest.");
			return false;
		}
	}

	public boolean verifyBalancingState(String arrayIndex, String stringIndex, String balanceState) {
		List<StringReport> strList = SystemInfo.getStringReportList(arrayIndex, cStackCount);
		if (strList != null && strList.isEmpty() == false) {
			if (stringIndex.isEmpty()) {
				return strList.stream().allMatch(stack -> stack.getBatteryPackReportList().stream()
						.allMatch(pack -> pack.getBatteryPackData().getBalancingState().equals(balanceState)));
			} else {
				for (StringReport report : strList) {
					if (report.getStringIndex() == Integer.parseInt(stringIndex)) {
						return report.getBatteryPackReportList().stream()
								.allMatch(pack -> pack.getBatteryPackData().getBalancingState().equals(balanceState));
					}
				}
			}
		}
		return false;
	}

	public boolean verifyBalancingMode(String arrayIndex, String stringIndex, String balanceMode) {
		List<StringReport> strList = SystemInfo.getStringReportList(arrayIndex, cStackCount);
		if (strList != null && strList.isEmpty() == false) {
			if (stringIndex.isEmpty()) {
				return strList.stream().allMatch(stack -> stack.getBatteryPackReportList().stream()
						.allMatch(pack -> pack.getBatteryPackData().getBatteryPackBalancingConfiguration()
								.getBalancingMode().equals(balanceMode)));
			} else {
				for (StringReport report : strList) {
					if (report.getStringIndex() == Integer.parseInt(stringIndex)) {
						return report.getBatteryPackReportList().stream()
								.allMatch(pack -> pack.getBatteryPackData().getBatteryPackBalancingConfiguration()
										.getBalancingMode().equals(balanceMode));
					}
				}
			}
		}
		return false;
	}

	public boolean verifyBalancingStateOfOtherStacksInTheArray(String arrayIndex, String stringIndex,
			String balanceState) {
		List<StringReport> strList = SystemInfo.getStringReportList(arrayIndex, cStackCount);
		if (strList != null && strList.isEmpty() == false) {

			for (StringReport report : strList) {
				if (report.getStringIndex() != Integer.parseInt(stringIndex)) {
					return report.getBatteryPackReportList().stream()
							.allMatch(pack -> pack.getBatteryPackData().getBalancingState().equals(balanceState));
				}
			}
		}
		return false;
	}

	public boolean verifyBalancingModeOfOtherStacksInTheArray(String arrayIndex, String stringIndex,
			String balanceMode) {
		List<StringReport> strList = SystemInfo.getStringReportList(arrayIndex, cStackCount);
		if (strList != null && strList.isEmpty() == false) {

			for (StringReport report : strList) {
				if (report.getStringIndex() != Integer.parseInt(stringIndex)) {
					return report.getBatteryPackReportList().stream()
							.allMatch(pack -> pack.getBatteryPackData().getBatteryPackBalancingConfiguration()
									.getBalancingMode().equals(balanceMode));
				}
			}
		}
		return false;
	}

	public boolean C931_BalanceSingleStackToAverage(String arrayIndex, String stringIndex) {
		LOG.info("Start testing TestRailC931-BalanceSingleStackToAverage");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (!startBalanceOneStackToAverageAndVerify(arrayIndex, stringIndex)) {
			return false;
		}
		if (verifyBalancingStateOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCING_OFF")) {
			LOG.info("TestRailC931 PASS!");
			return true;
		} else {
			LOG.info("TestRailC931 Failed, because the other Stack(s) did not stay BALANCING_OFF.");
			return false;
		}
	}

	public boolean C932_BalanceEntrieArrayToAverage(String arrayIndex) {
		LOG.info("Start testing TestRailC932-BalanceEntireArrayToAverage");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (startBalanceArrayToAverageAndVerify(arrayIndex)) {
			LOG.info("TestRailC932 PASS!");
			return true;
		} else {
			LOG.info("TestRailC932 Failed!");
			return false;
		}
	}

	public boolean C933_BalanceSingleStackToProvided(String arrayIndex, String stringIndex, int providedMv) {
		LOG.info("Start testing TestRailC933-BalanceSingleStackToProvided");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (!startBalanceOneStackToProvidedAndVerify(arrayIndex, stringIndex, providedMv)) {
			return false;
		}
		if (verifyBalancingStateOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCING_OFF")) {
			LOG.info("TestRailC933 PASS!");
			return true;
		} else {
			LOG.info("TestRailC933 Failed, because the other Stack(s) did not stay BALANCING_OFF.");
			return false;
		}
	}

	public boolean C934_BalanceEntrieArrayToProvided(String arrayIndex, int providedMv) {
		LOG.info("Start testing TestRailC934-BalanceEntireArrayToProvided");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			LOG.info("TestRailC934 PASS!");
			return true;
		} else {
			LOG.info("TestRailC934 Failed!");
			return false;
		}
	}

	public boolean C935_StopBalancingSingleStackToAverage(String arrayIndex, String stringIndex) {
		LOG.info("Start testing TestRailC935-StopBalancingSingleStackToAverage");
		if (!startBalanceArrayToAverageAndVerify(arrayIndex)) {
			return false;
		}
		if (!stopBalancingOneStackAndVerify(arrayIndex, stringIndex)) {
			return false;
		}
		if (verifyBalancingModeOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCE_TO_AVERAGE")) {
			LOG.info("TestRailC935 PASS!");
			return true;
		} else {
			LOG.info("TestRailC935 Failed, because the other Stack(s) did not stay BALANCE_TO_AVERAGE.");
			return false;
		}
	}

	public boolean C936_StopBalancingEntrieArrayToAverage(String arrayIndex) {
		LOG.info("Start testing TestRailC936-StopBalancingEntrieArrayToAverage");
		if (!startBalanceArrayToAverageAndVerify(arrayIndex)) {
			return false;
		}
		if (stopBalancingArrayAndVerify(arrayIndex)) {
			LOG.info("TestRailC936 PASS!");
			return true;
		} else {
			LOG.info("TestRailC936 Failed!");
			return false;
		}
	}

	public boolean C937_StopBalancingSingleStackToProvided(String arrayIndex, String stringIndex, int providedMv) {
		LOG.info("Start testing TestRailC937-StopBalancingSingleStackToProvided");
		if (!startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			return false;
		}
		if (!stopBalancingOneStackAndVerify(arrayIndex, stringIndex)) {
			return false;
		}
		if (verifyBalancingModeOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCE_TO_PROVIDED")) {
			LOG.info("TestRailC937 PASS!");
			return true;
		} else {
			LOG.info("TestRailC937 Failed, because the other Stack(s) did not stay BALANCE_TO_PROVIDED.");
			return false;
		}
	}

	public boolean C938_StopBalancingEntrieArrayToProvided(String arrayIndex, int providedMv) {
		LOG.info("Start testing TestRailC938-StopBalancingEntrieArrayToProvided");
		if (!startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			return false;
		}
		if (stopBalancingArrayAndVerify(arrayIndex)) {
			LOG.info("TestRailC938 PASS!");
			return true;
		} else {
			LOG.info("TestRailC938 Failed!");
			return false;
		}
	}

	public boolean C939_UpdateBalanceFromAverageToProvidedSingleStack(String arrayIndex, String stringIndex,
			int providedMv) {
		LOG.info("Start testing TestRailC939-UpdateBalanceFromAverageToProvidedSingleStack");
		if (!startBalanceArrayToAverageAndVerify(arrayIndex)) {
			return false;
		}
		if (!startBalanceOneStackToProvidedAndVerify(arrayIndex, stringIndex, providedMv)) {
			return false;
		}
		if (verifyBalancingModeOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCE_TO_AVERAGE")) {
			LOG.info("TestRailC939 PASS!");
			return true;
		} else {
			LOG.info("TestRailC939 Failed, because the other Stack(s) did not stay BALANCE_TO_AVERAGE.");
			return false;
		}
	}

	public boolean C940_UpdateBalanceFromProvidedToAverageSingleStack(String arrayIndex, String stringIndex,
			int providedMv) {
		LOG.info("Start testing TestRailC940_UpdateBalanceFromProvidedToAverageSingleStack");
		if (!startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			return false;
		}
		if (!startBalanceOneStackToAverageAndVerify(arrayIndex, stringIndex)) {
			return false;
		}
		if (verifyBalancingModeOfOtherStacksInTheArray(arrayIndex, stringIndex, "BALANCE_TO_PROVIDED")) {
			LOG.info("TestRailC940 PASS!");
			return true;
		} else {
			LOG.info("TestRailC940 Failed, because the other Stack(s) did not stay BALANCE_TO_PROVIDED.");
			return false;
		}
	}

	public boolean C941_UpdateBalanceFromAverageToProvidedEntireArray(String arrayIndex, int providedMv) {
		LOG.info("Start testing TestRailC941_UpdateBalanceFromAverageToProvidedEntireArray");
		if (!startBalanceArrayToAverageAndVerify(arrayIndex)) {
			return false;
		}
		if (startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			LOG.info("TestRailC941 PASS!");
			return true;
		} else {
			LOG.info("TestRailC941 Failed.");
			return false;
		}
	}

	public boolean C942_UpdateBalanceFromProvidedToAverageEntireArray(String arrayIndex, String stringIndex,
			int providedMv) {
		LOG.info("Start testing TestRailC942_UpdateBalanceFromProvidedToAverageEntireArray");
		if (!startBalanceArrayToProvidedAndVerify(arrayIndex, providedMv)) {
			return false;
		}
		if (startBalanceArrayToAverageAndVerify(arrayIndex)) {
			LOG.info("TestRailC942 PASS!");
			return true;
		} else {
			LOG.info("TestRailC942 Failed.");
			return false;
		}
	}

	public boolean C1046_BalanceToLowestCG(String arrayIndex, String stringIndex) {
		LOG.info("Start testing TestRailC1046-BalanceToLowestCG");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (startBalanceOneStackToLowestAndVerify(arrayIndex, stringIndex)) {
			LOG.info("TestRailC1046 PASS!");
			return true;
		} else {
			LOG.info("TestRailC1046 Failed!");
			return false;
		}
	}

	public boolean C1047_BalanceToHighestCG(String arrayIndex, String stringIndex) {
		LOG.info("Start testing TestRailC1046-BalanceToLowestCG");
		if (!stopBalancingArrayAndVerify(arrayIndex)) {
			return false;
		}
		if (startBalanceOneStackToHighestAndVerify(arrayIndex, stringIndex)) {
			LOG.info("TestRailC1046 PASS!");
			return true;
		} else {
			LOG.info("TestRailC1046 Failed!");
			return false;
		}
	}

	@SuppressWarnings("null")
	public static void main(String[] args)
			throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		// String stopUrl = "https://localhost:8443/turtle/qaqc/1/balance/stop";
		// String chargeUrl = "https://localhost:8443/turtle/qaqc/1/1/1/1/balance/up";
		// String dischargeUrl =
		// "https://localhost:8443/turtle/qaqc/1/1/1/1/balance/down";
		Balancing mBalancing = new Balancing(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString());
		mBalancing.C931_BalanceSingleStackToAverage(PowinProperty.ARRAY_INDEX.toString(),
				PowinProperty.STRING_INDEX.toString());
		// mBalancing.balanceCgCharging("1","1","1","1");
		// mBalancing.balanceCgDischarging("1","1","1","1");
		// mBalancing.stopBalancingArrayAndVerify("1");
		// mBalancing.balanceStop("1", "1");
		// mBalancing.startBalanceToProvidedAndVerify("1", "1", 3280, 10);

		// https://localhost:8443/turtle/qaqc/1/1/contactors/open

		// mBalancing.closeContactors("1","1");
		// mBalancing.openContactors("1", "1");
		// mBalancing.testDisableBalancing("1", "1");
		// mBalancing.testBalanceSingleStackToAverage("1", "1");
		// mBalancing.testBalanceToProvidedMv("1", "1");
		// mBalancing.startBalanceToAverageAndVerify("1", "1", 30, 4);
		// mBalancing.stopBalancingAndVerify("1", "1");
	}
}