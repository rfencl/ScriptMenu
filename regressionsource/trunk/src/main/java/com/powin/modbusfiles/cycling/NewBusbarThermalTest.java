package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class NewBusbarThermalTest {
	// Modbus parameters
	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;
	// Devices
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static Modbus802 cBatteryBaseModelBlockMaster;
	// Test settings
	private static int chargingPowerKw;
	private static int dischargingPowerKw;
	private static int restPeriodSeconds;
	private static int logInterval = 1;
	private static int targetChargeSOC;
	private static int targetDischargeSOC;
	private static int maxCycles;
	// Test utilities
	private final static Logger LOG = LogManager.getLogger();
	private static Reports strReport;
	private static FileHelper fStringReport;
	private static String arrayIndex;
	private static String stringIndex;

	// Constructor
	public NewBusbarThermalTest(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
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
		fStringReport=new FileHelper("bc_string_report_csv_file_path");
		// Output report files
		fStringReport.writeToCSV("operation,timestamp,bp,cg,cellVoltageMin,bp,cg,cellVoltageMax,cellVoltageAvg,"
				+ "bp,cg,cellTempMin,bp,cg,cellTempMax,cellTempAvg,"
				+ "stackDcCurrent,stackDcPower,stackDCBusVoltage,Soc"
				+"pcsAcPower,pcsDCPower");
		// Set up devices
		resetDevices();
		// Get test parameters from command line. Default values are supplied in case parameters not in command line
		chargingPowerKw = args.length > 0 ? Integer.parseInt(args[0]) : PowinProperty.BC_CHARGINGPOWERW.intValue();
		dischargingPowerKw = args.length > 1 ? Integer.parseInt(args[1]) : PowinProperty.BC_DISCHARGINGPOWERW.intValue();
		restPeriodSeconds = args.length > 2 ? Integer.parseInt(args[2]) : PowinProperty.BC_RESTPERIODSECONDS.intValue();
		targetChargeSOC = args.length > 3 ? Integer.parseInt(args[3]) : PowinProperty.BC_TARGETCHARGESOC.intValue();
		targetDischargeSOC = args.length > 4 ? Integer.parseInt(args[4]) : PowinProperty.BC_TARGETCHARGESOC.intValue();
		maxCycles = args.length > 5 ? Integer.parseInt(args[5]) : PowinProperty.BC_MAXCYCLES.intValue();
		logInterval = args.length > 6 ? Integer.parseInt(args[6]) : PowinProperty.BC_LOGINTERVAL.intValue();
	}

	public void resetDevices() {
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		// cWyeConnectThreePhaseabcnMeterBlockMaster = new Modbus203(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

	// Device methods
	public static void chargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(power);
	}

	public static void dischargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(power);
	}

	public static void stopViaSimpleBasicCommand() throws ModbusException {
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommandOff();
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
		// Keep cycle running till target SOC is reached
		int instantaneousSOC = Integer.parseInt(strReport.getStringSoc());
		boolean targetNotReached = isCharging ? instantaneousSOC < targetChargeSOC : instantaneousSOC > targetDischargeSOC;
		while (targetNotReached) {
			
			//fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport_vt_id()+","+
			//getWatts() + "," + getDCWatts());
			//strReport = new Reports(arrayIndex + "," + stringIndex);
			instantaneousSOC = Integer.parseInt(strReport.getStringSoc());
			targetNotReached = isCharging ? instantaneousSOC < targetChargeSOC : instantaneousSOC > targetDischargeSOC;
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete" );
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
			NewBusbarThermalTest mCharacterizationTest = new NewBusbarThermalTest(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
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