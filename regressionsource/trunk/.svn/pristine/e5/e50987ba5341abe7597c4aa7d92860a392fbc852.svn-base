package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.Modbus203;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class BusBarThermalTest extends CyclingTestBase {
	private static Modbus203 cWyeConnectThreePhaseabcnMeterBlockMaster;
	private static ModbusPowinBlock cModbusPowinBlock;
	private static Modbus123 cImmediateControlsBlockMaster;
	private static BigDecimal chargingPowerAsPct;
	private static BigDecimal dischargingPowerAsPct;
	
	private static int restPeriodSeconds;
	private static int logInterval = 2;
	private static int targetChargeVoltage;
	private static int targetDischargeVoltage;
	private static int maxCycles;
	// Test utilities
	private static Reports strReport;
	private final static Logger LOG = LogManager.getLogger();
	private static FileHelper fStringReport;
	private static String arrayIndex;
	private static String stringIndex;

	// Constructor
	public BusBarThermalTest(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
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
		chargingPowerAsPct = args.length > 0 ? BigDecimal.valueOf(Double.parseDouble(args[0])) : new BigDecimal(PowinProperty.BC_CHARGINGPOWERASPCT.intValue());
		dischargingPowerAsPct = args.length > 1 ? BigDecimal.valueOf(Double.parseDouble(args[0])) : new BigDecimal(PowinProperty.BC_DISCHARGINGPOWERASPCT.intValue());
		
		// Output report files
		fStringReport = new FileHelper("bc_string_report_csv_file_path");
		fStringReport.writeToCSV("operation,timestamp,bp,cg,cellVoltageMin,bp,cg,cellVoltageMax,cellVoltageAvg,"
				+ "bp,cg,cellTempMin,bp,cg,cellTempMax,cellTempAvg,"
				+ "stackDcCurrent,stackDcPower,stackDCBusVoltage,Soc,"
				+"pcsAcPower,pcsDCPower");
		
		String columns = "operation,timestamp,voltageMax,voltageMin,voltageAvg,stringCurrent,stringPower,calculatedStringVoltage,measuredStringVoltage,stringSoc,acPower,pcsDcPower,pcsWhours,pcsDcBusVoltage";
		if (Boolean.valueOf(PowinProperty.BC_SEL735.toString()) == Boolean.TRUE) {
			fStringReport.writeToCSV(columns+",acPowerSEL,kwhExpSEL,kwhImpSEL");
		} else {
			fStringReport.writeToCSV(columns);
		}
		// Set up devices
		resetDevices();
		initParameters(args);
	}

	public static void resetDevices()  {
		CyclingTestBase.resetDevices();
		cImmediateControlsBlockMaster = new Modbus123(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		if (Boolean.valueOf(PowinProperty.BC_SEL735.toString()) == Boolean.TRUE) 
			cWyeConnectThreePhaseabcnMeterBlockMaster = new Modbus203(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

	// Device methods
	public static void chargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandPrioritySOC(power);
	}

	public static void dischargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException {
		cModbusPowinBlock.runSimpeBasicCommandPriorityPower(power);
		// resetDevices();
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

	public static int getWattsSEL() throws ModbusException {
		return cWyeConnectThreePhaseabcnMeterBlockMaster.getWatts();
	}

	public static int getKwhExpSEL() throws ModbusException {
		return cWyeConnectThreePhaseabcnMeterBlockMaster.getTotalWhExported().intValue();
	}

	public static int getKwhImpSEL() throws ModbusException {
		return cWyeConnectThreePhaseabcnMeterBlockMaster.getTotalWhImported().intValue();
	}
	public static void setPAsPercent(BigDecimal percent) throws ModbusException {
		cImmediateControlsBlockMaster.setWMaxLimPct(percent);
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

	// Test methods
	public static void runCycle(Boolean isCharging) throws ModbusException, InterruptedException, IOException {
		// Run either a charge or discharge based on cycleType
		String cycleType = isCharging ? "Charging" : "Discharging";
		BigDecimal powerAsPct = isCharging ? chargingPowerAsPct.negate() : dischargingPowerAsPct;
		setPAsPercent(powerAsPct);
//		if (isCharging)
//			chargeViaSimpleBasicCommand(BigDecimal.valueOf(-chargingPowerKw));
//		else
//			dischargeViaSimpleBasicCommand(BigDecimal.valueOf(dischargingPowerKw));
	
		// Keep cycle running till target voltage is reached
		int instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
		boolean targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		while (targetNotReached) {
			resetDevices();
			// string report
//			fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport_vt_id()+","+
//					getWatts() + "," + getDCWatts());
//					strReport = new Reports(arrayIndex + "," + stringIndex);
			Thread.sleep(1000 * logInterval);
			strReport = new Reports(arrayIndex + "," + stringIndex);
			instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
			targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete," + strReport.getStringReport());
		resetDevices();
	}

//	public static void rest(int restPeriodInSecs) throws InterruptedException {
//		// Stop powering
//		try {
//			stopViaSimpleBasicCommand();
//		} catch (ModbusException e) {
//			e.printStackTrace();
//		}
//		// Rest
//		Thread.sleep(restPeriodInSecs * 1000);
//	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Throwable {
		try {
			BusBarThermalTest mCharacterizationTest = new BusBarThermalTest(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mCharacterizationTest.init(args);
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
				// Discharge

				mCharacterizationTest.runCycle(false);
				mCharacterizationTest.rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
				// Charge
				mCharacterizationTest.runCycle(true);
				mCharacterizationTest.rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
				// Discharge

				mCharacterizationTest.runCycle(false);
				mCharacterizationTest.rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
				
			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
