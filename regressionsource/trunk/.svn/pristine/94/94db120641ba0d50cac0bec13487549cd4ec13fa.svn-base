package com.powin.modbusfiles.awe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.JSchException;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.stringpusher.app.NotificationSend;

public class ZeroConfigTurtleToString {
	private static StackType cStackType;
	private static String cStationCode;
	private static int cArrayCount;
	private static int cStringCount;
	private static String cConfigFileLocation = "/etc/powin/qaqc/configlog/stationCode/SetSafetyAndNotificationConfiguration/";
	private final static Logger LOG = LogManager.getLogger(ZeroConfigTurtleToString.class.getName());

	public ZeroConfigTurtleToString(StackType stackType, String stationCode,int arrayCount,int stringCount) throws IOException, InterruptedException, JSchException {
		cStackType		=	stackType	;
		if(stationCode.length()!=0) {
			cStationCode	=	stationCode	;
		}
		else {
			cStationCode = SystemInfo.getStationCode();
		}
		cArrayCount		=	arrayCount	;
		cStringCount	=	stringCount	;
		init();
	}

	public void init() throws IOException, InterruptedException, JSchException {
		cConfigFileLocation=cConfigFileLocation.replace("stationCode", cStationCode+"-1");
	}
	
	public void resetConfiguration() throws IOException, InterruptedException, JSchException {
		
	}
	
	private void deleteOldConfigFiles() throws IOException {
		String command = "sudo rm -rf /etc/powin/qaqc/configlog*";
		CommonHelper.runScriptRemotelyOnTurtle(command);
	}
	
	private void getNewConfigFiles() throws IOException, InterruptedException, JSchException {
		CommonHelper.restartTomcat();
	}
	
	private StackType getSystemStackType() {
		StackType stackType = StackType.STACK_225_GEN22;
		return stackType;
	}
	
	public boolean validateConfigFiles( ) throws IOException, InterruptedException {
		//Get the expected file contents
		File expectedFile = CommonHelper.getFileFromResources(cStackType.safetyAndNotificationConfigurationFile);
		String expectedFileContents=CommonHelper.getFileContents(expectedFile);
		//Get list of files expected to be generated
		String localFolderForConfigFiles = CommonHelper.createDynamicFolder("configTest","/home/powin/") ;
		localFolderForConfigFiles=CommonHelper.stringSuffix(localFolderForConfigFiles, "/");
		ArrayList<String> expectedFileNameList = new ArrayList();
		for (int arrCount =1 ; arrCount<=cArrayCount;arrCount++) {
			for (int strCount =1 ; strCount<=cStringCount;strCount++) {
				String filename = arrCount+"-"+strCount+" - SetSafetyAndNotificationConfiguration.csv";
				expectedFileNameList.add(filename);
			}
		}
		Collections.sort(expectedFileNameList);
		String expectedFileNameListAsString =CommonHelper.convertArrayListToString(expectedFileNameList) ;
		//Copy files from turtle location to local
		String command = "sudo chmod -R 777 "+localFolderForConfigFiles;
		CommonHelper.callScriptSudo("powin",command);
		CommonHelper.runScriptRemotelyOnTurtle("sudo chmod -R 777 /etc/powin/qaqc/");
		CommonHelper.copyFileFromTurtleToLocal(cConfigFileLocation ,localFolderForConfigFiles);
		//Get list of files actually generated
		ArrayList<String> actualFileNameList = new ArrayList<>();
		boolean fileContentMatch=true;
		for (File generatedFile: new File(localFolderForConfigFiles+"SetSafetyAndNotificationConfiguration/").listFiles()){
			actualFileNameList.add(generatedFile.getName());
			fileContentMatch &= CommonHelper.compareTwoFiles(expectedFile.getAbsolutePath(), generatedFile.getAbsolutePath());
		}
		Collections.sort(actualFileNameList);
		String actualFileNameListAsString =CommonHelper.convertArrayListToString(actualFileNameList) ;
		boolean fileNamesMatch = actualFileNameListAsString.contentEquals(expectedFileNameListAsString);
		System.out.println("All file names match: "+fileNamesMatch);
		//Verify file contents match expected file contents
		System.out.println("All file contents match: "+fileContentMatch);
		//Delete local folder
		//CommonHelper.deleteFolderContents2(localFolderForConfigFiles);
		return fileNamesMatch & fileContentMatch ;
	}
	
	public boolean workFlow() throws IOException, InterruptedException, JSchException {
		deleteOldConfigFiles();
//		resetConfiguration();
		getNewConfigFiles() ;
		return validateConfigFiles( );
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, JSchException{
		ZeroConfigTurtleToString mZeroConfig = new ZeroConfigTurtleToString(StackType.STACK_225_GEN22, "MBTEST55225b", 5, 5);
		mZeroConfig.validateConfigFiles();
//		mZeroConfig.resetConfiguration();
//		mZeroConfig.workFlow();
		
	}
}
