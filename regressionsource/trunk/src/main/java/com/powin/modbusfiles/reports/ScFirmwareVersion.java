package com.powin.modbusfiles.reports;

public class ScFirmwareVersion {
	private String firmwareType;

	private int fwVersionMajor;

	private int fwVersionMinor;

	private int fwVersionRevision;

	public void setFirmwareType(String firmwareType) {
		this.firmwareType = firmwareType;
	}

	public String getFirmwareType() {
		return this.firmwareType;
	}

	public void setFwVersionMajor(int fwVersionMajor) {
		this.fwVersionMajor = fwVersionMajor;
	}

	public int getFwVersionMajor() {
		return this.fwVersionMajor;
	}

	public void setFwVersionMinor(int fwVersionMinor) {
		this.fwVersionMinor = fwVersionMinor;
	}

	public int getFwVersionMinor() {
		return this.fwVersionMinor;
	}

	public void setFwVersionRevision(int fwVersionRevision) {
		this.fwVersionRevision = fwVersionRevision;
	}

	public int getFwVersionRevision() {
		return this.fwVersionRevision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firmwareType == null) ? 0 : firmwareType.hashCode());
		result = prime * result + fwVersionMajor;
		result = prime * result + fwVersionMinor;
		result = prime * result + fwVersionRevision;
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
		ScFirmwareVersion other = (ScFirmwareVersion) obj;
		if (firmwareType == null) {
			if (other.firmwareType != null)
				return false;
		} else if (!firmwareType.equals(other.firmwareType))
			return false;
		if (fwVersionMajor != other.fwVersionMajor)
			return false;
		if (fwVersionMinor != other.fwVersionMinor)
			return false;
		if (fwVersionRevision != other.fwVersionRevision)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScFirmwareVersion [firmwareType=" + firmwareType + ", fwVersionMajor=" + fwVersionMajor
				+ ", fwVersionMinor=" + fwVersionMinor + ", fwVersionRevision=" + fwVersionRevision + "]";
	}
}
