package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class DevicePhoenixDcBattery {
	public DevicePhoenixDcBattery(int arrayIndex, int commsTimeout, boolean enabled,  int stopPriority,int startPriority) {
		super();
		this.arrayIndex = arrayIndex;
		this.commsTimeout=commsTimeout;
		this.enabled = enabled;
		this.stopPriority = stopPriority;
		this.startPriority = startPriority;
	}

	private int arrayIndex;
	private int commsTimeout;
	private boolean enabled;
	private int stopPriority;
	private int startPriority;
	
	public int getArrayIndex() {
		return arrayIndex;
	}
	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	
	public int getCommsTimeout() {
		return commsTimeout;
	}
	public void setCommsTimeout(int commsTimeout) {
		this.commsTimeout = commsTimeout;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getStopPriority() {
		return stopPriority;
	}
	public void setStopPriority(int stopPriority) {
		this.stopPriority = stopPriority;
	}
	public int getStartPriority() {
		return startPriority;
	}
	public void setStartPriority(int startPriority) {
		this.startPriority = startPriority;
	}
}