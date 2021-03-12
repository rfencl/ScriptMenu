package com.powin.stackcommander;

public class Power {

	public static Power fromString(final String pwr) {
		Power ret = null;

		if (pwr.toString().matches(toRegEx())) {
			ret = new Power(pwr);
		}

		return ret;
	}

	public static String toRegEx() {
		return ("\\d?\\d?\\dkW|P/\\d|kW,\\d?\\d?\\d|P/N,\\d");
	}

	private int kWvalue = -1;

	private int Pvalue = 0;

	public Power(final String val) {
		if (val.contains("kW")) {
			kWvalue = Integer.parseInt(val.replace(",", "").replace("kW", ""));
		} else if (val.startsWith("P/N")) {
			Pvalue = (Integer.parseInt(val.replace("P/N,", "")));
		} else if (val.startsWith("P")) {
			Pvalue = (Integer.parseInt(val.replace("P/", "")));
		}
	}

	public int getPower() {
		final int ret = Math.max(kWvalue, Pvalue);
		return ret;
	}
	
	public boolean isUnitKW() {
		return this.kWvalue >= 0;
	}

	@Override
	public String toString() {
		String ret = "";
		final int p = getPower();
		if (this.Pvalue == p) {
			ret = "P/" + p;
		} else {
			ret = p + "kW";
		}
		return ret;
	}
}
