package com.powin.modbusfiles.stackoperations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbusfiles.utilities.PowinProperty;

public class Contactors {
	
	final static Logger LOG = LogManager.getLogger(Contactors.class.getName());

	public static String getCloseContactorsURL(int arrayIndex, int stringIndex) {
		return PowinProperty.TURTLE_URL + String.join("/", "turtle", "qaqc", String.valueOf(arrayIndex),
				String.valueOf(stringIndex), "contactors", "close");
	}
	public static String getOpenContactorsURL(int arrayIndex, int stringIndex) {
		return PowinProperty.TURTLE_URL + String.join("/", "turtle", "qaqc", String.valueOf(arrayIndex),
				String.valueOf(stringIndex), "contactors", "open");
	}
	public static void closeContactors(String arrayIndex, String stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		Contactors.closeContactors(Integer.valueOf(arrayIndex), Integer.valueOf(stringIndex));
	}
	public static void closeContactors(int arrayIndex, int stringIndex)	 {
		Balancing.LOG.info(String.format("Closing Contactors for string %s", stringIndex));
		String closeContactorsUrl = getCloseContactorsURL(arrayIndex, stringIndex);
		Balancing.executeCommand(closeContactorsUrl);
	}
	public static void openContactors(String arrayIndex, String stringIndex){
		Contactors.openContactors(Integer.valueOf(arrayIndex), Integer.valueOf(stringIndex));
	}
	public static void openContactors(int arrayIndex, int stringIndex){
		Balancing.LOG.info(String.format("Opening Contactors for string %s", stringIndex));
		String openContactorsUrl = getOpenContactorsURL(arrayIndex, stringIndex);
		Balancing.executeCommand(openContactorsUrl);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
