package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class CharacterizationTestSOC  extends CyclingTestBase  {
	// Test utilities
	private final static Logger LOG = LogManager.getLogger();
	private static Reports strReport;
	private static FileHelper fStringReport;
	private static String arrayIndex;
	private static String stringIndex;
	private static int targetChargeSOC;
	private static int targetDischargeSOC;

	// Constructor
	public CharacterizationTestSOC(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
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
		// Output report files
		fStringReport.writeToCSV("operation,timestamp,cellVoltageMax,cellVoltageMin,cellVoltageAvg,stackDcCurrent,stackDcPower,calculatedStrVolts,Soc");

		resetDevices();
		int i = 0;
		chargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_CHARGINGPOWERW.intValue();        		
		dischargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_DISCHARGINGPOWERW.intValue(); 		
		restPeriodSeconds = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_RESTPERIODSECONDS.intValue(); 		 
		targetChargeSOC = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETCHARGESOC.intValue(); 		
		targetDischargeSOC = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETDISCHARGESOC.intValue(); 
		maxCycles = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_MAXCYCLES.intValue(); 							
		logInterval = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_LOGINTERVAL.intValue(); 	
		}


	// Device methods
	public static void chargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommand(power);
	}

	public static void dischargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommand(power);
	}

	public static void stopViaSimpleBasicCommand() throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandOff();
	}

	public static int getDCBusVoltage() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCBusVoltage();
	}

	public static int getWatts() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWatts();
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
		// Keep cycle running till target SOC is reached
		int instantaneousSOC = Integer.parseInt(strReport.getStringSoc());
		boolean targetNotReached = isCharging ? instantaneousSOC < targetChargeSOC : instantaneousSOC > targetDischargeSOC;
		while (targetNotReached) {
			fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport());
			instantaneousSOC = Integer.parseInt(strReport.getStringSoc());
			targetNotReached = isCharging ? instantaneousSOC < targetChargeSOC : instantaneousSOC > targetDischargeSOC;
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete," + strReport.getStringReport() + "," + strReport.getCalculatedStringVoltage());
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
			CharacterizationTestSOC mCharacterizationTest = new CharacterizationTestSOC(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mCharacterizationTest.init(args);
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
				// Discharge
				runCycle(false);
				// Rest
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