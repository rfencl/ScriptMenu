package com.powin.modbusfiles;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.PowinProperty;


public class socTesting {
	private static Reports strReport ;
	private static String reportUrl ;

	public socTesting(String url) throws IOException {
		reportUrl=url;
		strReport= new Reports(url);
	}
	
	private static void refreshReport() throws IOException {
		strReport= new Reports(reportUrl);
	}

	public static String getStringTimestamp() throws IOException {
		refreshReport();
		return strReport.getStringTimestamp();
	}
	
	public static String getStringMeasuredVoltage() throws IOException {
		refreshReport();
		return strReport.getMeasuredStringVoltage();
	}
	
	public static String getStringCalculatedVoltage() throws IOException {
		refreshReport();
		return strReport.getCalculatedStringVoltage();
	}

	public String getStringSoc() throws IOException {
		refreshReport();
		return strReport.getStringSoc();
	}
	
	public String getSunspecSoc() throws ModbusException, IOException {
		Modbus802 cBatteryBaseModelBlockMaster;
		cBatteryBaseModelBlockMaster = new Modbus802(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
		return String.valueOf(cBatteryBaseModelBlockMaster.getSOC());
	}
	
	public String getLastCallsSoc() throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		LastCalls lc = new LastCalls();
		return lc.getSoc();
	}
	
	private static boolean useStringCalculatedVoltage() {
		boolean condition=true;
		//check if any alarm in system. 
		//If yes, use calculated 
		if (condition)
			return true;
		else
			return false;
	}
	
	public String getSocDriftData(String stringReportDatafilename) throws IOException, InterruptedException {	
		String localHomeDirectory = CommonHelper.getLocalHome();
		//Get initial turtle.log snapshot
		String initialTurtleSnapshot = "soc1.txt";
		getSocDataFromTurtleLog(initialTurtleSnapshot);
		Thread.sleep(2000);
		File initialTurtleSnapshotFile = new File(localHomeDirectory+initialTurtleSnapshot);
		//Testing commences. 
		String stringDataFile=localHomeDirectory+stringReportDatafilename ;
		CommonHelper.deleteExistingFile(stringDataFile);
		FileHelper fStringReport = new FileHelper(stringDataFile);
		fStringReport.writeToCSV("timestamp,cellVMax,cellVMin,cellVAvg,stringCurrent,stringPower,calculatedStringV,measuredStringV,stringSoc");
		for (int idx = 1;idx < 30;idx++) {
			refreshReport();
			String getReportData = strReport.getStringReport();
			//Convert the utc timestamp to local time
			String timeStamp = getReportData.split(",")[0];
			String timeStampLocal=CommonHelper.convertUtcToLocalDatetime(Long.valueOf(timeStamp));
			String getReportDataLocal=getReportData.replace(timeStamp, timeStampLocal);
			fStringReport.writeToCSV(getReportDataLocal);	
			Thread.sleep(2000);
		}
		//Testing ends
		//Get final turtle.log snapshot
		String finalTurtleSnapshot = "soc2.txt";
		getSocDataFromTurtleLog(finalTurtleSnapshot);
		Thread.sleep(2000);
		File finalTurtleSnapshotFile = new File(localHomeDirectory+finalTurtleSnapshot);
		//Get turtle data pertaining to the test duration
		return CommonHelper.getFileContentDifference(initialTurtleSnapshotFile,finalTurtleSnapshotFile);
	}
	
	public void getSocDataFromTurtleLog(String localFilename) throws IOException, InterruptedException {
		//Runs this command in turtle (localhost)
		//Get the Voltage drag reading from turtle
		//sudo tail -f /var/log/tomcat8/turtle.log | grep VoltageDragSocCalculator
		//sudo tail -f /var/log/tomcat8/turtle.log | grep 'VoltageDragSocCalculator.*Projected\|New.*'
		//2020-08-21 00:00:01.354 [StringReportDequeuer] DEBUG com.powin.okiku.voltagedrag.VoltageDragSocCalculator - mNoCurrent = true. Projected SOC = 0.6298484848484844
		//2020-08-21 00:00:01.354 [StringReportDequeuer] DEBUG com.powin.okiku.voltagedrag.VoltageDragSocCalculator - New SOC is = 0.61
		//Projected is what the SOC should be according to the stack voltage.
		//New SOC is what the SOC is currently at.
		//Change log level of VoltageDragSocCalculator to debug in ./var/lib/tomcat/webapps/turtle/WEB-INF/classes/log4j2.xml
		//In configuration.json
				//batteryIdentifier" : "e-trust-40Ah"
				//batteryIdentifier" : "catl-271Ah"
				//"useOkikuForEnergy" : true,
		//After charging is done, track the new and projected soc in turtle.log
		//When projected and new are very close, you will see "SOC is close enough"
		//The time for this drift is configurable
		//To change the drift rampup time before it goes to a full 1% drift, please add  the following to turtle.xml:
		//<Parameter name="socNoCurrentWaitToDriftMillis" value="900000" />
		String turtleDataFilename="soc.txt";
		String turtleDataFilePath= CommonHelper.getLocalHome()+turtleDataFilename;
		String command ="sudo ";
		command  +=		"grep 'VoltageDragSocCalculator.*Projected\\|New\\|SOC is close enough.*' /var/log/tomcat8/turtle.log ";
		command  +=		 "> "+turtleDataFilePath;
		CommonHelper.runScriptRemotelyOnTurtle(command);
		Thread.sleep(1000);
		//moveTurtleLogToLocalHome.sh
		CommonHelper.copyFileFromTurtleHomeToLocalHome(turtleDataFilename,localFilename);
	}
	
	public static void processTurtleData(String rawSocData, String turtleSocDataFilename) throws IOException {		
		Pattern newSocPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(New SOC is = )(.*)");
		Pattern projectedSocPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(Projected SOC = )(.*)");
		Pattern socCloseEnoughPattern = Pattern.compile("(.*)([StringReportDequeuer])(.*)(SOC is close enough)(.*)");
		Pattern timestampPattern=Pattern.compile("(.*)(\\[StringReportDequeuer\\])(.*)");;
		//File to store data from turtle
		String turtleDataFilePath = CommonHelper.getLocalHome()+turtleSocDataFilename ;
		CommonHelper.deleteExistingFile(turtleDataFilePath);
		FileHelper turtleDataFile = new FileHelper(turtleDataFilePath);
		turtleDataFile.writeToCSV("timestamp,new soc,projected soc");
		
		String [] socData = rawSocData.split("\n");
		for(String logLine :socData) {	
			//Get timestamp
			String timestamp= "";
			Matcher matcher = timestampPattern.matcher(logLine);
			if (matcher.find()) {
				timestamp= matcher.group(1);
				timestamp=timestamp.trim();			
			}
			//Check if SOC is close enough
			matcher = socCloseEnoughPattern.matcher(logLine);
			if (matcher.find()) {
				String closeEnoughMessage= matcher.group(4);
				turtleDataFile.writeToCSV(timestamp+","+closeEnoughMessage);
				continue;
			}
			//Get new and projected soc
			String newSoc="";
			String projectedSoc="";
			matcher = newSocPattern.matcher(logLine);
			if (matcher.find()) {
				newSoc= matcher.group(5);		
			}
			else {
				matcher = projectedSocPattern.matcher(logLine);
				if (matcher.find()) {
					projectedSoc= matcher.group(5);
				}	
			}
			turtleDataFile.writeToCSV(timestamp+","+newSoc+","+projectedSoc);
		}
	}
	
	public File stitchFiles(File turtleData, File stringData,File finalFile) throws IOException, ParseException, java.text.ParseException {
		Scanner input1 = new Scanner(turtleData);// read first file
		Scanner input2 = new Scanner(stringData);// read second file
		String f1Line="";
		String f2Line="";
		String f1Ts="";
		String f2Ts="";
		String prevF2Ts="";
		String prevF2Line="";
		CommonHelper.deleteExistingFile(finalFile.getPath());
		FileHelper f3 = new FileHelper(finalFile.getPath());
		String header ="timestamp,new soc,projected soc,"+
		"timestamp,cellVMax,cellVMin,cellVAvg,stringCurrent,stringPower,calculatedStringV,measuredStringV,stringSoc";
		f3.writeToCSV(header);
		//Skip the first few lines of the first file which are out of range of the second file
		input1.nextLine();//skip header
		input2.nextLine();//skip header
		f2Line = input2.nextLine();
		f2Ts=f2Line.split(",")[0];
		f1Line = input1.nextLine();
		f1Ts=f1Line.split(",")[0];
		boolean found =false;
		if(CommonHelper.compareDates(f1Ts, f2Ts)<0) {	
			while (input1.hasNextLine() && !found) {
				if(CommonHelper.compareDates(f1Ts, f2Ts)>=0) {
					found =true;
				}
				f1Line = input1.nextLine();
				f1Ts=f1Line.split(",")[0];
			}
		}
		//File 1 is now cued up within range of second file
		//Starting with the cued up line of the first file, look for a line in File 2 which is greater than it
		
		input2 = new Scanner(stringData);
		f2Line = input2.nextLine();
		while (input1.hasNextLine()) {		
			found =false;
			while (input2.hasNextLine() && !found) {
				f2Line = input2.nextLine();
				f2Ts=f2Line.split(",")[0];			
				if(CommonHelper.compareDates(f1Ts, f2Ts)<=0) {
					found =true;
					break;
				}
				prevF2Ts=f2Ts;//keep track of the previous line 
				prevF2Line=f2Line;
			}
			//Check which of its neighbors it needs to pick up
			switch(CommonHelper.getClosestDate(prevF2Ts,f2Ts,f1Ts)) {
			case -1:
				f3.writeToCSV(f1Line +","+prevF2Line);
				break;
			case 1:
				f3.writeToCSV(f1Line +","+f2Line);
				break;
			case 0:
				f3.writeToCSV(f1Line +","+prevF2Line);
				break;			
			}
			//Reset second file to first data row
			input2 = new Scanner(stringData);
			f2Line = input2.nextLine();
			//Move the first file a line down
			f1Line = input1.nextLine();
			f1Ts=f1Line.split(",")[0];
		}
		input1.close();
		input2.close();
		return finalFile;
	}
	
public File consolidateFile(File f2, File consolidatedFile) throws IOException, ParseException, java.text.ParseException {
		
		Scanner input1 = new Scanner(f2);// read first file		
		String firstLine="";
		String secondLine="";
		String firstTs="";
		String secondTs="";
		
		CommonHelper.deleteExistingFile(consolidatedFile.getPath());
		FileHelper consolidatedFileHelper = new FileHelper(consolidatedFile.getPath());
		firstLine = input1.nextLine();
		while (input1.hasNextLine()){
			firstLine = input1.nextLine();
			firstTs=firstLine.split(",")[0];
			if(!input1.hasNextLine()) {
				System.out.println("Reached end of file");
				break;
			}
			secondLine = input1.nextLine();
			if(secondLine.contains("SOC is close enough")) { //Print the "SOC is close enough" line as-is and get the next line to complete the new-projected pair of lines
				consolidatedFileHelper.writeToCSV(secondLine+",na") ;
				secondLine = input1.nextLine();
			}
			secondTs=secondLine.split(",")[0];
			if( CommonHelper.compareDates(firstTs, secondTs) == 0) {
				consolidatedFileHelper.writeToCSV(mergeLines(firstLine, secondLine) );
			}		
		}
		input1.close();
		return consolidatedFile;
	}
	
	private static String mergeLines(String line1, String line2) {
		String [] arrLine1=line1.split(",");
		String [] arrLine2=line2.split(",");		
		if(arrLine2.length > arrLine2.length) {
			return String.join(",",arrLine1[0],arrLine1[1],arrLine2[2]);
		}else {
			return String.join(",",arrLine2[0],arrLine2[1],arrLine1[2]);
		}		
	}
	
	public File getCombinedTurtleAndStringReportData() throws IOException, InterruptedException, ParseException, java.text.ParseException {
		String stringReportDataFilename="stringSocData.txt" ;
		String turtleSocDataFilename="turtleSocData.txt";
		File turtleSocDataFile = new File("/home/powin/turtleSocData.txt");
		File consolidatedTurtleSocDataFile = new File("/home/powin/consolidatedTurtleSocData.txt");
		File stringReportDataFile = new File("/home/powin/stringSocData.txt");
		File finalReport = new File("/home/powin/socFullReport.csv");
		String rawSocData=getSocDriftData(stringReportDataFilename);
		processTurtleData(rawSocData,turtleSocDataFilename);	
		turtleSocDataFile=consolidateFile(turtleSocDataFile,consolidatedTurtleSocDataFile);
		stitchFiles(consolidatedTurtleSocDataFile,stringReportDataFile,finalReport);
		return finalReport;
	}
	
	public void testSocCalculation() {
//		//Get measured string voltage
//		int numBatteryPacks=11;
//		int numCellGroupsPerBatteryPack = 24;
//		int measuredStringVoltage = Integer.parseInt(getStringMeasuredVoltage());
//		System.out.println("String measured voltage: "+measuredStringVoltage);
//		//Get calculated string voltage
//		int calculatedStringVoltage = Integer.parseInt(getStringCalculatedVoltage());
//		System.out.println("String calculated voltage: "+calculatedStringVoltage);
////		Get average cell voltage from String voltage
//		double cellVoltAverage = 0;
//		if (useStringCalculatedVoltage() ){
//			cellVoltAverage = (double)calculatedStringVoltage/(numBatteryPacks*numCellGroupsPerBatteryPack);
//		}else {
//			cellVoltAverage = (double)calculatedStringVoltage/(numBatteryPacks*numCellGroupsPerBatteryPack);
//		}
//		System.out.println("Average cell voltage: "+String.valueOf(cellVoltAverage));
//		DecimalFormat df = new DecimalFormat("#.###");      
//		cellVoltAverage = Double.valueOf(df.format(cellVoltAverage));
//		System.out.println("Use String calculated voltage: "+useStringCalculatedVoltage());
//		System.out.println("Average cell voltage: "+String.valueOf(cellVoltAverage));
		
		//Get soc from soc-cell voltage table
			//Open the file
			//Read line by line
				//Prefix timestamp
				//Check if actual voltage is less than voltage in current line
					//If yes, skip to next line
					//If no, get the corresponding SOC and quit loop
			//Close file
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, ModbusException, ParseException, java.text.ParseException {	
		socTesting socTest = new socTesting("1,1");
		socTest.getCombinedTurtleAndStringReportData() ;
	}

}