package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class CharacterizationTest_SP572 extends CyclingTestBase {
	// Test utilities
	private final static Logger LOG = LogManager.getLogger();
	private static Reports strReport;
	private static FileHelper fStringReport;
	private static String arrayIndex;
	private static String stringIndex;

	// Constructor
	public CharacterizationTest_SP572(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
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
		fStringReport.writeToCSV("operation,timestamp,cellVoltageMax,cellVoltageMin,cellVoltageAvg,cellTempMax,cellTempMin,cellTempAvg,stackDcCurrent,stackDcPower,calculatedStrVolts,measuredStrVolts,Soc,Notifications,BasicOpsStatus,contactorPos,ContactorNeg");

		resetDevices();
		int i = 0;
		chargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_CHARGINGPOWERW.intValue();        		
		dischargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_DISCHARGINGPOWERW.intValue(); 		
		restPeriodSeconds = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_RESTPERIODSECONDS.intValue(); 		 
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
	
	public static String getBasicOpsStatus() throws ModbusException {
		resetDevices();
		return cModbusPowinBlock.getStatus();
	}
	
	public static String printNotification() throws IOException {
		Notifications notifications = new Notifications(arrayIndex + "," + stringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return CommonHelper.convertArrayListToString(notificationList,":");
	}
	
	public static boolean isChargingComplete() throws IOException, ModbusException {
		
		return getBasicOpsStatus().toUpperCase().contentEquals("DONE");
	}
	
	public static boolean isDischargingComplete() throws IOException {
		Notifications notifications = new Notifications(arrayIndex + "," + stringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf("1004") != -1 ? true : false;
	}

	// Test methods
	public static void runCycle(Boolean isCharging) throws ModbusException, InterruptedException, IOException {
		// Run either a charge or discharge based on cycleType
		String cycleType = isCharging ? "Charging" : "Discharging";
		if (isCharging) {
			chargeViaSimpleBasicCommand(BigDecimal.valueOf(-chargingPowerKw));
		}
		else {
			dischargeViaSimpleBasicCommand(BigDecimal.valueOf(dischargingPowerKw));
		}
		// Wait till the inverter actually switches on, i.e., current is non-zero
		int currentDC = 0;
		while (currentDC == 0) {
			Thread.sleep(500);
			currentDC = getDCCurrent();
		}
		// Keep cycle running till target SOC is reached
		boolean targetNotReached = isCharging ? !isChargingComplete(): !isDischargingComplete();
		while (targetNotReached) {
			fStringReport.writeToCSV(cycleType + " cycle in progress," + 
					strReport.getStringReport_vt()+","+
					printNotification()+","+
					getBasicOpsStatus()+ "," +
					strReport.getStringPositiveContactorStatus()+ "," + 
					strReport.getStringNegativeContactorStatus());
			targetNotReached = isCharging ? !isChargingComplete(): !isDischargingComplete();
			Thread.sleep(1000 * logInterval);
			strReport = new Reports(arrayIndex + "," + stringIndex);
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete," + strReport.getStringReport_vt() + "," + strReport.getCalculatedStringVoltage());
	}

	public static void rest(int restPeriodInSecs) throws InterruptedException, IOException, ModbusException {
		// Stop powering
		try {
			int logInterval =8;//log frequency; make sure it is less than the sunspec timeout in turtle
			stopViaSimpleBasicCommand();
			for (int idx=0;idx<restPeriodInSecs/logInterval;idx++){
				fStringReport.writeToCSV("Resting...," + 
						strReport.getStringReport_vt()+","+
						printNotification()+","+
						getBasicOpsStatus()+ "," +
						strReport.getStringPositiveContactorStatus()+ "," + 
						strReport.getStringNegativeContactorStatus());
				Thread.sleep(logInterval* 1000);
				strReport = new Reports(arrayIndex + "," + stringIndex);
			}
			
		} catch (ModbusException e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) throws Throwable {
		try {
			CharacterizationTest_SP572 mCharacterizationTest = new CharacterizationTest_SP572(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mCharacterizationTest.init(args);
			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
			
				// Discharge
				resetDevices();
				runCycle(false);
				resetDevices();
				rest(restPeriodSeconds);	
				// Charge
				resetDevices();
				runCycle(true);
				resetDevices();
				rest(restPeriodSeconds);
			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}