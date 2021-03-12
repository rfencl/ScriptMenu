//package com.powin.modbusfiles;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.net.MalformedURLException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.codehaus.plexus.util.StringUtils;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
//
//import com.powin.modbus.ModbusException;
//
//
//public class socTesting {
//	private static Reports strReport ;
//	private static String reportUrl ;
//
//	public socTesting(String url) throws IOException {
//		reportUrl=url;
//		strReport= new Reports(url);
//	}
//	
//	private static void refreshReport() throws IOException {
//		strReport= new Reports(reportUrl);
//	}
//
//	public static String getStringTimestamp() throws IOException {
//		refreshReport();
//		return strReport.getStringTimestamp();
//	}
//	
//	public static String getStringMeasuredVoltage() throws IOException {
//		refreshReport();
//		return strReport.getMeasuredStringVoltage();
//	}
//	
//	public static String getStringCalculatedVoltage() throws IOException {
//		refreshReport();
//		return strReport.getCalculatedStringVoltage();
//	}
//
//	public String getStringSoc() throws IOException {
//		refreshReport();
//		return strReport.getStringSoc();
//	}
//	
//	public String getSunspecSoc() throws ModbusException {
//		Modbus802 cBatteryBaseModelBlockMaster;
//		cBatteryBaseModelBlockMaster = new Modbus802(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
//		return String.valueOf(cBatteryBaseModelBlockMaster.getSOC());
//	}
//	
//	public String getLastCallsSoc() throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
//		LastCalls lc = new LastCalls();
//		return lc.getSoc();
//	}
//	
//	private static boolean useStringCalculatedVoltage() {
//		boolean condition=true;
//		//check if any alarm in system. 
//		//If yes, use calculated 
//		if (condition)
//			return true;
//		else
//			return false;
//	}
//	
//	public String getSocDriftData() throws IOException, InterruptedException {
//		//Get initial turtle.log snapshot
//		mCommonHelper.getSocDataFromTurtleLog("soc1.txt");
//		Thread.sleep(2000);
//		File f1 = new File("/home/powin/soc1.txt");
//		//Insert action here
//		mCommonHelper.deleteExistingFile("/home/powin/socStringData.txt");
//		FileHelper fStringReport = new FileHelper("/home/powin/socStringData.txt");
//		fStringReport.writeToCSV("timestamp,cellVMax,cellVMin,cellVAvg,stringCurrent,stringPower,calculatedStringV,measuredStringV,stringSoc,socFromTurtleLog");
//		for (int idx = 1;idx < 30;idx++) {
//			Reports strReport = new Reports("1,1");
//			String getReportData = strReport.getStringReport();
//			String timeStamp = getReportData.split(",")[0];
//			String timeStampLocal=mCommonHelper.convertUtcToLocalDatetime(Long.valueOf(timeStamp));
//			String getReportDataLocal=getReportData.replace(timeStamp, timeStampLocal);
//			fStringReport.writeToCSV(getReportDataLocal);	
//			Thread.sleep(2000);
//		}
//		//Get final turtle.log snapshot
//		mCommonHelper.getSocDataFromTurtleLog("soc2.txt");
//		Thread.sleep(2000);
//		File f2 = new File("/home/powin/soc2.txt");
//		return mCommonHelper.getFileContentDifference(f1,f2);
//	}
//	
//	public String getSocDataFromTurtleLog(String file) throws IOException, InterruptedException {
//		mCommonHelper.getSocDataFromTurtleLog(file);
//		Thread.sleep(1000);
//		File f1 = new File(mCommonHelper.getLocalHome()+file);
//		Thread.sleep(1000);
//		return mCommonHelper.getFileContents(f1);
//	}
//	
//	public static void processTurtleData(String rawSocData) throws IOException {
//		String [] socData = rawSocData.split("\n");
//		Pattern newSocPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(New SOC is = )(.*)");
//		Pattern projectedSocPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(Projected SOC = )(.*)");
//		Pattern socCloseEnoughPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(SOC is close enough)(.*)");
//		Pattern timestampPattern=Pattern.compile("(.*)(\\[StringReportDequeuer\\])(.*)");;
//		mCommonHelper.deleteExistingFile("/home/powin/socTesting.txt");
//		FileHelper fStringReport = new FileHelper("/home/powin/socTesting.txt");
//		fStringReport.writeToCSV("timestamp,new soc,projected soc");
//
//		for(String logLine :socData) {	
//			//Get timestamp
//			String timestamp= "";
//			Matcher matcher = timestampPattern.matcher(logLine);
//			if (matcher.find()) {
//				timestamp= matcher.group(1);
//				timestamp=timestamp.trim();			
//			}
//			//Check if SOC is close enough
//			matcher = socCloseEnoughPattern.matcher(logLine);
//			if (matcher.find()) {
//				String closeEnoughMessage= matcher.group(4);
//				fStringReport.writeToCSV(timestamp+","+closeEnoughMessage);
//				continue;
//			}
//			//Get new and projected soc
//			String newSoc="";
//			String projectedSoc="";
//			matcher = newSocPattern.matcher(logLine);
//			if (matcher.find()) {
//				newSoc= matcher.group(5);		
//			}
//			else {
//				matcher = projectedSocPattern.matcher(logLine);
//				if (matcher.find()) {
//					projectedSoc= matcher.group(5);
//				}	
//			}
//			fStringReport.writeToCSV(timestamp+","+newSoc+","+projectedSoc);
//		}
//	}
//	
//	public void testSocCalculation() {
////		//Get measured string voltage
////		int numBatteryPacks=11;
////		int numCellGroupsPerBatteryPack = 24;
////		int measuredStringVoltage = Integer.parseInt(getStringMeasuredVoltage());
////		System.out.println("String measured voltage: "+measuredStringVoltage);
////		//Get calculated string voltage
////		int calculatedStringVoltage = Integer.parseInt(getStringCalculatedVoltage());
////		System.out.println("String calculated voltage: "+calculatedStringVoltage);
//////		Get average cell voltage from String voltage
////		double cellVoltAverage = 0;
////		if (useStringCalculatedVoltage() ){
////			cellVoltAverage = (double)calculatedStringVoltage/(numBatteryPacks*numCellGroupsPerBatteryPack);
////		}else {
////			cellVoltAverage = (double)calculatedStringVoltage/(numBatteryPacks*numCellGroupsPerBatteryPack);
////		}
////		System.out.println("Average cell voltage: "+String.valueOf(cellVoltAverage));
////		DecimalFormat df = new DecimalFormat("#.###");      
////		cellVoltAverage = Double.valueOf(df.format(cellVoltAverage));
////		System.out.println("Use String calculated voltage: "+useStringCalculatedVoltage());
////		System.out.println("Average cell voltage: "+String.valueOf(cellVoltAverage));
//		
//		//Get soc from soc-cell voltage table
//			//Open the file
//			//Read line by line
//				//Prefix timestamp
//				//Check if actual voltage is less than voltage in current line
//					//If yes, skip to next line
//					//If no, get the corresponding SOC and quit loop
//			//Close file
//	}
//	
//	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ModbusException, ParseException {	
//		socTesting socTest = new socTesting("1,1");
//		String rawSocData=socTest.getSocDriftData();
//		System.out.println(rawSocData);
//		processTurtleData(rawSocData);
//	}
//
//}