package com.powin.modbusfiles.derating;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.powin.modbusfiles.awe.BatteryPackVoltageNotificationCommands;
import com.powin.modbusfiles.awe.BatteryPackVoltageNotifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.stringpusher.app.NotificationSend;

public class WarnDerateTestOriginal {
	private static NotificationSend cNotiSend;
	private static StackStatusChecker cStackStatusChecker1;
	private static StackStatusChecker cStackStatusChecker2;
	private static BatteryPackVoltageNotifications cBpVoltNotification1;

	private final static Logger LOG = LogManager.getLogger(WarnDerateTestOriginal.class.getName());

	public WarnDerateTestOriginal() throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		init();
	}

	public NotificationSend getNotiSend() {
		return cNotiSend;
	}

	public static void setNotiSend(NotificationSend notiSend) {
		cNotiSend = notiSend;
	}

	public static StackStatusChecker getStackStatusChecker1() {
		return cStackStatusChecker1;
	}

	public static void setStackStatusChecker1(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker1 = stackStatusChecker;
	}

	public static StackStatusChecker getStackStatusChecker2() {
		return cStackStatusChecker2;
	}

	public static void setStackStatusChecker2(StackStatusChecker stackStatusChecker) {
		cStackStatusChecker2 = stackStatusChecker;
	}

	public static BatteryPackVoltageNotifications getBpVoltNotification1() {
		return cBpVoltNotification1;
	}

	public static void setBpVoltNotification1(BatteryPackVoltageNotifications bpVoltNotification1) {
		cBpVoltNotification1 = bpVoltNotification1;
	}

	public void init() throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		setNotiSend(new NotificationSend());
		// TODO: Let's configure the machine to be 1 array two strings.
		setStackStatusChecker1(new StackStatusChecker(1, 1));
		setStackStatusChecker2(new StackStatusChecker(1, 2));
		setBpVoltNotification1(new BatteryPackVoltageNotifications(1, 1));
	}

	public String getMaxAllowableChargeCurrent() throws IOException {
		Reports arrReport = new Reports(Integer.toString(1));
		return arrReport.getArrayMaxAllowedChargeCurrent();
	}

	public String getMaxAllowableDischargeCurrent() throws IOException {
		Reports arrReport = new Reports(Integer.toString(1));
		return arrReport.getArrayMaxAllowedDischargeCurrent();
	}

	public boolean validateWarnDerate() throws InstantiationException, IllegalAccessException {
		boolean isTestPass = true;
		try {
			LOG.info("Clear notifications.");
			BatteryPackVoltageNotificationCommands.disableWarning("");
			// getBpVoltNotification1().setHighCellGroupVoltageWarning(3650, 3600);
			// getBpVoltNotification1().resetDefaultsBatteryPackVoltageNotificationConfiguration();
			boolean isStatusCorrect = false;
			isStatusCorrect = getStackStatusChecker1().checkForNotification(false, "2004", 60);

			LOG.info("MaxAllowedChargeCurrent(before warning is set) ={}", getMaxAllowableChargeCurrent());
			LOG.info("MaxAllowedDischargeCurrent(before warning is set) ={}", getMaxAllowableDischargeCurrent());
			LOG.info("Try to send SET notification {} for Stack1.", 2004);
			BatteryPackVoltageNotificationCommands.setLowCellGroupVoltageWarning(3300, 3350);
			LOG.info("SET notification {} sent.", 2004);
			isStatusCorrect = false;
			isStatusCorrect = getStackStatusChecker1().checkForNotification(true, "2004", 60);
			if (isStatusCorrect) {
				LOG.info("PASS: Notification {} triggered.", 2004);
				Thread.sleep(10000);
				LOG.info("MaxAllowedChargeCurrent(after warning is set) ={}", getMaxAllowableChargeCurrent());
				LOG.info("MaxAllowedDischargeCurrent(after warning is set) ={}", getMaxAllowableDischargeCurrent());
			} else {
				LOG.info("FAIL: Notification {} not triggered.", 2004);
				isTestPass = false;
			}

		} catch (Exception e) {
			LOG.info(e.toString());
			isTestPass = false;
		}

		BatteryPackVoltageNotificationCommands.resetDefaultsBatteryPackVoltageNotificationConfiguration();

		return isTestPass;
	}

	public static void main(String[] args)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, InstantiationException, IllegalAccessException {
		WarnDerateTestOriginal mWarnDerateTest = new WarnDerateTestOriginal();
		mWarnDerateTest.validateWarnDerate();
	}
}
