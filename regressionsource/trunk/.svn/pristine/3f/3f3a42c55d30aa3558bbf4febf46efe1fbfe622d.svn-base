package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class DeviceAcBatteryConfigFileCreator extends DeviceFileCreator {
	public void CreateConfigurationFile(String filePath, int arrayIndex, List<Integer> pcsIndexes, boolean enabled,  int stopPriority,int startPriority) {
		DeviceAcBattery AcBatteryConfigFile=new DeviceAcBattery( arrayIndex, pcsIndexes,  enabled,   stopPriority, startPriority) ;
		createDeviceJsonFile(filePath, AcBatteryConfigFile);
	}

	public void createAcBatteryConfigFiles(String filePath, int arrayCount,boolean enabled,  int stopPriority,int startPriority) {
		for (int arrIndex=1;arrIndex<arrayCount+1;arrIndex++) {
			List<Integer> pcsIndexes = new ArrayList<Integer>();
			pcsIndexes.add(arrIndex);	
			String filename = filePath+"device-"+String.valueOf(startPriority-1+arrIndex)+"-AcBattery.json";
			CreateConfigurationFile(filename, arrIndex,pcsIndexes,enabled,startPriority-1+arrIndex,startPriority-1+arrIndex);
		}
	}

	public static void main(String[] args) {
		DeviceAcBatteryConfigFileCreator mCreator = new DeviceAcBatteryConfigFileCreator();
		mCreator.createAcBatteryConfigFiles("/home/powin/", 4,true,50,50);
	}
}
