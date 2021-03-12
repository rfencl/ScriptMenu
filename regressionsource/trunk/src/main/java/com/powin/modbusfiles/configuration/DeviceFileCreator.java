package com.powin.modbusfiles.configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceFileCreator {

	protected <T> void createDeviceJsonFile(String filePath, T device) {
		ObjectMapper objectMapper = new ObjectMapper();
		String objectToJson = ".....";
		try {
			objectToJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device);
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

}
