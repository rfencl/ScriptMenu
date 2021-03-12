package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.powinwebappbase.HttpHelper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandPayload;
import com.powin.tongue.fourba.command.Endpoint;
import com.powin.tongue.fourba.command.EndpointType;
import com.powin.tongue.fourba.command.SetBatteryPackTemperatureNotificationConfiguration;
import com.powin.tongue.fourba.command.TemperatureTrigger;

public class BatteryPackTemperatureNotifications {

	// private final static Logger LOG = LogManager.getLogger();
	private final static Logger LOG = LogManager.getLogger(BatteryPackTemperatureNotifications.class.getName());
	private HttpClient cHttpClient = HttpHelper.buildHttpClient(true);
	private String cRemoteUrl;
	private String cStationCode;
	private int cArrayIndex;
	private int cStringIndex;
	StackType stackType = SystemInfo.getStackType();
	protected static Balancing cBalancing = new Balancing(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString());;


	public BatteryPackTemperatureNotifications(int arrayIndex, int stringIndex) {
		cStationCode = SystemInfo.getStationCode();
		String append = String.valueOf(arrayIndex) + "/" + String.valueOf(stringIndex);
		cRemoteUrl = PowinProperty.TURTLE_URL.toString() + "/" + PowinProperty.PHOENIX_COMMAND_INJECT_URL.toString() + append;
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
	}
	
	public BatteryPackTemperatureNotifications(String url, int arrayIndex, int stringIndex) {
		cStationCode = SystemInfo.getStationCode();
		cRemoteUrl = url;
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
		
	}

	// CELL Low temperature warning
	public void setLowCellGroupTemperatureWarning(int set, int clear) {
		if (set < clear) {
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setLowCellGroupTemperatureWarning(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// CELL High temperature warning
	public void setHighCellGroupTemperatureWarning(int set, int clear) {
		if (set > clear) {
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setHighCellGroupTemperatureWarning(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// Cell high temperature delta warning
	public void setHighCellGroupTemperatureDeltaWarning(int set, int clear) {
		if (set > clear) {
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setHighCellGroupTemperatureDeltaWarning(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// CELL Low temperature alarm
	public void setLowCellGroupTemperatureAlarm(int set, int clear) {
		if (set < clear) {
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setLowCellGroupTemperatureAlarm(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	public void setHighCellGroupTemperatureAlarm(int set, int clear) throws InstantiationException, IllegalAccessException {
		if (set > clear) {
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDefaultBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setHighCellGroupTemperatureAlarm(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// Cell high temperature delta alarm
	public void setHighCellGroupTemperatureDeltaAlarm(int set, int clear) {
//		if (set > clear) 
		{
			SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
			mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder(mConfig).setHighCellGroupTemperatureDeltaAlarm(
					TemperatureTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	public void resetDefaultsBatteryPackTemperatureNotificationConfiguration() throws InstantiationException, IllegalAccessException {
		SetBatteryPackTemperatureNotificationConfiguration mConfig = getDefaultBatteryPackTemperatureNotificationConfiguration();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);
	}

	public void disableBatteryPackTemperatureNotificationConfiguration() {
		SetBatteryPackTemperatureNotificationConfiguration mConfig = getDisabledBatteryPackTemperatureNotificationConfiguration();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);
	}

	private SetBatteryPackTemperatureNotificationConfiguration getDisabledBatteryPackTemperatureNotificationConfiguration() {
		SetBatteryPackTemperatureNotificationConfiguration mConfig = SetBatteryPackTemperatureNotificationConfiguration
				.newBuilder().setHighCellGroupTemperatureAlarm(TemperatureTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupTemperatureWarning(TemperatureTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupTemperatureWarrantyAlarm(TemperatureTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupTemperatureWarrantyWarning(TemperatureTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupTemperatureAlarm(TemperatureTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupTemperatureWarning(TemperatureTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupTemperatureWarrantyAlarm(TemperatureTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupTemperatureWarrantyWarning(TemperatureTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupTemperatureDeltaAlarm(TemperatureTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupTemperatureDeltaWarning(TemperatureTrigger.newBuilder().setEnabled(false)).build();
		return mConfig;
	}

	private SetBatteryPackTemperatureNotificationConfiguration getDefaultBatteryPackTemperatureNotificationConfiguration() throws InstantiationException, IllegalAccessException {
		SetBatteryPackTemperatureNotificationConfiguration mConfig = null;
		mConfig = SetBatteryPackTemperatureNotificationConfiguration.newBuilder()
				.setHighCellGroupTemperatureAlarm(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_ALARM_SET).setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_ALARM_CLEAR))
				.setHighCellGroupTemperatureWarning(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARNING_SET).setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARNING_CLEAR))
				.setHighCellGroupTemperatureWarrantyAlarm(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARRANTY_ALARM_SET)
						.setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARRANTY_ALARM_CLEAR))
				.setHighCellGroupTemperatureWarrantyWarning(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARRANTY_WARNING_SET)
						.setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_WARRANTY_WARNING_SET))
				.setLowCellGroupTemperatureAlarm(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_ALARM_SET).setClear(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_ALARM_CLEAR))
				.setLowCellGroupTemperatureWarning(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARNING_SET).setClear(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARNING_CLEAR))
				.setLowCellGroupTemperatureWarrantyAlarm(
						TemperatureTrigger.newBuilder().setEnabled(true).setSet(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARRANTY_ALARM_SET)
								.setClear(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARRANTY_ALARM_CLEAR))
				.setLowCellGroupTemperatureWarrantyWarning(TemperatureTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARRANTY_WARNING_SET)
						.setClear(stackType.getCellTemperatureLimits().CELL_LOW_TEMPERATURE_WARRANTY_WARNING_CLEAR))
				.setHighCellGroupTemperatureDeltaAlarm(
						TemperatureTrigger.newBuilder().setEnabled(true).setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_DELTA_ALARM_SET)
								.setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_DELTA_ALARM_CLEAR))
				.setHighCellGroupTemperatureDeltaWarning(
						TemperatureTrigger.newBuilder().setEnabled(true).setSet(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_DELTA_WARNING_SET)
								.setClear(stackType.getCellTemperatureLimits().CELL_HIGH_TEMPERATURE_DELTA_WARNING_CLEAR))
				.build();
		return mConfig;
	}

	private Command getCommand(SetBatteryPackTemperatureNotificationConfiguration mConfig) {
		Command mCommand = Command.newBuilder().setCommandId(UUID.randomUUID().toString())
				.setCommandPayload(
						CommandPayload.newBuilder().setSetBatteryPackTemperatureNotificationConfiguration(mConfig))
				.setCommandSource(Endpoint.newBuilder().setEndpointType(EndpointType.GOBLIN))
				.setCommandTarget(Endpoint.newBuilder().setEndpointType(EndpointType.ARRAY).setStationCode(cStationCode)
						.setStringIndex(cStringIndex).setArrayIndex(cArrayIndex))
				.build();
		return mCommand;
	}

	private void postCommand(Command command) {
		AweCommon.postCommand(command);
	}

	public void setNotification(NotificationCodes awType, int set, int clear) throws IOException, InstantiationException, IllegalAccessException {
		switch (awType) {
		case CELL_HIGH_TEMPERATURE_WARNING:
			setHighCellGroupTemperatureWarning(set, clear);
			break;
		case CELL_LOW_TEMPERATURE_WARNING:
			setLowCellGroupTemperatureWarning(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_WARNING:
			setHighCellGroupTemperatureDeltaWarning(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_ALARM:
			setHighCellGroupTemperatureAlarm(set, clear);
			break;
		case CELL_LOW_TEMPERATURE_ALARM:
			setLowCellGroupTemperatureAlarm(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_ALARM:
			setHighCellGroupTemperatureDeltaAlarm(set, clear);
			break;
		default:
			break;
		}
	}

	public void setNotification(NotificationCodes awType) throws IOException, ModbusException, InterruptedException, InstantiationException, IllegalAccessException {
		int set = 0, clear = 0;
		set = getSetValue(awType);
		clear = getClearValue(awType);
		;

		switch (awType) {
		case CELL_HIGH_TEMPERATURE_WARNING:
			setHighCellGroupTemperatureWarning(set, clear);
			break;
		case CELL_LOW_TEMPERATURE_WARNING:
			setLowCellGroupTemperatureWarning(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_WARNING:
			setHighCellGroupTemperatureDeltaWarning(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_ALARM:
			setHighCellGroupTemperatureAlarm(set, clear);
			break;
		case CELL_LOW_TEMPERATURE_ALARM:
			setLowCellGroupTemperatureAlarm(set, clear);
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_ALARM:
			setHighCellGroupTemperatureDeltaAlarm(set, clear);
			break;
		default:
			break;
		}
	}

	public void resetNotification() throws IOException, InterruptedException, InstantiationException, IllegalAccessException {
		resetDefaultsBatteryPackTemperatureNotificationConfiguration();
		Thread.sleep(5000);
	}

	private int getClearValue(NotificationCodes notificationType) throws IOException, ModbusException, InterruptedException {
		int clearValue = 0;
		switch (notificationType) {
		case CELL_HIGH_TEMPERATURE_WARNING:

			break;
		case CELL_LOW_TEMPERATURE_WARNING:

			break;
		case CELL_HIGH_TEMPERATURE_DELTA_WARNING:

			break;
		case CELL_HIGH_TEMPERATURE_ALARM:
			clearValue = getSetValue(NotificationCodes.CELL_HIGH_TEMPERATURE_ALARM) - 5;
			break;
		case CELL_LOW_TEMPERATURE_ALARM:
			clearValue = getSetValue(NotificationCodes.CELL_LOW_TEMPERATURE_ALARM) + 5;
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_ALARM:
			clearValue = 0;// getSetValue(CELL_HIGH_TEMPERATURE_DELTA_ALARM)
							// ==0?0:getSetValue("CELL_HIGH_TEMPERATURE_DELTA_ALARM")- 1;
			break;
		default:
			break;
		}
		return clearValue;
	}

	private int getSetValue(NotificationCodes notificationType) throws IOException, ModbusException, InterruptedException {
		int setValue = 0;
		Reports strReport = new Reports(cArrayIndex + "," + cStringIndex);
		int cellTemperatureAverage = Integer.parseInt(strReport.getAvgCellGroupTemperature()) / 10;
		int cellTemperatureMinimum = Integer.parseInt(strReport.getMinCellGroupTemperature()) / 10;
		switch (notificationType) {
		case CELL_HIGH_TEMPERATURE_WARNING:
			break;
		case CELL_LOW_TEMPERATURE_WARNING:
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_WARNING:
			break;
		case CELL_HIGH_TEMPERATURE_ALARM:
			// setValue = cellTemperatureAverage - 3;
			setValue = cellTemperatureMinimum - 5;
			break;
		case CELL_LOW_TEMPERATURE_ALARM:
			setValue = cellTemperatureAverage + 3;
			break;
		case CELL_HIGH_TEMPERATURE_DELTA_ALARM:
			setValue = 0;// cellTemperatureDelta >3?cellTemperatureDelta - 3:0;
			break;
		default:
			break;
		}
		return setValue;
	}

	public String getCurrentNotifications() throws IOException {
		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		LOG.info("notificationList:" + notificationList);
		return CommonHelper.convertArrayListToString(notificationList);
	}
	
	//TODO Pull up to base class
		public boolean runTest(NotificationCodes notificationType) throws IOException,
			InterruptedException, JSchException, KeyManagementException, NoSuchAlgorithmException, ModbusException, InstantiationException, IllegalAccessException {
			int setValue = getSetValue(notificationType);
			int clearValue = getClearValue(notificationType);
			return runTest(notificationType, setValue, clearValue);
		}
		
		public boolean runTest(NotificationCodes notificationType, int setValue, int clearValue)
				throws IOException, InterruptedException, JSchException, KeyManagementException, NoSuchAlgorithmException, InstantiationException, IllegalAccessException {
			boolean isTestPass = true;
			// Close contactors
			Contactors.closeContactors(cArrayIndex, cStringIndex);
			// Set notification
			setNotification(notificationType, setValue, clearValue);
			// Verify notification appeared
			int timeout = 120;
			isTestPass = AweCommon.verifyNotificationAppeared(notificationType, setValue, clearValue, timeout);
			//Verify contactor status
			Contactors.closeContactors(cArrayIndex, cStringIndex);
			AweCommon.verifyContactorBehaviorWhenNotificationSet(notificationType,timeout);
			// Reset warnings to defaults
			resetNotification();
			// Verify notification cleared
			AweCommon.verifyNotificationsCleared( notificationType,  timeout);
			//Verify contactor status
			AweCommon.verifyContactorsClosed(timeout,"after "+notificationType.name()+" cleared.");
			return isTestPass;
		}

	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException,
			NoSuchAlgorithmException, JSchException, ModbusException, InstantiationException, IllegalAccessException {
		BatteryPackTemperatureNotifications mBatteryPackTemperatureNotifications = new BatteryPackTemperatureNotifications( PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());

		mBatteryPackTemperatureNotifications.resetDefaultsBatteryPackTemperatureNotificationConfiguration();
		mBatteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_HIGH_TEMPERATURE_ALARM);
		mBatteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_LOW_TEMPERATURE_ALARM);
		mBatteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_HIGH_TEMPERATURE_DELTA_ALARM);
	}
}