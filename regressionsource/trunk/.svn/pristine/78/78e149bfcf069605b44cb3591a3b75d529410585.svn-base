package com.powin.modbusfiles.awe;

public enum InverterSafetyLimits {
	
	INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT(963),
	INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT(963),
	INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT(963),
	INVERTER_CHARGE_LOW_VOLTAGE_LIMIT(660),
	INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT(660),
	INVERTER_OPERATION_LOW_VOLTAGE_LIMIT(660),
	INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT(47.0F),
	INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT(47.0F),
	INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT(47.0F),
	INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT(4.0F),
	INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT(4.0F),
	INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT(4.0F),
	INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT(1150),
	INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT(1150),
	INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT(1150),
	INVERTER_CHARGE_MIN_STRING_COUNT(1),
	INVERTER_DISCHARGE_MIN_STRING_COUNT(1),
	INVERTER_OPERATION_MIN_STRING_COUNT(1),
	INVERTER_FAILURE_RECOVERY_MS(30000),
	VOLTAGE_LIMIT_HYSTERESIS(20),
	UNDEFINED(0);

	private Number value;

	InverterSafetyLimits(Number i) {
		value = i;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public int intValue() {
		return (int) this.value;
	}
	public float floatValue() {
		return (float) this.value;
	}
	
	public static InverterSafetyLimits fromCode(final String code) {
		InverterSafetyLimits ret = InverterSafetyLimits.UNDEFINED;
	         for (final InverterSafetyLimits notificationCode : InverterSafetyLimits.values()) {
	             if (notificationCode.toString().equals(code)) {
	                 ret = notificationCode;
	                 break;
	             }
	         }
	        return ret;
	}
	
	public static InverterSafetyLimits fromCode(final int code) {
	   return fromCode(String.valueOf(code));
	}
}