package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.powin.modbusfiles.reports.StackStatusChecker;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.stringpusher.app.NotificationSend;

public class ZeroConfigFromString {
	private static NotificationSend cNotificationSend;
	private static String cPhoenixUrl = PowinProperty.TURTLE_URL.toString()+"turtle/";
	private static StackStatusChecker cStackStatusChecker;
	private final static Logger LOG = LogManager.getLogger(ZeroConfigFromString.class.getName());

	public ZeroConfigFromString()
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		init();
	}
	
	public ZeroConfigFromString(String phoenixUrl)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		cPhoenixUrl=phoenixUrl;
		init();	
	}

	public void init() throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		cNotificationSend=new NotificationSend();
		cNotificationSend.setPhoenixUrl(cPhoenixUrl);	
	}
	
	public static boolean sendNotification(int notificationId, int triggerMsg,int arrayIndex,int stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		boolean isTestPass = true;
		try {
			LOG.info("Try to send SET notification " + notificationId);
			cNotificationSend.sendChange(arrayIndex,stringIndex, notificationId, triggerMsg, true);
			boolean notificationAppeared = getNotificationStatus(true,arrayIndex, stringIndex, notificationId);
			if (notificationAppeared) {
				LOG.info("PASS: Notification {} triggered.", notificationId);
			} else {
				LOG.info("FAIL: Notification {} not triggered.", notificationId);
				isTestPass = false;
			}
		} catch (Exception e) {
			LOG.info(e.toString());
			isTestPass = false;
		}
		return isTestPass;
	}
	
	public static boolean clearNotification(int notificationId, int triggerMsg,int arrayIndex,int stringIndex)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		boolean isTestPass = true;
		try {
		//	cNotificationSend.sendEmpty(arrayIndex,stringIndex,batteryPackIndex);
			cNotificationSend.sendChange(arrayIndex,stringIndex, notificationId, triggerMsg, false);
			LOG.info("Clear notification message sent.");
			boolean notificationCleared = getNotificationStatus(false,arrayIndex, stringIndex, notificationId);
			if (notificationCleared) {
				LOG.info("Notification {} cleared.", notificationId);
			} else {
				LOG.info("FAIL: Notification {} not cleared.", notificationId);
				isTestPass = false;
			}
		}
		catch (Exception e) {
			LOG.info(e.toString());
			isTestPass = false;
		}
		return isTestPass;
	}

	private static boolean getNotificationStatus(boolean notificationExpected, int arrayIndex, int stringIndex, int notificationId)
			throws IOException, InterruptedException {
		boolean isStatusCorrect = false;
		cStackStatusChecker=new StackStatusChecker(arrayIndex,stringIndex);
		isStatusCorrect = cStackStatusChecker.checkForNotification(notificationExpected, Integer.toString(notificationId), 120);
		return isStatusCorrect;
	}
	
	public boolean testNotifications(int notificationId, int triggerMsg) throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		int arrayIndex=1;
		int stringIndex=1;
		boolean testStatus=true;
		testStatus &= sendNotification( notificationId, triggerMsg, arrayIndex,stringIndex);
		testStatus &= clearNotification( notificationId, triggerMsg,arrayIndex,stringIndex );
		return testStatus;
	}

	public static void main(String[] args)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		ZeroConfigFromString mZeroConfig = new ZeroConfigFromString();
		int notificationId=2014;//2019,2020
		int  triggerMsg=3777;
		int arrayIndex=1;
		int stringIndex=1;
		mZeroConfig.sendNotification( notificationId, triggerMsg,arrayIndex,stringIndex);
		mZeroConfig.clearNotification( notificationId, triggerMsg,arrayIndex,stringIndex);
	}
}
