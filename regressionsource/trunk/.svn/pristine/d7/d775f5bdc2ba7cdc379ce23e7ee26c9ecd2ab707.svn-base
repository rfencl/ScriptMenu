package com.powin.modbusfiles.reports;

public class CellGroupReport {
	private String timeStamp;

	private CellGroupData cellGroupData;

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setCellGroupData(CellGroupData cellGroupData) {
		this.cellGroupData = cellGroupData;
	}

	public CellGroupData getCellGroupData() {
		return this.cellGroupData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellGroupData == null) ? 0 : cellGroupData.hashCode());
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
		CellGroupReport other = (CellGroupReport) obj;
		if (cellGroupData == null) {
			if (other.cellGroupData != null)
				return false;
		} else if (!cellGroupData.equals(other.cellGroupData))
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
		return "CellGroupReport [timeStamp=" + timeStamp + ", cellGroupData=" + cellGroupData + "]";
	}
}