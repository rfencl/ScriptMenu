package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.PowinProperty;
import com.powin.powinwebappbase.HttpHelper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandPayload;
import com.powin.tongue.fourba.command.CommandResponse;
import com.powin.tongue.fourba.command.Endpoint;
import com.powin.tongue.fourba.command.EndpointType;
import com.powin.tongue.fourba.command.SetStringContactorSafetyConfiguration;

public class StringContactorSafetyNotifications {

	private final static Logger LOG = LogManager.getLogger();
	private HttpClient cHttpClient = HttpHelper.buildHttpClient(true);
	private String cRemoteUrl;
	private String cStationCode;
	private int cArrayIndex;
	private int cStringIndex;
	private LastCalls cLastCalls;
	private static Balancing cBalancing = new Balancing(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString());

	// These values are from the recommended settings document created by Dylan
	// Vance:
	// https://powin365.sharepoint.com/Project%20Success/Forms/AllItems.aspx?FolderCTID=0x012000145D0C9521F3D0459B3D7A629243BE33&View=%7B5ED3F878%2D3088%2D433A%2DA325%2D287DD1CDF95F%7D&InitialTabId=Ribbon%2ERead&VisibilityContext=WSSTabPersistence&CT=1584566630014&OR=OWA%2DNT&CID=b10ae0ab%2Df606%2Dac45%2D65a4%2Dd1762957860a&id=%2FProject%20Success%2FPowin%20Energy%20Client%20Documentation%20Suite%2FProduct%20Client%20Documentation%2FProduct%20Guides%2FPowin%20Default%20System%20Configurations%20V1%2E0%2Epdf&parent=%2FProject%20Success%2FPowin%20Energy%20Client%20Documentation%20Suite%2FProduct%20Client%20Documentation%2FProduct%20Guides
	// DEFAULTS: String Contactor Safety Notifications
	private static final int STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT = 990;
	private static final int STRING_CONTACTOR_LOW_VOLTAGE_LIMIT = 750;
	private static final int STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT = 45;
	private static final int STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT = -5;
	private static final int STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT = 0;// spec says 500mV but UI allows V
	private static final int STRING_CONTACTOR_CURRENT_LIMIT = 260;
	private static final int STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT = 10;
	private static final int STRING_CONTACTOR_RECLOSE_TIME = 3000;// spec says 60000
	// #endregion

	public StringContactorSafetyNotifications(int arrayIndex, int stringIndex) {
		cLastCalls = new LastCalls();
		cStationCode = cLastCalls.getStationCode();
		String append = String.valueOf(arrayIndex) + "/" + String.valueOf(stringIndex);
		cRemoteUrl = PowinProperty.TURTLE_URL.toString() + "/" + PowinProperty.PHOENIX_COMMAND_INJECT_URL.toString() + append;
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
	}
	public StringContactorSafetyNotifications(String url, int arrayIndex, int stringIndex) {
		cLastCalls = new LastCalls();
		cStationCode = cLastCalls.getStationCode();
		cRemoteUrl = url;
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
		
	}
	// CELL Low voltage warning
	public void setStringContactorHighVoltageLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorHighVoltageLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);
	}

	private void setStringContactorLowVoltageLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorLowVoltageLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorHighTemperatureLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorHighTempLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorLowTemperatureLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorLowTempLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorVoltageDeltaLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorVoltageDeltaLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorCurrentLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorCurrentLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorRecloseTime(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorRecloseTime(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	private void setStringContactorStackBusVoltageDeltaLimit(int set) {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		mConfig = SetStringContactorSafetyConfiguration.newBuilder(mConfig).setStringcontactorStackBusVoltageDeltaLimit(set).build();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);

	}

	public void resetDefaultSetStringContactorSafetyConfiguration() {
		SetStringContactorSafetyConfiguration mConfig = getDefaultStringContactorSafetyConfiguration();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);
	}

	public void disableSetStringContactorSafetyConfiguration() {
		SetStringContactorSafetyConfiguration mConfig = getDisabledStringContactorSafetyConfiguration();
		Command mCommand = getCommand(mConfig);
		postCommand(mCommand);
	}

	private SetStringContactorSafetyConfiguration getDisabledStringContactorSafetyConfiguration() {
		SetStringContactorSafetyConfiguration mConfig = SetStringContactorSafetyConfiguration.newBuilder()
				.setStringcontactorHighVoltageLimit(2 * STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT)
				.setStringcontactorLowVoltageLimit(STRING_CONTACTOR_LOW_VOLTAGE_LIMIT / 2)
				.setStringcontactorHighTempLimit(2 * STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT)
				.setStringcontactorLowTempLimit(STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT / 2)
				.setStringcontactorVoltageDeltaLimit(2 * STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT)
				.setStringcontactorCurrentLimit(2 * STRING_CONTACTOR_CURRENT_LIMIT)
				.setStringcontactorRecloseTime(10 * STRING_CONTACTOR_RECLOSE_TIME)
				.setStringcontactorStackBusVoltageDeltaLimit(10 * STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT)
				.build();
		return mConfig;
	}

	public final static SetStringContactorSafetyConfiguration getDefaultStringContactorSafetyConfiguration() {
		return SetStringContactorSafetyConfiguration.newBuilder()
				.setStringcontactorHighVoltageLimit(STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT)
				.setStringcontactorLowVoltageLimit(STRING_CONTACTOR_LOW_VOLTAGE_LIMIT)
				.setStringcontactorHighTempLimit(STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT)
				.setStringcontactorLowTempLimit(STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT)
				.setStringcontactorVoltageDeltaLimit(STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT)
				.setStringcontactorCurrentLimit(STRING_CONTACTOR_CURRENT_LIMIT)
				.setStringcontactorRecloseTime(STRING_CONTACTOR_RECLOSE_TIME)
				.setStringcontactorStackBusVoltageDeltaLimit(STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT).build();
	}

	private Command getCommand(SetStringContactorSafetyConfiguration mConfig) {
		Command mCommand = Command.newBuilder().setCommandId(UUID.randomUUID().toString())
				.setCommandPayload(CommandPayload.newBuilder().setSetStringContactorSafetyConfiguration(mConfig))
				.setCommandSource(Endpoint.newBuilder().setEndpointType(EndpointType.GOBLIN))
				.setCommandTarget(Endpoint.newBuilder().setEndpointType(EndpointType.ARRAY).setStationCode(cStationCode)
						.setStringIndex(cStringIndex).setArrayIndex(cArrayIndex))
				.build();
		return mCommand;
	}

	private void postCommand(Command command) {
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

	public void setNotification(String awType, int set) throws IOException {
		switch (awType) {
		case "STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT":
			setStringContactorHighVoltageLimit(set);
			break;
		case "STRING_CONTACTOR_LOW_VOLTAGE_LIMIT":
			setStringContactorLowVoltageLimit(set);
			break;
		case "STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT":
			setStringContactorHighTemperatureLimit(set);
			break;
		case "STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT":
			setStringContactorLowTemperatureLimit(set);
			break;
		case "STRING_CONTACTOR__VOLTAGE_DELTA_LIMIT":
			setStringContactorVoltageDeltaLimit(set);
			break;
		case "STRING_CONTACTOR_CURRENT_LIMIT":
			setStringContactorCurrentLimit(set);
			break;
		case "STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT":
			setStringContactorStackBusVoltageDeltaLimit(set);
			break;
		case "STRING_CONTACTOR_RECLOSE_TIME":
			setStringContactorRecloseTime(set);
			break;

		}
	}

	public void resetNotification(String awType) throws IOException, InterruptedException {
		resetDefaultSetStringContactorSafetyConfiguration();
		Thread.sleep(5000);
	}

	private String getNotificationCode(String notificationType) {
		String notificationCode = "";
		switch (notificationType.toUpperCase()) {
		case "STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT":
		case "STRING_CONTACTOR_LOW_VOLTAGE_LIMIT":
		case "STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT":
		case "STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT":
		case "STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT":
		case "STRING_CONTACTOR_CURRENT_LIMIT":
		case "STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT":
			notificationCode = "2534";
			break;
		}
		return notificationCode;
	}

	public void runTest(String notificationType, int setValue)
			throws IOException, InterruptedException, JSchException, KeyManagementException, NoSuchAlgorithmException {
		resetDefaultSetStringContactorSafetyConfiguration();
		Contactors.closeContactors(cArrayIndex, cStringIndex);
//		cBalancing.openContactors(String.valueOf(cArrayIndex), String.valueOf(cStringIndex));

		// Set notification
		// TODO: Get the current cell max,min and avg voltage levels
		System.out.println("setting notification " + notificationType + "...waiting");
		setNotification(notificationType, setValue);
		Thread.sleep(25000);

		String notificationCode = getNotificationCode(notificationType);
		if (didNotificationAppear(notificationCode)) {
			System.out.println(
					"PASS: Notification " + notificationCode + " appeared for notification " + notificationType);
		} else {
			System.out.println(
					"FAIL: Notification " + notificationCode + " did not appear for notification " + notificationType);
		}
		// Check if contactors opened
		Thread.sleep(15000);
		if (didNotificationAppear("2534")) {
			System.out.println(" - Contactors opened after setting notification: " + notificationType);
		}
		// Reset warnings to the original value
		System.out.println("clearing notification " + notificationType + "...waiting");
		resetNotification(notificationType);
		Thread.sleep(25000);

		if (!didNotificationAppear(notificationCode)) {
			System.out.println(
					"PASS: Notification " + notificationCode + " cleared for notification " + notificationType);

		} else {
			System.out.println(
					"FAIL: Notification " + notificationCode + " did not clear for notification " + notificationType);
		}
		// Check if contactors opened
		Thread.sleep(15000);
		if (didNotificationAppear("2534")) {
			System.out.println(" - Contactors did not reclose after clearing notification: " + notificationType);
		}
	}

	public boolean didNotificationAppear(String notificationCode) throws IOException {
		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		System.out.println("notificationList:" + notificationList);
		return notificationList.indexOf(notificationCode) != -1 ? true : false;
	}

	public static void main(String[] args)
			throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, JSchException {
		StringContactorSafetyNotifications mStringContactorSafetyNotifications = new StringContactorSafetyNotifications(
				PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT",
//				STRING_CONTACTOR_LOW_VOLTAGE_LIMIT + 10);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_LOW_VOLTAGE_LIMIT", STRING_CONTACTOR_HIGH_VOLTAGE_LIMIT-10);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT", STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT+5);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_LOW_TEMPERATURE_LIMIT", STRING_CONTACTOR_HIGH_TEMPERATURE_LIMIT-5);
		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT", STRING_CONTACTOR_VOLTAGE_DELTA_LIMIT);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_CURRENT_LIMIT", 10);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_STACK_BUS_VOLTAGE_DELTA_LIMIT", 0);
//		mStringContactorSafetyNotifications.runTest("STRING_CONTACTOR_RECLOSE_TIME", 800);

	}
}