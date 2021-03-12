package com.powin.modbusfiles.future;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommandHelper;

public class NotificationTriggerByMovingPower {
	//Functions below can be moved to a class for setting limits via power movement
//		public void runHighCellVoltageAlarmTest_clean() throws IOException, InterruptedException, KeyManagementException,
//				NoSuchAlgorithmException, ModbusException, InstantiationException, IllegalAccessException {
//			NotificationCodes notificationType = NotificationCodes.CELL_HIGH_VOLTAGE_ALARM;
//			int initialSetValue = 3550;
//			int finalSetValue = 3600;
//			int setValueIncrement = 10;
//			int dischargeTargetVolt = 3275;
//			int percentPower = 90;
//			int dischargeWaitTime = 10;// minutes
//			int chargeWaitTime = 10;// minutes
//
//			for (int setValue = initialSetValue; setValue < finalSetValue; setValue += setValueIncrement) {
//				resetNotification();
//				cBalancing.closeContactors(cArrayIndex, cStringIndex);
//				// Discharge to target discharge cvoltage
//				Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//				int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//				if (cellVoltageMaximum > dischargeTargetVolt) {
//					System.out.println(
//							"Start discharging until the max cell voltage is under " + dischargeTargetVolt + "mV.");
//					powerToTargetCellVoltage(percentPower, dischargeTargetVolt);
//				}
//				// Wait after discharge for hysteresis relaxation
//				for (int k = 0; k < dischargeWaitTime * 2; k++) {
//					System.out.println("Resting for 30 minutes. " + (1800 - k * 30) + " seconds left.");
//					Thread.sleep(30000);
//				}
//				printMaxMinCellVoltage(strReport);
//				// Set high voltage alarm
//				setNotification(notificationType, setValue, setValue - 50);
//				// Charge till alarm is triggered
//				powerUntilAlarmTriggered(setValue, notificationType);
//				// Wait after charging for hysteresis relaxation
//				for (int k = 0; k < chargeWaitTime * 15; k++) {
//					System.out.println("Resting for " + chargeWaitTime + " minutes after charging. " + (600 - k * 4)
//							+ " seconds left.");
//					Thread.sleep(4000);
//					printMaxMinCellVoltage();
//				}
//			}
//		}
//
//		public boolean runLowCellVoltageAlarmTest() throws IOException, InterruptedException, KeyManagementException,
//				NoSuchAlgorithmException, ModbusException, InstantiationException, IllegalAccessException {
//			NotificationCodes notificationType = NotificationCodes.CELL_LOW_VOLTAGE_ALARM;
//			boolean testPass = true;
//			int setValue = 2600;
//			for (setValue = 2560; setValue >= 2520; setValue -= 10) {
//
//				resetNotification();
//				// Close contactors
//				cBalancing.closeContactors(cArrayIndex, cStringIndex);
//
//				Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//				int cellVoltageMin = Integer.parseInt(strReport.getMinCellGroupVoltage());
//				int chargeTargetVolt = 3000;
//
//				if (cellVoltageMin < chargeTargetVolt) {
//					System.out.println("Start charging until the min cell voltage is over " + chargeTargetVolt + "mV.");
//					powerToTargetCellVoltage(-90, chargeTargetVolt);
//					for (int k = 0; k < 60; k++) {
//						System.out.println("Resting for 30 minutes. " + (1800 - k * 30) + " seconds left.");
//						Thread.sleep(30000);
//						printMaxMinCellVoltage(strReport);
//					}
//				}
//
//				// Set the notificationType
//				String notificationCode = notificationType.toString();
//				setNotification(notificationType, setValue, setValue + 50);
//
//				powerUntilAlarmTriggered(setValue, notificationType);
//
//				int timeout = 120;
//				if (checkForNotification(true, notificationCode, timeout)) {
//					LOG.info("PASS: Notification " + notificationCode + " appeared for notification " + notificationType
//							+ ". SetValue=" + setValue);
//				} else {
//					LOG.info("FAIL: Notification " + notificationCode + " did not appear for notification "
//							+ notificationType + ". SetValue=" + setValue);
//				}
//
//				for (int k = 0; k < 150; k++) {
//					System.out.println("Resting for 10 minutes after discharging. " + (600 - k * 4) + " seconds left.");
//					Thread.sleep(4000);
//					printMaxMinCellVoltage();
//					// Also for every 4 seconds, print out the timestamp, min and max cell voltage
//				}
//
//				int dcbusVoltage = Integer.parseInt(strReport.getDcBusVoltage());
//				powerFor10MinutesOrToTargetVoltage(-90, dcbusVoltage + 30);
//
//				for (int k = 0; k < 300; k++) {
//					System.out.println("Resting for 20 minutes after charging. " + (1200 - k * 4) + " seconds left.");
//					Thread.sleep(4000);
//					printMaxMinCellVoltage();
//				}
//			}
//	        return testPass;
//		}
//
//		public void printMaxMinCellVoltage() throws IOException {
//			Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//			int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//			int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//			System.out.println(DateTime.now().toString("HH:mm:ss") + " Current maximum cell voltage:" + cellVoltageMaximum
//					+ " . Current minimum cell voltage: " + cellVoltageMinimum);
//		}
//
//		public void printMaxMinCellVoltage(Reports strReport) throws IOException {
//			strReport.getReportContents();
//			int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//			int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//			System.out.println(DateTime.now().toString("HH:mm:ss") + " Current maximum cell voltage:" + cellVoltageMaximum
//					+ " . Current minimum cell voltage: " + cellVoltageMinimum);
//		}
//
//		public void testGetReportsWithoutCreatingNewInstances() throws IOException, InterruptedException {
//			Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//
//			for (int k = 0; k < 1500; k++) {
//
//				Thread.sleep(1000);
//				strReport.getReportContents();
//				int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//				int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//				System.out.println(DateTime.now().toString("HH:mm:ss") + " Current maximum cell voltage:"
//						+ cellVoltageMaximum + " . Current minimum cell voltage: " + cellVoltageMinimum);
//			}
//
//		}
//
//		// public void runHighCellVoltageAlarmTest() throws IOException,
//		// InterruptedException, KeyManagementException,
//		// NoSuchAlgorithmException, ModbusException {
//		// String notificationType = "stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_ALARM";
//		// int setValue = 3550;
//		// for (setValue = 3590; setValue < 36000; setValue += 10) {
//		//
//		// resetNotification(notificationType);
//		// // Close contactors
//		// cBalancing.closeContactors(cArrayIndex, cStringIndex);
//		//
//		// Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//		// int cellVoltageMaximum =
//		// Integer.parseInt(strReport.getMaxCellGroupVoltage());
//		// int dischargeTargetVolt = 3275;
//		//
//		// if (cellVoltageMaximum > dischargeTargetVolt) {
//		// System.out.println(
//		// "Start discharging until the max cell voltage is under " +
//		// dischargeTargetVolt + "mV.");
//		// powerToTargetCellVoltage(100, dischargeTargetVolt);
//		// for (int k = 0; k < 60; k++) {
//		// System.out.println("Resting for 10 minutes. " + (600 - k * 10) + " seconds
//		// left.");
//		// Thread.sleep(30000);
//		// printMaxMinCellVoltage(strReport);
//		// }
//		// }
//		//
//		// // Set the notificationType
//		// String notificationCode = getNotificationCode(notificationType);
//		// setNotification(notificationType, setValue, setValue - 50);
//		//
//		// powerUntilAlarmTriggered(setValue, notificationCode);
//		//
//		// int timeout = 120;
//		// if (checkForNotification(true, notificationCode, timeout)) {
//		// LOG.info("PASS: Notification " + notificationCode + " appeared for
//		// notification " + notificationType
//		// + ". SetValue=" + setValue);
//		// } else {
//		// LOG.info("FAIL: Notification " + notificationCode + " did not appear for
//		// notification "
//		// + notificationType + ". SetValue=" + setValue);
//		// }
//		//
//		// for (int k = 0; k < 150; k++) {
//		// System.out.println("Resting for 10 minutes after charging. " + (600 - k * 4)
//		// + " seconds left.");
//		// Thread.sleep(4000);
//		// printMaxMinCellVoltage();
//		// // Also for every 4 seconds, print out the timestamp, min and max cell
//		// voltage
//		// }
//		//
//		// int dcbusVoltage = Integer.parseInt(strReport.getDcBusVoltage());
//		// powerFor10MinutesOrToTargetVoltage(113, dcbusVoltage - 30);
//		//
//		// for (int k = 0; k < 450; k++) {
//		// System.out.println("Resting for 10 minutes after discharging. " + (600 - k *
//		// 4) + " seconds left.");
//		// Thread.sleep(4000);
//		// printMaxMinCellVoltage();
//		// }
//		// }
//		//
//		// }
//		public void powerUntilAlarmTriggered(int voltageTarget, NotificationCodes notificationCode)
//				throws IOException, ModbusException, InterruptedException {
//			// ModbusPowinBlock cModbusPowinBlock = new ModbusPowinBlock(cModbusHostName, 4502,
//			// 255, false);
//			Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//			int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//			int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//			int voltageOffset = 50;
//			boolean isHighCellVoltageAlarmTriggered = false;
//			boolean isLowCellVoltageAlarmTriggered = false;
//			boolean isContactorOpenAlarmTriggered = false;
//
//			if (notificationCode == NotificationCodes.CELL_HIGH_VOLTAGE_ALARM) {
//				// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(-113000));
//				CommandHelper.setPAsPercent(BigDecimal.valueOf(-90));
//				System.out.println("Powering up to " + voltageTarget);
//			} else if (notificationCode == NotificationCodes.CELL_LOW_VOLTAGE_ALARM) {
//				// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(113000));
//				CommandHelper.setPAsPercent(BigDecimal.valueOf(90));
//				System.out.println("Powering down to " + voltageTarget);
//			}
//
//			while (((cellVoltageMaximum < (voltageTarget + voltageOffset)) && notificationCode == NotificationCodes.CELL_HIGH_VOLTAGE_ALARM)
//					|| ((cellVoltageMinimum > (voltageTarget - voltageOffset)) && notificationCode == NotificationCodes.CELL_LOW_VOLTAGE_ALARM)) {
//				Thread.sleep(400);
//				printMaxMinCellVoltage(strReport);
//				if (checkForNotification(true, "1001")) {
//					isHighCellVoltageAlarmTriggered = true;
//					System.out.println(DateTime.now().toString("HH:mm:ss - ") + "HighCellVoltageAlarm triggered.");
//					break;
//				}
//
//				if (checkForNotification(true, "2534")) {
//					isContactorOpenAlarmTriggered = true;
//					System.out.println(DateTime.now().toString("HH:mm:ss - ") + "ContactorOpenAlarm triggered.");
//					break;
//				}
//
//				if (checkForNotification(true, "1004")) {
//					isHighCellVoltageAlarmTriggered = true;
//					System.out.println(DateTime.now().toString("HH:mm:ss - ") + "LowCellVoltageAlarm triggered.");
//					break;
//				}
//
//				cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//				cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//			}
//
//			if (isHighCellVoltageAlarmTriggered == false && isLowCellVoltageAlarmTriggered == false
//					&& isContactorOpenAlarmTriggered == false) {
//				System.out.println(DateTime.now().toString("HH:mm:ss - ")
//						+ "Target cell voltage reached, but no alarm was triggered.");
//			}
//
//			// Switch off power
//			// cModbusPowinBlock = new ModbusPowinBlock(cModbusHostName, 4502, 255, false);
//			// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(0));
//
//			CommandHelper.setPAsPercent(BigDecimal.ZERO);
//
//			for (int i = 0; i < 300; i++) {
//				if (!isHighCellVoltageAlarmTriggered) {
//					if (checkForNotification(true, "1001")) {
//						isHighCellVoltageAlarmTriggered = true;
//						System.out.println(DateTime.now().toString("HH:mm:ss - ") + "HighCellVoltageAlarm triggered.");
//					}
//				}
//
//				if (!isContactorOpenAlarmTriggered) {
//					if (checkForNotification(true, "2534")) {
//						isContactorOpenAlarmTriggered = true;
//						System.out.println(DateTime.now().toString("HH:mm:ss - ") + "ContactorOpenAlarm triggered.");
//					}
//				}
//
//				if (!isHighCellVoltageAlarmTriggered) {
//					if (checkForNotification(true, "1004")) {
//						isHighCellVoltageAlarmTriggered = true;
//						System.out.println(DateTime.now().toString("HH:mm:ss - ") + "LowCellVoltageAlarm triggered.");
//					}
//				}
//
//				printMaxMinCellVoltage(strReport);
//				Thread.sleep(400);
//			}
//
//		}
//
//		public void powerFor10MinutesOrToTargetVoltage(int percentage, int targetVoltage)
//				throws IOException, ModbusException, InterruptedException {
//			// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(powerKw * 1000));
//			CommandHelper.setPAsPercent(BigDecimal.valueOf(percentage));
//			Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//			int dcbusVoltage;
//			int actualPower = (150 * percentage / 100);
//			for (int k = 0; k < 150; k++) {
//				if (percentage > 0)
//					System.out.println(
//							"Powering down at " + actualPower + "Kw for 10 minutes. " + (600 - k * 4) + " seconds left.");
//				else {
//					System.out.println(
//							"Powering up at " + actualPower + "Kw for 10 minutes. " + (600 - k * 4) + " seconds left.");
//				}
//				Thread.sleep(4000);
//				// Also for every 4 seconds, print out the timestamp, min and max cell voltage
//				printMaxMinCellVoltage();
//				strReport = new Reports(cArrayIndex + "," + cStringIndex);
//				dcbusVoltage = Integer.parseInt(strReport.getDcBusVoltage());
//				if ((dcbusVoltage < targetVoltage && percentage > 0) || (dcbusVoltage > targetVoltage && percentage < 0)) {
//					System.out.println("Charging/Discharging abort because target voltage reached.");
//					break;
//				}
//			}
//			// Switch off power
//			// cModbusPowinBlock = new ModbusPowinBlock(cModbusHostName, 4502, 255, false);
//			// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(0));
//
//			CommandHelper.setPAsPercent(BigDecimal.ZERO);
//		}
//
//		public void powerToTargetCellVoltage(int percentage, int targetVoltage)
//				throws IOException, ModbusException, InterruptedException {
//			// ModbusPowinBlock cModbusPowinBlock = new ModbusPowinBlock(cModbusHostName, 4502,
//			// 255, false);
//			// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(powerKw * 1000));
//			CommandHelper.setPAsPercent(BigDecimal.valueOf(percentage));
//			Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//			int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//			int cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//			if (percentage > 0) {
//				while (cellVoltageMaximum > targetVoltage) {
//					strReport.getReportContents();
//					cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//					Thread.sleep(500);
//				}
//			} else if (percentage < 0) {
//				while (cellVoltageMinimum < targetVoltage) {
//					strReport.getReportContents();
//					cellVoltageMinimum = Integer.parseInt(strReport.getMinCellGroupVoltage());
//					Thread.sleep(500);
//				}
//			}
//
//			System.out.println("Target cell voltage reached.");
//			// Switch off power
//			// cModbusPowinBlock = new ModbusPowinBlock(cModbusHostName, 4502, 255, false);
//			// cModbusPowinBlock.runSimpeBasicCommand(BigDecimal.valueOf(0));
//			CommandHelper.setPAsPercent(BigDecimal.ZERO);
//		}
//
//
//		public void runHighCellVoltageAlarmTest() throws IOException, InterruptedException, KeyManagementException,
//		NoSuchAlgorithmException, ModbusException, InstantiationException, IllegalAccessException {
//	NotificationCodes notificationType = NotificationCodes.CELL_HIGH_VOLTAGE_ALARM;
//	int setValue = 3550;
//	for (setValue = 3550; setValue <= 3600; setValue += 10) {
//		resetNotification();
//		// Close contactors
//		cBalancing.closeContactors(cArrayIndex, cStringIndex);
//		Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
//		int cellVoltageMaximum = Integer.parseInt(strReport.getMaxCellGroupVoltage());
//		int dischargeTargetVolt = 3275;
//
//		if (cellVoltageMaximum > dischargeTargetVolt) {
//			System.out.println(
//					"Start discharging until the max cell voltage is under " + dischargeTargetVolt + "mV.");
//			powerToTargetCellVoltage(90, dischargeTargetVolt);
//			for (int k = 0; k < 20; k++) {
//				System.out.println("Resting for 30 minutes. " + (1800 - k * 30) + " seconds left.");
//				Thread.sleep(30000);
//				printMaxMinCellVoltage(strReport);
//			}
//		}
//
//		// Set the notificationType
//		String notificationCode = notificationType.toString();
//		setNotification(notificationType, setValue, setValue - 50);
//
//		powerUntilAlarmTriggered(setValue, notificationType);
//
//		int timeout = 120;
//		if (checkForNotification(true, notificationCode, timeout)) {
//			LOG.info("PASS: Notification " + notificationCode + " appeared for notification " + notificationType
//					+ ". SetValue=" + setValue);
//		} else {
//			LOG.info("FAIL: Notification " + notificationCode + " did not appear for notification "
//					+ notificationType + ". SetValue=" + setValue);
//		}
//
//		for (int k = 0; k < 150; k++) {
//			System.out.println("Resting for 10 minutes after charging. " + (600 - k * 4) + " seconds left.");
//			Thread.sleep(4000);
//			printMaxMinCellVoltage();
//			// Also for every 4 seconds, print out the timestamp, min and max cell voltage
//		}
//
//		int dcbusVoltage = Integer.parseInt(strReport.getDcBusVoltage());
//		powerFor10MinutesOrToTargetVoltage(90, dcbusVoltage - 30);
//
//		for (int k = 0; k < 300; k++) {
//			System.out.println("Resting for 20 minutes after discharging. " + (1200 - k * 4) + " seconds left.");
//			Thread.sleep(4000);
//			printMaxMinCellVoltage();
//		}
//	}
	
//	public static void testInverterChargeHighVoltageLimit(InverterSafety isn)
//			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
//		InverterSafetyCommands.setInverterChargeHighVoltageLimit(800);
//		Thread.sleep(15000);
//		
//		String blockStr = SystemInfo.getBsfStatus();
//		String codeStr = SystemInfo.getBsfErrorCodes();
//
//		logResult(blockStr.contains("CHG-BLOCKED") && codeStr.contains("CHRG:HVH") && codeStr.contains("CHRG:HV"), "Set InverterChargeHighVoltageLimit notification");
//
//		InverterSafetyCommands.setDefaultInverterSafetyConfiguration();
//		Thread.sleep(15000);
//		String clearStr = SystemInfo.getBsfGood();
//		logResult(clearStr.contains("CHG-RECOVERING") || clearStr.contains("GOOD"), "Clear InverterChargeHighVoltageLimit notification");
//
//
//	}
}
