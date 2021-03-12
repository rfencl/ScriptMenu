package com.powin.modbusfiles.awe;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.joda.time.DateTimeUtils;
import org.json.simple.parser.ParseException;

import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.tongue.fourba.command.Command;
import com.powin.tongue.fourba.command.CommandPayload;
import com.powin.tongue.fourba.command.Endpoint;
import com.powin.tongue.fourba.command.EndpointType;
import com.powin.tongue.fourba.command.SetInverterSafetyConfiguration;


public class InverterSafetyCommands {

	public static void setDefaultInverterSafetyConfiguration() {
		SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}

	public static void setInverterChargeHighTempLimit(float setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeHighTempLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterChargeHighVoltageLimit(int set) {
		if (set > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeHighVoltageLimit(set).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}
	
	public static void setInverterChargeHighVoltageLimitWithHysteresis(int set, int hysteresis) {
		if (set > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeHighVoltageLimit(set).build();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setVoltageLimitRecoveryHysteresis(hysteresis).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterChargeLowTempLimit(float setValue) {
		SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
		mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeLowTempLimit(setValue).build();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}

	public static void setInverterChargeLowVoltageLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeLowVoltageLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterChargeMinStringCount(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeMinStringCount(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterChargeVoltageDeltaLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterChargeVoltageDeltaLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterDischargeHighTempLimit(float setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeHighTempLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterDischargeHighVoltageLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeHighVoltageLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterDischargeLowTempLimit(float setValue) {
		SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
		mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeLowTempLimit(setValue).build();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}

	public static void setInverterDischargeLowVoltageLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeLowVoltageLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterDischargeLowVoltageLimitWithHysteresis(int setValue, int hysteresis) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeLowVoltageLimit(setValue).build();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setVoltageLimitRecoveryHysteresis(hysteresis).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	};

	public static void setInverterDischargeMinStringCount(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeMinStringCount(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterDischargeVoltageDeltaLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterDischargeVoltageDeltaLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterFailureRecoveryTime(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setFailureRecoveryTimeMilliseconds(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationHighTempLimit(float setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationHighTempLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationHighVoltageLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationHighVoltageLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationHighVoltageLimitWithHysteresis(int setValue, int hysteresis) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationHighVoltageLimit(setValue).build();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setVoltageLimitRecoveryHysteresis(hysteresis).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationLowTempLimit(float setValue) {
		SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
		mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationLowTempLimit(setValue).build();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}

	public static void setInverterOperationLowVoltageLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationLowVoltageLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationLowVoltageLimitWithHysteresis(int setValue, int hysteresis) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationLowVoltageLimit(setValue).build();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setVoltageLimitRecoveryHysteresis(hysteresis).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationMinStringCount(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationMinStringCount(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setInverterOperationVoltageDeltaLimit(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setInverterOperationVoltageDeltaLimit(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}

	public static void setVoltageLimitRecoveryHysteresis(int setValue) {
		if (setValue > 0) {
			SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
			mConfig = SetInverterSafetyConfiguration.newBuilder(mConfig).setVoltageLimitRecoveryHysteresis(setValue).build();
			Command mCommand = getCommand(mConfig);
			AweCommon.postCommand(mCommand);
		}
	}
	
	public static Command getCommand(SetInverterSafetyConfiguration mConfig) {
		Command mCommand = Command.newBuilder().setCommandId(UUID.randomUUID().toString())
				.setCommandPayload(CommandPayload.newBuilder().setSetInverterSafetyConfiguration(mConfig))
				.setCommandSource(Endpoint.newBuilder().setEndpointType(EndpointType.GOBLIN))
				.setCommandTarget(Endpoint.newBuilder().setEndpointType(EndpointType.BLOCK)).build();
		return mCommand;
	}

	public static SetInverterSafetyConfiguration getDefaultInverterSafetyConfiguration() {
		SetInverterSafetyConfiguration mConfig = null;
		mConfig = SetInverterSafetyConfiguration.newBuilder()
				.setInverterChargeHighVoltageLimit(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT.intValue())
				.setInverterDischargeHighVoltageLimit(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT.intValue())
				.setInverterOperationHighVoltageLimit(InverterSafetyLimits.INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT.intValue())
				.setInverterChargeLowVoltageLimit(InverterSafetyLimits.INVERTER_CHARGE_LOW_VOLTAGE_LIMIT.intValue())
				.setInverterDischargeLowVoltageLimit(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT.intValue())
				.setInverterOperationLowVoltageLimit(InverterSafetyLimits.INVERTER_OPERATION_LOW_VOLTAGE_LIMIT.intValue())
				.setInverterChargeHighTempLimit(InverterSafetyLimits.INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT.floatValue())
				.setInverterDischargeHighTempLimit(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT.floatValue())
				.setInverterOperationHighTempLimit(InverterSafetyLimits.INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT.floatValue())
				.setInverterChargeLowTempLimit(InverterSafetyLimits.INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT.floatValue())
				.setInverterDischargeLowTempLimit(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT.floatValue())
				.setInverterOperationLowTempLimit(InverterSafetyLimits.INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT.floatValue())
				.setInverterChargeVoltageDeltaLimit(InverterSafetyLimits.INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT.intValue())
				.setInverterDischargeVoltageDeltaLimit(InverterSafetyLimits.INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT.intValue())
				.setInverterOperationVoltageDeltaLimit(InverterSafetyLimits.INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT.intValue())
				.setInverterChargeMinStringCount(InverterSafetyLimits.INVERTER_CHARGE_MIN_STRING_COUNT.intValue())
				.setInverterDischargeMinStringCount(InverterSafetyLimits.INVERTER_DISCHARGE_MIN_STRING_COUNT.intValue())
				.setInverterOperationMinStringCount(InverterSafetyLimits.INVERTER_DISCHARGE_MIN_STRING_COUNT.intValue())
				.setFailureRecoveryTimeMilliseconds(InverterSafetyLimits.INVERTER_FAILURE_RECOVERY_MS.intValue())
				.setVoltageLimitRecoveryHysteresis(InverterSafetyLimits.VOLTAGE_LIMIT_HYSTERESIS.intValue())
				.setUseWarnings(false).build();
		return mConfig;

	}
	
	public void disableInverterSafetyNotificationConfiguration() {
		SetInverterSafetyConfiguration mConfig = getDisabledInverterSafetyConfiguration();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}
	
	public static void setConfiguration(InverterSafetyLimits inverterSafetyConfigItem, int set)  {
		switch (inverterSafetyConfigItem) {
		case INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT:
			InverterSafetyCommands.setInverterChargeHighVoltageLimit(set);
			break;
		case INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT:
			setInverterDischargeHighVoltageLimit(set);
			break;
		case INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT:
			setInverterOperationHighVoltageLimit(set);
			break;
		case INVERTER_CHARGE_LOW_VOLTAGE_LIMIT:
			setInverterChargeLowVoltageLimit(set);
			break;
		case INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT:
			setInverterDischargeLowVoltageLimit(set);
			break;
		case INVERTER_OPERATION_LOW_VOLTAGE_LIMIT:
			setInverterOperationLowVoltageLimit(set);
			break;

		case INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT:
			InverterSafetyCommands.setInverterChargeHighTempLimit(set);
			break;
		case INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT:
			setInverterDischargeHighTempLimit(set);
			break;
		case INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT:
			setInverterOperationHighTempLimit(set);
			break;
		case INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT:
			setInverterChargeLowTempLimit(set);
			break;
		case INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT:
			setInverterDischargeLowTempLimit(set);
			break;
		case INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT:
			setInverterOperationLowTempLimit(set);
			break;
		case INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT:
			setInverterChargeVoltageDeltaLimit(set);
			break;
		case INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT:
			setInverterDischargeVoltageDeltaLimit(set);
			break;
		case INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT:
			setInverterOperationVoltageDeltaLimit(set);
			break;
		case INVERTER_CHARGE_MIN_STRING_COUNT:
			setInverterChargeMinStringCount(set);
			break;
		case INVERTER_DISCHARGE_MIN_STRING_COUNT:
			setInverterDischargeMinStringCount(set);
			break;
		case INVERTER_OPERATION_MIN_STRING_COUNT:
			setInverterOperationMinStringCount(set);
			break;
		case INVERTER_FAILURE_RECOVERY_MS:
			setInverterFailureRecoveryTime(set);
			break;
		case VOLTAGE_LIMIT_HYSTERESIS:
			setVoltageLimitRecoveryHysteresis(set);
			break;
		default:
			break;
		}
	}
	
	private SetInverterSafetyConfiguration getDisabledInverterSafetyConfiguration() {
		// TO DO setEnabled false
		SetInverterSafetyConfiguration mConfig = null;
		mConfig = SetInverterSafetyConfiguration.newBuilder().setInverterChargeHighVoltageLimit(900)
				.setInverterDischargeHighVoltageLimit(900).setInverterOperationHighVoltageLimit(900)
				.setInverterChargeLowVoltageLimit(750).setInverterDischargeLowVoltageLimit(750)
				.setInverterOperationLowVoltageLimit(750).setInverterChargeHighTempLimit(60.0F)
				.setInverterDischargeHighTempLimit(60.0F).setInverterOperationHighTempLimit(60.0F)
				.setInverterChargeLowTempLimit(0.0F).setInverterDischargeLowTempLimit(0.0F)
				.setInverterOperationLowTempLimit(0.0F).setInverterChargeHighSocLimit(99)
				.setInverterDischargeHighSocLimit(99).setInverterOperationHighSocLimit(99)
				.setInverterChargeLowSocLimit(0).setInverterDischargeLowSocLimit(0).setInverterOperationLowSocLimit(0)
				.setInverterChargeVoltageDeltaLimit(2000).setInverterDischargeVoltageDeltaLimit(2000)
				.setInverterOperationVoltageDeltaLimit(2000).setInverterChargeCurrentLimit(200.0F)
				.setInverterDischargeCurrentLimit(200.0F).setInverterOperationCurrentLimit(200.0F)
				.setInverterChargeMinStringCount(0).setInverterDischargeMinStringCount(0)
				.setInverterOperationMinStringCount(0)
				// .setFailureRecoveryTimeMilliseconds(10000)
				// .setVoltageLimitRecoveryHysteresis(20).setUseWarnings(false)
				.build();
		return mConfig;
	}

	public static String getExpectedBsfMessage(InverterSafetyLimits inverterSafetyConfigItem) {
		String expectedBsfMessage = "";
		switch (inverterSafetyConfigItem) {
		case INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:HV,CHRG:HVH";
			break;
		case INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:HV,DISC:HVH";
			break;
		case INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:HV,OPS:HVH";
			break;
	
		case INVERTER_CHARGE_LOW_VOLTAGE_LIMIT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:LV,CHRG:LVH";
			break;
		case INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:LV,DISC:LVH";
			break;
		case INVERTER_OPERATION_LOW_VOLTAGE_LIMIT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:LV,OPS:LVH";
			break;
	
		case INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:HT";
			break;
		case INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:HT";
			break;
		case INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:HT";
			break;
	
		case INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:LT";
			break;
		case INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:LT";
			break;
		case INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:LT";
			break;
	
		case INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:VD";
			break;
		case INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:VD";
			break;
		case INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:VD";
			break;
	
		case INVERTER_CHARGE_MIN_STRING_COUNT:
			expectedBsfMessage = "CHG-BLOCKED|CHRG:SC";
			break;
		case INVERTER_DISCHARGE_MIN_STRING_COUNT:
			expectedBsfMessage = "DISCHG-BLOCKED|DISC:SC";
			break;
		case INVERTER_OPERATION_MIN_STRING_COUNT:
			expectedBsfMessage = "OPS-BLOCKED|OPS:SC";
			break;
	    // TODO get appropriate values
		case INVERTER_FAILURE_RECOVERY_MS:
			expectedBsfMessage = "2001";
			break;
		case VOLTAGE_LIMIT_HYSTERESIS:
			expectedBsfMessage = "2004";
			break;
		default:
			break;
		}
		return expectedBsfMessage;
	}

	public static boolean validateBsfMessagesSet(InverterSafetyLimits invConfigItem){
		boolean isTestPass = true;
		String expectedBSFMessage = getExpectedBsfMessage(invConfigItem);
		String expectedStatus = expectedBSFMessage.split("\\|")[0];
		String[] expectedErrorCodes = expectedBSFMessage.split("\\|")[1].split(",");
		ArrayList<String> errorCodesList = new ArrayList<>(Arrays.asList(expectedErrorCodes));
		boolean codesMatch = true;
		String blockStr = "";
		String codeStr = "";
		for (int i = 0; i < 30; i++) {
			CommonHelper.quietSleep(1000);
			codesMatch = true;
			SystemInfo.getLastCallsContent();
			blockStr = SystemInfo.getBsfStatus();
			codeStr = SystemInfo.getBSFMessage();
			for (String code : errorCodesList) {
				InverterSafety.LOG.debug("looking for {} in {}", code, codeStr);
				codesMatch = codeStr.contains(code);
			}
			if (codesMatch)
				break;
		}
		if ((isTestPass = blockStr.contains(expectedStatus) && codesMatch)) {
			InverterSafety.LOG.info("PASS: " + blockStr + codeStr + " appeared when setting " + invConfigItem.name());
		} else {
			InverterSafety.LOG.info("FAIL:{}{} did not appear when setting {}, Expected Status:{} Acutal message: {}|{}", blockStr,codeStr,invConfigItem.name(), expectedStatus, blockStr, codeStr);
		}
		return isTestPass;
	}

	public static boolean verifyBsfStatusGood(int mTimeoutSeconds,String logMessage) {
		//Wait for BSF app to report "GOOD"
		long mStopTime = DateTimeUtils.currentTimeMillis() + mTimeoutSeconds*InverterSafety.MILLIS_PER_SECOND;
		while(DateTimeUtils.currentTimeMillis() < mStopTime) {
			if (SystemInfo.getBsfGood().contains("GOOD")) {
				InverterSafety.LOG.info("PASS: BSF status is back to \"GOOD\" within " + mTimeoutSeconds+" seconds. "+logMessage);
				return true;
			}
			CommonHelper.quietSleep(1000);
		}
		InverterSafety.LOG.info("FAIL: BSF status is NOT back to \"GOOD\" within " + mTimeoutSeconds + " seconds. "+logMessage);
		return false;
	}

	public static boolean checkBsfMessagesSet(InverterSafetyLimits invConfigItem)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		String expectedBSFMessage = getExpectedBsfMessage(invConfigItem);
		String expectedStatus = expectedBSFMessage.split("\\|")[0];
		String[] expectedErrorCodes = expectedBSFMessage.split("\\|")[1].split(",");
		ArrayList<String> errorCodesList = new ArrayList<>(Arrays.asList(expectedErrorCodes));
		boolean codesMatch = true;
		String blockStr = "";
		String codeStr = "";
		for (int i = 0; i < 30; i++) {
			codesMatch = true;
			SystemInfo.getLastCallsContent();
			blockStr = SystemInfo.getBsfStatus();
			codeStr = SystemInfo.getBSFMessage();
			for (String code : errorCodesList) {
				if (!codeStr.contains(code)) {
					codesMatch = false;
				}
			}
			if (codesMatch)
				break;
		}
	
		if (blockStr.contains(expectedStatus) && codesMatch) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkBsfMessagesClear(InverterSafetyLimits invConfigItem)
			throws InterruptedException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		String blockStr = SystemInfo.getBsfStatus();
		if (blockStr.contains("RECOVERING") || blockStr.contains("GOOD")) {
			InverterSafety.LOG.info("PASS: errors cleared after clearing " + invConfigItem.name());
			return true;
		}
		return false;
	}

	public static void resetInverterSafetyLimits() {
		SetInverterSafetyConfiguration mConfig = getDefaultInverterSafetyConfiguration();
		Command mCommand = getCommand(mConfig);
		AweCommon.postCommand(mCommand);
	}

	public static int getSetValue(InverterSafetyLimits inverterSafetyConfigItem){
		int setValue = 0;
		Reports strReport = new Reports(InverterSafety.cArrayIndex + "," + InverterSafety.cStringIndex);
		int stringVoltageMeasured = Integer.parseInt(strReport.getMeasuredStringVoltage());
		int stringVoltageCalculated = Integer.parseInt(strReport.getCalculatedStringVoltage());
		int stringVoltageDelta = Math.abs(stringVoltageMeasured - stringVoltageCalculated);
	
		Integer.parseInt(strReport.getAvgCellGroupTemperature());
		int cellTemperatureMaximum = Integer.parseInt(strReport.getMaxCellGroupTemperature()) / 10;
		int cellTemperatureMinimum = Integer.parseInt(strReport.getMinCellGroupTemperature()) / 10;
		int connectedStringCount = SystemInfo.getStackCountCommunicating();
	
		switch (inverterSafetyConfigItem) {
		case INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured - 30;				
	
			break;
		case INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured - 30;
			break;
		case INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured - 30;
			break;
		case INVERTER_CHARGE_LOW_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured + 30;
			break;
		case INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured + 30;
			break;
		case INVERTER_OPERATION_LOW_VOLTAGE_LIMIT:
			setValue = stringVoltageMeasured + 30;
			break;
		case INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT:
			setValue = cellTemperatureMinimum - 3;
			break;
		case INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT:				
	
			setValue = cellTemperatureMinimum - 3;
			break;
		case INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT:
			setValue = cellTemperatureMinimum - 3;
			break;
		case INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT:
			setValue = cellTemperatureMaximum + 3;
			break;
		case INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT:
			setValue = cellTemperatureMaximum + 3;
			break;
		case INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT:
			setValue = cellTemperatureMaximum + 3;
			break;
		case INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT:
			setValue = stringVoltageDelta > 10 ? -stringVoltageDelta - 10 : 1;
			break;
		case INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT:
			setValue = stringVoltageDelta > 10 ? -stringVoltageDelta - 10 : 1;
			break;
		case INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT:
			setValue = stringVoltageDelta > 10 ? -stringVoltageDelta - 10 : 1;
			break;
		case INVERTER_CHARGE_MIN_STRING_COUNT:
			setValue = connectedStringCount + 1;
			break;
		case INVERTER_DISCHARGE_MIN_STRING_COUNT:
			setValue = connectedStringCount + 1;
			break;
		case INVERTER_OPERATION_MIN_STRING_COUNT:
			setValue = connectedStringCount + 1;
			break;
		case INVERTER_FAILURE_RECOVERY_MS:
			setValue = connectedStringCount + 1;
			break;
		case VOLTAGE_LIMIT_HYSTERESIS:
			// TO Do
			break;
		default:
			break;
		}
		return setValue;
	}

}
