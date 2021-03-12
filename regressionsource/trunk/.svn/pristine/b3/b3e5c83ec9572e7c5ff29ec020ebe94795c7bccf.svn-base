package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.simple.JSONObject;

import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class StringReport {
	private String timeStamp;

	private int arrayIndex;

	private int stringIndex;

	private StringData stringData;

	private BatteryPackReport batteryPackReport;

	private List<BatteryPackReport> batteryPackReportList;

	private ScFirmwareVersion scFirmwareVersion;

	private ScHardwareVersion scHardwareVersion;

	private String stackType;

	private static final String TURTLE_URL = "turtle_url";
	private static final String RESOURCE_NAME = "default.properties";


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	public int getArrayIndex() {
		return this.arrayIndex;
	}

	public void setStringIndex(int stringIndex) {
		this.stringIndex = stringIndex;
	}

	public int getStringIndex() {
		return this.stringIndex;
	}

	public void setStringData(StringData stringData) {
		this.stringData = stringData;
	}

	public StringData getStringData() {
		return this.stringData;
	}

	public void setBatteryPackReport(BatteryPackReport batteryPackReport) {
		this.batteryPackReport = batteryPackReport;
	}

	public BatteryPackReport getBatteryPackReport() {
		return this.batteryPackReport;
	}

	public void setBatteryPackReportList(List<BatteryPackReport> batteryPackReportList) {
		this.batteryPackReportList = batteryPackReportList;
	}

	public List<BatteryPackReport> getBatteryPackReportList() {
		return this.batteryPackReportList;
	}

	public void setScFirmwareVersion(ScFirmwareVersion scFirmwareVersion) {
		this.scFirmwareVersion = scFirmwareVersion;
	}

	public ScFirmwareVersion getScFirmwareVersion() {
		return this.scFirmwareVersion;
	}

	public void setScHardwareVersion(ScHardwareVersion scHardwareVersion) {
		this.scHardwareVersion = scHardwareVersion;
	}

	public ScHardwareVersion getScHardwareVersion() {
		return this.scHardwareVersion;
	}
    //TODO
	public void setStackType(String stackType) {
//		JSONObject stringReportString = getStringReportJSON(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.toString());
//		List<String> results = JsonParserHelper.getFieldJSONObject(stringReportString, "stackType", "");
//		this.stackType = results.get(0).toString();
	}

	public String getStackType() {
		return this.stackType;
	}

	public static String getStringReportString(String arrayIndex, String stringIndex) {
		String reportUrl = PowinProperty.TURTLE_URL.toString()
				+ String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "report.json");

		HttpHelper mHttpHelper = new HttpHelper(reportUrl);
		return mHttpHelper.getJSONFromFile().toString();
	}
	
	public static JSONObject getStringReportJSON(String arrayIndex, String stringIndex) {
		String reportUrl = PowinProperty.TURTLE_URL.toString()
				+ String.join("/", "turtle", "qaqc", arrayIndex, stringIndex, "report.json");

		HttpHelper mHttpHelper = new HttpHelper(reportUrl);
		return mHttpHelper.getJSONFromFile();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arrayIndex;
		result = prime * result + ((batteryPackReport == null) ? 0 : batteryPackReport.hashCode());
		result = prime * result + ((batteryPackReportList == null) ? 0 : batteryPackReportList.hashCode());
		result = prime * result + ((scFirmwareVersion == null) ? 0 : scFirmwareVersion.hashCode());
		result = prime * result + ((scHardwareVersion == null) ? 0 : scHardwareVersion.hashCode());
		result = prime * result + ((stackType == null) ? 0 : stackType.hashCode());
		result = prime * result + ((stringData == null) ? 0 : stringData.hashCode());
		result = prime * result + stringIndex;
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringReport other = (StringReport) obj;
		if (arrayIndex != other.arrayIndex)
			return false;
		if (batteryPackReport == null) {
			if (other.batteryPackReport != null)
				return false;
		} else if (!batteryPackReport.equals(other.batteryPackReport))
			return false;
		if (batteryPackReportList == null) {
			if (other.batteryPackReportList != null)
				return false;
		} else if (!batteryPackReportList.equals(other.batteryPackReportList))
			return false;
		if (scFirmwareVersion == null) {
			if (other.scFirmwareVersion != null)
				return false;
		} else if (!scFirmwareVersion.equals(other.scFirmwareVersion))
			return false;
		if (scHardwareVersion == null) {
			if (other.scHardwareVersion != null)
				return false;
		} else if (!scHardwareVersion.equals(other.scHardwareVersion))
			return false;
		if (stackType == null) {
			if (other.stackType != null)
				return false;
		} else if (!stackType.equals(other.stackType))
			return false;
		if (stringData == null) {
			if (other.stringData != null)
				return false;
		} else if (!stringData.equals(other.stringData))
			return false;
		if (stringIndex != other.stringIndex)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringReport [timeStamp=" + timeStamp + ", arrayIndex=" + arrayIndex + ", stringIndex=" + stringIndex
				+ ", stringData=" + stringData + ", batteryPackReport=" + batteryPackReport + ", batteryPackReportList="
				+ batteryPackReportList + ", scFirmwareVersion=" + scFirmwareVersion + ", scHardwareVersion="
				+ scHardwareVersion + ", stackType=" + stackType + "]";
	}
}
