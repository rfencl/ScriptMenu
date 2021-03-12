package com.powin.modbusfiles.reports;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.InverterSafety;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.configuration.TurtleConfiguration;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus120;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.qilin.configuration.BlockConfiguration;

public class SystemInfo {
    
	private final static Logger LOG = LogManager.getLogger(SystemInfo.class.getName());
    private final static String cArrayIndex=PowinProperty.ARRAY_INDEX.toString();
    private final static String cStringIndex=PowinProperty.STRING_INDEX.toString();
    private final static String cStringReportIdentifier = cArrayIndex+","+cStringIndex;
    private final static String cArrayReportIdentifier = cArrayIndex;

	public static int getActualAcPower() throws ModbusException {
		Modbus103 mModbus103 = new Modbus103(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
		mModbus103.init();
		return mModbus103.getWatts()/1000;	
	}
	
	public static double getArrayCurrent() {
		return Double.parseDouble(getArrayReport().getArrayCurrent());
	}

	public static Reports getArrayReport() {
		return new Reports(cArrayIndex);
	}
	
	public static int getAverageStringVoltage(List<StringReport> stringList) {
		int avgVolt = 0;
		if (stringList != null && stringList.size() > 0) {
//			int sumVolt = 0;
//			for (StringReport report : stringList) {
//				sumVolt += report.getStringData().getCalculatedStringVoltage();
//			}
//			avgVolt = sumVolt / stringList.size();
			avgVolt = (int) stringList.stream()
					.mapToInt(StringReport -> StringReport.getStringData().getDcBusVoltage())
					.average().getAsDouble();
		}
		return avgVolt;
	}

	public static int getAvgCellGroupVoltage() {
	   	 StringReport stringReport = new StringReport();
	    	 List<StringReport> stringReportList = getStringReportList();
	   	 return stringReportList.get(PowinProperty.STRING_INDEX.intValue()-1).getStringData().getAvgCellGroupVoltage();
	}

	public static List<StringReport> getStringReportList() {
		List<StringReport> stringReportList = SystemInfo.getStringReportList(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.intValue());
		return stringReportList;
	}

	public static int getBatteryPackCount() {
    	StringReport stringReport = new StringReport();
    	List<StringReport> stringReportList = getStringReportList();
		return stringReportList.get(PowinProperty.STRING_INDEX.intValue()-1).getBatteryPackReportList().size();
    }
	
	public static int getBlockIndex() {
		LastCalls mLastCalls = new LastCalls();
		return Integer.parseInt(mLastCalls.getBlockIndex());
	}

	public static int getMaxAllowableChargeCurrent(int arrayIndex) throws IOException {
		Reports arrReport = getArrayReport();
		return Integer.parseInt(arrReport.getArrayMaxAllowedChargeCurrent());
	}
	
	public static int getMaxAllowableChargePower(int arrayIndex) throws IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LastCalls lc=new LastCalls();
		return (int) Double.parseDouble(lc.getAvailableACChargekW(String.valueOf(arrayIndex)));
	}
	
	public static int getMaxAllowableDischargeCurrent(int arrayIndex) throws IOException {
		Reports arrReport = getArrayReport();
		return Integer.parseInt(arrReport.getArrayMaxAllowedDischargeCurrent());
	}

	public static int getMaxAllowableDischargePower(int arrayIndex) throws IOException, ModbusException, NumberFormatException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LastCalls lc=new LastCalls();
		return (int) Double.parseDouble(lc.getAvailableACDischargekW(String.valueOf(arrayIndex)));
	}
    
	public static int getMaxCellGroupVoltage() {
   	 StringReport stringReport = new StringReport();
    	 List<StringReport> stringReportList = getStringReportList();
   	 return stringReportList.get(PowinProperty.STRING_INDEX.intValue()-1).getStringData().getMaxCellGroupVoltage();
   }
    
	public static int getMaxStringVoltage(List<StringReport> stringList) {
		int maxVolt = 0;
		if (stringList != null && stringList.size() > 0) {
			maxVolt = stringList.get(0).getStringData().getDcBusVoltage();
			for (StringReport report : stringList) {
				if (report.getStringData().getDcBusVoltage() > maxVolt) {
					maxVolt = report.getStringData().getDcBusVoltage();
				}
			}
		}
		return maxVolt;
	}
    
	public static int getMaxStringVoltageIndex(List<StringReport> stringList) {
		int index = 0;
		int maxVolt = 0;
		if (stringList != null && stringList.size() > 0) {
			index = stringList.get(0).getStringIndex();
			maxVolt = stringList.get(0).getStringData().getDcBusVoltage();
			for (StringReport report : stringList) {
				if (report.getStringData().getDcBusVoltage() > maxVolt) {
					maxVolt = report.getStringData().getDcBusVoltage();
					index = report.getStringIndex();
				}
			}
		}
		return index;
	}
    
   public static int getMeasuredStringVoltage() {
	Reports strReport = getStringReport();
	int startStringVoltage = Integer.parseInt(strReport.getMeasuredStringVoltage());
	return startStringVoltage;
}
    
    public static int getMinCellGroupVoltage() {
    	 StringReport stringReport = new StringReport();
     	 List<StringReport> stringReportList = getStringReportList();
    	 return stringReportList.get(PowinProperty.STRING_INDEX.intValue()-1).getStringData().getMinCellGroupVoltage();
    }

	public static int getMinStringVoltage(List<StringReport> stringList) {
		int minVolt = 0;
		if (stringList != null && stringList.size() > 0) {
			minVolt = stringList.get(0).getStringData().getDcBusVoltage();
			for (StringReport report : stringList) {
				if (report.getStringData().getDcBusVoltage() < minVolt) {
					minVolt = report.getStringData().getDcBusVoltage();
				}
			}
		}
		return minVolt;
	}

	public static int getMinStringVoltageIndex(List<StringReport> stringList) {
		int index = 0;
		int minVolt = 0;
		if (stringList != null && stringList.size() > 0) {
			index = stringList.get(0).getStringIndex();
			minVolt = stringList.get(0).getStringData().getDcBusVoltage();
			for (StringReport report : stringList) {
				if (report.getStringData().getDcBusVoltage() < minVolt) {
					minVolt = report.getStringData().getDcBusVoltage();
					index = report.getStringIndex();
				}
			}
		}
		return index;
	}

	public static int getNameplateKw() {
		Modbus120 mModbus120 = new Modbus120(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
		mModbus120.init();
		int nameplateKw = 0;
		try {
			int powerRatingRaw = mModbus120.getWRtg().intValue();
			int scalingFactor = mModbus120.getWRtgSf();
			nameplateKw = (int)(powerRatingRaw * Math.pow(10, -scalingFactor));
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nameplateKw;
	}
	
	public static void getQilinInfo() throws JsonProcessingException {
		// TODO Auto-generated method stub
		BlockConfiguration mBlockConfiguration = new BlockConfiguration();

        ObjectMapper mObjectMapper = new ObjectMapper();
        LogManager.getLogger().info(mObjectMapper.writeValueAsString(mBlockConfiguration));
		
	}

	public static int getStackChargeCurrentCapacity() {
		LastCalls mLastCalls = new LastCalls();
		return Integer.parseInt(mLastCalls.getMaxAmpCharge());
	}

	public static int getStackCountCommunicating() {
		Reports arrReport = getArrayReport();
		return Integer.parseInt(arrReport.getCommunicatingStackCount());
	}

	public static int getStackDischargeCurrentCapacity() {
			LastCalls mLastCalls = new LastCalls();
			return Integer.parseInt(mLastCalls.getMaxAmpDischarge());
		}

	public static StackType getStackType() {
		Reports reports = getStringReport();
		return StackType.fromName(reports.getStackType());
	}

	public static String getStationCode() {
		LastCalls mLastCalls = new LastCalls();
		return mLastCalls.getStationCode();	
	}

	public static Reports getStringReport() {
		return new Reports(cArrayIndex + "," + cStringIndex);
	}

	public static List<StringReport> getStringReportList(String arrayIndex, int stringCount) {
		List<StringReport> reportList = new ArrayList<StringReport>();
		String reportContent = "";
		ObjectMapper objectMapper = new ObjectMapper();
		for (int i = 1; i <= stringCount; i++) {
			reportContent = StringReport.getStringReportString(arrayIndex, Integer.toString(i));
			if (reportContent.isEmpty() == false) {
				StringReport stringReportInstance;
				try {
					stringReportInstance = objectMapper.readValue(reportContent, StringReport.class);
					if (stringReportInstance != null) {
						reportList.add(stringReportInstance);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return reportList;
	}

	public static TurtleConfiguration getTurtleConfiguration() {
			TurtleConfiguration turtleConfiguration = null;
			try {
				CommonHelper.copyFileFromTurtleToLocal("/etc/powin/configurtion.json", "home/powin/configuration.json");
				turtleConfiguration =  (new ObjectMapper()).readValue(new File("/home/powin/configuration.json"), TurtleConfiguration.class);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return turtleConfiguration;
		}

	public static String getDcBusVoltage() {
		return String.valueOf(getStringReportList().get(0).getStringData().getDcBusVoltage());
	}

	public static String getBsfStatus() {
		LastCalls cLastCalls = new LastCalls();
		return cLastCalls.getBsfStatus();
	}

	public static void getLastCallsContent() {
		 LastCalls cLastCalls = new LastCalls();
		cLastCalls.getLastCallsContent();
	}

	public static String getBsfGood() {
		LastCalls cLastCalls = new LastCalls();
		return cLastCalls.getBSFGood();
	}

	public static String getBsfErrorCodes() {
		LastCalls cLastCalls = new LastCalls();
		return cLastCalls.getBsfErrorCodes();
	}

	public static String getBSFMessage() {
		LastCalls cLastCalls = new LastCalls();
		return cLastCalls.getBSFMessage();
	}

	//TODO move to SystemInfo/Power
	public static boolean targetNotReached(int voltageTarget, boolean isCharging, int stringVoltageMeasured) {
		LOG.info(String.format("%s to target voltage of %d. Current string voltage: %s", isCharging ? "Charging" : "Discharging",
				voltageTarget, stringVoltageMeasured));
		return isCharging ? stringVoltageMeasured < voltageTarget : stringVoltageMeasured > voltageTarget;
	}

	//TODO move to SystemInfo
	public static boolean isHysteresisCleared() {
		LastCalls cLastCalls = new LastCalls();
		String statusStr = cLastCalls.getBSFMessage();
		return statusStr != null && !statusStr.contains("VH");
	}

	public static boolean isPowerBlockedMessageDisplayed() {
		String statusStr = getBSFMessage();
		return statusStr != null && statusStr.contains("BLOCKED");
	}
}
