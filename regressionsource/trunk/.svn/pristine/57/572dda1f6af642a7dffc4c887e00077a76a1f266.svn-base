package com.powin.modbusfiles.awe;

import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.power.movePower;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.stackoperations.Contactors;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class InverterSafety {
	public final static Logger LOG = LogManager.getLogger(InverterSafety.class.getName());

    private static final boolean MOVING_POWER_ALLOWED = true;
    private static final boolean MOVING_POWER_NOT_ALLOWED = false;
	private static final boolean DISCHARGING = false;
	private static final boolean CHARGING = true;
	private static final boolean CLOSE_CONTACTORS_AFTER_OPENING = true;
	private static final boolean DO_NOT_CLOSE_CONTACTORS_AFTER_OPENING = false;
	
	static final int MILLIS_PER_SECOND = 1000;
	
	public static final double HYSTERESIS_FACTOR = 0.10;
	static int cArrayIndex;
	static int cStringIndex;
	
	public InverterSafety() {
		cArrayIndex = 	PowinProperty.ARRAY_INDEX.intValue();
		cStringIndex = 	PowinProperty.STRING_INDEX.intValue();	
	}

	public InverterSafety(int arrayIndex, int stringIndex) {
		cArrayIndex = arrayIndex;
		cStringIndex = stringIndex;	
	}

	public boolean inverterChargeHappyPath(int targetVoltageDelta) {
        return inverterHappyPath(targetVoltageDelta,CHARGING);
	}

	public boolean inverterDischargeHappyPath(int targetVoltageDelta) {
        return inverterHappyPath(targetVoltageDelta,DISCHARGING);
	}

	public boolean inverterHappyPath(int targetVoltageDelta, boolean charging) {
        boolean isTestPass = false;
        if(charging)
        	movePower.chargePAsPercent(BigDecimal.valueOf(-80));
        else
        	movePower.dischargePAsPercent(BigDecimal.valueOf(80));
		int timeoutMinutes		=	4;
		int startStringVoltage 	= 	SystemInfo.getMeasuredStringVoltage();
		int actualVoltageOffset =	0;
		DateTime currentTime	=	DateTime.now();
		DateTime endTime 		= 	currentTime.plusMinutes(timeoutMinutes);
		while (DateTime.now().isBefore(endTime)) {
			actualVoltageOffset = Math.abs(SystemInfo.getMeasuredStringVoltage() - startStringVoltage);
			LOG.info("Current flowing: {}",SystemInfo.getArrayCurrent());
			if (actualVoltageOffset >= targetVoltageDelta) {
				LOG.info("PASS: Target voltage delta of {} was reached {} within {} minutes",targetVoltageDelta,timeoutMinutes);
				isTestPass = true;
				break;
			}
			CommonHelper.quietSleep(4000);
		}
        if (!isTestPass) {
        	LOG.info("FAIL:String voltage delta was only {} in {} minutes",actualVoltageOffset,timeoutMinutes);
        	LOG.info("... whereas the target voltage delta was {}",targetVoltageDelta);
        }
        ModbusPowinBlock.getModbusPowinBlock().disableSunspec();
		return isTestPass;
	}

	public boolean runHysteresisTest(InverterSafetyLimits invConfigItem) {
		int hysteresisValue = 15;
		int hysteresisClearingTargetVoltage = 0;
        boolean isTestPass = true;
		int setValue;
		boolean mIsHighVoltageLimitTest = false;
		int timeout = 120;

		InverterSafetyCommands.resetInverterSafetyLimits();
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		isTestPass &= AweCommon.verifyContactorsClosed(timeout, "after closing contactors prior to hysteresis test for: "+invConfigItem.name());
		// Set limit and power to the limit. Check that limit was reached
		int initialStringVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
		switch (invConfigItem) {
		case INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT:
			setValue = initialStringVoltage + hysteresisValue;
			mIsHighVoltageLimitTest = true;
			InverterSafetyCommands.setInverterChargeHighVoltageLimitWithHysteresis(setValue, hysteresisValue);
			movePower.powerToTargetStringVoltage(setValue, true, 5);
			hysteresisClearingTargetVoltage = setValue - hysteresisValue - 5;
			break;
		case INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT:
			setValue = initialStringVoltage + hysteresisValue;
			mIsHighVoltageLimitTest = true;
			InverterSafetyCommands.setInverterOperationHighVoltageLimitWithHysteresis(setValue, hysteresisValue);
			movePower.powerToTargetStringVoltage(setValue, true, 5);
			hysteresisClearingTargetVoltage = setValue - hysteresisValue - 5;
			break;
		case INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT:
			setValue = initialStringVoltage - hysteresisValue;
			mIsHighVoltageLimitTest = false;
			InverterSafetyCommands.setInverterDischargeLowVoltageLimitWithHysteresis(setValue, hysteresisValue);
			movePower.powerToTargetStringVoltage(setValue - 5, false, 5);
			hysteresisClearingTargetVoltage = setValue + hysteresisValue + 5;
			break;
		case INVERTER_OPERATION_LOW_VOLTAGE_LIMIT:
			setValue = initialStringVoltage - hysteresisValue;
			mIsHighVoltageLimitTest = false;
			InverterSafetyCommands.setInverterOperationLowVoltageLimitWithHysteresis(setValue, hysteresisValue);
			movePower.powerToTargetStringVoltage(setValue, false, 5);
			hysteresisClearingTargetVoltage = setValue + hysteresisValue + 5;
			break;
		default:
			setValue = 0;
			break;
		}
		LOG.info("Limit set at " + setValue);
		// Validate the correct error codes were set
		isTestPass &= InverterSafetyCommands.validateBsfMessagesSet(invConfigItem)
		&& AweCommon.verifyContactorsClosed(timeout, "after hysteresis limit was triggered for: "+invConfigItem.name());
		// Power in the opposite direction to reset the original and the hysteresis
		if (isTestPass) {
			movePower.powerToTargetHysteresisClearingStringVoltage(hysteresisClearingTargetVoltage, !mIsHighVoltageLimitTest);
		}
		// Reset config
		InverterSafetyCommands.resetInverterSafetyLimits();
		return isTestPass;
	}
	
	public boolean runRecoveryTimeTest(InverterSafetyLimits invConfigItem) {
		LOG.info("Start Recovery Time test.");
        boolean isTestPass = true;
        isTestPass &= verifySettingLimit( invConfigItem,120);
		// Reset warnings to the original value
		InverterSafetyCommands.resetInverterSafetyLimits();
        int recoveryTimeout= InverterSafetyLimits.INVERTER_FAILURE_RECOVERY_MS.intValue() ;
        //Verify status returned to GOOD within the recovery period set by the safety limit
        isTestPass &= InverterSafetyCommands.verifyBsfStatusGood(recoveryTimeout, "");
        return isTestPass;
	}
	
	public boolean restoreDefaultConfigurationAndCloseContactors(int timeoutSeconds,String logMessage) {
		InverterSafetyCommands.resetInverterSafetyLimits();
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		return AweCommon.verifyContactorsClosed(timeoutSeconds, logMessage);
	}
	
	public boolean openContactorTest(int powerPercent,boolean closeAfterOpening){
		boolean isTestPass = true;
		int timeoutSeconds=120;
		if(!restoreDefaultConfigurationAndCloseContactors(timeoutSeconds," prior to idling test")) {
			LOG.info("Aborting test - could not set system default state" );
			return false;
		}
		movePower.setPAsPercent(BigDecimal.valueOf(powerPercent));
		//Open contactors
		Contactors.openContactors(cArrayIndex, cStringIndex);
		String poweringMode = powerPercent >0 ? "discharge" : (powerPercent <0 ? "charge":"idle");
		
		isTestPass &= AweCommon.verifyContactorsOpened(timeoutSeconds, "after a post-"+poweringMode+" opening of contactors");
		if (closeAfterOpening) {
			Contactors.closeContactors(cArrayIndex, cStringIndex);
			isTestPass &= AweCommon.verifyContactorsClosed(timeoutSeconds, "after a post-"+poweringMode+" opening of contactors");
		}
		return isTestPass;
	}
	
	public boolean testCloseContactorsAfterOpeningWhileIdle(){
		return openContactorTest(0,CLOSE_CONTACTORS_AFTER_OPENING);
	}
	
	public boolean testOpenContactorsWhileIdle(){
		return openContactorTest(0,DO_NOT_CLOSE_CONTACTORS_AFTER_OPENING);
	}
	
	public boolean testOpenContactorsWhileCharging(){
        return openContactorTest(-50,DO_NOT_CLOSE_CONTACTORS_AFTER_OPENING);
	}
	
	public boolean testOpenContactorsWhileDischarging(){
        return openContactorTest(50,DO_NOT_CLOSE_CONTACTORS_AFTER_OPENING);
	}

	public boolean validateBsfMessagesClear(InverterSafetyLimits invConfigItem){
		boolean isTestPass = true;
		String blockStr = "";
		for (int i = 0; i < 30; i++) {
			SystemInfo.getLastCallsContent();
			blockStr = SystemInfo.getBsfStatus();
			if ((isTestPass &= blockStr.contains("RECOVERING") || blockStr.contains("GOOD"))) {
				LOG.info("PASS: errors cleared after clearing " + invConfigItem.name());
				break;
			}
			CommonHelper.quietSleep(1000);
		}
		if (!isTestPass) {
			LOG.info("FAIL: errors remained after clearing " + invConfigItem.name());
		}
        return isTestPass;
	}

	public boolean validatePowerBlockClear(InverterSafetyLimits invConfigItem) {
		boolean isTestPass = true;
		LOG.info("Ensuring charging is allowed after clearing: " + invConfigItem.name());
		isTestPass &= movePower.validatePowerFlowing(CHARGING, MOVING_POWER_ALLOWED);
		movePower.stopPowerPAsPercent();
		LOG.info("Ensuring discharging is allowed after clearing: " + invConfigItem.name());
		isTestPass &= movePower.validatePowerFlowing(DISCHARGING, MOVING_POWER_ALLOWED);
		return isTestPass;
	}

	public boolean validatePowerBlockSet(InverterSafetyLimits invConfigItem)  {
		boolean isTestPass = true;
		if (invConfigItem.name().contains("_CHARGE_")) {
			isTestPass &= movePower.validatePowerFlowing(CHARGING, MOVING_POWER_NOT_ALLOWED);
			isTestPass &= movePower.validatePowerFlowing(DISCHARGING, MOVING_POWER_ALLOWED);
		} else if (invConfigItem.name().contains("_DISCHARGE_")) {
			isTestPass &= movePower.validatePowerFlowing(DISCHARGING, MOVING_POWER_NOT_ALLOWED);
			isTestPass &= movePower.validatePowerFlowing(CHARGING, MOVING_POWER_ALLOWED);
		} else if (invConfigItem.name().contains("_OPERATION_")) {
			isTestPass &= movePower.validatePowerFlowing(CHARGING, MOVING_POWER_NOT_ALLOWED);
			isTestPass &= movePower.validatePowerFlowing(DISCHARGING, MOVING_POWER_NOT_ALLOWED);
		}
		if (isTestPass) {
			LOG.info("PASS: Power was blocked/allowed appropriately for: "+invConfigItem.name());
		}
		else {
			LOG.info("FAIL: Power was NOT blocked/allowed appropriately for: "+invConfigItem.name());
		}
		return isTestPass;
	}

	public boolean basicInverterTest(InverterSafetyLimits invConfigItem) {
		LOG.info("**********Start testing " + invConfigItem.name() + "**********");
		int setValue = InverterSafetyCommands.getSetValue(invConfigItem);
		boolean isTestPass = basicInverterTest(invConfigItem, setValue);
		LOG.info("**********Finish testing " + invConfigItem.name() + "**********");
		return isTestPass;
	}
	
	public boolean basicInverterTest(InverterSafetyLimits invConfigItem, int setValue) {
		boolean isTestPass = true;
		int timeout = 120;
		isTestPass &= verifySettingLimit( invConfigItem,  setValue) 
		// Reset warnings to the original value
		&& verifyResettingLimit(invConfigItem,timeout, "");
		return isTestPass;
	}
	
	public boolean verifySettingLimit(InverterSafetyLimits invConfigItem,int timeoutSeconds) {
		return verifySettingLimit( invConfigItem, InverterSafetyCommands.getSetValue(invConfigItem), timeoutSeconds,"");
	}
	
	public boolean verifySettingLimit(InverterSafetyLimits invConfigItem, int setValue,int timeoutSeconds,String logMessage) {
		boolean isTestPass = true;
		InverterSafetyCommands.resetInverterSafetyLimits();
		Contactors.closeContactors(cArrayIndex, cStringIndex);
		isTestPass &= AweCommon.verifyContactorsClosed(timeoutSeconds, "after setting inverter safety limit: "+invConfigItem.name()+logMessage);
		// Set inverter safety limit
		InverterSafetyCommands.setConfiguration(invConfigItem, setValue);
		CommonHelper.quietSleep(5000);
		// Verify BSF message 
		isTestPass &= InverterSafetyCommands.validateBsfMessagesSet(invConfigItem)
		// Check contactors remain closed
		&& AweCommon.verifyContactorsClosed(timeoutSeconds, "after setting inverter safety limit: "+invConfigItem.name()+logMessage) 
		// Check power is blocked/allowed as per spec
		&& validatePowerBlockSet(invConfigItem);
		return isTestPass;
	}
	
	public boolean verifyResettingLimit(InverterSafetyLimits invConfigItem,int timeout) {
		return verifyResettingLimit( invConfigItem, timeout,"");
	}
	
	public boolean verifyResettingLimit(InverterSafetyLimits invConfigItem,int timeout,String logMessage) {
		boolean isTestPass = true;
		// Reset warnings to the original value
		InverterSafetyCommands.resetInverterSafetyLimits();
		isTestPass &= AweCommon.verifyContactorsClosed(timeout, "after re-setting inverter safety limit: "+invConfigItem.name())
		// Validate BSF codes cleared
		&& InverterSafetyCommands.verifyBsfStatusGood(timeout," after re-setting inverter safety limit: "+invConfigItem.name()+logMessage)
		// Check if contactors remain closed after clearing notification
		&& AweCommon.verifyContactorsClosed(timeout, "after re-setting inverter safety limit: "+invConfigItem.name())
		// Validate power moves as expected
		&& validatePowerBlockClear(invConfigItem);
		return isTestPass;
	}

	
	public static void main(String[] args) {
		
		 InverterSafety mInverterSafetyNotifications = new InverterSafety( PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());
		 //Basic inverter safety tests		 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT);// 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT);//	 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT);//	 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_LOW_VOLTAGE_LIMIT);// 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_LOW_VOLTAGE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT);//	 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT);//	 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT);// 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_MIN_STRING_COUNT);//
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_MIN_STRING_COUNT);//	 
		 mInverterSafetyNotifications.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_MIN_STRING_COUNT);//	 
		 LOG.info("Basic inverter safety tests finished");
		 // Dynamic and hysteresis tests
		 mInverterSafetyNotifications.runHysteresisTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT);//
		 mInverterSafetyNotifications.runHysteresisTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT);//
		// Happy path tests
		 mInverterSafetyNotifications.inverterDischargeHappyPath(5);
		 mInverterSafetyNotifications.inverterChargeHappyPath(5);
		// Open contactor tests
		 mInverterSafetyNotifications.testOpenContactorsWhileIdle(); 
		 mInverterSafetyNotifications.testCloseContactorsAfterOpeningWhileIdle();
		 mInverterSafetyNotifications.testOpenContactorsWhileCharging();
		 mInverterSafetyNotifications.testOpenContactorsWhileDischarging();
		 mInverterSafetyNotifications.runRecoveryTimeTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT);
		 LOG.info("Test finished.");
	}

}
