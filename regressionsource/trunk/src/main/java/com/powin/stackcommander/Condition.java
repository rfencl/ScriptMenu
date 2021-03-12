package com.powin.stackcommander;

public enum Condition {
	SoCGT("SoC >"),SoCLT("SoC <"),SoCEQ("SoC ="),Undefined("");

	public static Condition fromString(final String condition) {
		Condition ret = Condition.Undefined;
		for (final Condition cnd : Condition.values()) {
			if (cnd == Condition.Undefined) {
				return cnd;
			}
			if (condition.contains(cnd.toString())) {
				ret = cnd;
   			    ret.setSoCValue(Integer.parseInt(condition.replaceAll("SoC (<|>|=),", "")));
				break;
			}
		}
		return ret;
	}

	public static String toRegEx() {
		return ("SoC <,\\d?\\d?\\d|SoC >,\\d?\\d?\\d|SoC =,\\d?\\d?\\d");
	}


	private String value;
	private int soCValue;
	
	Condition(final String val) {
		value = val;
		if (val.matches(Condition.toRegEx())) {
			
		}
	}
	
	public int getSoCValue() {
		return soCValue;
	}
	
    private void setSoCValue(int val) {
    	soCValue = val;
    }
    
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
