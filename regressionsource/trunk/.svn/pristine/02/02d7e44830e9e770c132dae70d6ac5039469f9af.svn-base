package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.power.movePower;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandPayload;
import com.powin.tongue.fourba.command.Endpoint;
import com.powin.tongue.fourba.command.EndpointType;
import com.powin.tongue.fourba.command.SetBatteryPackVoltageNotificationConfiguration;
import com.powin.tongue.fourba.command.VoltageTrigger;

public class BatteryPackTemperatureNotificationCommands {
	final static Logger LOG = LogManager.getLogger(BatteryPackTemperatureNotificationCommands.class.getName());
	private static String cStationCode;
	private static int cArrayIndex;
	private static int cStringIndex;
	static StackType stackType = SystemInfo.getStackType();
	
	
	public BatteryPackTemperatureNotificationCommands(int arrayIndex, int stringIndex) {
		cStationCode = SystemInfo.getStationCode();
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;
	}

	public static void disableBatteryPackVoltageNotificationConfiguration() {
		SetBatteryPackVoltageNotificationConfiguration mConfig = BatteryPackTemperatureNotificationCommands.getDisabledBatteryPackVoltageNotificationConfiguration();
		Command mCommand = BatteryPackTemperatureNotificationCommands.getCommand(mConfig);
		BatteryPackTemperatureNotificationCommands.postCommand(mCommand);
	}

	public static void disableWarning(String awType) throws IOException, InterruptedException {
		disableBatteryPackVoltageNotificationConfiguration();
		Thread.sleep(5000);
	}

	public static void resetDefaultsBatteryPackVoltageNotificationConfiguration(){
		SetBatteryPackVoltageNotificationConfiguration mConfig = BatteryPackTemperatureNotificationCommands.getDefaultBatteryPackVoltageNotificationConfiguration();
		Command mCommand = BatteryPackTemperatureNotificationCommands.getCommand(mConfig);
		BatteryPackTemperatureNotificationCommands.postCommand(mCommand);
	}

	public static void resetNotification() {
		resetDefaultsBatteryPackVoltageNotificationConfiguration();
		CommonHelper.quietSleep(5000);
	}

	public static void setNotification(NotificationCodes awType, int set, int clear) {
		switch (awType) {
		case CELL_HIGH_VOLTAGE_WARNING:
			BatteryPackTemperatureNotificationCommands.setHighCellGroupVoltageWarning(set, clear);
			break;
		case CELL_LOW_VOLTAGE_WARNING:
			BatteryPackTemperatureNotificationCommands.setLowCellGroupVoltageWarning(set, clear);
			break;
		case CELL_HIGH_VOLTAGE_DELTA_WARNING:
			BatteryPackTemperatureNotificationCommands.setHighCellGroupVoltageDeltaWarning(set, clear);
			break;
		case CELL_HIGH_VOLTAGE_ALARM:
			BatteryPackTemperatureNotificationCommands.setHighCellGroupVoltageAlarm(set, clear);
			break;
		case CELL_LOW_VOLTAGE_ALARM:
			BatteryPackTemperatureNotificationCommands.setLowCellGroupVoltageAlarm(set, clear);
			break;
		case CELL_HIGH_VOLTAGE_DELTA_ALARM:
			BatteryPackTemperatureNotificationCommands.setHighCellGroupVoltageDeltaAlarm(set, clear);
			break;
		default:
			break;
		}
	}

	public static int getClearValue(NotificationCodes notificationType, int setValue) {
		int clearValue = 0;
		switch (notificationType) {
		case CELL_HIGH_VOLTAGE_WARNING:
			break;
		case CELL_LOW_VOLTAGE_WARNING:
			break;
		case CELL_HIGH_VOLTAGE_DELTA_WARNING:
			break;
		case CELL_HIGH_VOLTAGE_ALARM:
			clearValue = setValue== 0 ? 0 : setValue- 10;
			break;
		case CELL_LOW_VOLTAGE_ALARM:
			clearValue = setValue == 0 ? 0 : setValue + 10;
			break;
		case CELL_HIGH_VOLTAGE_DELTA_ALARM:
			clearValue = 0;
			// getSetValue("stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_ALARM") == 0 ? 0
			// : getSetValue("stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_ALARM") - 10;
			break;
		}
		return clearValue;
	}

	public static Command getCommand(SetBatteryPackVoltageNotificationConfiguration mConfig) {
		Command mCommand = Command.newBuilder().setCommandId(UUID.randomUUID().toString())
				.setCommandPayload(
						CommandPayload.newBuilder().setSetBatteryPackVoltageNotificationConfiguration(mConfig))
				.setCommandSource(Endpoint.newBuilder().setEndpointType(EndpointType.GOBLIN))
				.setCommandTarget(Endpoint.newBuilder().setEndpointType(EndpointType.ARRAY).setStationCode(cStationCode)
						.setStringIndex(cStringIndex).setArrayIndex(cArrayIndex))
				.build();
		return mCommand;
	}

	public static SetBatteryPackVoltageNotificationConfiguration getDefaultBatteryPackVoltageNotificationConfiguration()  {
		SetBatteryPackVoltageNotificationConfiguration mConfig = SetBatteryPackVoltageNotificationConfiguration
				.newBuilder()
				.setHighCellGroupVoltageAlarm(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_ALARM_SET).setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_ALARM_CLEAR))
				.setHighCellGroupVoltageWarning(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARNING_SET).setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARNING_CLEAR))
				.setHighCellGroupVoltageWarrantyAlarm(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARRANTY_ALARM_SET).setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARRANTY_ALARM_CLEAR))
				.setHighCellGroupVoltageWarrantyWarning(
						VoltageTrigger.newBuilder().setEnabled(true).setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARRANTY_WARNING_SET)
								.setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_WARRANTY_WARNING_SET))
				.setLowCellGroupVoltageAlarm(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_ALARM_SET).setClear(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_ALARM_CLEAR))
				.setLowCellGroupVoltageWarning(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARNING_SET).setClear(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARNING_CLEAR))
				.setLowCellGroupVoltageWarrantyAlarm(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARRANTY_ALARM_SET).setClear(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARRANTY_ALARM_CLEAR))
				.setLowCellGroupVoltageWarrantyWarning(
						VoltageTrigger.newBuilder().setEnabled(true).setSet(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARRANTY_WARNING_SET)
								.setClear(stackType.getCellVoltageLimits().CELL_LOW_VOLTAGE_WARRANTY_WARNING_CLEAR))
				.setHighCellGroupVoltageDeltaAlarm(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_ALARM_SET).setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR))
				.setHighCellGroupVoltageDeltaWarning(VoltageTrigger.newBuilder().setEnabled(true)
						.setSet(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_WARNING_SET).setClear(stackType.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_WARNING_CLEAR))
				.build();
		return mConfig;
	}

	public static SetBatteryPackVoltageNotificationConfiguration getDisabledBatteryPackVoltageNotificationConfiguration() {
		SetBatteryPackVoltageNotificationConfiguration mConfig = SetBatteryPackVoltageNotificationConfiguration
				.newBuilder().setHighCellGroupVoltageAlarm(VoltageTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupVoltageWarning(VoltageTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupVoltageWarrantyAlarm(VoltageTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupVoltageWarrantyWarning(VoltageTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupVoltageAlarm(VoltageTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupVoltageWarning(VoltageTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupVoltageWarrantyAlarm(VoltageTrigger.newBuilder().setEnabled(false))
				.setLowCellGroupVoltageWarrantyWarning(VoltageTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupVoltageDeltaAlarm(VoltageTrigger.newBuilder().setEnabled(false))
				.setHighCellGroupVoltageDeltaWarning(VoltageTrigger.newBuilder().setEnabled(false)).build();
		return mConfig;
	}

	public static int getSetValue(NotificationCodes notificationType) {
		int setValue = 0;
		int cellVoltageMinimum = SystemInfo.getMinCellGroupVoltage();
		int cellVoltageAverage = SystemInfo.getAvgCellGroupVoltage();
		int cellVoltageMaximum = SystemInfo.getMaxCellGroupVoltage();
		assert cellVoltageMinimum > 0 : "cellVoltageMinimum == 0";
		assert cellVoltageAverage > 0 : "cellVoltageAverage == 0";
		assert cellVoltageMaximum > 0 : "cellVoltageMaximum == 0";
		int cellVoltageDelta = cellVoltageMaximum - cellVoltageMinimum;
		switch (notificationType) {
		case CELL_HIGH_VOLTAGE_WARNING:
			break;
		case CELL_LOW_VOLTAGE_WARNING:
			break;
		case CELL_HIGH_VOLTAGE_DELTA_WARNING:
			break;
		case CELL_HIGH_VOLTAGE_ALARM:
			setValue = cellVoltageAverage;
			break;
		case CELL_LOW_VOLTAGE_ALARM:
			if (cellVoltageMinimum > 2950) {
				 movePower.powerDown(2950);
			}
			setValue = cellVoltageMinimum > 2500 ? cellVoltageAverage : 2510;
			break;
		case CELL_HIGH_VOLTAGE_DELTA_ALARM:
			setValue = cellVoltageDelta > 200 ? cellVoltageDelta - 200 : 1;
			break;
		default:
			break;
		}
		return setValue;
	}

	// CELL Low voltage warning
	public static void setLowCellGroupVoltageWarning(int set, int clear) {
		if (set < clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setLowCellGroupVoltageWarning(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			BatteryPackTemperatureNotificationCommands.postCommand(mCommand);
		}
	}

	public static void postCommand(Command command) {
		AweCommon.postCommand(command);
	}

	// CELL Low voltage alarm
	public static void setLowCellGroupVoltageAlarm(int set, int clear) {
		if (set < clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setLowCellGroupVoltageAlarm(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// CELL High voltage warning
	public static void setHighCellGroupVoltageWarning(int set, int clear) {
		if (set > clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setHighCellGroupVoltageWarning(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// Cell high voltage delta warning
	public static void setHighCellGroupVoltageDeltaWarning(int set, int clear) {
		if (set > clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setHighCellGroupVoltageDeltaWarning(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// Cell high voltage delta alarm
	public static void setHighCellGroupVoltageDeltaAlarm(int set, int clear) {
		if (set > clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setHighCellGroupVoltageDeltaAlarm(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

	// CELL High voltage alarm
	public static void setHighCellGroupVoltageAlarm(int set, int clear) {
		if (set > clear) {
			SetBatteryPackVoltageNotificationConfiguration mConfig = getDisabledBatteryPackVoltageNotificationConfiguration();
			mConfig = SetBatteryPackVoltageNotificationConfiguration.newBuilder(mConfig).setHighCellGroupVoltageAlarm(
					VoltageTrigger.newBuilder().setEnabled(true).setSet(set).setClear(clear)).build();
			Command mCommand = getCommand(mConfig);
			postCommand(mCommand);
		}
	}

}
