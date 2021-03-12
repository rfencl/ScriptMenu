package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class CharacterizationTestOrig extends CyclingTestBase {
	// Test settings
	private static int chargingPowerKw;
	private static int dischargingPowerKw;
	private static int restPeriodSeconds;
	private static int logInterval = 2;
	private static int targetChargeVoltage;
	private static int targetDischargeVoltage;
	private static int maxCycles;
	// Test utilities
	private static Reports strReport;
	private static Reports arrReport;
	private final static Logger LOG = LogManager.getLogger();
	private static FileHelper fCellGroupReportSecond, fCellGroupReportMinute, fStringReport, fArrayReport;
	private static String arrayIndex;
	private static String stringIndex;

	// Constructor
	public CharacterizationTestOrig(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		cModbusHostName = modbusHostName;
		cModbusPort = modbusPort;
		cModbusUnitId = modbusUnitId;
		cEnableModbusLogging = enableModbusLogging;

	}

	public void init(String[] args) throws IOException, InterruptedException {
		// System string and array reports
		arrayIndex = PowinProperty.ARRAY_INDEX.toString();
		stringIndex = PowinProperty.STRING_INDEX.toString();
		strReport = new Reports(arrayIndex + "," + stringIndex);
		arrReport = new Reports(arrayIndex);
		// Output report files
		fCellGroupReportMinute = new FileHelper("bc_cell_group_report_minute_csv_file_path");
		fCellGroupReportSecond = new FileHelper("bc_cell_group_report_second_csv_file_path");
		fStringReport = new FileHelper("bc_string_report_csv_file_path");
		fArrayReport = new FileHelper("bc_array_report_csv_file_path");
		fCellGroupReportMinute.writeToCSV("operation,timestamp,bp,cg,voltageMax,voltageMin,voltageAvg");
		fCellGroupReportSecond.writeToCSV("operation," + strReport.getCellGroupReportHeader() + "stackVoltage,stackCurrent,power,kwh,ah,soc");
		fStringReport.writeToCSV("operation,timestamp,voltageMax,voltageMin,stringCurrent,stringPower");
		fArrayReport.writeToCSV("operation,timestamp,arrayCurrent,arrayPower");
		
		resetDevices();
        initParameters(args);		
	}


	// Device methods
	public static void chargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandPrioritySOC(power);
	}

	public static void dischargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandPriorityPower(power);
	}

	public static void stopViaSimpleBasicCommand() throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandOff();
	}

	public static int getDCBusVoltage() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCBusVoltage();
	}

	public static int getWatts() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWatts();
		// return cWyeConnectThreePhaseabcnMeterBlockMaster.getWatts();
	}

	public static int getDCWatts() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCWatts();
	}

	public static int getWattHours() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWattHours();
	}

	public static int getDCCurrent() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getTotalDCCurrent();
	}

	public static int getStringWattsDC() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getTotalPower();
	}

	public static int getSOC() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getSOC();
	}

	// Test methods
	public static void runCycle(Boolean isCharging) throws ModbusException, InterruptedException, IOException {
		// Run either a charge or discharge based on cycleType
		String cycleType = isCharging ? "Charging" : "Discharging";
		if (isCharging)
			chargeViaSimpleBasicCommand(BigDecimal.valueOf(-chargingPowerKw));
		else
			dischargeViaSimpleBasicCommand(BigDecimal.valueOf(dischargingPowerKw));
		// Wait till the inverter actually switches on, i.e., current is non-zero
		int currentDC = 0;
		while (currentDC == 0) {
			Thread.sleep(500);
			currentDC = getDCCurrent();
		}
		// Keep cycle running till target voltage is reached
		int instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
		boolean targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		List<List<String>> collectedCgReports = new ArrayList<>();
		boolean minuteActivated = false;
		while (targetNotReached) {
			//collectedCgReports = new ArrayList<>();
			int timeIncrement = 0;
			for (; timeIncrement < 60 && targetNotReached; timeIncrement++) {
				// Cell group report every second
				fCellGroupReportSecond.writeToCSV(cycleType + " cycle in progress," + strReport.getCellGroupReportAsString("voltage") + ","
						+ strReport.getMeasuredStringVoltage() + "," + strReport.getStringCurrent() + "," + strReport.getStringPower()
						+ "," + strReport.getStringkWh() + "," + strReport.getStringAh() + "," + strReport.getStringSoc());
				// collects info for cell group report per minute
				List<String> instantaneousCgReport = strReport.getBatteryPackInfo(true, "voltage");
				collectedCgReports.add(instantaneousCgReport);
				// string report
				fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport()
						+ "," + getWatts() + "," + getDCWatts() + "," + getWattHours() + "," + getDCBusVoltage());
				// array report
				fArrayReport.writeToCSV(cycleType + " cycle in progress," + arrReport.getStringTimestamp() + "," + arrReport.getArrayCurrent() + "," + arrReport.getArrayPower());
				Thread.sleep(1000 * logInterval);
				strReport = new Reports(arrayIndex + "," + stringIndex);
				arrReport = new Reports(arrayIndex);
				instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
				targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
			}
			// cell group report
			if (timeIncrement == 60) {
				fCellGroupReportMinute.writeToCSV(cycleType + " cycle in progress,", strReport.processCellGroupData(collectedCgReports));
				minuteActivated = true;
			}
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete," + strReport.getStringReport());
		fArrayReport.writeToCSV(cycleType + " cycle complete," + arrReport.getStringTimestamp() + "," + arrReport.getArrayCurrent() + "," + arrReport.getArrayPower());
		if (minuteActivated)
			fCellGroupReportMinute.writeToCSV(cycleType + " cycle complete,", strReport.processCellGroupData(collectedCgReports));
	}

	public static void rest(int restPeriodInSecs) throws InterruptedException {
		// Stop powering
		try {
			stopViaSimpleBasicCommand();
		} catch (ModbusException e) {
			e.printStackTrace();
		}
		// Rest
		Thread.sleep(restPeriodInSecs * 1000);
	}

	public static void main(String[] args) throws Throwable {
		try {
			CharacterizationTestOrig mCharacterizationTest = new CharacterizationTestOrig(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mCharacterizationTest.init(args);
			// System.out.println(mCharacterizationTest.getDCWatts());
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
				// Discharge
				runCycle(false);
				rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
				// Charge
				runCycle(true);
				rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();

			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
