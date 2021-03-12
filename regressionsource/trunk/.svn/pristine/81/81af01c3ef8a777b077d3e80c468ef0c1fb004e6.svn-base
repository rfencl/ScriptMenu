package com.powin.modbusfiles.reports;

public class BatteryPackData {
	private BatteryPackBalancingConfiguration batteryPackBalancingConfiguration;

	private int balancingCellGroup;

	private String balancingState;

	public void setBatteryPackBalancingConfiguration(
			BatteryPackBalancingConfiguration batteryPackBalancingConfiguration) {
		this.batteryPackBalancingConfiguration = batteryPackBalancingConfiguration;
	}

	public BatteryPackBalancingConfiguration getBatteryPackBalancingConfiguration() {
		return this.batteryPackBalancingConfiguration;
	}

	public void setBalancingCellGroup(int balancingCellGroup) {
		this.balancingCellGroup = balancingCellGroup;
	}

	public int getBalancingCellGroup() {
		return this.balancingCellGroup;
	}

	public void setBalancingState(String balancingState) {
		this.balancingState = balancingState;
	}

	public String getBalancingState() {
		return this.balancingState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balancingCellGroup;
		result = prime * result + ((balancingState == null) ? 0 : balancingState.hashCode());
		result = prime * result
				+ ((batteryPackBalancingConfiguration == null) ? 0 : batteryPackBalancingConfiguration.hashCode());
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
		BatteryPackData other = (BatteryPackData) obj;
		if (balancingCellGroup != other.balancingCellGroup)
			return false;
		if (balancingState == null) {
			if (other.balancingState != null)
				return false;
		} else if (!balancingState.equals(other.balancingState))
			return false;
		if (batteryPackBalancingConfiguration == null) {
			if (other.batteryPackBalancingConfiguration != null)
				return false;
		} else if (!batteryPackBalancingConfiguration.equals(other.batteryPackBalancingConfiguration))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryPackData [batteryPackBalancingConfiguration=" + batteryPackBalancingConfiguration
				+ ", balancingCellGroup=" + balancingCellGroup + ", balancingState=" + balancingState + "]";
	}
}