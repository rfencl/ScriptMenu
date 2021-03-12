package com.powin.modbusfiles.awe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.PowinProperty;


public class BatteryPackVoltageNotifications {
	final static Logger LOG = LogManager.getLogger(BatteryPackVoltageNotifications.class.getName());
	public static Balancing cBalancing = new Balancing(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString());
	private static int cArrayIndex;
	private static int cStringIndex;
	static StackType stackType = SystemInfo.getStackType();
	private static BatteryPackVoltageNotificationCommands bpVoltageNotificationCommands ;

	public BatteryPackVoltageNotifications(int arrayIndex, int stringIndex) {
		SystemInfo.getStationCode();
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
		bpVoltageNotificationCommands = new BatteryPackVoltageNotificationCommands(arrayIndex,stringIndex);
	}

	//TODO Pull up to base class
	public boolean runTest(NotificationCodes notificationType) {
		int setValue = bpVoltageNotificationCommands.getSetValue(notificationType);
		int clearValue = bpVoltageNotificationCommands.getClearValue(notificationType, setValue);
		return runTest(notificationType, setValue, clearValue);
	}

	public boolean runTest(NotificationCodes notificationType, int setValue, int clearValue) {
		boolean isTestPass = true;
		// Close contactors
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		// Set notification
		bpVoltageNotificationCommands.setNotification(notificationType, setValue, clearValue);
		// Verify notification appeared
		int timeout = 120;
		isTestPass = AweCommon.verifyNotificationAppeared(notificationType, setValue, clearValue, timeout);
		//Verify contactor status
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		isTestPass&= AweCommon.verifyContactorBehaviorWhenNotificationSet(notificationType,timeout);
		// Reset warnings to defaults
		bpVoltageNotificationCommands.resetNotification();
		// Verify notification cleared
		isTestPass&= AweCommon.verifyNotificationsCleared( notificationType,  timeout);
		//Verify contactor status
		isTestPass&= AweCommon.verifyContactorsClosed(timeout,"");
		return isTestPass;
	}
	
	public static void main(String[] args) {

	BatteryPackVoltageNotifications mBatteryPackVoltageNotifications=	new BatteryPackVoltageNotifications(PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());
	
	mBatteryPackVoltageNotifications.runTest(NotificationCodes.CELL_HIGH_VOLTAGE_ALARM);
	mBatteryPackVoltageNotifications.runTest(NotificationCodes.CELL_LOW_VOLTAGE_ALARM);
	mBatteryPackVoltageNotifications.runTest(NotificationCodes.CELL_HIGH_VOLTAGE_DELTA_ALARM);
	}
}