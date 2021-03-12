package com.powin.modbusfiles.reports;

public class StringData {
	private double ah;

	private int kW;

	private double kWh;

	private int soc;

	private int calculatedStringVoltage;

	private int measuredStringVoltage;

	private int stringCurrent;

	private int groundLeakageCurrent;

	private int dcBusVoltage;

	private boolean contactorsCloseExpected;

	private boolean positiveContactorClosed;

	private boolean negativeContactorClosed;

	private int recloseCount;

	private boolean outRotation;

	private boolean hasPreciseCalculatedStringVoltage;

	private double preciseCalculatedStringVoltage;

	private int totalChargekWh;

	private int totalDischargekWh;

	private NotificationReport notificationReport;

	private boolean hasCellGroupStats;

	private int maxCellGroupTemp;

	private int minCellGroupTemp;

	private int avgCellGroupTemp;

	private int maxCellGroupVoltage;

	private int minCellGroupVoltage;

	private int avgCellGroupVoltage;

	private int ctCurrent1;

	private int ctCurrent2;

	private int usedStringVoltage;

	private String stringConnectionState;

	public void setAh(double ah) {
		this.ah = ah;
	}

	public double getAh() {
		return this.ah;
	}

	public void setkW(int kW) {
		this.kW = kW;
	}

	public int getkW() {
		return this.kW;
	}

	public void setkWh(double kWh) {
		this.kWh = kWh;
	}

	public double getkWh() {
		return this.kWh;
	}

	public void setSoc(int soc) {
		this.soc = soc;
	}

	public int getSoc() {
		return this.soc;
	}

	public void setCalculatedStringVoltage(int calculatedStringVoltage) {
		this.calculatedStringVoltage = calculatedStringVoltage;
	}

	public int getCalculatedStringVoltage() {
		return this.calculatedStringVoltage;
	}

	public void setMeasuredStringVoltage(int measuredStringVoltage) {
		this.measuredStringVoltage = measuredStringVoltage;
	}

	public int getMeasuredStringVoltage() {
		return this.measuredStringVoltage;
	}

	public void setStringCurrent(int stringCurrent) {
		this.stringCurrent = stringCurrent;
	}

	public int getStringCurrent() {
		return this.stringCurrent;
	}

	public void setGroundLeakageCurrent(int groundLeakageCurrent) {
		this.groundLeakageCurrent = groundLeakageCurrent;
	}

	public int getGroundLeakageCurrent() {
		return this.groundLeakageCurrent;
	}

	public void setDcBusVoltage(int dcBusVoltage) {
		this.dcBusVoltage = dcBusVoltage;
	}

	public int getDcBusVoltage() {
		return this.dcBusVoltage;
	}

	public void setContactorsCloseExpected(boolean contactorsCloseExpected) {
		this.contactorsCloseExpected = contactorsCloseExpected;
	}

	public boolean getContactorsCloseExpected() {
		return this.contactorsCloseExpected;
	}

	public void setPositiveContactorClosed(boolean positiveContactorClosed) {
		this.positiveContactorClosed = positiveContactorClosed;
	}

	public boolean getPositiveContactorClosed() {
		return this.positiveContactorClosed;
	}

	public void setNegativeContactorClosed(boolean negativeContactorClosed) {
		this.negativeContactorClosed = negativeContactorClosed;
	}

	public boolean getNegativeContactorClosed() {
		return this.negativeContactorClosed;
	}

	public void setRecloseCount(int recloseCount) {
		this.recloseCount = recloseCount;
	}

	public int getRecloseCount() {
		return this.recloseCount;
	}

	public void setOutRotation(boolean outRotation) {
		this.outRotation = outRotation;
	}

	public boolean getOutRotation() {
		return this.outRotation;
	}

	public void setHasPreciseCalculatedStringVoltage(boolean hasPreciseCalculatedStringVoltage) {
		this.hasPreciseCalculatedStringVoltage = hasPreciseCalculatedStringVoltage;
	}

	public boolean getHasPreciseCalculatedStringVoltage() {
		return this.hasPreciseCalculatedStringVoltage;
	}

	public void setPreciseCalculatedStringVoltage(double preciseCalculatedStringVoltage) {
		this.preciseCalculatedStringVoltage = preciseCalculatedStringVoltage;
	}

	public double getPreciseCalculatedStringVoltage() {
		return this.preciseCalculatedStringVoltage;
	}

	public void setTotalChargekWh(int totalChargekWh) {
		this.totalChargekWh = totalChargekWh;
	}

	public int getTotalChargekWh() {
		return this.totalChargekWh;
	}

	public void setTotalDischargekWh(int totalDischargekWh) {
		this.totalDischargekWh = totalDischargekWh;
	}

	public int getTotalDischargekWh() {
		return this.totalDischargekWh;
	}

	public void setNotificationReport(NotificationReport notificationReport) {
		this.notificationReport = notificationReport;
	}

	public NotificationReport getNotificationReport() {
		return this.notificationReport;
	}

	public void setHasCellGroupStats(boolean hasCellGroupStats) {
		this.hasCellGroupStats = hasCellGroupStats;
	}

	public boolean getHasCellGroupStats() {
		return this.hasCellGroupStats;
	}

	public void setMaxCellGroupTemp(int maxCellGroupTemp) {
		this.maxCellGroupTemp = maxCellGroupTemp;
	}

	public int getMaxCellGroupTemp() {
		return this.maxCellGroupTemp;
	}

	public void setMinCellGroupTemp(int minCellGroupTemp) {
		this.minCellGroupTemp = minCellGroupTemp;
	}

	public int getMinCellGroupTemp() {
		return this.minCellGroupTemp;
	}

	public void setAvgCellGroupTemp(int avgCellGroupTemp) {
		this.avgCellGroupTemp = avgCellGroupTemp;
	}

	public int getAvgCellGroupTemp() {
		return this.avgCellGroupTemp;
	}

	public void setMaxCellGroupVoltage(int maxCellGroupVoltage) {
		this.maxCellGroupVoltage = maxCellGroupVoltage;
	}

	public int getMaxCellGroupVoltage() {
		return this.maxCellGroupVoltage;
	}

	public void setMinCellGroupVoltage(int minCellGroupVoltage) {
		this.minCellGroupVoltage = minCellGroupVoltage;
	}

	public int getMinCellGroupVoltage() {
		return this.minCellGroupVoltage;
	}

	public void setAvgCellGroupVoltage(int avgCellGroupVoltage) {
		this.avgCellGroupVoltage = avgCellGroupVoltage;
	}

	public int getAvgCellGroupVoltage() {
		return this.avgCellGroupVoltage;
	}

	public void setCtCurrent1(int ctCurrent1) {
		this.ctCurrent1 = ctCurrent1;
	}

	public int getCtCurrent1() {
		return this.ctCurrent1;
	}

	public void setCtCurrent2(int ctCurrent2) {
		this.ctCurrent2 = ctCurrent2;
	}

	public int getCtCurrent2() {
		return this.ctCurrent2;
	}

	public void setUsedStringVoltage(int usedStringVoltage) {
		this.usedStringVoltage = usedStringVoltage;
	}

	public int getUsedStringVoltage() {
		return this.usedStringVoltage;
	}

	public void setStringConnectionState(String stringConnectionState) {
		this.stringConnectionState = stringConnectionState;
	}

	public String getStringConnectionState() {
		return this.stringConnectionState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(ah);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + avgCellGroupTemp;
		result = prime * result + avgCellGroupVoltage;
		result = prime * result + calculatedStringVoltage;
		result = prime * result + (contactorsCloseExpected ? 1231 : 1237);
		result = prime * result + ctCurrent1;
		result = prime * result + ctCurrent2;
		result = prime * result + dcBusVoltage;
		result = prime * result + groundLeakageCurrent;
		result = prime * result + (hasCellGroupStats ? 1231 : 1237);
		result = prime * result + (hasPreciseCalculatedStringVoltage ? 1231 : 1237);
		result = prime * result + kW;
		temp = Double.doubleToLongBits(kWh);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + maxCellGroupTemp;
		result = prime * result + maxCellGroupVoltage;
		result = prime * result + measuredStringVoltage;
		result = prime * result + minCellGroupTemp;
		result = prime * result + minCellGroupVoltage;
		result = prime * result + (negativeContactorClosed ? 1231 : 1237);
		result = prime * result + ((notificationReport == null) ? 0 : notificationReport.hashCode());
		result = prime * result + (outRotation ? 1231 : 1237);
		result = prime * result + (positiveContactorClosed ? 1231 : 1237);
		temp = Double.doubleToLongBits(preciseCalculatedStringVoltage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + recloseCount;
		result = prime * result + soc;
		result = prime * result + ((stringConnectionState == null) ? 0 : stringConnectionState.hashCode());
		result = prime * result + stringCurrent;
		result = prime * result + totalChargekWh;
		result = prime * result + totalDischargekWh;
		result = prime * result + usedStringVoltage;
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
		StringData other = (StringData) obj;
		if (Double.doubleToLongBits(ah) != Double.doubleToLongBits(other.ah))
			return false;
		if (avgCellGroupTemp != other.avgCellGroupTemp)
			return false;
		if (avgCellGroupVoltage != other.avgCellGroupVoltage)
			return false;
		if (calculatedStringVoltage != other.calculatedStringVoltage)
			return false;
		if (contactorsCloseExpected != other.contactorsCloseExpected)
			return false;
		if (ctCurrent1 != other.ctCurrent1)
			return false;
		if (ctCurrent2 != other.ctCurrent2)
			return false;
		if (dcBusVoltage != other.dcBusVoltage)
			return false;
		if (groundLeakageCurrent != other.groundLeakageCurrent)
			return false;
		if (hasCellGroupStats != other.hasCellGroupStats)
			return false;
		if (hasPreciseCalculatedStringVoltage != other.hasPreciseCalculatedStringVoltage)
			return false;
		if (kW != other.kW)
			return false;
		if (Double.doubleToLongBits(kWh) != Double.doubleToLongBits(other.kWh))
			return false;
		if (maxCellGroupTemp != other.maxCellGroupTemp)
			return false;
		if (maxCellGroupVoltage != other.maxCellGroupVoltage)
			return false;
		if (measuredStringVoltage != other.measuredStringVoltage)
			return false;
		if (minCellGroupTemp != other.minCellGroupTemp)
			return false;
		if (minCellGroupVoltage != other.minCellGroupVoltage)
			return false;
		if (negativeContactorClosed != other.negativeContactorClosed)
			return false;
		if (notificationReport == null) {
			if (other.notificationReport != null)
				return false;
		} else if (!notificationReport.equals(other.notificationReport))
			return false;
		if (outRotation != other.outRotation)
			return false;
		if (positiveContactorClosed != other.positiveContactorClosed)
			return false;
		if (Double.doubleToLongBits(preciseCalculatedStringVoltage) != Double
				.doubleToLongBits(other.preciseCalculatedStringVoltage))
			return false;
		if (recloseCount != other.recloseCount)
			return false;
		if (soc != other.soc)
			return false;
		if (stringConnectionState == null) {
			if (other.stringConnectionState != null)
				return false;
		} else if (!stringConnectionState.equals(other.stringConnectionState))
			return false;
		if (stringCurrent != other.stringCurrent)
			return false;
		if (totalChargekWh != other.totalChargekWh)
			return false;
		if (totalDischargekWh != other.totalDischargekWh)
			return false;
		if (usedStringVoltage != other.usedStringVoltage)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringData [ah=" + ah + ", kW=" + kW + ", kWh=" + kWh + ", soc=" + soc + ", calculatedStringVoltage="
				+ calculatedStringVoltage + ", measuredStringVoltage=" + measuredStringVoltage + ", stringCurrent="
				+ stringCurrent + ", groundLeakageCurrent=" + groundLeakageCurrent + ", dcBusVoltage=" + dcBusVoltage
				+ ", contactorsCloseExpected=" + contactorsCloseExpected + ", positiveContactorClosed="
				+ positiveContactorClosed + ", negativeContactorClosed=" + negativeContactorClosed + ", recloseCount="
				+ recloseCount + ", outRotation=" + outRotation + ", hasPreciseCalculatedStringVoltage="
				+ hasPreciseCalculatedStringVoltage + ", preciseCalculatedStringVoltage="
				+ preciseCalculatedStringVoltage + ", totalChargekWh=" + totalChargekWh + ", totalDischargekWh="
				+ totalDischargekWh + ", notificationReport=" + notificationReport + ", hasCellGroupStats="
				+ hasCellGroupStats + ", maxCellGroupTemp=" + maxCellGroupTemp + ", minCellGroupTemp="
				+ minCellGroupTemp + ", avgCellGroupTemp=" + avgCellGroupTemp + ", maxCellGroupVoltage="
				+ maxCellGroupVoltage + ", minCellGroupVoltage=" + minCellGroupVoltage + ", avgCellGroupVoltage="
				+ avgCellGroupVoltage + ", ctCurrent1=" + ctCurrent1 + ", ctCurrent2=" + ctCurrent2
				+ ", usedStringVoltage=" + usedStringVoltage + ", stringConnectionState=" + stringConnectionState + "]";
	}
}
