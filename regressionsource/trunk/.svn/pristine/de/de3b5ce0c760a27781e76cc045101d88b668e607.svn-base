package com.powin.modbusfiles.derating;

import java.io.IOException;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.CommandHelper;
import com.powin.modbusfiles.utilities.CommonHelper;

public class WarnStopOriginal {

	private static String cStationCode;
	private static int cArrayIndex;
	private static int cStringIndex;
	// These values are from the recommended settings document created by Dylan Vance:
	// https://powin365.sharepoint.com/Project%20Success/Forms/AllItems.aspx?FolderCTID=0x012000145D0C9521F3D0459B3D7A629243BE33&View=%7B5ED3F878%2D3088%2D433A%2DA325%2D287DD1CDF95F%7D&InitialTabId=Ribbon%2ERead&VisibilityContext=WSSTabPersistence&CT=1584566630014&OR=OWA%2DNT&CID=b10ae0ab%2Df606%2Dac45%2D65a4%2Dd1762957860a&id=%2FProject%20Success%2FPowin%20Energy%20Client%20Documentation%20Suite%2FProduct%20Client%20Documentation%2FProduct%20Guides%2FPowin%20Default%20System%20Configurations%20V1%2E0%2Epdf&parent=%2FProject%20Success%2FPowin%20Energy%20Client%20Documentation%20Suite%2FProduct%20Client%20Documentation%2FProduct%20Guides
	private static final int CELL_HIGH_VOLTAGE_WARNING_SET = 3600;
	private static final int CELL_HIGH_VOLTAGE_WARNING_CLEAR = 3525;
	private static final int CELL_LOW_VOLTAGE_WARNING_SET = 2800;
	private static final int CELL_LOW_VOLTAGE_WARNING_CLEAR = 2900;
	private static final int CELL_HIGH_VOLTAGE_DELTA_WARNING_SET = 800;
	private static final int CELL_HIGH_VOLTAGE_DELTA_WARNING_CLEAR = 600;
	private static final int CELL_HIGH_TEMPERATURE_WARNING_SET = 42;
	private static final int CELL_HIGH_TEMPERATURE_WARNING_CLEAR = 37;
	private static final int CELL_LOW_TEMPERATURE_WARNING_SET = 9;
	private static final int CELL_LOW_TEMPERATURE_WARNING_CLEAR = 14;
	private static final int STRING_HIGH_VOLTAGE_WARNING_SET = 953;
	private static final int STRING_HIGH_VOLTAGE_WARNING_CLEAR = 943;
	private static final int STRING_LOW_VOLTAGE_WARNING_SET = 670;
	private static final int STRING_LOW_VOLTAGE_WARNING_CLEAR = 680;

	private static final int CELL_HIGH_VOLTAGE_ALARM_SET = 3650;// Notification code: 1001
	private static final int CELL_HIGH_VOLTAGE_ALARM_CLEAR = 3600;
	private static final int CELL_LOW_VOLTAGE_ALARM_SET = 2500;// Notification code: 1004
	private static final int CELL_LOW_VOLTAGE_ALARM_CLEAR = 2800;
	private static final int CELL_HIGH_VOLTAGE_DELTA_ALARM_SET = 1000;// Notification code: 1007
	private static final int CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR = 800;
	private static final int CELL_HIGH_TEMPERATURE_ALARM_SET = 47;// Notification code: 1010
	private static final int CELL_HIGH_TEMPERATURE_ALARM_CLEAR = 42;
	private static final int CELL_LOW_TEMPERATURE_ALARM_SET = 4;// Notification code: 1014
	private static final int CELL_LOW_TEMPERATURE_ALARM_CLEAR = 9;
	private static final int STRING_HIGH_VOLTAGE_ALARM_SET = 963;// Notification code: 1003
	private static final int STRING_HIGH_VOLTAGE_ALARM_CLEAR = 953;
	private static final int STRING_LOW_VOLTAGE_ALARM_SET = 660;// Notification code: 1006
	private static final int STRING_LOW_VOLTAGE_ALARM_CLEAR = 670;

	public WarnStopOriginal(String stationCode, int arrayIndex, int stringIndex) {
		cStationCode = stationCode;
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
	}

	private static void enableWarnStop() throws IOException, InterruptedException, JSchException {
		// TO DO: move file to resources folder and get the variables from default properties
		String remote = "/home/powin/";
		String local = "/home/maheshb/";
		String fileName = "editConfigurationJson.sh";

		CommonHelper.copyLocalFiletoRemoteLocation(local, remote, fileName);
		CommonHelper.editConfigurationJson("editConfigurationJson.sh", "useWarnStop", "true","/etc/powin/configuration.json");
	}
	// TO DO
	// private static void disableWarnStop() throws IOException, InterruptedException, JSchException {
	// // TO DO: move file to resources folder and get the variables from default properties
	// String remote = "/home/powin/";
	// String local = "/home/maheshb/";
	// String fileName = "editConfigurationJson.sh";
	//
	// mCommonUtils.copyLocalFiletoRemoteLocation(local, remote, fileName);
	// mCommonUtils.editConfigurationJson("editConfigurationJson.sh", "useWarnStop", "/etc/powin/configuration.json");
	// }

	public void setWarning(String awType, int set, int clear) throws IOException {
		// CommandHelper mCommands = new CommandHelper("", cStationCode, cArrayIndex, cStringIndex);
		// switch (awType) {
		// case "CELL_HIGH_VOLTAGE_WARNING":
		// mCommands.setHighCellGroupVoltageWarning(set, clear);
		// break;
		// case "CELL_LOW_VOLTAGE_WARNING":
		// mCommands.setLowCellGroupVoltageWarning(set, clear);
		// break;
		// case "CELL_HIGH_VOLTAGE_DELTA_WARNING":
		// mCommands.setHighCellGroupVoltageDeltaWarning(set, clear);
		// break;
		// case "CELL_HIGH_TEMPERATURE_WARNING":
		// mCommands.setBatteryPackHighTemperatureWarning(set, clear);
		// break;
		// case "CELL_LOW_TEMPERATURE_WARNING":
		// mCommands.setBatteryPackLowTemperatureWarning(set, clear);
		// break;
		// case "STRING_HIGH_VOLTAGE_WARNING":
		// mCommands.setStringHighVoltageWarning(set, clear);
		// break;
		// case "STRING_LOW_VOLTAGE_WARNING":
		// mCommands.setStringHighVoltageWarning(set, clear);
		// break;
		// }
	}

	public void resetWarning(String awType) throws IOException, InterruptedException {
		// CommandHelper mCommands = new CommandHelper("", cStationCode, cArrayIndex, cStringIndex);
		// switch (awType) {
		// case "CELL_HIGH_VOLTAGE_WARNING":
		// mCommands.setHighCellGroupVoltageWarning(CELL_HIGH_VOLTAGE_WARNING_SET, CELL_HIGH_VOLTAGE_WARNING_CLEAR);
		// break;
		// case "CELL_LOW_VOLTAGE_WARNING":
		// mCommands.setLowCellGroupVoltageWarning(CELL_LOW_VOLTAGE_WARNING_SET, CELL_LOW_VOLTAGE_WARNING_CLEAR);
		// break;
		// case "CELL_HIGH_VOLTAGE_DELTA_WARNING":
		// mCommands.setHighCellGroupVoltageDeltaWarning(CELL_HIGH_VOLTAGE_DELTA_WARNING_SET, CELL_HIGH_VOLTAGE_DELTA_WARNING_CLEAR);
		// break;
		// case "CELL_HIGH_TEMPERATURE_WARNING":
		// mCommands.setBatteryPackHighTemperatureWarning(CELL_HIGH_TEMPERATURE_WARNING_SET, CELL_HIGH_TEMPERATURE_WARNING_CLEAR);
		// break;
		// case "CELL_LOW_TEMPERATURE_WARNING":
		// mCommands.setBatteryPackLowTemperatureWarning(CELL_LOW_TEMPERATURE_WARNING_SET, CELL_LOW_TEMPERATURE_WARNING_CLEAR);
		// break;
		// case "STRING_HIGH_VOLTAGE_WARNING":
		// mCommands.setStringHighVoltageWarning(STRING_HIGH_VOLTAGE_WARNING_SET, STRING_HIGH_VOLTAGE_WARNING_CLEAR);
		// break;
		// case "STRING_LOW_VOLTAGE_WARNING":
		// mCommands.setStringHighVoltageWarning(STRING_LOW_VOLTAGE_WARNING_SET, STRING_LOW_VOLTAGE_WARNING_CLEAR);
		// break;
		// }
		Thread.sleep(5000);
	}

	public void resetAllWarnings() throws IOException, InterruptedException {
		resetWarning("CELL_HIGH_VOLTAGE_WARNING");
		resetWarning("CELL_LOW_VOLTAGE_WARNING");
		resetWarning("CELL_HIGH_VOLTAGE_DELTA_WARNING");
		resetWarning("CELL_HIGH_TEMPERATURE_WARNING");
		resetWarning("CELL_LOW_TEMPERATURE_WARNING");
		resetWarning("STRING_HIGH_VOLTAGE_WARNING");
		resetWarning("STRING_LOW_VOLTAGE_WARNING");
	}

	public void disableWarning(String awType) throws IOException, InterruptedException {
		// CommandHelper mCommands = new CommandHelper("", cStationCode, cArrayIndex, cStringIndex);
		// switch (awType) {
		// case "CELL_HIGH_VOLTAGE_WARNING":
		// mCommands.disableHighCellGroupVoltageWarning();
		// break;
		// case "CELL_LOW_VOLTAGE_WARNING":
		// mCommands.disableLowCellGroupVoltageWarning();
		// break;
		// case "CELL_HIGH_VOLTAGE_DELTA_WARNING":
		// mCommands.disableHighCellGroupVoltageDeltaWarning();
		// break;
		// case "CELL_HIGH_TEMPERATURE_WARNING":
		// mCommands.disableBatteryPackHighTemperatureWarning();
		// break;
		// case "CELL_LOW_TEMPERATURE_WARNING":
		// mCommands.disableBatteryPackLowTemperatureWarning();
		// break;
		// case "STRING_HIGH_VOLTAGE_WARNING":
		// mCommands.disableStringHighVoltageWarning();
		// break;
		// case "STRING_LOW_VOLTAGE_WARNING":
		// mCommands.disableStringHighVoltageWarning();
		// break;
		// }
		// Thread.sleep(5000);
	}

	public void disableAllWarnings() throws IOException, InterruptedException {
		disableWarning("CELL_HIGH_VOLTAGE_WARNING");
		disableWarning("CELL_LOW_VOLTAGE_WARNING");
		disableWarning("CELL_HIGH_VOLTAGE_DELTA_WARNING");
		disableWarning("CELL_HIGH_TEMPERATURE_WARNING");
		disableWarning("CELL_LOW_TEMPERATURE_WARNING");
		disableWarning("STRING_HIGH_VOLTAGE_WARNING");
		disableWarning("STRING_LOW_VOLTAGE_WARNING");
	}

	public boolean validateWarnStop(String awType, int set, int clear) throws IOException {
		CommandHelper mCommands = new CommandHelper("", cStationCode, cArrayIndex, cStringIndex);
		boolean status = false;
		switch (awType) {
		case "CELL_HIGH_VOLTAGE_WARNING":
			status = chargeBlocked() && !dischargeBlocked();
			break;
		case "CELL_LOW_VOLTAGE_WARNING":
			status = !chargeBlocked() && dischargeBlocked();
			break;
		case "CELL_HIGH_VOLTAGE_DELTA_WARNING":
			status = chargeBlocked() && dischargeBlocked();
			break;
		case "CELL_HIGH_TEMPERATURE_WARNING":
			status = chargeBlocked() && dischargeBlocked();
			break;
		case "CELL_LOW_TEMPERATURE_WARNING":
			status = chargeBlocked() && dischargeBlocked();
			break;
		case "STRING_HIGH_VOLTAGE_WARNING":
			status = chargeBlocked() && !dischargeBlocked();
			break;
		case "STRING_LOW_VOLTAGE_WARNING":
			status = !chargeBlocked() && dischargeBlocked();
			break;
		}
		return status;
	}

	public boolean chargeBlocked() throws IOException {
		return getMaxAllowableChargeCurrent().equalsIgnoreCase("0");
	}

	public boolean dischargeBlocked() throws IOException {
		return getMaxAllowableDischargeCurrent().equalsIgnoreCase("0");
	}

	public String getMaxAllowableChargeCurrent() throws IOException {
		Reports arrReport = new Reports(Integer.toString(cArrayIndex));
		return arrReport.getArrayMaxAllowedChargeCurrent();
	}

	public String getMaxAllowableDischargeCurrent() throws IOException {
		Reports arrReport = new Reports(Integer.toString(cArrayIndex));
		return arrReport.getArrayMaxAllowedDischargeCurrent();
	}

	private String getNotificationCode(String warningType) {
		String notificationCode = null;
		switch (warningType) {
		case "CELL_HIGH_VOLTAGE_WARNING":
			notificationCode = "2001";
			break;
		case "CELL_LOW_VOLTAGE_WARNING":
			notificationCode = "2004";
			break;
		case "CELL_HIGH_VOLTAGE_DELTA_WARNING":
			notificationCode = "2007";
			break;
		case "CELL_HIGH_TEMPERATURE_WARNING":
			notificationCode = "2010";
			break;
		case "CELL_LOW_TEMPERATURE_WARNING":
			notificationCode = "2014";
			break;
		case "STRING_HIGH_VOLTAGE_WARNING":
			notificationCode = "2003";
			break;
		case "STRING_LOW_VOLTAGE_WARNING":
			notificationCode = "2006";
			break;
		}
		return notificationCode;
	}

	public String runTest(String warningType, int setValue, int clearValue) throws IOException, InterruptedException, JSchException {
		boolean status = false;
		// Enable warn stop
		// enableWarnStop();

		System.out.println("Charge/discharge limits before setting warning:" + warningType);
		System.out.println(getMaxAllowableChargeCurrent());
		System.out.println(getMaxAllowableDischargeCurrent());

		// Set warning
		// TODO: Get the current cell max,min and avg voltage levels
		setWarning(warningType, setValue, clearValue);
		Thread.sleep(15000);

		String notificationCode = getNotificationCode(warningType);
		System.out.println("notificationCode:" + notificationCode);
		if (!didNotificationAppear(notificationCode)) {
			System.out.println("Test status: " + status);
			System.out.println(" - Notifications did not appear for warning: " + warningType);
			// return "Test status: " + status + ". Notifications did not appear";
		} else {
			System.out.println("Notifications appeared for warning: " + warningType);
		}
		System.out.println("Charge/discharge limits after setting warning:" + warningType);
		Thread.sleep(15000);
		System.out.println(getMaxAllowableChargeCurrent());
		System.out.println(getMaxAllowableDischargeCurrent());
		// Validate charge/discharge blocked based on warning type
		status = validateWarnStop(warningType, setValue, clearValue);
		System.out.println("Test status: " + (status ? "PASS" : "FAIL"));
		// Reset warnings to the original value
		resetWarning(warningType);
		Thread.sleep(25000);

		if (didNotificationAppear(notificationCode)) {
			System.out.println("Test status: " + (status ? "PASS" : "FAIL"));
			System.out.println(" - Notifications did not clear for warning: " + warningType);
			// return "Test status: " + status + ". Notifications did not clear";
		} else {
			System.out.println("Test status: " + (status ? "PASS" : "FAIL"));
			System.out.println(" - Notifications cleared for warning: " + warningType);
		}
		// Validate allowable power set to original value
		System.out.println("Charge/discharge limits after clearing warning:" + warningType);
		System.out.println(getMaxAllowableChargeCurrent());
		System.out.println(getMaxAllowableDischargeCurrent());
		return "Test status: " + (status ? "PASS" : "FAIL");
	}

	public boolean didNotificationAppear(String notificationCode) throws IOException {
		Notifications notifications = new Notifications(Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		System.out.println(notificationList);
		return notificationList.indexOf(notificationCode) != -1 ? true : false;
	}

	public static void main(String[] args) throws IOException, InterruptedException, JSchException {
		WarnStopOriginal mWarnStop = new WarnStopOriginal("QA22500FG", 1, 2);
		mWarnStop.setWarning("STRING_HIGH_VOLTAGE_WARNING", 850, 800);
		// mWarnStop.disableAllWarnings();

		// mWarnStop.resetAllWarnings();

		// mWarnStop.runTest("CELL_HIGH_VOLTAGE_WARNING", 3300, 3200);
		// mWarnStop.runTest("CELL_LOW_VOLTAGE_WARNING", 3295, 3350);
		// mWarnStop.runTest("CELL_HIGH_VOLTAGE_DELTA_WARNING", 200, 190);
		// mWarnStop.runTest("CELL_HIGH_TEMPERATURE_WARNING", 20, 19);
		// mWarnStop.runTest("CELL_LOW_TEMPERATURE_WARNING", 20, 21);
		// mWarnStop.runTest("STRING_HIGH_VOLTAGE_WARNING", 800, 790);
		// mWarnStop.runTest("STRING_LOW_VOLTAGE_WARNING", 900, 910);

		// System.out.println(mWarnStop.runTest("CELL_HIGH_VOLTAGE_WARNING", 3143, 3005));

		// System.out.println(mWarnStop.didNotificationAppear("3008"));
	}
}
