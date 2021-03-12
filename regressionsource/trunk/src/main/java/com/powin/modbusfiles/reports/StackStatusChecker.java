package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbusfiles.utilities.CommonHelper;

public class StackStatusChecker {
	private final static Logger LOG = LogManager.getLogger(StackStatusChecker.class.getName());

	private int cArrayIndex;
	private int cStringIndex;
	
	public StackStatusChecker(int arrayIndex, int stringIndex) {
		setArrayIndex(arrayIndex);
		setStringIndex(stringIndex);
	}
	

	public int getArrayIndex() {
		return cArrayIndex;
	}

	public void setArrayIndex(int arrayIndex) {
		cArrayIndex = arrayIndex;
	}

	public int getStringIndex() {
		return cStringIndex;
	}

	public void setStringIndex(int stringIndex) {
		cStringIndex = stringIndex;
	}

	public boolean checkForNotification(boolean notificationExpected, String notificationCode, int timeout) {
		boolean status = false;
		boolean timeoutExpired = false;
		long endTime = System.currentTimeMillis() + 1000 * timeout;
		while (!status && !timeoutExpired) {
			status = notificationExpected ? didNotificationAppear(notificationCode)
					: !didNotificationAppear(notificationCode);
			if (!status) {
				CommonHelper.sleep(1000);
				timeoutExpired = System.currentTimeMillis() > endTime;
			}
		}
		return status;
	}

	public boolean checkForNotification(boolean notificationExpected, String notificationCode) throws IOException {
		return notificationExpected ? didNotificationAppear(notificationCode)
				: !didNotificationAppear(notificationCode);
	}

	public boolean checkForContactorStatus(boolean openExpected, int timeout) {
		boolean status = checkForNotification(openExpected, "2534", timeout);
		LOG.info(String.format("Contactors %s %s", (openExpected ? "opened" : "closed"), status));
		return status;
	}
	
	public boolean didNotificationAppear(String notificationCode)  {
		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf(notificationCode) != -1 ? true : false;
	}
}
