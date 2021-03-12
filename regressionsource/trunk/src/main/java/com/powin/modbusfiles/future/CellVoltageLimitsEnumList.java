package com.powin.modbusfiles.future;

public class CellVoltageLimitsEnumList {
	
	@Override
	public String toString() {
		return "CellVoltageLimits [toString()=" + enumLimits.values().length + "]";
	}

	public enum enumLimits{
		CELL_HIGH_VOLTAGE_ALARM_CLEAR(3600),				// Notification code: 1001
		CELL_HIGH_VOLTAGE_ALARM_SET(3650),
		CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR(800),			// Notification code: 2001
		CELL_HIGH_VOLTAGE_DELTA_ALARM_SET(1000),
		CELL_HIGH_VOLTAGE_DELTA_WARNING_CLEAR(600),		// Notification code: 8001
		CELL_HIGH_VOLTAGE_DELTA_WARNING_SET(800),
		CELL_HIGH_VOLTAGE_WARNING_CLEAR(3525),	// Notification code: 9001
		CELL_HIGH_VOLTAGE_WARNING_SET(3600),
		CELL_HIGH_VOLTAGE_WARRANTY_ALARM_CLEAR(3600),				// Notification code: 1004
		CELL_HIGH_VOLTAGE_WARRANTY_ALARM_SET(3650),
		CELL_HIGH_VOLTAGE_WARRANTY_WARNING_CLEAR(3500),				// Notification code: 2004
		CELL_HIGH_VOLTAGE_WARRANTY_WARNING_SET(3600),
		CELL_LOW_VOLTAGE_ALARM_CLEAR(2550),		// Notification code: 8004
		CELL_LOW_VOLTAGE_ALARM_SET(2500),
		CELL_LOW_VOLTAGE_WARNING_CLEAR(2900),    // Notification code: 9004
		CELL_LOW_VOLTAGE_WARNING_SET(2800),
		CELL_LOW_VOLTAGE_WARRANTY_ALARM_CLEAR(2700),		// Notification code: 1007
		CELL_LOW_VOLTAGE_WARRANTY_ALARM_SET(2500),
		CELL_LOW_VOLTAGE_WARRANTY_WARNING_CLEAR(2800),		// Notification code: 2007
		CELL_LOW_VOLTAGE_WARRANTY_WARNING_SET(2700),
		UNDEFINED(0);
		
		public static enumLimits fromCode(final int code) {
			   return fromCode(String.valueOf(code));
		}

		public static enumLimits fromCode(final String code) {
			enumLimits ret = enumLimits.UNDEFINED;
		         for (final enumLimits limit : enumLimits.values()) {
		             if (limit.toString().equals(code)) {
		                 ret = limit;
		                 break;
		             }
		         }
		        return ret;
		}
		private int value;
		enumLimits(int i) {
			value = i;
		}
		public int getValue() {
			return this.value;
		}
		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	public enumLimits getEnum() {
		// TODO Auto-generated method stub
		return null;
	};

}
