package com.powin.modbusfiles.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DevicePhoenixDcBatteryConfigFileCreator extends DeviceFileCreator {
	public void CreateConfigurationFile(String filePath, int arrayIndex, int commsTimeout, boolean enabled,  int stopPriority,int startPriority) {
		DevicePhoenixDcBattery phoenixDcBatteryConfigFile = new DevicePhoenixDcBattery( arrayIndex,  commsTimeout,  enabled,   stopPriority, startPriority) ;
		createDeviceJsonFile(filePath, phoenixDcBatteryConfigFile);
	}
	
	public void createPhoenixDcBatteryConfigFiles(String filePath, int arrayCount, int commsTimeout,boolean enabled,  int stopPriority,int startPriority) {
		for (int arrIndex=1;arrIndex<arrayCount+1;arrIndex++) {
			CreateConfigurationFile( filePath+"device-"+String.valueOf(startPriority-1+arrIndex)+"-PhoenixDcBattery.json",  arrIndex,  commsTimeout, enabled,   stopPriority-1+arrIndex, startPriority-1+arrIndex);
		}	
	}

	public static void main(String[] args) {
		DevicePhoenixDcBatteryConfigFileCreator mCreator = new DevicePhoenixDcBatteryConfigFileCreator();
		mCreator.createPhoenixDcBatteryConfigFiles("/home/powin/", 4,10000,true,10,10);
	}
}
