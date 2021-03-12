package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.Modbus203;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class RteTest {
	// Modbus parameters
	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;
	// Devices
	private static Modbus123 cImmediateControlsBlockMaster;
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static Modbus802 cBatteryBaseModelBlockMaster;
	private static Modbus203 cWyeConnectThreePhaseabcnMeterBlockMaster;
	// Test settings
	private static BigDecimal chargingPowerAsPct;
	private static BigDecimal dischargingPowerAsPct;
	private static int restPeriodSeconds;
	private static int logInterval = 2;
	private static int targetChargeVoltage;
	private static int targetDischargeVoltage;
	private static int maxCycles;
	// Test utilities
	private static Reports report;
	private final static Logger LOG = LogManager.getLogger();
	private static FileHelper fu;

	// Constructor
	public RteTest(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		cModbusHostName = modbusHostName;
		cModbusPort = modbusPort;
		cModbusUnitId = modbusUnitId;
		cEnableModbusLogging = enableModbusLogging;
	}

	public void init(String[] args) throws IOException {
		fu = new FileHelper("rte_csv_file_path");
		report = new Reports("1,2");
		// Set up devices
		resetDevices();
		// Get test parameters from command line. Default values are supplied in case parameters not in command line
		chargingPowerAsPct = args.length > 0 ? BigDecimal.valueOf(Double.parseDouble(args[0])) : BigDecimal.valueOf(Double.parseDouble("9.33"));
		dischargingPowerAsPct = args.length > 1 ? BigDecimal.valueOf(Double.parseDouble(args[0])) : BigDecimal.valueOf(Double.parseDouble("9.33"));
		restPeriodSeconds = args.length > 2 ? Integer.parseInt(args[2]) : 300;
		targetChargeVoltage = args.length > 3 ? Integer.parseInt(args[3]) : 880;
		targetDischargeVoltage = args.length > 4 ? Integer.parseInt(args[4]) : 830;
		maxCycles = args.length > 5 ? Integer.parseInt(args[5]) : 2;
		logInterval = 2;
	}

	public void resetDevices() {
		cImmediateControlsBlockMaster = new Modbus123(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		// cWyeConnectThreePhaseabcnMeterBlockMaster = new Modbus203(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

	// Device methods
	public static int getPAsPercent() throws ModbusException {
		return cImmediateControlsBlockMaster.getWMaxLimPct();
	}

	public static void setPAsPercent(BigDecimal percent) throws ModbusException {
		cImmediateControlsBlockMaster.setWMaxLimPct(percent);
	}

	public static void setPAsPercentWait(BigDecimal percent, int waitseconds) throws ModbusException, InterruptedException {
		for (int idx = 0; idx < waitseconds / 10; idx++) {
			try {
				setPAsPercent(percent);
			} catch (ModbusException e) {
				e.printStackTrace();
			}
			Thread.sleep(10000);
		}
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
		LOG.info(cycleType + " cycle zero current Timestamp = {}", report.getStringTimestamp());
		fu.writeToCSV(cycleType + " cycle zero current," + report.getStringTimestamp() + "," + report.getCalculatedStringVoltage() + "," + report.getStringSoc() + ",<NULL>,<NULL>,<NULL>");
		BigDecimal powerAsPct = isCharging ? chargingPowerAsPct.negate() : dischargingPowerAsPct;
		setPAsPercent(powerAsPct);
		// Wait till the inverter103 actually switches on, i.e., current is non-zero
		int currentDC = 0;
		while (currentDC == 0) {
			Thread.sleep(500);
			currentDC = getDCCurrent();
		}
		LOG.info(cycleType + " cycle non-zero current Timestamp = {}", report.getStringTimestamp());
		fu.writeToCSV(cycleType + " cycle wait for non-zero current," + report.getStringTimestamp() + "," + report.getCalculatedStringVoltage() + "," + report.getStringSoc() + ",<NULL>,<NULL>,<NULL>");
		// Keep cycle running till target voltage is reached
		int instantaneousVoltage = Integer.parseInt(report.getCalculatedStringVoltage());
		boolean targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;

		while (targetNotReached) {
			setPAsPercent(powerAsPct);
			LOG.info("Timestamp during " + cycleType + "={}, Calculated String Voltage ={},SOC= {},Inverter Watts AC = {}, " + "Inverter Watts DC={},String Watts DC= {},Calculated String Voltage = {}", report.getStringTimestamp(), report.getCalculatedStringVoltage(), report.getStringSoc(),
					getWatts(), getDCWatts(),
					getStringWattsDC(), report.getCalculatedStringVoltage());
			fu.writeToCSV(cycleType + " cycle in progress," + report.getStringTimestamp() + "," + report.getCalculatedStringVoltage() + "," + report.getStringSoc() + "," + getWatts() + "," + getDCWatts() + "," + getStringWattsDC());
			Thread.sleep(1000 * logInterval);
			instantaneousVoltage = Integer.parseInt(report.getCalculatedStringVoltage());
			targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		}
		LOG.info("Timestamp " + cycleType + " complete={}, Calculated String Voltage ={},SOC= {}", report.getStringTimestamp(), report.getCalculatedStringVoltage(), report.getStringSoc());
		fu.writeToCSV(cycleType + " cycle complete," + report.getStringTimestamp() + "," + report.getCalculatedStringVoltage() + "," + report.getStringSoc() + ",<NULL>,<NULL>,<NULL>");
	}

	public static void rest(int restPeriodInSecs) throws InterruptedException {
		// This is a workaround. If the delay is more than 20 seconds, then a subsequent non-zero charge/discharge will not work
		for (int idx = 0; idx < restPeriodInSecs / 10; idx++) {
			try {
				setPAsPercent(BigDecimal.valueOf(0));
			} catch (ModbusException e) {
				e.printStackTrace();
			}
			Thread.sleep(10000);
		}

	}

	public void SOFT_886() throws InterruptedException {
		try {
			for (int powerAsPct = 10; powerAsPct < 21; powerAsPct++) {
				setPAsPercentWait(BigDecimal.valueOf(powerAsPct), 30);
				resetDevices();
				LOG.info("Inverter Watts AC = {}", getWatts());
			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}

	public static void main(String[] args) throws IOException {
		// argument list: chargingPowerAsPct,dischargingPowerAsPct,restPeriodSeconds,targetChargeVoltage,targetDischargeVoltage,maxCycles
		try {
			RteTest mRTE_Test = new RteTest(PowinProperty.TURTLEHOST.toString(), 4502, 255, true);
			mRTE_Test.init(args);
			mRTE_Test.fu.addHeader("operation,timestamp,calculatedStringVoltage,soc,inverterWattsAC,inverterWattsDC,stringWattsDC");
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
				// Discharge
				runCycle(false);
				// Rest
				rest(restPeriodSeconds);
				mRTE_Test.resetDevices();
				// Charge
				runCycle(true);
				rest(restPeriodSeconds);
				mRTE_Test.resetDevices();
			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
