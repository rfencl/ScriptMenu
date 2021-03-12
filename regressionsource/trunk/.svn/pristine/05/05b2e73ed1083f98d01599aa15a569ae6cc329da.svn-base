package com.powin.modbusfiles.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationFileCreator {
	private boolean cDebug;//Code Review (MB): What is this for?

	public boolean isDebug() {
		return cDebug;
	}

	public void setDebug(boolean debug) {
		cDebug = debug;
	}

	public ConfigurationFileCreator() {
	}

	public void CreateConfigurationFile(String filePath, List<ConfigurationParameters> parameters) {
		TurtleConfiguration turtleConfig = new TurtleConfiguration();
		turtleConfig.setBlockConfiguration(new BlockConfiguration());
		turtleConfig.getBlockConfiguration().init(parameters.get(0).getStationName(),parameters.get(0).getStackType());

		List<ArrayConfiguration> arrayConfigList = new ArrayList<ArrayConfiguration>();

		for (ConfigurationParameters param : parameters) {
			ArrayConfiguration arrayConfig = new ArrayConfiguration();
			arrayConfig.init(param.getStackType(),param.getStringCount(), param.getContainerIndex(), param.getArrayCount(),
					param.getArrayStartIndex());
			arrayConfigList.add(arrayConfig);
		}

		turtleConfig.getBlockConfiguration().setArrayConfigurations(arrayConfigList);

		ObjectMapper objectMapper = new ObjectMapper();
		String objectToJson = ".....";
		try {
			objectToJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(turtleConfig);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(objectToJson);

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(objectToJson + "\r\n");
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CreateConfigurationFile(String filePath,String stationName,StackType stackType, int stringCount, int arrayCount) {
		setDebug(true);
		List<ConfigurationParameters> parameters = new ArrayList<ConfigurationParameters>();
		ConfigurationParameters param1 = new ConfigurationParameters(stationName,stackType, stringCount, 2, arrayCount, 1);
		parameters.add(param1);
		CreateConfigurationFile(filePath+"configuration.json", parameters);
	}

	public static void main(String[] args) {
		ConfigurationFileCreator mCreator = new ConfigurationFileCreator();
//		mCreator.setDebug(true);
//
//		List<ConfigurationParameters> parameters = new ArrayList<ConfigurationParameters>();
//		ConfigurationParameters param1 = new ConfigurationParameters(StackType.S11_114_120_0817_E40_derate01, 4, 2, 4, 1);
//		//ConfigurationParameters param2 = new ConfigurationParameters(StackType.S11_114_120_0817_E40_derate01, 5, 6, 7, 8);
//		parameters.add(param1);
//		//parameters.add(param2);
//		mCreator.CreateConfigurationFile("/home/powin/testConfigFile.json", parameters);
		
		mCreator.CreateConfigurationFile("/home/powin/","mb021621",StackType.STACK_225_GEN22, 3,3);

	}
}
