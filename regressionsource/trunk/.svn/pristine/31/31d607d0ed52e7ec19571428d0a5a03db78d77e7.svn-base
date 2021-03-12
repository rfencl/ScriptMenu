package com.powin.stackcommander;

public enum Directive {
	DirectP("DirectP:?"), BasicOp("BasicOp:?"), step("step"), at("at"), ON("ON|On"), OFF("OFF|Off"), FOR("for"),
	OR("or"), FULL("Full"), SOE("SOE value"), BMS("BMS"), Until("Until"), Undefined("");

	public static Directive fromString(final String directive) {
		Directive ret = Directive.Undefined;
		for (final Directive dir : Directive.values()) {
			if (directive.matches(dir.toString())) {
				ret = dir;
				break;
			}
		}
		return ret;
	}

	public static String toRegEx() {
		final StringBuilder ret = new StringBuilder();
		for (final Directive dir : Directive.values()) {
			if (dir != Directive.Undefined) {
				ret.append(dir).append("|");
			} else {
				ret.deleteCharAt(ret.lastIndexOf("|"));
			}
		}
		return ret.toString();
	}

	private String value;
	private int repeatStep;
	String title;
	
	Directive(final String val) {
		value = val;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	public int getRepeatStep() {
		return repeatStep;
	}

	public void setRepeatStep(final int n) {
		repeatStep = n;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
