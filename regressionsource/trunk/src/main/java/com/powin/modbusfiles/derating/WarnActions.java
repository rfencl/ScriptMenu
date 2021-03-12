package com.powin.modbusfiles.derating;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.ZeroConfigFromString;
import com.powin.modbusfiles.configuration.TurtleConfiguration;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;

public class WarnActions {
	private static  ZeroConfigFromString cNoticationCommander ;
	private final static Logger LOG = LogManager.getLogger(WarnActions.class.getName());
	private final ObjectMapper cObjectMapper = new ObjectMapper();
	//TO DO the limits need to be extracted from the system dynamically
	private static int cArrayCount;
	private static int cStringCount;
	private static String cWarnActionType;
	private final int maxChargeCurrent =600;
	private final int maxDischargeCurrent =600;
	private final int maxChargePower =450;
	private final int maxDischargePower =450;
	

	public WarnActions(String warnActionType,int arrayCount, int stringCount) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException, JSchException {		
		cArrayCount=arrayCount;
		cStringCount=stringCount;
		cWarnActionType=warnActionType;
		init();
	}
	

	
	public void init() throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException, JSchException {
		setNotificationCommander(new ZeroConfigFromString());
		if(cWarnActionType.contains("STOP")) {
			System.out.println("Warn stop test");
			boolean stopEnabled = getwarnStopStatus().contains("true");
			if(!stopEnabled) {
				setWarnStopStatus(true);
				System.out.println("Warn stop explicitly enabled");
			}
		}
		else {
			System.out.println("Warn derate test");
			if(getwarnStopStatus().contains("true")) {
				setWarnStopStatus(false);
				System.out.println("Warn stop explicitly disabled");
			}
		}
	}
	
//	public WarnActions(int arrayCount, int stringCount) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {		
//		LastCalls lc=new LastCalls();
//		lc.getStationCode();
//		cArrayCount=arrayCount;
//		cStringCount=stringCount;
//		init();
//	}
	
	public WarnActions() {
		// TODO Auto-generated constructor stub
	}

	public int getSystemMaxChargePower() {
		return maxChargePower;
	}

	public int getSystenMaxDischargePower() {
		return maxDischargePower;
	}

	public int getSystemMaxChargeCurrent() {
		return maxChargeCurrent;
	}

	public int getSystemMaxDischargeCurrent() {
		return maxDischargeCurrent;
	}

	public ZeroConfigFromString getNotificationCommander() {
		return cNoticationCommander;
	}

	public static void setNotificationCommander(ZeroConfigFromString notificationCommander) {
		cNoticationCommander = notificationCommander;
	}

	public int getDcBusVoltage() throws IOException {
		Reports arrReport = new Reports(Integer.toString(cArrayCount));
		return  Integer.parseInt(arrReport.getArrayDcBusVoltage());
	}

	private double getDerateFactor(String commandSet,int targetArrayIndex,boolean chargeDerating) {
		int deratingCount=0;
		String [] commandArray =commandSet.split("\\|");
		int notificationId = 0;
		int arrayIndex = 0;
		String stackInWarnState = "";
		ArrayList <String> stacksInWarnStateList = new ArrayList<String>();
		for (String command:commandArray) {		
			notificationId = Integer.parseInt(command.split(",")[0]);		  
			arrayIndex = Integer.parseInt(command.split(",")[2]);
			stackInWarnState = command.split(",")[3];				
			if(targetArrayIndex==arrayIndex ) {
				deratingCount = getDeratingCount( notificationId, chargeDerating) ;
				if(deratingCount!=0) {
//					if(notificationId==2019 ||notificationId==2020||cWarnActionType=="STOP") {
					if(cWarnActionType=="STOP") {
						return 0;
					}					
					if (!stacksInWarnStateList.contains(String.valueOf(arrayIndex)+":"+stackInWarnState+":"+String.valueOf(chargeDerating)) ){
						stacksInWarnStateList.add(String.valueOf(arrayIndex)+":"+stackInWarnState+":"+String.valueOf(chargeDerating));
					}				
				}
			}	
		}
		int stacksInWarn = stacksInWarnStateList.size();
//		LOG.info(commandSet+":"+chargeDerating+":"+stacksInWarn);
		stacksInWarn=stacksInWarn==cStringCount?cStringCount-1:stacksInWarn;
		return 1-(double)stacksInWarn/cStringCount;
	}
	
	public int getExpectedMaxAllowableChargeCurrent(String commandSet,int targetArrayIndex,boolean chargeDerating) throws IOException {
		double derateFactor = getDerateFactor( commandSet, targetArrayIndex,chargeDerating ) ;
		int arrayCurrentCapacity = SystemInfo.getStackChargeCurrentCapacity()*SystemInfo.getStackCountCommunicating();
		return(int) (arrayCurrentCapacity*derateFactor);
		//return calculatedExpectedMaxAllowableChargeCurrent > arrayCurrentCapacity ? arrayCurrentCapacity : calculatedExpectedMaxAllowableChargeCurrent;
	}

	public int getExpectedMaxAllowableDischargeCurrent(String commandSet,int targetArrayIndex,boolean chargeDerating) throws IOException {
		double derateFactor = getDerateFactor( commandSet, targetArrayIndex,chargeDerating ) ;
		int arrayCurrentCapacity = SystemInfo.getStackDischargeCurrentCapacity()*SystemInfo.getStackCountCommunicating();
		return(int) (arrayCurrentCapacity*derateFactor);
//		double derateFactor = getDerateFactor(commandSet, targetArrayIndex,chargeDerating );
//		int normalizedMaxCurrent = getSystemMaxDischargeCurrent();
//		int calculatedExpectedMaxAllowableDischargeCurrent=(int) (normalizedMaxCurrent*derateFactor);
//		return calculatedExpectedMaxAllowableDischargeCurrent >normalizedMaxCurrent?normalizedMaxCurrent:calculatedExpectedMaxAllowableDischargeCurrent;
	}
	
	public int getExpectedMaxAllowableChargePower(String commandSet,int targetArrayIndex,boolean chargeDerating) throws IOException, ModbusException {
		int dcBusVoltage = getDcBusVoltage();
		double efficiencyFactor=0.96;
		int calculatedExpectedMaxAllowableChargePower=(int) (efficiencyFactor*getExpectedMaxAllowableChargeCurrent(commandSet, targetArrayIndex,chargeDerating )*dcBusVoltage/1000);
		return calculatedExpectedMaxAllowableChargePower >maxChargePower ?maxChargePower:calculatedExpectedMaxAllowableChargePower;
	}

	public int getExpectedMaxAllowableDischargePower(String commandSet,int targetArrayIndex,boolean chargeDerating) throws IOException, ModbusException {
		int dcBusVoltage = getDcBusVoltage();
		double efficiencyFactor=0.96;
		int calculatedExpectedMaxAllowableDischargePower=(int) (efficiencyFactor*getExpectedMaxAllowableDischargeCurrent(commandSet, targetArrayIndex,chargeDerating )*dcBusVoltage/1000);
		return calculatedExpectedMaxAllowableDischargePower >maxDischargePower ?maxDischargePower:calculatedExpectedMaxAllowableDischargePower;
	}
	
	public void setNotification(int notificationId, int triggerMsg,int arrayIndex,int stringIndex) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		ZeroConfigFromString.sendNotification( notificationId,  triggerMsg,arrayIndex, stringIndex);
	}
	
	public void setNotification(String commandSet) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		String [] commandArray =commandSet.split("\\|");
		 int notificationId = 0;
		 int  triggerMsg = 0;
		 int arrayIndex = 0;
		 int stacksInWarnState = 0;
		 for (String command:commandArray) {
			 notificationId = Integer.parseInt(command.split(",")[0]);
			 triggerMsg = Integer.parseInt(command.split(",")[1]);		  
			 arrayIndex = Integer.parseInt(command.split(",")[2]);
			 stacksInWarnState = Integer.parseInt(command.split(",")[3]);
			 setNotification(notificationId, triggerMsg,arrayIndex, stacksInWarnState);
		 }
	}
	
	public void clearNotification(int notificationId, int triggerMsg,int arrayIndex,int stringIndex) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		ZeroConfigFromString.clearNotification( notificationId,  triggerMsg,arrayIndex, stringIndex);
	}
	
	public void clearNotification(String commandSet) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		String [] commandArray =commandSet.split("\\|");
		 int notificationId = 0;
		 int  triggerMsg = 0;
		 int arrayIndex = 0;
		 int stacksInWarnState = 0;
		 for (String command:commandArray) {
			 notificationId = Integer.parseInt(command.split(",")[0]);
			 triggerMsg = Integer.parseInt(command.split(",")[1]);		  
			 arrayIndex = Integer.parseInt(command.split(",")[2]);
			 stacksInWarnState = Integer.parseInt(command.split(",")[3]);
			 clearNotification(notificationId, triggerMsg,arrayIndex, stacksInWarnState);
		 }
	}
	
	public void clearNotificationX() throws IOException, InterruptedException, JSchException {
		//Till the 5 minute hard-coded delay is configurable, lets just restart tomcat
		CommonHelper.restartTomcat();	
	}
	
	private String getDeratingType(int notificationId) {
		String deratingType = "";
		switch (String.valueOf(notificationId)) {
		case "2001"://hi voltage warning
			deratingType="C";
			break;
		case "2004"://lo voltage warning
			deratingType="D";
			break;
		case "2010"://high temperature warning
			deratingType="CD";
			break;		
		default:
			break ;
		}
		return deratingType;
	}
	
	private int getDeratingCount(int notificationId,boolean chargeDerating) {
		String deratingType = "";
		int deratingCount=0;
		deratingType=getDeratingType(notificationId);
		if(chargeDerating) {
			if(deratingType.toUpperCase().contains("C"))
				deratingCount=1;
		}
		else {
			if(deratingType.toUpperCase().contains("D"))
				deratingCount=1;
		}
		return deratingCount;
	}

	public boolean validateWarnAction(String commandSet,int targetArrayIndex) throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException {
		boolean isTestPass = true;
		//Get actual allowable current and power values
		int maxAllowableChargePower			= SystemInfo.getMaxAllowableChargePower(targetArrayIndex);
		int maxAllowableDischargePower		= SystemInfo.getMaxAllowableDischargePower(targetArrayIndex);
		int maxAllowableChargeCurrent		= SystemInfo.getMaxAllowableChargeCurrent(targetArrayIndex);
		int maxAllowableDischargeCurrent	= SystemInfo.getMaxAllowableDischargeCurrent(targetArrayIndex);
		LOG.info("maxAllowableChargePower: "		+maxAllowableChargePower);
		LOG.info("maxAllowableChargeCurrent: "		+maxAllowableChargeCurrent);
		LOG.info("maxAllowableDischargePower: "		+maxAllowableDischargePower);
		LOG.info("maxAllowableDischargeCurrent: "	+maxAllowableDischargeCurrent);
		//Get expected current and power values
		int maxAllowableChargePowerExpected 		= 0;
		int maxAllowableChargeCurrentExpected 		= 0;
		int maxAllowableDischargeCurrentExpected 	= 0;
		int maxAllowableDischargePowerExpected 		= 0;	
		//Get the expected values for charge
		maxAllowableChargePowerExpected=getExpectedMaxAllowableChargePower(commandSet, targetArrayIndex,true );
		maxAllowableChargeCurrentExpected= getExpectedMaxAllowableChargeCurrent(commandSet, targetArrayIndex,true );
		//Get the expected values for discharge
		maxAllowableDischargeCurrentExpected= getExpectedMaxAllowableDischargeCurrent(commandSet, targetArrayIndex,false );
		maxAllowableDischargePowerExpected= getExpectedMaxAllowableDischargePower(commandSet, targetArrayIndex,false );	
		LOG.info("maxAllowableChargePowerExpected:"			+maxAllowableChargePowerExpected);
		LOG.info("maxAllowableChargeCurrentExpected: "		+maxAllowableChargeCurrentExpected);
		LOG.info("maxAllowableDischargePowerExpected:"		+maxAllowableDischargePowerExpected);
		LOG.info("maxAllowableDischargeCurrentExpected: "	+maxAllowableDischargeCurrentExpected);	
		//Compare actual and expected permissible values for power and current
		int tolerance =10;
		boolean testMaxAllowableChargeCurrent 	=	CommonHelper.compareIntegers(maxAllowableChargeCurrent, maxAllowableChargeCurrentExpected, tolerance);
		boolean testMaxAllowableChargePower 	=	CommonHelper.compareIntegers(maxAllowableChargePower, maxAllowableChargePowerExpected, tolerance);
		boolean testMaxAllowableDischargeCurrent=	CommonHelper.compareIntegers(maxAllowableDischargeCurrent, maxAllowableDischargeCurrentExpected ,tolerance);
		boolean testMaxAllowableDischargePower 	=	CommonHelper.compareIntegers(maxAllowableDischargePower, maxAllowableDischargePowerExpected, tolerance);
		
		LOG.info("Test for max chargepower:"		+	testMaxAllowableChargePower);
		LOG.info("Test for max charge current:"		+	testMaxAllowableChargeCurrent);
		LOG.info("Test for max discharge power:"	+	testMaxAllowableDischargePower);
		LOG.info("Test for max discharge current:"	+	testMaxAllowableDischargeCurrent);
		
		isTestPass=	testMaxAllowableChargeCurrent 
					&& testMaxAllowableChargePower 
					&& testMaxAllowableDischargeCurrent 
					&& testMaxAllowableDischargeCurrent ;		
		return isTestPass;
	}

	public String getwarnStopStatus() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		String command = "sudo chmod -R 777 /etc/powin/configuration.json";
		CommonHelper.runScriptRemotelyOnTurtle(command);
		command = "sudo rm -rf /home/powin/configuration.json";
		CommonHelper.callScriptSudo("powin",command);
		CommonHelper.copyFileFromTurtleToLocal("/etc/powin/configuration.json", "");;
		TurtleConfiguration cj = null;
        cj = cObjectMapper.readValue(new File("/home/powin/configuration.json"), TurtleConfiguration.class);
        return String.valueOf(cj.getBlockConfiguration().getArrayConfigurations().get(0).getUseWarnStop());
	}
	
	public static void setWarnStopStatus(boolean enable) throws IOException, InterruptedException, JSchException {
		// TO DO: move file to resources folder and get the variables from default properties
		String remote = "/home/powin/";
		String local = "/home/powin/";
		String fileName = "editConfigurationJson.sh";
		
		String localScriptFile=CommonHelper.copyScriptFileToLocalHome("editConfigurationJson.sh");
		CommonHelper.copyLocalFiletoRemoteLocation(local, remote, fileName);
		String enabledStatus=enable?"true":"false";
		CommonHelper.editConfigurationJson("editConfigurationJson.sh", "useWarnStop", enabledStatus,"/etc/powin/configuration.json");
	}

	public static void main(String[] args)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException, InterruptedException, JSchException {
		int arrayCount=4;
		int stringCount=4;
		WarnActions mWarnAction= new WarnActions("STOP", arrayCount,stringCount);	
//		WarnActions mWarnAction= new WarnActions();	
		String commandSet="2001,3777,1,1";
//		commandSet+=     "|2004,3777,2,2|2010,3777,2,2|2004,3777,2,3|2001,3777,2,2";
//		String commandSet=     "2004,3777,2,2";
		mWarnAction.setNotification(commandSet);
//		Thread.sleep(6000);	
//		for (int arrayIndex=1;arrayIndex <= cArrayCount;arrayIndex++) {
//			LOG.info("Validating for array: "+arrayIndex);
//			mWarnAction.validateWarnAction( commandSet,arrayIndex) ;
//		}
//		mWarnAction.setWarnStopStatus(true);
		//System.out.println(mWarnAction.getwarnStopStatus());
		
	}
}