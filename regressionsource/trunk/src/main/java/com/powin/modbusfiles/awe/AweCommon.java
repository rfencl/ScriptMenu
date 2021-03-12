package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.powinwebappbase.HttpHelper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandResponse;

public class AweCommon {
	static final boolean NOTIFICATION_EXPECTED = true;
	static final boolean NOTIFICATION_NOT_EXPECTED = false;
	private static final int ARRAY_INDEX = PowinProperty.ARRAY_INDEX.intValue();
	private static final int STRING_INDEX = PowinProperty.STRING_INDEX.intValue();
	private static final StackType stackType = SystemInfo.getStackType();
	public final static Logger LOG = LogManager.getLogger(AweCommon.class.getName());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//Notification appearance status
	public static boolean didNotificationAppear(NotificationCodes notificationCode) {
		Notifications notifications = new Notifications(Integer.toString(ARRAY_INDEX) + "," + Integer.toString(STRING_INDEX));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList.indexOf(notificationCode.toString()) != -1 ? true : false;
	}

	public static boolean checkForNotification(boolean notificationExpected, NotificationCodes notificationCode, int timeout){
		boolean status = false;
		boolean timeoutExpired = false;
		long endTime = System.currentTimeMillis() + (1000 * timeout);
		while (!status && !timeoutExpired) {
			CommonHelper.sleep(4000);
			status = notificationExpected ? didNotificationAppear(notificationCode)
					: !didNotificationAppear(notificationCode);
			if (!status) {
				CommonHelper.quietSleep(1000);
				timeoutExpired = System.currentTimeMillis() > endTime;
			}
		}
		return status;
	}

	public static boolean verifyNotificationsCleared(NotificationCodes notificationType, int timeout)  {
	    boolean isTestPass = true;
		if (isTestPass = checkForNotification(NOTIFICATION_NOT_EXPECTED, notificationType, timeout)) {
			LOG.info("PASS: Notification " + notificationType.name() + " cleared.");
		} else {
			LOG.info("FAIL: Notification " + notificationType.name() + " did not clear.");
			getCurrentNotifications();
		}
		return isTestPass;
	}

	public static boolean verifyNotificationAppeared(NotificationCodes notificationType, int setValue, int clearValue,int timeout) {
		boolean isTestPass = true;
		if (isTestPass = checkForNotification(NOTIFICATION_EXPECTED, notificationType, timeout)){
			LOG.info("PASS: Notification " + notificationType.name() + " appeared .");
		} else {
			LOG.info("FAIL: Notification " + notificationType.name() + " did not appear.");
			//TODO: We may need to fail the test if notifications do not appear the first time alarm is set
//			LOG.info("Second attempt at setting "+notificationType);
//			setNotification(notificationType, setValue, clearValue);
//			if ((isTestPass &= checkForNotification(BatteryPackVoltageNotifications.NOTIFICATION_EXPECTED, notificationType, timeout))) {
//				LOG.info("PASS: Notification " + notificationType + " appeared for notification.");
//			} else {
//				LOG.info("FAIL: Notification " + notificationType + " did not appear.");
//			}
		}
		return isTestPass;
	}

	public static boolean isContactorOpen(int timeout){
		return checkForNotification(NOTIFICATION_EXPECTED, NotificationCodes.CONTACTOR_OPEN_WARNING, timeout);
	}
	
	private static boolean isContactorClosed(int timeout) {
		return checkForNotification(NOTIFICATION_NOT_EXPECTED, NotificationCodes.CONTACTOR_OPEN_WARNING, timeout);
	}

	public static String getCurrentNotifications()  {
		Notifications notifications = new Notifications(Integer.toString(ARRAY_INDEX) + "," + Integer.toString(STRING_INDEX));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		LOG.info("notificationList:" + notificationList);
		return CommonHelper.convertArrayListToString(notificationList);
	}

	public static boolean verifyContactorBehaviorWhenNotificationSet(NotificationCodes notificationType, int timeout) {
		boolean isTestPass=true;
		boolean willContactorOpenWhenAlarmSet=stackType.getNotificationBehavior().getBehavior(notificationType);
		
		if(willContactorOpenWhenAlarmSet) {
			isTestPass=isContactorOpen(timeout);
			if(isTestPass) {
				LOG.info("PASS: Contactors opened after setting notification: " + notificationType.name());
			}
			else {
				LOG.info("FAIL: Contactors did NOT open after setting notification: " + notificationType.name());
			}
		}
		else {
			isTestPass=!isContactorOpen(timeout);
			if(isTestPass) {
				LOG.info("PASS: Contactors did not open after setting notification: " + notificationType.name());
			}
			else {
				LOG.info("FAIL: Contactors opened after setting notification: " + notificationType.name());
			}
		}
		return isTestPass;
	}

	public static boolean verifyContactorsClosed(int timeout, String logMessage) {
		boolean isTestPass;
		isTestPass=isContactorClosed(timeout);
		if(isTestPass) {
			LOG.info("PASS: Contactors closed " + logMessage);
		}
		else {
			LOG.info("FAIL: Contactors not closed" + logMessage);
		}
		return isTestPass;
	}
	
	public static boolean verifyContactorsOpened(int timeout, String logMessage) {
		boolean isTestPass;
		isTestPass=isContactorOpen(timeout);
		if(isTestPass) {
			LOG.info("PASS: Contactors opened " + logMessage);
		}
		else {
			LOG.info("FAIL: Contactors not open" + logMessage);
		}
		return isTestPass;
	}

	public static void postCommand(Command command) {
		HttpClient cHttpClient = HttpHelper.buildHttpClient(true);
		String cRemoteUrl=	PowinProperty.TURTLE_URL.toString() + "/" + 
							PowinProperty.PHOENIX_COMMAND_INJECT_URL.toString() + 
							PowinProperty.ARRAY_INDEX + "/" + 
							PowinProperty.ARRAY_INDEX;
	
		if (command != null) {
			byte[] mRequestData = command.toByteArray();
			HttpPost mHttpPost = new HttpPost(cRemoteUrl);
			ByteArrayEntity mEntity = new ByteArrayEntity(mRequestData);
			mHttpPost.setEntity(mEntity);
			try {
				HttpResponse mHttpResponse = cHttpClient.execute(mHttpPost);
				byte[] mResponseData = EntityUtils.toByteArray(mHttpResponse.getEntity());
				CommandResponse.newBuilder().mergeFrom(mResponseData).build();
			} catch (Exception e) {
				LOG.error("Exception in sending command {} to endpoint.", command, cRemoteUrl, e);
			}
		}
	}
}
