package com.powin.modbusfiles.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceAcBatteryBlockConfigFileCreator extends DeviceFileCreator {
	public void CreateConfigurationFile(String filePath, List<Integer> arrayIndexes, boolean distributePowerEvenly,boolean enabled,  int stopPriority,int startPriority) {
		DeviceAcBatteryBlock acBatteryBlockConfigFile=new DeviceAcBatteryBlock( arrayIndexes,  distributePowerEvenly,enabled,   stopPriority, startPriority) ;
		createDeviceJsonFile(filePath, acBatteryBlockConfigFile);
	}
	
	public void createAcBatteryBlockConfigFiles(String filePath, int arrayCount, boolean distributePowerEvenly,boolean enabled,  int stopPriority,int startPriority) {
		List<Integer> arrayIndexes = new ArrayList<Integer>();
		for (int arrIndex=1;arrIndex<arrayCount+1;arrIndex++) {
			arrayIndexes.add(arrIndex);	
		}
		CreateConfigurationFile( filePath+"device-60-AcBatteryBlock.json",  arrayIndexes,  distributePowerEvenly, enabled,   stopPriority, startPriority);
	}

	public static void main(String[] args) {
		DeviceAcBatteryBlockConfigFileCreator mCreator = new DeviceAcBatteryBlockConfigFileCreator();
		mCreator.createAcBatteryBlockConfigFiles("/home/powin/", 4,true,true,60,60);
	}
}
