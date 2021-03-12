package com.powin.modbusfiles.reports;

public class ArrayReport {

	private int arrayIndex;

	private String timeStamp;

	private ArrayData arrayData;

	// @JsonProperty(value = "stringReport")
	// private List<StringReport> stringReport;

	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	public int getArrayIndex() {
		return this.arrayIndex;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setArrayData(ArrayData arrayData) {
		this.arrayData = arrayData;
	}

	public ArrayData getArrayData() {
		return this.arrayData;
	}

	// public void setStringReport(List<StringReport> stringReportList) {
	// this.stringReport = stringReportList;
	// }
	//
	// public List<StringReport> getStringReport() {
	// return stringReport;
	// }

}
