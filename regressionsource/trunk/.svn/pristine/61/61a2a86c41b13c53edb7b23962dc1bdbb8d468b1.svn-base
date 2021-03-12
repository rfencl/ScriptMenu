package com.powin.modbusfiles.derating;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerCommandApp;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class CellVoltageDerate {
	// Modbus parameters
	private  static String cModbusHostName;
	private  static int cModbusPort;
	private  static int cModbusUnitId;
	private  static boolean cEnableModbusLogging;
	// Devices
	private static Modbus103 cInverterThreePhaseBlockMaster;
	private static Modbus802 cBatteryBaseModelBlockMaster;
	// Test settings
	private static int chargingPowerKw;
	private static int dischargingPowerKw;
	private static int restPeriodSeconds;
	private static int logInterval = 1;
	private static int maxCycles;
	// Test utilities
	private final static Logger LOG = LogManager.getLogger();
	private static FileHelper fStringReport;
	private static String arrayIndex;
	private static String stringIndex;
	// Constructor
	public CellVoltageDerate() {
		
	}

	public void init(String[] args) throws IOException, InterruptedException {
		// System string and array reports
		arrayIndex = PowinProperty.ARRAY_INDEX.toString();
		stringIndex = PowinProperty.STRING_INDEX.toString();
		// Output report files
		//CommonHelper.deleteExistingFile(cCloudAppsUpdateDirectory+cCloudAppsUpdateFile);
		fStringReport = new FileHelper("bc_string_report_csv_file_path");
		fStringReport.deleteFileContents();
		fStringReport.writeToCSV("operation,timestamp,"
				+"cellVoltageMax1,"
				+"cellVoltageMax2,"
				+"cellVoltageMax3,"
				+"cellVoltageMax4,"
				+"availableChargeCurrent,availableDischargeCurrent,availableChargePower,availableDischargePower,"
				+"actualChargeCurrent,actualChargePower,Notifications");
		
		
		resetDevices();
		
		chargingPowerKw = args.length > 0 ? Integer.parseInt(args[0]) : PowinProperty.BC_CHARGINGPOWERW.intValue(); 
		dischargingPowerKw = args.length > 1 ? Integer.parseInt(args[1]) : PowinProperty.BC_DISCHARGINGPOWERW.intValue(); 
		restPeriodSeconds = args.length > 2 ? Integer.parseInt(args[2]) : PowinProperty.BC_RESTPERIODSECONDS.intValue(); 
		maxCycles = args.length > 3 ? Integer.parseInt(args[3]) : PowinProperty.BC_MAXCYCLES.intValue(); 
		logInterval = args.length > 4 ? Integer.parseInt(args[4]) : PowinProperty.BC_LOGINTERVAL.intValue();  
	}

	public static  void resetDevices() {
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		// cWyeConnectThreePhaseabcnMeterBlockMaster = new Modbus203(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

	// Device methods
	public static void chargeViaSimpleBasicCommand(BigDecimal power) throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		ModbusPowinBlock.getModbusPowinBlock().runSimpeBasicCommand(power);	
	}
	public static void chargeViaPowerCommandApp(int power) throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		PowerCommandApp pcApp =  new PowerCommandApp();
		pcApp.setPower(power/1000,0);
	}
	
	public static void dischargeViaPowerCommandApp(int power) throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		PowerCommandApp pcApp =  new PowerCommandApp();
		pcApp.setPower(power/1000,0);	
	}
	
	public static void stopViaPowerCommandApp() throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		chargeViaPowerCommandApp(0);
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
	
	public static String getBasicOpsStatus() throws ModbusException {
		resetDevices();
		return ModbusPowinBlock.getModbusPowinBlock().getStatus();
	}
	
	public static String printNotification() throws IOException {
		Notifications notifications = new Notifications(arrayIndex + "," + stringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return CommonHelper.convertArrayListToString(notificationList,":");
	}
	
	public static boolean isChargingComplete() throws IOException, ModbusException {
		
		//return getBasicOpsStatus().toUpperCase().contentEquals("DONE");
		Notifications notifications = new Notifications(arrayIndex + "," + stringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf("1001") != -1 ? true : false;
		
		
	}
	
	public static boolean isDischargingComplete() throws IOException {
		Notifications notifications = new Notifications(arrayIndex + "," + stringIndex);
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf("1004") != -1 ? true : false;
	}

	// Test methods
	public static void runCycle(Boolean isCharging) throws ModbusException, InterruptedException, IOException, KeyManagementException, NumberFormatException, NoSuchAlgorithmException, ParseException {
		// Run either a charge or discharge based on cycleType
		String cycleType = isCharging ? "Charging" : "Discharging";
		if (isCharging) {
			//chargeViaSimpleBasicCommand(BigDecimal.valueOf(-chargingPowerKw));
			chargeViaPowerCommandApp(Integer.valueOf(-chargingPowerKw));
		}
		else {
//			dischargeViaSimpleBasicCommand(BigDecimal.valueOf(dischargingPowerKw));
			dischargeViaPowerCommandApp(Integer.valueOf(dischargingPowerKw));
		}
		// Keep cycle running till target SOC is reached
//		boolean targetNotReached = isCharging ? !isChargingComplete(): !isDischargingComplete();
		Reports arrReport = new Reports(Integer.toString(1));
//		int instantaneousMaxCellVoltage = Integer.parseInt(arrReport.getArrayMaxCellVoltage());
//		boolean targetNotReached = isCharging ? instantaneousMaxCellVoltage < 3405 : instantaneousMaxCellVoltage > 3354;
		boolean targetNotReached = isCharging ? !isChargingComplete(): !isDischargingComplete();
		
		while (targetNotReached) {
			fStringReport.writeToCSV(cycleType + " cycle in progress," + 
					getReportData()+","+
					printNotification());
			arrReport = new Reports(Integer.toString(1));
//			instantaneousMaxCellVoltage = Integer.parseInt(arrReport.getArrayMaxCellVoltage());
//			targetNotReached = isCharging ? instantaneousMaxCellVoltage < 3401 : instantaneousMaxCellVoltage > 3360;
			targetNotReached = isCharging ? !isChargingComplete(): !isDischargingComplete();
			Thread.sleep(1000 * logInterval);
			fStringReport.writeToCSV(cycleType + " cycle in progress," + 
					getReportData()+","+
					printNotification());
			
		}
		for (int timer =0;timer<150;timer++) {
			Thread.sleep(4000);
			fStringReport.writeToCSV(cycleType + " cycle in progress," + 
					getReportData()+","+
					printNotification());
		}
		// Cycle complete
		fStringReport.writeToCSV(cycleType + " cycle complete.");
	}
	public static int getMaxAllowableChargePower(int arrayIndex) throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LastCalls lc=new LastCalls();
		return (int) Double.parseDouble(lc.getAvailableACChargekW(String.valueOf(arrayIndex)));
	}

	public static int getMaxAllowableDischargePower(int arrayIndex) throws IOException, ModbusException, NumberFormatException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LastCalls lc=new LastCalls();
		return (int) Double.parseDouble(lc.getAvailableACDischargekW(String.valueOf(arrayIndex)));
	}
	public static String getReportData() throws IOException, KeyManagementException, NumberFormatException, NoSuchAlgorithmException, ModbusException, ParseException {
		//String reportContents="";
		StringBuilder reportContents = new StringBuilder();
		Reports strReport=new Reports("1,1");
		reportContents=reportContents.append(strReport.getStringTimestamp()).append(",");
		for(int stringId=1;stringId<5;stringId++) {
			strReport = new Reports(arrayIndex + "," + String.valueOf(stringId));
			reportContents=reportContents.append(strReport.getMaxCellGroupVoltage()).append(",");
		}
		Reports arrReport = new Reports(Integer.toString(1));
		reportContents=reportContents.append(
								arrReport.getArrayMaxAllowedChargeCurrent()+","+
								arrReport.getArrayMaxAllowedDischargeCurrent()+","+
								getMaxAllowableChargePower(1)+","+
								getMaxAllowableDischargePower(1)+","+
								arrReport.getArrayCurrent()+","+
								arrReport.getArrayPower()
							);
		return reportContents.toString();
		
	}

	public static void rest(int restPeriodInSecs) throws InterruptedException, IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		int logInterval =8;//log frequency; make sure it is less than the sunspec timeout in turtle
		//stopViaPowerCommandApp() ;
		for (int idx=0;idx<restPeriodInSecs/logInterval;idx++){
			fStringReport.writeToCSV("Resting..." );
			Thread.sleep(logInterval* 1000);
		}	
	}
	
	public static void processDerateStrategy() throws IOException, InterruptedException {
//		"stringCurrentCharge01EnterMillivolts": 3400,
//		  "stringCurrentCharge01ExitMillivolts": 3360,
//		  "stringCurrentCharge01ExitMilliseconds": 60000,
//		  "stringCurrentCharge01Derate": 0.66,
		
		String deratingStrategySet= "3400,3360,60000,0.66|3410,3370,80000,0.33|3420,3380,100000,0.17|3430,3390,120000,0.08|3440,3410,140000,0.04";
		List<String> testData=FileHelper.readFileToList("/home/powin/derateTest.txt").stream().filter(e -> !e.isEmpty()).collect(Collectors.toList()) ;
		int presentVoltage ;
		//presentVoltage = Integer.parseInt(rowData);
		String lastKnownDerateStrategy="";
		for (String rowData:testData) {
		//Get which strategy applies to the present voltage
			presentVoltage = Integer.parseInt(rowData);
			String presentDerateStrategy = getApplicableDerateStrategy( deratingStrategySet, Integer.parseInt(rowData)) ;
			System.out.println("derate strategy for present voltage("+presentVoltage+"): "+presentDerateStrategy);
			if (presentDerateStrategy.contentEquals(lastKnownDerateStrategy)) {
				int exitVoltage=Integer.parseInt(presentDerateStrategy.split(",")[1]);
				if(presentVoltage < exitVoltage) {
					//Start timer
					//Validate derate has cleared
				}
				Thread.sleep(10000);
			}
			else {
				lastKnownDerateStrategy=presentDerateStrategy;
			}
			
		}
		
	}
	
	public static String getApplicableDerateStrategy(String deratingStrategySet,int presentVoltage) {
		String [] deratingStrategyArray =deratingStrategySet.split("\\|");
		ArrayList <String> deratingStrategyList = new ArrayList<String>();
		for (String deratingStrategy:deratingStrategyArray) {
			deratingStrategyList.add(deratingStrategy);
		}
		CommonHelper.sortListBySubstring(deratingStrategyList,true,0,"INT");
//		System.out.println("strategy sorted in ascending order: \n"+CommonHelper.convertArrayListToString(deratingStrategyList,"\n"));
		int findDerateStrategy= searchArrayList(deratingStrategyList,presentVoltage,"");
		if(findDerateStrategy == -1) {
//			System.out.println(presentVoltage+" is not governed by strategy");	
			return null;
		}
		else {
//			System.out.println(presentVoltage+" is governed by strategy: "+deratingStrategyList.get(findDerateStrategy));
			return deratingStrategyList.get(findDerateStrategy);
		}
	}
	
	public static int searchArrayList(ArrayList <String> targetArrayList, int searchTerm, String mode) {
		int currentIndex=0;
		for (currentIndex=0;currentIndex<targetArrayList.size();currentIndex++) {
			int entryVoltage = Integer.parseInt(targetArrayList.get(currentIndex).split(",")[0]);
			if(searchTerm < entryVoltage) {
					break;
			}
		}
//		System.out.println("item found at  "+String.valueOf(currentIndex -1));
		return currentIndex -1 ;		
	}

	public static void main(String[] args) throws Throwable {
		try {
			CellVoltageDerate mCharacterizationTest = new CellVoltageDerate();
			mCharacterizationTest.init(args);
			mCharacterizationTest.processDerateStrategy();
//			System.out.println(mCharacterizationTest.printNotification());
//			System.out.println(mCharacterizationTest.getReportData());
//			for (int numCycles = 0; numCycles < maxCycles; numCycles++) {
//				
//				//First cycle
//				resetDevices();
//				runCycle(true);
//				resetDevices();
////				rest(60);	
////				//Second cycle
////				resetDevices();
////				runCycle(false);
////				resetDevices();
////				rest(60);
//			}
		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}