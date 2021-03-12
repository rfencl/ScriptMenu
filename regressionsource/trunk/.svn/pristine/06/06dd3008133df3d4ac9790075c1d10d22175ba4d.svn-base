package com.powin.modbusfiles.future;

public enum CellVoltageLimitsOld {
	CELL_HIGH_VOLTAGE_ALARM_SET(3650),				// Notification code: 1001
	CELL_HIGH_VOLTAGE_ALARM_CLEAR(3600),
	CELL_HIGH_VOLTAGE_WARNING_SET(3600),			// Notification code: 2001
	CELL_HIGH_VOLTAGE_WARNING_CLEAR(3525),
	CELL_HIGH_VOLTAGE_WARRANTY_ALARM_SET(3650),		// Notification code: 8001
	CELL_HIGH_VOLTAGE_WARRANTY_ALARM_CLEAR(3600),
	CELL_HIGH_VOLTAGE_WARRANTY_WARNING_SET(3600),	// Notification code: 9001
	CELL_HIGH_VOLTAGE_WARRANTY_WARNING_CLEAR(3500),
	CELL_LOW_VOLTAGE_ALARM_SET(2500),				// Notification code: 1004
	CELL_LOW_VOLTAGE_ALARM_CLEAR(2550),
	CELL_LOW_VOLTAGE_WARNING_SET(2800),				// Notification code: 2004
	CELL_LOW_VOLTAGE_WARNING_CLEAR(2900),
	CELL_LOW_VOLTAGE_WARRANTY_ALARM_SET(2500),		// Notification code: 8004
	CELL_LOW_VOLTAGE_WARRANTY_ALARM_CLEAR(2700),
	CELL_LOW_VOLTAGE_WARRANTY_WARNING_SET(2700),    // Notification code: 9004
	CELL_LOW_VOLTAGE_WARRANTY_WARNING_CLEAR(2800),
	CELL_HIGH_VOLTAGE_DELTA_ALARM_SET(1000),		// Notification code: 1007
	CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR(800),
	CELL_HIGH_VOLTAGE_DELTA_WARNING_SET(800),		// Notification code: 2007
	CELL_HIGH_VOLTAGE_DELTA_WARNING_CLEAR(600),
	UNDEFINED(0);

	
	private int value;

	CellVoltageLimitsOld(int i) {
		value = i;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static CellVoltageLimitsOld fromCode(final String code) {
		CellVoltageLimitsOld ret = CellVoltageLimitsOld.UNDEFINED;
	         for (final CellVoltageLimitsOld limit : CellVoltageLimitsOld.values()) {
	             if (limit.toString().equals(code)) {
	                 ret = limit;
	                 break;
	             }
	         }
	        return ret;
	}
	
	public static CellVoltageLimitsOld fromCode(final int code) {
	   return fromCode(String.valueOf(code));
	}
}
