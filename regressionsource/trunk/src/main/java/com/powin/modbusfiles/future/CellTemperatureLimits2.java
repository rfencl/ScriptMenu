package com.powin.modbusfiles.future;

public enum CellTemperatureLimits2 {
	CELL_HIGH_TEMPERATURE_ALARM_SET(47),			// Notification code: 1010
	CELL_HIGH_TEMPERATURE_ALARM_CLEAR(42),
	CELL_HIGH_TEMPERATURE_WARNING_SET(42),			// Notification code: 2010
	CELL_HIGH_TEMPERATURE_WARNING_CLEAR(37),
	CELL_HIGH_TEMPERATURE_WARRANTY_ALARM_SET(38),	// Notification code: 8010
	CELL_HIGH_TEMPERATURE_WARRANTY_ALARM_CLEAR(33),
	CELL_HIGH_TEMPERATURE_WARRANTY_WARNING_SET(33),	// Notification code: 9010
	CELL_HIGH_TEMPERATURE_WARRANTY_WARNING_CLEAR(28),
	CELL_LOW_TEMPERATURE_ALARM_SET(4),				// Notification code: 1014
	CELL_LOW_TEMPERATURE_ALARM_CLEAR(9),
	CELL_LOW_TEMPERATURE_WARNING_SET(9),			// Notification code: 2014
	CELL_LOW_TEMPERATURE_WARNING_CLEAR(14),
	CELL_LOW_TEMPERATURE_WARRANTY_ALARM_SET(4),		// Notification code: 8014
	CELL_LOW_TEMPERATURE_WARRANTY_ALARM_CLEAR(9),
	CELL_LOW_TEMPERATURE_WARRANTY_WARNING_SET(9),	// Notification code: 9014
	CELL_LOW_TEMPERATURE_WARRANTY_WARNING_CLEAR(14),
	CELL_HIGH_TEMPERATURE_DELTA_ALARM_SET(43),		// Notification code: 1018
	CELL_HIGH_TEMPERATURE_DELTA_ALARM_CLEAR(33),
	CELL_HIGH_TEMPERATURE_DELTA_WARNING_SET(33),	// Notification code: 2018
	CELL_HIGH_TEMPERATURE_DELTA_WARNING_CLEAR(23),
	UNDEFINED(0);

	
	private int value;

	CellTemperatureLimits2(int i) {
		value = i;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static CellTemperatureLimits2 fromCode(final String code) {
		CellTemperatureLimits2 ret = CellTemperatureLimits2.UNDEFINED;
	         for (final CellTemperatureLimits2 limit : CellTemperatureLimits2.values()) {
	             if (limit.toString().equals(code)) {
	                 ret = limit;
	                 break;
	             }
	         }
	        return ret;
	}
	
	public static CellTemperatureLimits2 fromCode(final int code) {
	   return fromCode(String.valueOf(code));
	}
}
