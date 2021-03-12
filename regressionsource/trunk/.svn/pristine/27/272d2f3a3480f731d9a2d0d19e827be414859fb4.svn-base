package com.powin.modbusfiles.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DevicePcsSimulatorConfigFileCreator   extends DeviceFileCreator {

	public void CreateConfigurationFile(String filePath, int arrayIndex, int pcsIndex, String defaultGridMode,
			boolean defaultWattFrequencyEnabled, int maxAllowedkVAr, int maxAllowedkW, int minAllowedkVAr,
			int minAllowedkW, int nameplateA, int nameplatekVAr, int nameplatekW, boolean enabled, int stopPriority,
			int startPriority) {
		DevicePcsSimulator pcsConfigFile= new DevicePcsSimulator( arrayIndex,  pcsIndex,  defaultGridMode,
				 defaultWattFrequencyEnabled,  maxAllowedkVAr,  maxAllowedkW,  minAllowedkVAr,
				 minAllowedkW,  nameplateA,  nameplatekVAr,  nameplatekW,  enabled,  stopPriority,
				 startPriority) ;
		createDeviceJsonFile(filePath, pcsConfigFile);
	}
	
	public void createPcsSimulatorConfigFiles(String filePath, int arrayCount, String defaultGridMode,
			boolean defaultWattFrequencyEnabled, int maxAllowedkVAr, int maxAllowedkW, int minAllowedkVAr,
			int minAllowedkW, int nameplateA, int nameplatekVAr, int nameplatekW, boolean enabled, int stopPriority,
			int startPriority) {
			for (int arrIndex=1;arrIndex<arrayCount+1;arrIndex++) {
				CreateConfigurationFile(filePath+"device-"+String.valueOf(startPriority-1+arrIndex)+"-PcsSimulator.json", arrIndex,arrIndex,defaultGridMode,
						defaultWattFrequencyEnabled,maxAllowedkVAr,maxAllowedkW,minAllowedkVAr,minAllowedkW,nameplateA,
						nameplatekVAr,nameplatekW,enabled,20+arrIndex-1,20+arrIndex-1);
			}	
	}

	public static void main(String[] args) {
		DevicePcsSimulatorConfigFileCreator mCreator = new DevicePcsSimulatorConfigFileCreator();

		mCreator.createPcsSimulatorConfigFiles("/home/powin/", 4,"GRID_FOLLOWING",
				false,450,450,-450,-450,650,450,450,true,20,20);


	}
}
