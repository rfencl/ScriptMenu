package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.Modbus203;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class CharacterizationTestSunspec  extends CyclingTestBase {

	// Devices
	private static Modbus123 cImmediateControlsBlockMaster;
	private static Modbus203 cWyeConnectThreePhaseabcnMeterBlockMaster;
	// Test settings
	private static BigDecimal chargingPowerAsPct;
	private static BigDecimal dischargingPowerAsPct;
	// Test utilities
	private static Reports strReport;
	private static Reports arrReport;
	private final static Logger LOG = LogManager.getLogger();
	private static FileHelper fCellGroupReportSecond, fCellGroupReportMinute, fStringReport, fArrayReport;
	private static String arrayIndex;
	private static String stringIndex;
	
	//TO DO: power cycling is to be by Power Control app

	// Constructor
	public CharacterizationTestSunspec(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
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
		fStringReport = new FileHelper("bc_string_report_csv_file_path");
		                  
		String columns = "operation,timestamp,voltageMax,voltageMin,voltageAvg,stringCurrent,stringPower,calculatedStringVoltage,measuredStringVoltage,stringSoc,acPower,pcsDcPower,pcsWhours,pcsDcBusVoltage";
		if (Boolean.valueOf(PowinProperty.BC_SEL735.toString()) == Boolean.TRUE) {
			fStringReport.writeToCSV(columns+",acPowerSEL,kwhExpSEL,kwhImpSEL");
		} else {
			fStringReport.writeToCSV(columns);
		}

		resetDevices();
		int i = 0;
		chargingPowerAsPct = args.length > i ? BigDecimal.valueOf(Double.parseDouble(args[i++])) : new BigDecimal( PowinProperty.BC_CHARGINGPOWERASPCT.toString() );
		dischargingPowerAsPct = args.length > i ? BigDecimal.valueOf(Double.parseDouble(args[i++])) : new BigDecimal(	 PowinProperty.BC_DISCHARGINGPOWERASPCT.toString() );
		restPeriodSeconds = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_RESTPERIODSECONDS.intValue();
		targetChargeVoltage = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETCHARGEVOLTAGE.intValue();
		targetDischargeVoltage = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETDISCHARGEVOLTAGE.intValue();
		maxCycles = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_MAXCYCLES.intValue();
		logInterval = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_LOGINTERVAL.intValue();
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

	// Test methods
	public static void runCycle(Boolean isCharging) throws ModbusException, InterruptedException, IOException {
		// Run either a charge or discharge based on cycleType
		resetDevices();
		String cycleType = isCharging ? "Charging" : "Discharging";
		BigDecimal powerAsPct = isCharging ? chargingPowerAsPct.negate() : dischargingPowerAsPct;
		setPAsPercent(powerAsPct);
		// Wait till the inverter actually switches on, i.e., current is non-zero
		int currentDC = 0;
		while (currentDC == 0) {
			Thread.sleep(500);
			currentDC = getDCCurrent();
		}
		// Keep cycle running till target voltage is reached
		int instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
		boolean targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		while (targetNotReached) {
			resetDevices();
			// string report
			if (Boolean.valueOf(PowinProperty.BC_SEL735.toString()) == Boolean.TRUE){
				fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport()
						+ "," + getWatts() + "," + getDCWatts() + "," + getWattHours() + "," + getDCBusVoltage()
						+ "," + getWattsSEL() + "," + getKwhExpSEL() + "," + getKwhImpSEL());
			} else {
				fStringReport.writeToCSV(cycleType + " cycle in progress," + strReport.getStringReport()
						+ "," + getWatts() + "," + getDCWatts() + "," + getWattHours() + "," + getDCBusVoltage());
			}
			Thread.sleep(1000 * logInterval);
			strReport = new Reports(arrayIndex + "," + stringIndex);
			instantaneousVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
			targetNotReached = isCharging ? instantaneousVoltage < targetChargeVoltage : instantaneousVoltage > targetDischargeVoltage;
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete," + strReport.getStringReport());
		resetDevices();
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Throwable {
		try {
			CharacterizationTestSunspec mCharacterizationTest = new CharacterizationTestSunspec(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mCharacterizationTest.init(args);
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
				// Discharge
				mCharacterizationTest.resetDevices();
				mCharacterizationTest.runCycle(false);
				mCharacterizationTest.rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
				// Charge
				mCharacterizationTest.runCycle(true);
				mCharacterizationTest.rest(restPeriodSeconds);
				mCharacterizationTest.resetDevices();
			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}

		// String str = CommonHelper.getDefaultProperty("bc_chargingPowerAsPct");// "5.625";
		// // Using BigDecimal(String) constructor
		// BigDecimal num = new BigDecimal(CommonHelper.getDefaultProperty("bc_chargingPowerAsPct"));
		// // new BigDecimal(CommonHelper.getDefaultProperty("bc_chargingPowerAsPct"));
		// // Printing BigDecimal value
		// System.out.println("Converted String to BigDecimal : " + num);
	}
}
