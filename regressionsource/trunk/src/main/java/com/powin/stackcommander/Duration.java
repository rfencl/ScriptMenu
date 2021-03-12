package com.powin.stackcommander;

public class Duration {

	public static String MINUTES = "min";
	public static String SECONDS = "s";
	public static String HOURS = "h";

	public static Duration fromString(final String d) {
		Duration ret = null;

		if (d.matches(toRegEx())) {
			ret = new Duration(d, !d.contains(","));
		}
		return ret;
	}

	private static void initUnits(final boolean isShortForm) {
		if (isShortForm) {
			MINUTES = "min";
			SECONDS = "s";
			HOURS = "h";
		} else {
			MINUTES = "Minutes";
			SECONDS = "Seconds";
			HOURS = "Hours";

		}
	}

	public static String toRegEx() {
		return ("\\d?\\ds|\\d?\\dmin|\\dh|Hours,\\d?\\d|Minutes,\\d?\\d?\\d|Seconds,\\d?\\d");
	}

	java.time.Duration time;

	String units = "";

	public Duration(final String val, final boolean isShortForm) {
		initUnits(isShortForm);
		if (val.contains(SECONDS)) {
			time = java.time.Duration.ofSeconds(Long.parseLong(val.replace(SECONDS, "").replace(",", "")));
			units = SECONDS;
		} else if (val.contains(MINUTES)) {
			units = MINUTES;
			time = java.time.Duration.ofMinutes(Long.parseLong(val.replace(MINUTES, "").replace(",", "")));
		} else if (val.contains(HOURS)) {
			units = HOURS;
			time = java.time.Duration.ofHours(Long.parseLong(val.replace(HOURS, "").replace(",", "")));
		}
	}

	public java.time.Duration getTime() {
		return time;
	}

	public String getUnits() {
		return units;
	}

	@Override
	public String toString() {
		String ret = "";

		if (units.equals(SECONDS)) {
			ret = time.getSeconds() + SECONDS;
		}
		if (units.equals(MINUTES)) {
			ret = time.toMinutes() + MINUTES;
		}
		if (units.equals(HOURS)) {
			ret = time.toHours() + HOURS;
		}
		return ret;
	}
}
