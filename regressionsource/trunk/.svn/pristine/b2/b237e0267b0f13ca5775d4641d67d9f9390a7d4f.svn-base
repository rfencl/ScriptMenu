package com.powin.modbusfiles.awe;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum NotificationCodes {
	CELL_HIGH_TEMPERATURE_ALARM(1010),
	CELL_HIGH_TEMPERATURE_DELTA_ALARM(1018),
	CELL_HIGH_TEMPERATURE_DELTA_WARNING(2018),
	CELL_HIGH_TEMPERATURE_WARNING(2010),
	CELL_HIGH_TEMPERATURE_WARRANTY_ALARM(8010),
	CELL_HIGH_TEMPERATURE_WARRANTY_WARNING_(9010),
	CELL_HIGH_VOLTAGE_ALARM(1001),
	CELL_HIGH_VOLTAGE_DELTA_ALARM(1007),
	CELL_HIGH_VOLTAGE_DELTA_WARNING(2007),
	CELL_HIGH_VOLTAGE_WARNING(2001),
	CELL_LOW_TEMPERATURE_ALARM(1014),
	CELL_LOW_TEMPERATURE_WARNING(2014),
	CELL_LOW_TEMPERATURE_WARRANTY_ALARM(8014),
	CELL_LOW_TEMPERATURE_WARRANTY_WARNING(9014),
	CELL_LOW_VOLTAGE_ALARM(1004),
	CELL_LOW_VOLTAGE_WARNING(2004),
	CONTACTOR_OPEN_WARNING(2534),
	STRING_HIGH_VOLTAGE_ALARM(1003),
	STRING_LOW_VOLTAGE_ALARM(1006),
	STRING_OUT_OF_ROTATION_WARNING(2561),
	UNDEFINED(0);
	
	private int value;
   NotificationCodes(int i) {
	value = i;
   }

	public static NotificationCodes fromCode(final int code) {
		   return fromCode(String.valueOf(code));
	}
	
	public static NotificationCodes fromCode(final String code) {
		    NotificationCodes found = NotificationCodes.UNDEFINED;
		    List<NotificationCodes> list = NotificationCodes.stream()
		    .filter(d -> String.valueOf(d.value).equals(code))
		    .collect(Collectors.toList());
		     found = list.isEmpty() ? found : list.get(0);
		return found;
	}
	
	public static Stream<NotificationCodes> stream() {
		return Stream.of(NotificationCodes.values());
	}
	


   @Override
   public String toString() {
	return String.valueOf(this.value);
   }
}