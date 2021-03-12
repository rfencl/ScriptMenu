package com.powin.modbusfiles.reports;

import java.util.List;

public class BatteryPackReport {
	private String timeStamp;

	private BatteryPackData batteryPackData;

	private CellGroupReport cellGroupReport;
	
	private List<CellGroupReport> cellGroupReportList;

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setBatteryPackData(BatteryPackData batteryPackData) {
		this.batteryPackData = batteryPackData;
	}

	public BatteryPackData getBatteryPackData() {
		return this.batteryPackData;
	}

	public void setCellGroupReport(CellGroupReport cellGroupReport) {
		this.cellGroupReport = cellGroupReport;
	}

	public CellGroupReport getCellGroupReport() {
		return this.cellGroupReport;
	}

	public void setCellGroupReportList(List<CellGroupReport> cellGroupReportList) {
		this.cellGroupReportList = cellGroupReportList;
	}

	public List<CellGroupReport> getCellGroupReportList() {
		return this.cellGroupReportList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryPackData == null) ? 0 : batteryPackData.hashCode());
		result = prime * result + ((cellGroupReport == null) ? 0 : cellGroupReport.hashCode());
		result = prime * result + ((cellGroupReportList == null) ? 0 : cellGroupReportList.hashCode());
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
		BatteryPackReport other = (BatteryPackReport) obj;
		if (batteryPackData == null) {
			if (other.batteryPackData != null)
				return false;
		} else if (!batteryPackData.equals(other.batteryPackData))
			return false;
		if (cellGroupReport == null) {
			if (other.cellGroupReport != null)
				return false;
		} else if (!cellGroupReport.equals(other.cellGroupReport))
			return false;
		if (cellGroupReportList == null) {
			if (other.cellGroupReportList != null)
				return false;
		} else if (!cellGroupReportList.equals(other.cellGroupReportList))
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
		return "BatteryPackReport [timeStamp=" + timeStamp + ", batteryPackData=" + batteryPackData
				+ ", cellGroupReport=" + cellGroupReport + ", cellGroupReportList=" + cellGroupReportList + "]";
	}
}
