package com.powin.modbusfiles.apps;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.InverterSafety;
import com.powin.modbusfiles.awe.InverterSafetyCommands;
import com.powin.modbusfiles.awe.InverterSafetyLimits;
import com.powin.modbusfiles.modbus.Modbus120;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.powinwebappbase.HttpHelper;
import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;
public class BasicOpsApp {
	private final static Logger LOG = LogManager.getLogger(BasicOpsApp.class.getName());
	private int cArrayIndex;
	private int cStringIndex;
	private HttpClient cHttpClient;
	private static String cReportFileLocation;
	private static LastCalls cLastCalls;
	private static FileHelper cReportFile;
	private Reports cStringReport;
	private static Balancing cBalancing;
	private static StackStatusChecker cStackStatusChecker;
	private static String cStationCode;
	private InverterSafety cInverterSafety;

	public BasicOpsApp(int arrayIndex, int stringIndex)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		setArrayIndex(arrayIndex);
		setStringIndex(stringIndex);
		setBalancing(new Balancing(getArrayIndex(), getStringIndex()));
		setStackStatusChecker(new StackStatusChecker(getArrayIndex(), getStringIndex()));
		setLastCalls(new LastCalls());
		
		setReportFileLocation(PowinProperty.REPORTFOLDER.toString());
		setStationCode(getLastCalls().getStationCode());
		setInverterSafetyNotifications(new InverterSafety(getArrayIndex(),
				getStringIndex()));
		setStringReport(new Reports(cArrayIndex + "," + cStringIndex));
		cHttpClient = HttpHelper.buildHttpClient(true);
	}

	public InverterSafety getInverterSafetyNotifications() {
		return cInverterSafety;
	}

	public void setInverterSafetyNotifications(InverterSafety inverterSafetyNotifications) {
		cInverterSafety = inverterSafetyNotifications;
	}

	public static String getStationCode() {
		return cStationCode;
	}

	public static void setStationCode(String stationCode) {
		cStationCode = stationCode;
	}

	public static StackStatusChecker getStackStatusChecker() {
		return cStackStatusChecker;
	}

	public static void setStackStatusChecker(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker = stackStatusChecker;
	}

	public static Balancing getBalancing() {
		return cBalancing;
	}

	public static void setBalancing(Balancing balancing) {
		cBalancing = balancing;
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

	public HttpClient getHttpClient() {
		return cHttpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		cHttpClient = httpClient;
	}

	public static String getReportFileLocation() {
		return cReportFileLocation;
	}

	public static void setReportFileLocation(String reportFileLocation) {
		cReportFileLocation = reportFileLocation;
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

	// ***********************************
	public void createNewBasicOpTestFile(String fileName) {
		try {
			String turtleVer = CommonHelper
					.getTurtleVersion(PowinProperty.TURTLE_URL.toString() + "turtle/status");
			String koboldVer = CommonHelper
					.getKoboldVersion(PowinProperty.KOBOLD_URL.toString() + "kobold/status");
			fileName = fileName + "_T-" + turtleVer + "_K-" + koboldVer + "_";
			setReportFile(FileHelper.createTimeStampedFile(getReportFileLocation(), fileName, ".csv"));
			getReportFile().writeToCSV("Turtle Version, " + turtleVer);
			getReportFile().writeToCSV("Kobold Version, " + koboldVer);
			getReportFile().writeToCSV("");
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	public void testBasicOpAgainstDifferentTurtleVersion() {
		try {
			createNewBasicOpTestFile("BOP");
			cycleTargetPower(true, "default");
			cycleTargetPower(false, "default");
			for (int i = 3; i >= 0; i--) {
				getModbusPowinBlock().setW_SF(i);
				cycleTargetPower(true, Integer.toString(i));
				cycleTargetPower(false, Integer.toString(i));
			}
			getModbusPowinBlock().disableBasicOp();
			LOG.info("All cycles are done!");
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	public void cycleTargetPower(boolean isCharge, String wsf) {
		try {
			int targetSoc;
			int targetPower = 0;
			getReportFile().writeToCSV("Below is the results when W_SF is " + wsf);
			getReportFile().writeToCSV(
					"setW_SF(), getW_SF(), targetP, targetSOC, BasicOp Status, getBasicOpTargetPower(), getBasicOpTargetPowerCommand(), getBasicOpTargetSOC(), getBasicOpTargetSOCCommand(), Stack SOC, Stack Power, Stack Current");
			LOG.info("Start testing for W_SF={}", wsf);
			for (int i = 1; i <= 7; i++) {
				targetSoc = isCharge ? (99 - i) : (10 + i);
				targetPower = isCharge ? (targetPower * 10 - i) : (targetPower * 10 + i);
				LOG.info("setBasicOpPriorityPower: targetP={}, targetSoc={}", targetPower, targetSoc);
				getModbusPowinBlock().setBasicOpPriorityPower(targetPower, targetSoc);
				Thread.sleep(10000);
				saveBasicOpTestResults(wsf, Integer.toString(targetPower), Integer.toString(targetSoc));
			}
			getReportFile().writeToCSV("");
			LOG.info("Test for W_SF={} is done.", wsf);

		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	public void saveBasicOpTestResults(String wsf, String targetP, String targetSoc) {
		try {
			String wsfReading = Integer.toString(getModbusPowinBlock().getW_SF());
			String bopTargetPower = getModbusPowinBlock().getBasicOpTargetPower();
			String bopTargetPowerCmd = getModbusPowinBlock().getBasicOpTargetPowerCommand();
			String bopTargetSoc = getModbusPowinBlock().getBasicOpTargetSOC();
			String bopTargetSocCmd = getModbusPowinBlock().getBasicOpTargetSOCCommand();
			getStringReport().getReportContents();
			String stackCurrent = getStringReport().getStringCurrent();
			String stackPowerKW = getStringReport().getStringPower();
			String stackSoc = getStringReport().getStringSoc();
			getLastCalls().getLastCallsContent();
			String bopStatus = getLastCalls().getBOPStatus();

			String[] fields = { wsf, wsfReading, targetP, targetSoc, bopStatus, bopTargetPower, bopTargetPowerCmd,
					bopTargetSoc, bopTargetSocCmd, stackSoc, stackPowerKW, stackCurrent };
			getReportFile().writeToCSV(String.join(",", fields));
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	// ***************************TestRail Testings************************
	public boolean C531_BulkChargeTo100() {
		LOG.info("Start testing TestRail C531--Bulk Charge to 100% SOC");
		boolean result = bulkChargeDischargeSoc(true, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C531 PASS!");
		} else {
			LOG.info("TestRail C531 Failed!");
		}
		return result;
	}

	public boolean C521_BulkDischargeTo0() {
		LOG.info("Start testing TestRail C521--Bulk Discharge to 0% SOC");
		boolean result = bulkChargeDischargeSoc(false, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C521 PASS!");
		} else {
			LOG.info("TestRail C521 Failed!");
		}
		return result;
	}

	public boolean C524_EnableBasicOps() {
		LOG.info("Start testing TestRail C524--Enable BasicOps");
		boolean result = enableBOP(50);
		if (result) {
			LOG.info("TestRail C524 PASS!");
		} else {
			LOG.info("TestRail C524 Failed!");
		}
		return result;
	}

	public boolean C530_DisableBasicOps() {
		LOG.info("Start testing TestRail C530--Disable BasicOps");
		boolean result = disableBOP(50);
		if (result) {
			LOG.info("TestRail C530 PASS!");
		} else {
			LOG.info("TestRail C530 Failed!");
		}
		return result;
	}

	public boolean C526_PriorityPowerDischarge() {
		LOG.info("Start testing TestRail C526--Priority Power Discharge");
		boolean result = priorityPowerChargeDischarge(false, 5, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C526 PASS!");
		} else {
			LOG.info("TestRail C526 Failed!");
		}
		return result;
	}

	public boolean C537_PriorityPowerCharge() {
		LOG.info("Start testing TestRail C537--Priority Power Charge");
		boolean result = priorityPowerChargeDischarge(true, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C537 PASS!");
		} else {
			LOG.info("TestRail C537 Failed!");
		}
		return result;
	}

	public boolean C538_PrioritySocDischarge() {
		LOG.info("Start testing TestRail C538--Priority SOC Discharge");
		boolean result = prioritySocChargeDischarge(false, 5, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C538 PASS!");
		} else {
			LOG.info("TestRail C538 Failed!");
		}
		return result;
	}

	public boolean C527_PrioritySocCharge() {
		LOG.info("Start testing TestRail C527--Priority SOC Charge");
		boolean result = prioritySocChargeDischarge(true, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C527 PASS!");
		} else {
			LOG.info("TestRail C527 Failed!");
		}
		return result;
	}

	public boolean C528_PriorityPowerSocCharge() {
		LOG.info("Start testing TestRail C528--Priority Power/Soc Charge");
		boolean result = priorityPowerChargeDischarge(true, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C528 PASS!");
		} else {
			LOG.info("TestRail C528 Failed!");
		}
		return result;
	}

	public boolean C539_PriorityPowerSocDischarge() {
		LOG.info("Start testing TestRail C539--Priority Power/Soc Discharge");
		boolean result = priorityPowerChargeDischarge(false, 5, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C539 PASS!");
		} else {
			LOG.info("TestRail C539 Failed!");
		}
		return result;
	}

	public boolean C540_PrioritySocPowerCharge() {
		LOG.info("Start testing TestRail C540--Priority SOC/Power Charge");
		boolean result = prioritySocChargeDischarge(true, 100, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C540 PASS!");
		} else {
			LOG.info("TestRail C540 Failed!");
		}
		return result;
	}

	public boolean C529_PrioritySocPowerDischarge() {
		LOG.info("Start testing TestRail C529--Priority SOC/Power Discharge");
		boolean result = prioritySocChargeDischarge(false, 5, getPermittedMaxPower());
		if (result) {
			LOG.info("TestRail C529 PASS!");
		} else {
			LOG.info("TestRail C529 Failed!");
		}
		return result;
	}

	public boolean C677_StackSweep(int targetSoc) {
		try {
			LOG.info("Start testing TestRail C677--Stack Sweep");
			Contactors.openContactors(cArrayIndex, cStringIndex);
			LOG.info("Trying to open contactors.");
			if (getStackStatusChecker().checkForContactorStatus(true, 30)) {
				LOG.info("Contactors opened.");
			} else {
				LOG.info("Failed to open contactors, test abort!");
				return false;
			}

			
			Thread.sleep(20000);
			Contactors.closeContactors(cArrayIndex, cStringIndex);
			getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);	

			boolean isPreSweep = false;
			for (int j = 0; j < 600; j++) {
				getLastCalls().getLastCallsContent();
				String content = getLastCalls().getBOPStatusPrioritySoc();
				if (content != null && content.contains("PreSweep")) {
					isPreSweep = true;
					break;
				}
				Thread.sleep(1000);
			}

			if (isPreSweep) {
				LOG.info("PASS: PreSweep occurred.");
			} else {
				LOG.info("FAIL: PreSweep did not occur, test abort.");
				return false;
			}

			boolean isStackConnected = false;
			for (int j = 0; j < 600; j++) {
				getLastCalls().getLastCallsContent();
				String content = getLastCalls().getBOPStatusPrioritySoc();
				if (content != null && !content.contains("PreSweep") && content.contains("TopOff")) {
					isStackConnected = true;
					break;
				}
				Thread.sleep(1000);
				LOG.info("Waiting for Stack(s) connected.");
			}

			if (isStackConnected) {
				LOG.info("PASS: PreSweep finished. Stack(s) connected.");
				return true;
			} else {
				LOG.info("FAIL: PreSweep did not finish.");
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean C683_TopOffToTarget(int targetSoc) {
		try {
			setReportFile(FileHelper.createTimeStampedFile(getReportFileLocation(), "C683Test", ".csv"));
			getReportFile().writeToCSV(
					"TimeStamp, MaxVoltage, MinVoltagve, MaxTemp, MinTemp, StackVoltage, StackCurrent, kW, BOPStatus, Notifications");

			LOG.info("Start testing TestRail C683--Top Off to Target");

			// Check if BOP is disabled
			String statusString = "";
			boolean isStatusCorrect = false;

			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			if (statusString != null && statusString.contains("Disabled")) {
				LOG.info("Basic OPs has been disabled.");
			} else {
				LOG.info("Try to disable Basic Ops.");
				getModbusPowinBlock().disableBasicOp();
				if (verifyBOPEnalbeStatus(false) == false)
					return false;
			}

			// Start BOP, set priority to SOC
			InverterSafetyCommands.resetInverterSafetyLimits();
			getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
			LOG.info("Try to enable Basic Ops.");

			// Check if BOP is enabled
			if (verifyBOPEnalbeStatus(true) == false)
				return false;

			// Check TopOff status
			LOG.info("Start verifying TopOff status.");
			cReportFile.writeToCSV("Start verifying TopOff status");
			isStatusCorrect = false;
			String topoffStatus = "";
			while (isStatusCorrect == false) {
				getLastCalls().getLastCallsContent();
				statusString = getLastCalls().getBOPStatus();
				topoffStatus = getLastCalls().getTopoffStatus(statusString);
				if (topoffStatus != null) {
					LOG.info("TopOff status: " + topoffStatus);
					if (topoffStatus.contains("1 : ")) {
						isStatusCorrect = true;
					}
				}
				saveDataToCsv();
				Thread.sleep(1000);
			}
			LOG.info("PASS: TopOff status verified.");

			// Set InverterChargeHighVoltageLimitWithHysteresis
			LOG.info("Try to set InverterChargeHighVoltageLimit and Hysteresis.");

			getStringReport().getReportContents();
			int stackVolt = Integer.parseInt(getStringReport().getMeasuredStringVoltage()) + 3;
//			getInverterSafetyNotifications().setInverterChargeHighVoltageLimitWithHysteresis(stackVolt, 10);
			InverterSafetyCommands.setInverterChargeHighVoltageLimitWithHysteresis(stackVolt, 10);
			LOG.info("PASS: InverterChargeHighVoltageLimit and Hysteresis are SET.");

			// Keep charging until InverterChargeHighVoltage alarm is set.
			LOG.info("Waiting for InverterChargeHighVoltage alarm is set.");
			isStatusCorrect = false;
			while (isStatusCorrect == false) {
				if (InverterSafetyCommands
						.checkBsfMessagesSet(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT)) {
					isStatusCorrect = true;
				}
				getStringReport().getReportContents();
				LOG.info("InverterChargeHighVoltageLimit: " + stackVolt + ", current stack voltage: "
						+ cStringReport.getMeasuredStringVoltage());
				Thread.sleep(5000);
			}
			LOG.info("PASS: InverterChargeHighVoltage alarm is set.");

			// Wait for hysteresis drops alarm.
			LOG.info("Waiting for hysteresis drops alarm.");
			getReportFile().writeToCSV("Start waiting for hysteresis drops alarm.");
			isStatusCorrect = false;
			while (isStatusCorrect == false) {
				if (InverterSafetyCommands
						.checkBsfMessagesClear(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT)) {
					isStatusCorrect = true;
				}
				getStringReport().getReportContents();
				LOG.info("InverterChargeHighVoltageLimit: " + stackVolt + ", current stack voltage: "
						+ cStringReport.getMeasuredStringVoltage());
				saveDataToCsv();
				Thread.sleep(5000);
			}
			LOG.info("PASS: InverterChargeHighVoltage alarm is cleared.");

			LOG.info("Waiting for the inverter to resume to power the Stack.");
			isStatusCorrect = false;
			float stackCurrent = 0;
			while (isStatusCorrect == false) {
				getStringReport().getReportContents();
				stackCurrent = Float.parseFloat(getStringReport().getStringCurrent());
				getLastCalls().getLastCallsContent();
				statusString = getLastCalls().getBOPStatus();
				topoffStatus = getLastCalls().getTopoffStatus(statusString);
				if (topoffStatus != null) {
					LOG.info("TopOff status: " + topoffStatus);
					if (topoffStatus.contains("1 : ") && Math.abs(stackCurrent) > 0) {
						isStatusCorrect = true;
					}
				}
				saveDataToCsv();
				Thread.sleep(1000);
			}
			LOG.info("PASS: The inverter resumed to power the Stack.");
			LOG.info("Test finished.");
			InverterSafetyCommands.resetInverterSafetyLimits();
			getModbusPowinBlock().disableBasicOp();
			return true;
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}

	}

	public boolean C695_PrioritySocDone(boolean isCharge) {
		try {
			LOG.info("Start testing C695.");
			getStringReport().getReportContents();
			int startSoc = Integer.parseInt(cStringReport.getStringSoc());
			int targetSoc = 0;
			if (isCharge) {
				if (startSoc > 95) {
					LOG.info("SOC is too high to charge the Stack. Test abort.");
					return false;
				} else
					targetSoc = startSoc + 2;
			} else {
				if (startSoc < 5) {
					LOG.info("SOC is too low to discharge the Stack. Test abort.");
					return false;
				} else
					targetSoc = startSoc - 2;
			}

			LOG.info("Try to close contactors.");
			Contactors.closeContactors(getArrayIndex(), getStringIndex());
			if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
				LOG.info("Failed to close contactors, test abort.");
				return false;
			}
			LOG.info("PASS: Contactors closed.");

			Thread.sleep(1000);
			LOG.info("Try to set BOP to priority SOC.");
			getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
			LOG.info("PASS: BOP priority is set to SOC.");

			LOG.info("Start " + (isCharge ? "charging" : "discharging") + " to target SOC.");
			boolean isTargetSocReached = false;
			int currentSoc = 0;
			while (isTargetSocReached == false) {
				getStringReport().getReportContents();
				currentSoc = Integer.parseInt(getStringReport().getStringSoc());
				if (currentSoc == targetSoc) {
					LOG.info("Target SOC is reached.");
					isTargetSocReached = true;
				} else {
					LOG.info(DateTime.now().toString() + ": Target SOC=" + targetSoc + "%, Current SOC=" + currentSoc
							+ "%");
				}
				Thread.sleep(5000);
			}

			LOG.info("Verify if BOP status goes to Done.");

			String statusString = "";
			boolean isStatusCorrect = false;
			for (int i = 0; i < 600; i++) {
				getLastCalls().getLastCallsContent();
				statusString = getLastCalls().getBOPStatus();
				if (statusString.contains("Done")) {
					LOG.info("PASS: BOP status goes to Done.");
					isStatusCorrect = true;
					return true;
				} else {
					LOG.info(DateTime.now().toString() + ": Current BOP status: " + statusString);
				}
				Thread.sleep(2000);
			}
			if (isStatusCorrect == false) {
				LOG.info("FAIL: BOP status did not go to Done.");
				return false;
			} else
				return true;

		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	// ***************************End of TestRail Testings************************
	public boolean bulkChargeDischargeSoc(boolean isCharge, int socOffset, int targetPower) {
		try {
			int currentSoc = Integer.parseInt(getStringReport().getStringSoc());
			LOG.info("Current SOC is " + currentSoc + "%.");

			int targetSoc = isCharge ? currentSoc + Math.abs(socOffset) : currentSoc - Math.abs(socOffset);
			if (targetSoc < 0)
				targetSoc = 0;
			else if (targetSoc > 100)
				targetSoc = 100;

			LOG.info("Try to set TargetSoc to " + targetSoc + "%.");
			if (isCharge)
				getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, Math.abs(targetPower) * -1);
			else
				getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, Math.abs(targetPower));

			int timeOut = 60;
			String expectStr = "TargetSOC: " + targetSoc + "%";
			boolean isTargetSocSet = false;
			for (int j = 0; j < timeOut; j++) {
				String content = getLastCalls().getLastCallsContent();

				if (content.contains(expectStr)) {
					isTargetSocSet = true;
					break;
				}
				Thread.sleep(1000);
			}

			if (isTargetSocSet || getModbusPowinBlock().getStatus().contains("DONE")) {
				LOG.info("PASS: Target SOC is set to " + targetSoc + "%.");
				LOG.info("Start " + (isCharge ? "charging" : "discharging") + " to SOC " + targetSoc + "%.");
				getStringReport().getReportContents();
				int stringSoc = Integer.parseInt(getStringReport().getStringSoc());
				float realPower = Float.parseFloat(getStringReport().getStringPower());
				float stringCurrent = Float.parseFloat(getStringReport().getStringCurrent());
				while (stringSoc != targetSoc) {
					getStringReport().getReportContents();
					stringSoc = Integer.parseInt(getStringReport().getStringSoc());
					realPower = Float.parseFloat(getStringReport().getStringPower());
					stringCurrent = Float.parseFloat(getStringReport().getStringCurrent());
					LOG.info(DateTime.now().toString("HH:mm:ss") + " -Basic Ops status="
							+ getModbusPowinBlock().getStatus() + ", current SOC=" + stringSoc + "%, real power="
							+ realPower + "kW, string current=" + stringCurrent + "A.");
					Thread.sleep(4000);
				}
				LOG.info("Target SOC reached, test finished.");
				return true;
			} else {
				LOG.info("Failed to set Target SOC to " + targetSoc + "%. Test abort.");
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean priorityPowerChargeDischarge(boolean isCharge, int targetSoc, int targetPower) {
		boolean result = false;
		try {
			if (targetSoc == 0) {
				LOG.info("Start testing Priority Power " + (isCharge ? "Charge" : "Discharge") + " (TargetSOC=0).");
			} else {
				LOG.info("Start testing Priority Power " + (isCharge ? "Charge" : "Discharge") + " (TargetSOC!=0).");
			}

			if (isCharge) {
				targetPower = Math.abs(targetPower) * -1;
			} else {
				targetPower = Math.abs(targetPower);
			}

			initiateBasicOpParameters(true, targetPower, targetSoc);

			List<String> expectedStr = new ArrayList<>();
			expectedStr.add("Priority :Power");
			expectedStr.add("Current Stage: Bulk At Power");
			expectedStr.add("TargetP: " + targetPower / 1000 + ".0 kW");
			if (targetSoc != 0)
				expectedStr.add("TargetSOC: " + targetSoc + "%");

			if (verifyBasicOpStatus(expectedStr)) {
				if (verifyACPower(isCharge)) {
					LOG.info("PASS!");
					result = true;
				} else {
					LOG.info("FAILED! BasicOps status is as expected, but the AC Power is not correct.");
				}
			} else {
				LOG.info("FAILED! BasicOps status is not as expected.");
			}
			getModbusPowinBlock().disableBasicOp();
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		return result;
	}

	public boolean prioritySocChargeDischarge(boolean isCharge, int targetSoc, int targetPower) {

		boolean result = false;
		try {
			if (targetPower == 0) {
				System.out.println(
						"Start testing Priority SOC " + (isCharge ? "Charge" : "Discharge") + " (TargetPower=0).");
			} else {
				LOG.info("Start testing Priority SOC " + (isCharge ? "Charge" : "Discharge") + " (TargetPower!=0).");
			}

			if (isCharge) {
				targetPower = Math.abs(targetPower) * -1;
			} else {
				targetPower = Math.abs(targetPower);
			}
			initiateBasicOpParameters(false, targetPower, targetSoc);

			List<String> expectedStr = new ArrayList<>();
			expectedStr.add("Priority :SOC");
			expectedStr.add("Current Stage: TopOff");
			expectedStr.add("TargetSOC: " + targetSoc + "%");
			if (targetPower != 0)
				expectedStr.add("TargetP: " + targetPower / 1000 + ".0 kW");

			if (verifyBasicOpStatus(expectedStr)) {
				if (verifyACPower(isCharge)) {
					LOG.info("PASS!");
					result = true;
				} else {
					LOG.info("FAILED! BasicOps status is as expected, but the AC Power is not correct.");
				}
			} else {
				LOG.info("FAILED! BasicOps status is not as expected.");
			}
			getModbusPowinBlock().disableBasicOp();
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		return result;
	}

	public boolean enableBOP(int targetSoc) {
		try {
			LOG.info("Start testing Enable BOP -- Basic Ops Automation");

			LOG.info("Try to close contactors.");
			Contactors.closeContactors(getArrayIndex(), getStringIndex());
			if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
				LOG.info("Failed to close contactors, test abort.");
				return false;
			}
			LOG.info("PASS: Contactors closed.");

			String statusString = "";

			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			if (statusString != null && statusString.contains("Disabled")) {
				LOG.info("Basic OPs is disabled.");
			} else {
				LOG.info("Try to disable Basic Ops.");
				getModbusPowinBlock().disableBasicOp();
				if (verifyBOPEnalbeStatus(false) == false)
					return false;
			}

			// Start BOP, set priority to SOC
			InverterSafetyCommands.resetInverterSafetyLimits();
			getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
			LOG.info("Try to enable Basic Ops.");
			if (verifyBOPEnalbeStatus(true) == false) {
				LOG.info("FAIL: Basic Ops is not enabled.");
				return false;
			}

			LOG.info("PASS: Basic Ops is enabled.");
			return true;

		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean disableBOP(int targetSoc) {
		try {
			LOG.info("Start testing Disable BOP -- Basic Ops Automation");

			LOG.info("Try to close contactors.");
			Contactors.closeContactors(getArrayIndex(), getStringIndex());
			if (!getStackStatusChecker().checkForContactorStatus(false, 60)) {
				LOG.info("Failed to close contactors, test abort.");
				return false;
			}
			LOG.info("PASS: Contactors closed.");

			String statusString = "";

			getLastCalls().getLastCallsContent();
			statusString = getLastCalls().getBOPStatus();
			if (statusString != null && statusString.contains("Disabled")) {
				LOG.info("Basic OPs is disabled.");
				InverterSafetyCommands.resetInverterSafetyLimits();
				getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, 0);
				LOG.info("Try to enable Basic Ops.");
				if (verifyBOPEnalbeStatus(true) == false) {
					LOG.info("FAIL: Basic Ops is not enabled, test abort.");
					return false;
				}
			}
			LOG.info("Basic OPs is enabled.");

			LOG.info("Try to disable Basic Ops.");
			getModbusPowinBlock().disableBasicOp();
			if (verifyBOPEnalbeStatus(false) == false) {
				LOG.info("FAIL: Basic Ops is not disabled.");
				return false;
			}
			LOG.info("PASS: Basic Ops is disabled.");
			return true;
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean verifyBasicOpStatus(String expectedStr) {
		try {
			int timeOut = 60;
			for (int j = 0; j < timeOut; j++) {
				String content = getLastCalls().getLastCallsContent();
				if (content.contains(expectedStr)) {
					return true;
				}

				Thread.sleep(1000);
			}
			return false;
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean verifyBasicOpStatus(List<String> expectedStr) {
		try {
			int timeOut = 60;
			for (int j = 0; j < timeOut; j++) {
				String content = getLastCalls().getLastCallsContent();
				boolean isAllStatusStringsMatch = true;
				for (int i = 0; i < expectedStr.size(); i++) {
					if (!content.contains(expectedStr.get(i))) {
						isAllStatusStringsMatch = false;
						break;
					}
				}
				if (isAllStatusStringsMatch)
					return true;
				Thread.sleep(1000);
			}
			return false;
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public boolean verifyACPower(boolean isCharging) {
		try {
			String stringCurrent;
			int timeOut = 300;
			for (int j = 0; j < timeOut; j++) {
				getStringReport().getReportContents();
				stringCurrent = getStringReport().getStringCurrent();
				if (isCharging) {
					if (Float.parseFloat(stringCurrent) < 0) {
						return true;
					}
				} else {
					if (Float.parseFloat(stringCurrent) > 0) {
						return true;
					}
				}
				Thread.sleep(200);
			}
			return false;
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public void initiateBasicOpParameters(boolean isPriorityPower, int targetPower, int targetSoc) {
		try {
			if (isPriorityPower) {
				getModbusPowinBlock().setBasicOpPriorityPower(targetPower, targetSoc);
			} else {
				getModbusPowinBlock().setBasicOpPrioritySOC(targetSoc, targetPower);
			}
			Thread.sleep(5000);
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	public boolean verifyBOPEnalbeStatus(boolean isEnable) {
		try {
			int timeOut = 60;
			String statusString = "";
			boolean isStatusCorrect = false;
			LOG.info("Start verifying if Basic OPs is " + (isEnable ? "enabled." : "disabled."));
			String expectString = isEnable ? "TopOff" : "Disabled";
			for (int i = 0; i < timeOut; i++) {
				getLastCalls().getLastCallsContent();
				statusString = getLastCalls().getBOPStatus();
				if (statusString != null && statusString.contains(expectString)) {
					isStatusCorrect = true;
					break;
				}
				LOG.info("Basic OPs Status: " + statusString);
				Thread.sleep(1000);
			}
			if (isStatusCorrect) {
				LOG.info("PASS: Basic OPs is " + (isEnable ? "enabled." : "disabled."));
				return true;
			} else {
				LOG.info("FAIL: Basic OPs is not " + (isEnable ? "enabled." : "disabled.") + " Test abort.");
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			return false;
		}
	}

	public void saveDataToCsv() {
		try {
			cStringReport.getReportContents();
			String cellVoltMax = cStringReport.getMaxCellGroupVoltage();
			String cellVoltMin = cStringReport.getMinCellGroupVoltage();
			String cellTempMax = cStringReport.getMaxCellGroupTemperature();
			String cellTempMin = cStringReport.getMinCellGroupTemperature();
			String stackVolt = cStringReport.getMeasuredStringVoltage();
			String stackCurrent = cStringReport.getStringCurrent();
			String powerKW = cStringReport.getStringPower();
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

			String[] fields = { DateTime.now().toString(), cellVoltMax, cellVoltMin, cellTempMax, cellTempMin,
					stackVolt, stackCurrent, powerKW, bopStatus, notificationString };
			getReportFile().writeToCSV(String.join(",", fields));
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	public int getPermittedMaxPower() {
		int maxPower = 100000;
		try {
			int maxPercentagePower = Integer.parseInt(PowinProperty.MAXPERCENTAGEOFPOWER.toString());
			Modbus120 mNameplateBlockMaster = new Modbus120(PowinProperty.TURTLEHOST.toString(), 4502, 255,
					false);
			mNameplateBlockMaster.init();
			maxPower = (int) (mNameplateBlockMaster.getWRtg().intValue() * (maxPercentagePower / 100.0));
			// if (maxPower != 100) {
			// int sf = mNameplateBlockMaster.getWRtgSf();
			// maxPower = (int) (maxPower / Math.pow(10, sf));
			// }
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		return maxPower;
	}

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			ParseException, ModbusException, InterruptedException {
		//BasicOpsApp mBasicOpsApp = new BasicOpsApp(PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());

		// JSONObject jsonObj = mBasicOpsApp.getStringReport().getReportContents();
		// String jsonString = jsonObj.toString();
		// ObjectMapper objectMapper = new ObjectMapper();
		// StringReport stringReportInstance = objectMapper.readValue(jsonString,
		// StringReport.class);
		// if (stringReportInstance != null) {
		// LOG.info(stringReportInstance.getStackType());
		// }
		// Reports mArrayReportJson = new Reports("1");
		// jsonObj = mArrayReportJson.getReportContents();
		// jsonObj.remove("stringReport");
		// jsonString = jsonObj.toString();
		// ArrayReport arrayReportInstance = objectMapper.readValue(jsonString,
		// ArrayReport.class);
		// if (arrayReportInstance != null) {
		// LOG.info(arrayReportInstance.getArrayData().getConnectedSOC().getAh());
		// }
	}
}
