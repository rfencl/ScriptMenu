package com.powin.modbusfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.BatteryPackVoltageNotificationCommands;
import com.powin.modbusfiles.awe.BatteryPackVoltageNotifications;
import com.powin.modbusfiles.awe.InverterSafety;
import com.powin.modbusfiles.awe.InverterSafetyCommands;
import com.powin.modbusfiles.awe.InverterSafetyLimits;
import com.powin.modbusfiles.awe.NotificationCodes;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class StackSimulatorTest {
	private int cArrayIndex;
	private int cStringIndex;
	private static Balancing cBalancing;
	private static StackStatusChecker cStackStatusChecker;
	private static LastCalls cLastCalls;
	private static FileHelper cReportFile;
	private Reports cStringReport;
	private String cReportFolder;
	private String cModbusHostName;
	private String cStationCode;
	private int cMaxPermittedPower;

	public StackSimulatorTest(int arrayIndex, int stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		setArrayIndex(arrayIndex);
		setStringIndex(stringIndex);
		setBalancing(new Balancing(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString()));
		setStackStatusChecker(new StackStatusChecker(getArrayIndex(), getStringIndex()));
		setLastCalls(new LastCalls());
		cStringReport = new Reports(PowinProperty.ARRAY_INDEX.toString()+","+PowinProperty.STRING_INDEX.toString());
		setReportFolder(PowinProperty.REPORTFOLDER.toString());
		setModbusHostName(PowinProperty.TURTLEHOST.toString());
		setStationCode(SystemInfo.getStationCode());
	}

	public int getArrayIndex() {
		return cArrayIndex;
	}

	public void setArrayIndex(int arrayIndex) {
		cArrayIndex = arrayIndex;
	}

	public int getStringIndex() {
		return cStringIndex;
	}

	public void setStringIndex(int stringIndex) {
		cStringIndex = stringIndex;
	}

	public static Balancing getBalancing() {
		return cBalancing;
	}

	public static void setBalancing(Balancing balancing) {
		cBalancing = balancing;
	}

	public static StackStatusChecker getStackStatusChecker() {
		return cStackStatusChecker;
	}

	public static void setStackStatusChecker(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker = stackStatusChecker;
	}

	public static LastCalls getLastCalls() {
		return cLastCalls;
	}

	public static void setLastCalls(LastCalls lastCalls) {
		cLastCalls = lastCalls;
	}

	public static FileHelper getReportFile() {
		return cReportFile;
	}

	public static void setReportFile(FileHelper reportFile) {
		cReportFile = reportFile;
	}

	public Reports getStringReport() {
		return cStringReport;
	}

	public void setStringReport(Reports stringReport) {
		cStringReport = stringReport;
	}

	public String getReportFolder() {
		return cReportFolder;
	}

	public void setReportFolder(String reportFolder) {
		cReportFolder = reportFolder;
	}

	public String getModbusHostName() {
		return cModbusHostName;
	}

	public void setModbusHostName(String modbusHostName) {
		cModbusHostName = modbusHostName;
	}

	public String getStationCode() {
		return cStationCode;
	}

	public void setStationCode(String stationCode) {
		cStationCode = stationCode;
	}

	public int getMaxPermittedPower() {
		return cMaxPermittedPower;
	}

	public void setMaxPermittedPower(int maxPermittedPower) {
		cMaxPermittedPower = maxPermittedPower;
	}

	public void createNewReportFile(String fileName) throws IOException {
		setReportFile(new FileHelper(
				getReportFolder() + "/" + fileName + "(" + DateTime.now().toString("MM-dd HH:mm") + ").csv"));
		getReportFile().writeToCSV(
				"TimeStamp, MaxV, MinV, MaxT, MinT, StackV, StackC, kW, TargetP, SOC, TargetSOC, BopStatus, ContactorStatus, Notifications");
	}

	public void testTopOffToTarget(int targetSoc) throws ModbusException, KeyManagementException,
			NoSuchAlgorithmException, IOException, ParseException, InterruptedException {
		// cReportFile = new FileHelper(
		// getReportFolder() + "/StacksimTest(" + DateTime.now().toString("MM-dd HH:mm")
		// + ").csv");
		// cReportFile.writeToCSV(
		// "TimeStamp, MaxV, MinV, MaxT, MinT, StackV, StackC, kW, TargetP, SOC,
		// TargetSOC, BOPStatus, Notifications");
		createNewReportFile("TopOffToTarget");
		System.out.println("Start testing Top Off to Target -- Basic Ops Automation");

		// Check if BOP is disabled
		String statusString = "";
		boolean isStatusCorrect = false;

		getLastCalls().getLastCallsContent();
		statusString = getLastCalls().getBOPStatus();
		if (statusString != null && statusString.contains("Disabled")) {
			System.out.println("Basic OPs has been disabled.");
		} else {
			System.out.println("Try to disable Basic Ops.");
			ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
			if (verifyBOPEnalbeStatus(false) == false)
				return;
		}

		// Start BOP, set priority to SOC
		InverterSafety mInverterSafetyNotifications = new InverterSafety(PowinProperty.ARRAY_INDEX.intValue(),
				PowinProperty.STRING_INDEX.intValue());
		InverterSafetyCommands.resetInverterSafetyLimits();
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
		System.out.println("Try to enable Basic Ops.");

		// Check if BOP is enabled
		if (verifyBOPEnalbeStatus(true) == false)
			return;

		// Check TopOff status
		System.out.println("Start verifying TopOff status.");
		cReportFile.writeToCSV("Start verifying TopOff status");
		isStatusCorrect = false;
		String topoffStatus = "";
		while (isStatusCorrect == false) {
			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			topoffStatus = getLastCalls().getTopoffStatus(statusString);
			if (topoffStatus != null) {
				System.out.println("TopOff status: " + topoffStatus);
				if (topoffStatus.contains("1 : 32 / 32")) {
					isStatusCorrect = true;
				}
			}
			saveDataToCsv(targetSoc, 0);
			Thread.sleep(1000);
		}
		System.out.println("PASS: TopOff status verified.");

		// Set InverterChargeHighVoltageLimitWithHysteresis
		System.out.println("Try to set InverterChargeHighVoltageLimit and Hysteresis.");

		cStringReport.getReportContents();
		int stackVolt = Integer.parseInt(cStringReport.getMeasuredStringVoltage()) + 3;
		InverterSafetyCommands.setInverterChargeHighVoltageLimitWithHysteresis(stackVolt, 10);
		System.out.println("PASS: InverterChargeHighVoltageLimit and Hysteresis are SET.");

		// Keep charging until InverterChargeHighVoltage alarm is set.
		System.out.println("Waiting for InverterChargeHighVoltage alarm is set.");
		isStatusCorrect = false;
		while (isStatusCorrect == false) {
			if (InverterSafetyCommands
					.checkBsfMessagesSet(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT)) {
				isStatusCorrect = true;
			}
			cStringReport.getReportContents();
			System.out.println("InverterChargeHighVoltageLimit: " + stackVolt + ", current stack voltage: "
					+ cStringReport.getMeasuredStringVoltage());
			Thread.sleep(5000);
		}
		System.out.println("PASS: InverterChargeHighVoltage alarm is set.");

		// Wait for hysteresis drops alarm.
		System.out.println("Waiting for hysteresis drops alarm.");
		cReportFile.writeToCSV("Start waiting for hysteresis drops alarm.");
		isStatusCorrect = false;
		while (isStatusCorrect == false) {
			if (InverterSafetyCommands
					.checkBsfMessagesClear(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT)) {
				isStatusCorrect = true;
			}
			cStringReport.getReportContents();
			System.out.println("InverterChargeHighVoltageLimit: " + stackVolt + ", current stack voltage: "
					+ cStringReport.getMeasuredStringVoltage());
			saveDataToCsv(targetSoc, 0);
			Thread.sleep(5000);
		}
		System.out.println("PASS: InverterChargeHighVoltage alarm is cleared.");

		System.out.println("Waiting for the inverter to resume to power the Stack.");
		isStatusCorrect = false;
		while (isStatusCorrect == false) {
			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			topoffStatus = getLastCalls().getTopoffStatus(statusString);
			cStringReport.getReportContents();
			float stackCurrent = Math.abs(Float.parseFloat(cStringReport.getStringCurrent()));
			if (topoffStatus != null) {
				System.out.println("TopOff status: " + topoffStatus);
				if (topoffStatus.contains("1 : 32 / 32") && stackCurrent > 0) {
					isStatusCorrect = true;
				}
			}
			saveDataToCsv(targetSoc, 0);
			Thread.sleep(1000);
		}
		System.out.println("PASS: The inverter resumed to power the Stack.");
		System.out.println("Test finished.");
		InverterSafetyCommands.resetInverterSafetyLimits();
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
	}

	public boolean verifyBOPEnalbeStatus(boolean isEnable)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		int timeOut = 60;
		String statusString = "";
		boolean isStatusCorrect = false;
		System.out.println("Start verifying if Basic OPs is " + (isEnable ? "enabled." : "disabled."));
		String expectString = isEnable ? "TopOff" : "Disabled";
		for (int i = 0; i < timeOut; i++) {
			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			if (statusString != null && statusString.contains(expectString)) {
				isStatusCorrect = true;
				break;
			}
			System.out.println("Basic OPs Status: " + statusString);
			Thread.sleep(1000);
		}
		if (isStatusCorrect) {
			System.out.println("PASS: Basic OPs is " + (isEnable ? "enabled." : "disabled."));
			return true;
		} else {
			System.out.println("FAIL: Basic OPs is not " + (isEnable ? "enabled." : "disabled.") + " Test abort.");
			return false;
		}
	}

	public void saveDataToCsv(int targetSoc, int targetP)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {

		cStringReport.getReportContents();
		String cellVoltMax = cStringReport.getMaxCellGroupVoltage();
		String cellVoltMin = cStringReport.getMinCellGroupVoltage();
		String cellTempMax = cStringReport.getMaxCellGroupTemperature();
		String cellTempMin = cStringReport.getMinCellGroupTemperature();
		String stackVolt = cStringReport.getMeasuredStringVoltage();
		String stackCurrent = cStringReport.getStringCurrent();
		String powerKW = cStringReport.getStringPower();
		String soc = cStringReport.getStringSoc();
		String negContactorStatus = cStringReport.getStringNegativeContactorStatus();
		String posContactorStatus = cStringReport.getStringPositiveContactorStatus();
		String contactorStatus = negContactorStatus + "/" + posContactorStatus;
		getLastCalls().getLastCallsContent();
		String bopStatus = getLastCalls().getBOPStatus();

		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);

		String notificationString = "";
		if (notificationList != null && notificationList.size() > 0) {
			for (int i = 0; i < notificationList.size(); i++)
				notificationString += notificationList.get(i) + " ";
		}

		cReportFile.writeToCSV(DateTime.now().toString() + "," + cellVoltMax + "," + cellVoltMin + "," + cellTempMax
				+ "," + cellTempMin + "," + stackVolt + "," + stackCurrent + "," + powerKW + "," + targetP + "," + soc
				+ "," + targetSoc + "," + bopStatus + "," + contactorStatus + "," + notificationString);
	}

	public void testEnableBOP(int targetSoc) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			ParseException, ModbusException, InterruptedException {
		System.out.println("Start testing Enable BOP -- Basic Ops Automation");

		System.out.println("Try to close contactors.");
		Contactors.closeContactors(getArrayIndex(), getStringIndex());
		if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
			System.out.println("Failed to close contactors, test abort.");
			return;
		}
		System.out.println("PASS: Contactors closed.");

		String statusString = "";

		getLastCalls().getLastCallsContent();
		statusString = getLastCalls().getBOPStatus();
		if (statusString != null && statusString.contains("Disabled")) {
			System.out.println("Basic OPs is disabled.");
		} else {
			System.out.println("Try to disable Basic Ops.");
			ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
			if (verifyBOPEnalbeStatus(false) == false)
				return;
		}

		// Start BOP, set priority to SOC
		InverterSafety mInverterSafetyNotifications = new InverterSafety(1,
				1);
		InverterSafetyCommands.resetInverterSafetyLimits();
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
		System.out.println("Try to enable Basic Ops.");
		if (verifyBOPEnalbeStatus(true) == false) {
			System.out.println("FAIL: Basic Ops is not enabled.");
			return;
		}

		System.out.println("PASS: Basic Ops is enabled.");
	}

	public void testDisableBOP(int targetSoc) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			ParseException, ModbusException, InterruptedException {
		System.out.println("Start testing Disable BOP -- Basic Ops Automation");

		System.out.println("Try to close contactors.");
		Contactors.closeContactors(getArrayIndex(), getStringIndex());
		if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
			System.out.println("Failed to close contactors, test abort.");
			return;
		}
		System.out.println("PASS: Contactors closed.");

		String statusString = "";

		getLastCalls().getLastCallsContent();
		statusString = getLastCalls().getBOPStatus();
		if (statusString != null && statusString.contains("Disabled")) {
			System.out.println("Basic OPs is disabled.");
			InverterSafety mInverterSafetyNotifications = new InverterSafety(1,
					1);
			InverterSafetyCommands.resetInverterSafetyLimits();
			ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
			System.out.println("Try to enable Basic Ops.");
			if (verifyBOPEnalbeStatus(true) == false) {
				System.out.println("FAIL: Basic Ops is not enabled, test abort.");
				return;
			}
		}
		System.out.println("Basic OPs is enabled.");

		System.out.println("Try to disable Basic Ops.");
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
		if (verifyBOPEnalbeStatus(false) == false) {
			System.out.println("FAIL: Basic Ops is not disabled.");
			return;
		}
		System.out.println("PASS: Basic Ops is disabled.");
	}

	public void testPrioritySocDone(boolean isCharge) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, InterruptedException, ModbusException, ParseException {
		System.out.println("Start testing " + (isCharge ? "charge" : "discharge") + " to target SOC.");
		cStringReport.getReportContents();
		int startSoc = Integer.parseInt(cStringReport.getStringSoc());
		int targetSoc = 0;
		if (isCharge) {
			if (startSoc > 95) {
				System.out.println("SOC is too high to charge the Stack. Test abort.");
				return;
			} else
				targetSoc = startSoc + 5;
		} else {
			if (startSoc < 5) {
				System.out.println("SOC is too low to discharge the Stack. Test abort.");
				return;
			} else
				targetSoc = startSoc - 2;
		}

		System.out.println("Try to close contactors.");
		Contactors.closeContactors(getArrayIndex(), getStringIndex());
		if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
			System.out.println("Failed to close contactors, test abort.");
			return;
		}
		System.out.println("PASS: Contactors closed.");

		Thread.sleep(1000);
		System.out.println("Try to set BOP to priority SOC.");
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
		System.out.println("PASS: BOP priority is set to SOC.");

		System.out.println("Start " + (isCharge ? "charging" : "discharging") + " to target SOC.");
		boolean isTargetSocReached = false;
		int currentSoc = 0;
		while (isTargetSocReached == false) {
			cStringReport.getReportContents();
			currentSoc = Integer.parseInt(cStringReport.getStringSoc());
			if (currentSoc == targetSoc) {
				System.out.println("Target SOC is reached.");
				isTargetSocReached = true;
			} else {
				System.out.println(
						DateTime.now().toString() + ": Target SOC=" + targetSoc + "%, Current SOC=" + currentSoc + "%");
			}
			Thread.sleep(5000);
		}

		System.out.println("Verify if BOP status goes to Done.");

		String statusString = "";
		boolean isStatusCorrect = false;
		for (int i = 0; i < 600; i++) {
			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			if (statusString.contains("Done")) {
				System.out.println("PASS: BOP status goes to Done.");
				isStatusCorrect = true;
				break;
			} else {
				System.out.println(DateTime.now().toString() + ": Current BOP status: " + statusString);
			}
			Thread.sleep(2000);
		}
		if (isStatusCorrect == false)
			System.out.println("FAIL: BOP status did not go to Done.");
	}

	public void testStackSweep(int targetSoc) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		// getBalancing().balanceStop(String.valueOf(getArrayIndex()),String.valueOf(getArrayIndex()));

		System.out.println("Start testing Stack Sweep -- Basic Ops Automation");
		Contactors.openContactors(cArrayIndex, cStringIndex);
		System.out.println("Trying to open contactors.");
		if (getStackStatusChecker().checkForContactorStatus(true, 30)) {
			System.out.println("Contactors opened.");
		} else {
			System.out.println("Failed to open contactors, test abort!");
			return;
		}

		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);

		boolean isPreSweep = false;
		for (int j = 0; j < 90; j++) {
			getLastCalls().getLastCallsContent();
			String content = getLastCalls().getBOPStatusPrioritySoc();
			if (content != null && content.contains("PreSweep")) {
				isPreSweep = true;
				break;
			}
			Thread.sleep(1000);
		}

		if (isPreSweep) {
			System.out.println("PASS: PreSweep occurred.");
		} else {
			System.out.println("FAIL: PreSweep did not occur, test abort.");
			return;
		}

		boolean isStackConnected = false;
		for (int j = 0; j < 60; j++) {
			getLastCalls().getLastCallsContent();
			String content = getLastCalls().getBOPStatusPrioritySoc();
			if (content != null && !content.contains("PreSweep") && content.contains("TopOff")) {
				isStackConnected = true;
				break;
			}
			Thread.sleep(1000);
			System.out.println("Waiting for Stack(s) connected.");
		}

		if (isStackConnected) {
			System.out.println("PASS: PreSweep finished. Stack(s) connected.");
		} else {
			System.out.println("FAIL: PreSweep did not finish.");
		}
	}

	public void testCellHighVoltageAlarm() throws IOException, InterruptedException, KeyManagementException,
			NoSuchAlgorithmException, ParseException, ModbusException, InstantiationException, IllegalAccessException {

		BatteryPackVoltageNotifications mBPVoltageNotifications = new BatteryPackVoltageNotifications(
				PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());

		NotificationCodes notificationCode = NotificationCodes.CELL_HIGH_VOLTAGE_ALARM;
		BatteryPackVoltageNotificationCommands.resetNotification();
		String statusString = "";
		getLastCalls().getLastCallsContent();
		statusString = getLastCalls().getBOPStatus();
		if (statusString != null && statusString.contains("Disabled")) {
			System.out.println("Basic OPs has been disabled.");
		} else {
			System.out.println("Try to disable Basic Ops.");
			ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
			if (verifyBOPEnalbeStatus(false) == false)
				return;
		}

		createNewReportFile("CellHighVoltageAlarmTest");
		System.out.println("Start testing Cell High Voltage Alarm -- Stack Simulator");

		cStringReport.getReportContents();
		int cellVoltMax = Integer.parseInt(cStringReport.getMaxCellGroupVoltage());

		Contactors.closeContactors(cArrayIndex, cStringIndex);
		BatteryPackVoltageNotificationCommands.setNotification(NotificationCodes.CELL_HIGH_VOLTAGE_ALARM, cellVoltMax + 100, cellVoltMax + 50);

		if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
			System.out.println("Failed to close contactors, test abort!");
			return;
		}

		// Start BOP, set priority to SOC
		InverterSafety mInverterSafetyNotifications = new InverterSafety(1,
				1);
		InverterSafetyCommands.resetInverterSafetyLimits();
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPrioritySOC(95, 0);
		System.out.println("Start charging until Cell High Volatage Alarm is triggered.");

		boolean isLooping = true;
		int writeCsvCounter = 0;
		while (isLooping) {
			Thread.sleep(100);
			writeCsvCounter++;
			if (writeCsvCounter >= 50) {
				saveDataToCsv(95, 0);
				writeCsvCounter = 0;
			}
			if (writeCsvCounter % 10 == 0)
				printInfoInConsole();

			if (getStackStatusChecker().didNotificationAppear(notificationCode.toString())) {
				System.out.println("PASS: Cell High Voltage Alarm triggered.");
				cReportFile.writeToCSV(DateTime.now().toString() + ",Cell High Voltage Alarm triggered");
				isLooping = false;
			}
			if (getStackStatusChecker().didNotificationAppear("2534")) {
				System.out.println("PASS: Contactors open warning triggered.");
				cReportFile.writeToCSV(DateTime.now().toString() + ",Contactors open warning triggered");
				isLooping = false;
			}
		}
		BatteryPackVoltageNotificationCommands.resetNotification();
		System.out.println("Reset Battery Pack Voltage Notification thresholds. Waiting for the contacots reclose.");

		isLooping = true;
		while (isLooping) {
			Thread.sleep(100);
			writeCsvCounter++;
			if (writeCsvCounter >= 50) {
				saveDataToCsv(95, 0);
				writeCsvCounter = 0;
			}
			if (writeCsvCounter % 10 == 0)
				printInfoInConsole();

			if (!getStackStatusChecker().didNotificationAppear(notificationCode.toString())
					&& !getStackStatusChecker().didNotificationAppear("2534")) {
				System.out.println("PASS: Cell High Voltage Alarm cleared and contactors reclosed.");
				cReportFile.writeToCSV(
						DateTime.now().toString() + ",Cell High Voltage Alarm cleared and contactors reclosed");
				isLooping = false;
			}
		}
	}

	public void printInfoInConsole() throws IOException {
		getStringReport().getReportContents();
		String cellVoltageMaximum = getStringReport().getMaxCellGroupVoltage();
		String cellVoltageMinimum = getStringReport().getMinCellGroupVoltage();
		String soc = getStringReport().getStringSoc();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		System.out.println(DateTime.now().toString("HH:mm:ss") + "  vMax: " + cellVoltageMaximum + "  vMin: "
				+ cellVoltageMinimum + "  SOC: " + soc + "  DcbusV: " + dcbusVolt);
	}

	public void runCyclingTest(int powerDivider) throws IOException, InterruptedException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException {
		createNewReportFile("CyclingAtP" + powerDivider);

		fullChargeDischarge(4, false);

//		restMinutes(3);
		restUntilBalanced(60, 10);

		fullChargeDischarge(powerDivider, true);

		restMinutes(60);

		fullChargeDischarge(powerDivider, false);

		restMinutes(60);

		chargeDischargeMinutes(4, true, 24);

		restMinutes(60);
	}

	public void fullChargeDischarge(int powerDivider, boolean isCharging) throws IOException, InterruptedException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		cReportFile.writeToCSV(DateTime.now().toString() + ",******************************************");
		cReportFile.writeToCSV(DateTime.now().toString() + ",Start full " + (isCharging ? "charging" : "discharging")
				+ " at P/" + powerDivider);
		System.out.println("Start full " + (isCharging ? "charging" : "discharging") + " at P/" + powerDivider);

		if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
			System.out.println("Failed to close contactors, test abort!");
			return;
		}

		int targetP = 0;
		int targetSoc = 0;
		if (isCharging) {
			targetP = Math.abs(getMaxPermittedPower() / powerDivider) * -1;
			targetSoc = 100;
		} else {
			targetP = Math.abs(getMaxPermittedPower() / powerDivider);
			targetSoc = 0;
		}
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPriorityPower(targetP, targetSoc);
//		mModbusPowinBlock.setBasicOpPrioritySOC(targetSoc, targetP);

		boolean isTargetSocArrived = false;
		int printInfoCounter = 0;
		int soc = 50;
		while (isTargetSocArrived == false) {
			Thread.sleep(100);
			getStringReport().getReportContents();
			soc = Integer.parseInt(getStringReport().getStringSoc());
			if ((soc == 100 && isCharging) || (soc <= 1 && isCharging == false))
				isTargetSocArrived = true;
			printInfoCounter++;
			if (printInfoCounter % 50 == 0) {
				printInfoInConsole();
				saveDataToCsv(targetSoc, targetP);
			}
		}
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(BigDecimal.valueOf(0));
	}

	public void restUntilBalanced(int minutes, int toleranceMv)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {

		cReportFile.writeToCSV(DateTime.now().toString() + ",******************************************");
		cReportFile.writeToCSV(DateTime.now().toString() + ",Start resting until all cells are balanced");
		System.out.println("Start resting until all cells are balanced.");

		getBalancing().balanceToAverage(Integer.toString(getArrayIndex()), Integer.toString(getStringIndex()));

		boolean isBalanceToProvided = false;
		for (int i = 0; i < 60; i++) {
			String content = cLastCalls.getLastCallsContent();
			if (content.contains("BALANCE_TO_PROVIDED") && (content.contains("BATTERY_PACK_DISCHARGE_BALANCING_ON")
					|| content.contains("BATTERY_PACK_CHARGE_BALANCING_ON"))) {
				System.out.println("PASS: Balance to average set.");
				isBalanceToProvided = true;
				break;
			}
			Thread.sleep(1000);
		}

		if (!isBalanceToProvided) {
			System.out.println("Failed to set balance to average, test abort.");
			return;
		}

		boolean isCgcBalanced = false;
		getStringReport().getReportContents();
		int cellVoltageMaximum = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
		int cellVoltageMinimum = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
		int writeCsvCounter = 0;
		for (int i = 0; i < minutes * 60; i++) {

			writeCsvCounter++;
			if (writeCsvCounter % 5 == 0) {
				saveDataToCsv(0, 0);
				printInfoInConsole();
			}
			Thread.sleep(1000);
			getStringReport().getReportContents();
			cellVoltageMaximum = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
			cellVoltageMinimum = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
			if (cellVoltageMaximum - cellVoltageMinimum <= toleranceMv) {
				isCgcBalanced = true;
				cReportFile.writeToCSV(DateTime.now().toString() + ",All CGCs are balanced");
				System.out.println("All CGCs are balanced.");
				break;
			}
		}

		if (isCgcBalanced == false) {
			cReportFile.writeToCSV(DateTime.now().toString() + ",Not all CGCs are balanced, but time is up.");
			System.out.println("Not all CGCs are balanced, but time is up.");
		}
	}

	public void restMinutes(int minutes)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		cReportFile.writeToCSV(DateTime.now().toString() + ",******************************************");
		cReportFile.writeToCSV(DateTime.now().toString() + ",Start resting for " + minutes + " minutes.");
		System.out.println("Start resting for " + minutes + " minutes.");

		int writeCsvCounter = 0;
		for (int i = 0; i < minutes * 60; i++) {
			writeCsvCounter++;
			if (writeCsvCounter % 5 == 0) {
				saveDataToCsv(0, 0);
				printInfoInConsole();
			}
			Thread.sleep(1000);
		}
	}

	public void chargeDischargeMinutes(int powerDivider, boolean isCharging, int minutes) throws IOException,
			ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		cReportFile.writeToCSV(DateTime.now().toString() + ",******************************************");
		cReportFile.writeToCSV(DateTime.now().toString() + ",Start " + (isCharging ? "charging" : "discharging")
				+ " at P/" + powerDivider + " for " + minutes + "minutes.");
		System.out.println("Start " + (isCharging ? "charging" : "discharging") + " at P/" + powerDivider + " for "
				+ minutes + "minutes.");

		int targetP = 0;
		int targetSoc = 0;
		if (isCharging) {
			targetP = Math.abs(getMaxPermittedPower() / powerDivider) * -1;
			targetSoc = 100;
		} else {
			targetP = Math.abs(getMaxPermittedPower() / powerDivider);
			targetSoc = 0;
		}
		ModbusPowinBlock.getModbusPowinBlock().setBasicOpPriorityPower(targetP, targetSoc);

		int writeCsvCounter = 0;
		for (int i = 0; i < minutes * 60; i++) {
			writeCsvCounter++;
			if (writeCsvCounter % 5 == 0) {
				saveDataToCsv(targetSoc, targetP);
				printInfoInConsole();
			}
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			ParseException, ModbusException, InterruptedException {
		StackSimulatorTest mStackSimulatorTest = new StackSimulatorTest(PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());

		// mStackSimulatorTest.testStackSweep(60);
		// mStackSimulatorTest.testTopOffToTarget(85);
		// mStackSimulatorTest.testPrioritySocDone(false);
		// mStackSimulatorTest.testPrioritySocDone(true);
		// mStackSimulatorTest.testEnableBOP(60);
		// mStackSimulatorTest.testDisableBOP(60);
		// mStackSimulatorTest.testCellHighVoltageAlarm();
		mStackSimulatorTest.setMaxPermittedPower(240);
		mStackSimulatorTest.runCyclingTest(4);

	}
}
