package com.powin.modbusfiles.reports;

public class CellGroupData {


	private int voltageMin;

	private int voltageMax;

	private int voltageLatest;

	private int tempMin;

	private int tempMax;

	private int tempLatest;

	public void setVoltageMin(int voltageMin) {
		this.voltageMin = voltageMin;
	}

	public int getVoltageMin() {
		return this.voltageMin;
	}

	public void setVoltageMax(int voltageMax) {
		this.voltageMax = voltageMax;
	}

	public int getVoltageMax() {
		return this.voltageMax;
	}

	public void setVoltageLatest(int voltageLatest) {
		this.voltageLatest = voltageLatest;
	}

	public int getVoltageLatest() {
		return this.voltageLatest;
	}

	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}

	public int getTempMin() {
		return this.tempMin;
	}

	public void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}

	public int getTempMax() {
		return this.tempMax;
	}

	public void setTempLatest(int tempLatest) {
		this.tempLatest = tempLatest;
	}

	public int getTempLatest() {
		return this.tempLatest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tempLatest;
		result = prime * result + tempMax;
		result = prime * result + tempMin;
		result = prime * result + voltageLatest;
		result = prime * result + voltageMax;
		result = prime * result + voltageMin;
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
		CellGroupData other = (CellGroupData) obj;
		if (tempLatest != other.tempLatest)
			return false;
		if (tempMax != other.tempMax)
			return false;
		if (tempMin != other.tempMin)
			return false;
		if (voltageLatest != other.voltageLatest)
			return false;
		if (voltageMax != other.voltageMax)
			return false;
		if (voltageMin != other.voltageMin)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CellGroupData [voltageMin=" + voltageMin + ", voltageMax=" + voltageMax + ", voltageLatest="
				+ voltageLatest + ", tempMin=" + tempMin + ", tempMax=" + tempMax + ", tempLatest=" + tempLatest + "]";
	}
}
