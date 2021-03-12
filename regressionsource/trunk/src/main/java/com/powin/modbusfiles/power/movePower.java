package com.powin.modbusfiles.power;

import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.InverterSafety;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommandHelper;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class movePower {
	private final static Logger LOG = LogManager.getLogger(movePower.class.getName());
	private static int cArrayIndex = 	PowinProperty.ARRAY_INDEX.intValue();
	private static int cStringIndex = 	PowinProperty.STRING_INDEX.intValue();	
	
	
	public static void main(String[] args) {
		movePower.powerDown(3000);

	}

	public static void dischargePAsPercent(BigDecimal percent)  {
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		if (percent.compareTo(BigDecimal.ZERO) >= 0 && Math.abs(percent.intValue()) <= 100) {
			movePower.setPAsPercent(percent);
		} else {
			LOG.error("Discharge percentage should not be less than zero and should not be greater than 100");
		}
	}

	public static void chargePAsPercent(BigDecimal percent) {
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		if (percent.compareTo(BigDecimal.ZERO) <= 0 && Math.abs(percent.intValue()) <= 100) {
			movePower.setPAsPercent(percent);
		} else {
			LOG.error("Charge percentage should not be greater than zero and should not be greater than 100");
		}
	}

	public static void setPAsPercent(BigDecimal percent)  {
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		Modbus123 cImmediateControlsBlockMaster = new Modbus123(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
		try {
			cImmediateControlsBlockMaster.setWMaxLimPct(percent);
		} catch (ModbusException e) {
			LOG.error("Unable to set power", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void stopPowerPAsPercent() {
		setPAsPercent(BigDecimal.ZERO);
		CommonHelper.sleep(10000);
		LOG.info("Power has been stopped");
	}

	public static void powerDown(int voltageTarget) {
		int cellVoltageMinimum = SystemInfo.getMinCellGroupVoltage();
		dischargePAsPercent(BigDecimal.valueOf(80));
		while (cellVoltageMinimum > voltageTarget) {
			CommonHelper.quietSleep(10000);
			cellVoltageMinimum = SystemInfo.getMinCellGroupVoltage();;
			LOG.info("Powering down to {} . Current minimum voltage: {}" ,voltageTarget,cellVoltageMinimum);
		}
	}

	public static boolean powerToTargetHysteresisClearingStringVoltage(int voltageTarget, boolean isCharging) {
		boolean hysteresisCleared = true;
		LastCalls lc = new LastCalls();
		String statusStr = "";
		getModbusPowinBlock().enableSunspec();
		try {
			// Power up or down
			if (isCharging)
				chargePAsPercent(BigDecimal.valueOf(-30));
			else
				dischargePAsPercent(BigDecimal.valueOf(10));
			hysteresisCleared &= movePower.clearHysteresis(voltageTarget, isCharging, lc);
			int dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
			if (hysteresisCleared) {
				LOG.info("PASS:Hysteresis alarm cleared at " + dcBusVoltage);
			} else {
				LOG.info("FAIL:Hysteresis alarm did not clear. " + statusStr + dcBusVoltage);
			} 
		} finally {
			stopPowerPAsPercent();
		}
		return hysteresisCleared;
	}

	public static boolean clearHysteresis(int voltageTarget, boolean isCharging, LastCalls lc){
		// Keep cycle running till target voltage is reached
		boolean hysteresisCleared = false;
		LOG.info("Hysteresis limit set at " + voltageTarget);
		int dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
		while (SystemInfo.targetNotReached(voltageTarget, isCharging, dcBusVoltage)) {
			if (hysteresisCleared = SystemInfo.isHysteresisCleared()) {
				break;
			}
			CommonHelper.quietSleep(2000);
			dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
		}
	
		if (hysteresisCleared == false) {
			LOG.info("Waiting for hysteresis alarm to be cleared... ");
			for (int i = 0; i < 60; i++) {
				CommonHelper.sleep(1000);
				if (hysteresisCleared = SystemInfo.isHysteresisCleared()) {
					break;
				}
			}
		}
		return hysteresisCleared;
	}
	
	public static boolean powerToTargetStringVoltage(int voltageTarget, boolean isCharging) {
		return powerToTargetStringVoltage( voltageTarget,  isCharging, false); 
	}
	
	public static boolean powerToTargetStringVoltage(int voltageTarget, boolean isCharging, boolean triggerBsfAlarm) {
		//The offset is to ensure that the BSF alarm is hit. If you are not testing BSF alarms set it to 0.
		boolean isTestPass = false;
		int offset= triggerBsfAlarm?10:0;
		getModbusPowinBlock().enableSunspec();
		int targetVoltageWithOffset;
		try {
			// Power up or down
			if (isCharging) {
				chargePAsPercent(BigDecimal.valueOf(-40));
				targetVoltageWithOffset = voltageTarget + offset;
			} else {
				dischargePAsPercent(BigDecimal.valueOf(40));
				targetVoltageWithOffset = voltageTarget - offset;
			}
			// Keep cycle running till target voltage is reached
			int alarmVoltageTolerance = 10;
			int dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
			boolean blockingCodeDisplayed = false;
			while (SystemInfo.targetNotReached(targetVoltageWithOffset, isCharging, dcBusVoltage))
			{
				//Exit cycle if BSF alarm displays
				if (blockingCodeDisplayed = SystemInfo.isPowerBlockedMessageDisplayed()) {
					break;
				}
				dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
				CommonHelper.quietSleep(4000);
			}
			if(triggerBsfAlarm) {
				if (blockingCodeDisplayed) {
					isTestPass=true;
					LOG.info("PASS: Voltage alarm hit.");
					if (Math.abs(dcBusVoltage - voltageTarget) < alarmVoltageTolerance) {
						LOG.info("INFO: Alarm voltage and target voltage within tolerance of +- " + alarmVoltageTolerance
								+ " V");
					} else {
						LOG.info("WARN:Alarm voltage and target voltage  NOT within tolerance of +-" + alarmVoltageTolerance
								+ " V");
					}
				} else  {
					LOG.info("FAIL: Voltage target " + voltageTarget + "reached, but alarm did not hit.");
				} 
			}
			else {
				if (blockingCodeDisplayed) {
					isTestPass=false;
					LOG.info("FAIL: Voltage alarm hit.");
				} else  {
					isTestPass=true;
					LOG.info("PASS: Voltage target reached");
				} 
			}
		} finally {
			stopPowerPAsPercent();
		}
		getModbusPowinBlock().disableSunspec();
		return isTestPass;
	}
	
	public static boolean powerToTargetStringVoltage(int voltageTarget, boolean isCharging, int offset) {
		//The offset is to ensure that the BSF alarm is hit. If you are not testing BSF alarms set it to 0.
		boolean isTestPass = false;
		getModbusPowinBlock().enableSunspec();
		int targetVoltageWithOffset;
		try {
			// Power up or down
			if (isCharging) {
				chargePAsPercent(BigDecimal.valueOf(-40));
				targetVoltageWithOffset = voltageTarget + offset;
			} else {
				dischargePAsPercent(BigDecimal.valueOf(40));
				targetVoltageWithOffset = voltageTarget - offset;
			}
			// Keep cycle running till target voltage is reached
			int alarmVoltageTolerance = 10;
			int dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
			boolean blockingCodeDisplayed = false;
			while (SystemInfo.targetNotReached(targetVoltageWithOffset, isCharging, dcBusVoltage))
			{
				//Exit cycle if BSF alarm displays
				if (blockingCodeDisplayed = SystemInfo.isPowerBlockedMessageDisplayed()) {
					break;
				}
				dcBusVoltage = Integer.parseInt(SystemInfo.getDcBusVoltage());
				CommonHelper.quietSleep(4000);
			}
			if (blockingCodeDisplayed) {
				isTestPass=true;
				LOG.info("PASS: Voltage alarm hit.");
				if (Math.abs(dcBusVoltage - voltageTarget) < alarmVoltageTolerance) {
					LOG.info("INFO: Alarm voltage and target voltage within tolerance of +- " + alarmVoltageTolerance
							+ " V");
				} else {
					LOG.info("WARN:Alarm voltage and target voltage  NOT within tolerance of +-" + alarmVoltageTolerance
							+ " V");
				}
			} else  {
				LOG.info("FAIL: Voltage target " + voltageTarget + "reached, but alarm did not hit.");
			} 
		} finally {
			stopPowerPAsPercent();
		}
		getModbusPowinBlock().disableSunspec();
		return isTestPass;
	}

	public static boolean isPowerFlowing()  {
		Reports strReport;
		double stringCurrent;
		double stringPower;
		boolean powerFlowing;
		powerFlowing = false;
		int nameplateKw = SystemInfo.getNameplateKw();
		for (int i = 0; i < 6; i++) {
			strReport = new Reports(cArrayIndex + "," + cStringIndex);
			stringCurrent = Double.parseDouble(strReport.getStringCurrent());
			stringPower =  Double.parseDouble(strReport.getStringPower());
			LOG.info("current is:{} power is:{}", stringCurrent, stringPower);
			// Hysteresis leaves residual power which we estimate to be 5%.
			if (Math.abs(stringPower) > InverterSafety.HYSTERESIS_FACTOR * nameplateKw) {
				powerFlowing = true;
				break;
			}
			CommonHelper.sleep(10000);
		}
		return powerFlowing;
	}

	public static boolean validatePowerFlowing(boolean isCharging, boolean isAllowed)  {
	    boolean isTestPass = true;
		String cycleType;
		getModbusPowinBlock().enableSunspec();
		// Power up or down
		if (isCharging) {
			chargePAsPercent(BigDecimal.valueOf(-30));
			cycleType = "Charging";
		} else {
			dischargePAsPercent(BigDecimal.valueOf(20));
			cycleType = "Discharging";
		}
		boolean powerFlowing;
		if (isAllowed) {
			powerFlowing = isPowerFlowing();
			isTestPass &= powerFlowing;
			if (powerFlowing) {
				LOG.info("PASS: " + cycleType + " is allowed as expected");
			} else {
				LOG.info("FAIL: " + cycleType + " is blocked when it should have been allowed.");
			}
		} else {
			powerFlowing = isPowerFlowing();
			isTestPass &= !powerFlowing;
			if (powerFlowing) {
				LOG.info("FAIL: " + cycleType + " is allowed when it should have been blocked.");
			} else {
				LOG.info("PASS: " + cycleType + " is blocked as expected");				
			}
		}
		// Switch off power
		stopPowerPAsPercent();
		getModbusPowinBlock().disableSunspec();
		return isTestPass;
	}

}
