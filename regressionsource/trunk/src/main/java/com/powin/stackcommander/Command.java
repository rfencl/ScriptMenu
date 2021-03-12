package com.powin.stackcommander;

public enum Command {
	Title("Title"), Using("Using"), FullDischarge("Fully? discharge"), Balancing("Balancing|Balance"),
	FullCharge("Fully? charge"), Charge("Charge"), Discharge("Discharge"), Record("Record"), Turnoff("Turn off"),
	Restart("Restart"), Rest("Rest"), Repeat("Repeat"), Comment("Comment"), Undefined("");

	public static Command fromString(final String cmd) {
		Command ret = Command.Undefined;
		for (final Command command : Command.values()) {
			if (cmd.matches(command.toString())) {
				ret = command;
				break;
			}
		}
		return ret;
	}

	public static String toRegEx(final boolean isCSV) {
		final StringBuilder ret = new StringBuilder();
		for (final Command cmd : Command.values()) {
			if (cmd != Command.Undefined) {
				ret.append(cmd).append("|");
			} else {
				ret.deleteCharAt(ret.lastIndexOf("|"));
			}
		}
		return isCSV ? ret.toString().replace("Turn Off\\|", "").replace("Restart\\|", "").replace("Record\\|", "")
				: ret.toString();
	}

	private String value;

	Command(final String val) {
		value = val;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
