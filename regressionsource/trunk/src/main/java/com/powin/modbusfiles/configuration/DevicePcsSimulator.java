package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class DevicePcsSimulator {
	
	public DevicePcsSimulator(int arrayIndex, int pcsIndex, String defaultGridMode,
			boolean defaultWattFrequencyEnabled, int maxAllowedkVAr, int maxAllowedkW, int minAllowedkVAr,
			int minAllowedkW, int nameplateA, int nameplatekVAr, int nameplatekW, boolean enabled, int stopPriority,
			int startPriority) {
		super();
		this.arrayIndex = arrayIndex;
		this.pcsIndex = pcsIndex;
		this.defaultGridMode = defaultGridMode;
		this.defaultWattFrequencyEnabled = defaultWattFrequencyEnabled;
		this.maxAllowedkVAr = maxAllowedkVAr;
		this.maxAllowedkW = maxAllowedkW;
		this.minAllowedkVAr = minAllowedkVAr;
		this.minAllowedkW = minAllowedkW;
		this.nameplateA = nameplateA;
		this.nameplatekVAr = nameplatekVAr;
		this.nameplatekW = nameplatekW;
		this.enabled = enabled;
		this.stopPriority = stopPriority;
		this.startPriority = startPriority;
	}
	public DevicePcsSimulator(int arrayIndex, int pcsIndex, int nameplateA, int nameplatekW, int stopPriority,int startPriority) {
		super();
		this.arrayIndex = arrayIndex;
		this.pcsIndex = pcsIndex;
		this.defaultGridMode = "GRID_FOLLOWING";
		this.defaultWattFrequencyEnabled = false;
		this.maxAllowedkVAr = nameplatekW;
		this.maxAllowedkW = nameplatekW;
		this.minAllowedkVAr = -nameplatekW;
		this.minAllowedkW = -nameplatekW;
		this.nameplateA = nameplateA;
		this.nameplatekVAr = nameplatekW;
		this.nameplatekW = nameplatekW;
		this.enabled = true;
		this.stopPriority = stopPriority;
		this.startPriority = startPriority;
	}
	private int arrayIndex;
	private int pcsIndex;
	private String defaultGridMode ;
	private boolean defaultWattFrequencyEnabled ;
	private int maxAllowedkVAr;
	private int maxAllowedkW;
	private int minAllowedkVAr;
	private int minAllowedkW ;
	private int nameplateA;
	private int nameplatekVAr;
	private int nameplatekW;
	private boolean enabled;
	private int stopPriority;
	private int startPriority;
	
	public int getArrayIndex() {
		return arrayIndex;
	}
	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	public int getPcsIndex() {
		return pcsIndex;
	}
	public void setPcsIndex(int pcsIndex) {
		this.pcsIndex = pcsIndex;
	}
	public String getDefaultGridMode() {
		return defaultGridMode;
	}
	public void setDefaultGridMode(String defaultGridMode) {
		this.defaultGridMode = defaultGridMode;
	}
	public boolean isDefaultWattFrequencyEnabled() {
		return defaultWattFrequencyEnabled;
	}
	public void setDefaultWattFrequencyEnabled(boolean defaultWattFrequencyEnabled) {
		this.defaultWattFrequencyEnabled = defaultWattFrequencyEnabled;
	}
	public int getMaxAllowedkVAr() {
		return maxAllowedkVAr;
	}
	public void setMaxAllowedkVAr(int maxAllowedkVAr) {
		this.maxAllowedkVAr = maxAllowedkVAr;
	}
	public int getMaxAllowedkW() {
		return maxAllowedkW;
	}
	public void setMaxAllowedkW(int maxAllowedkW) {
		this.maxAllowedkW = maxAllowedkW;
	}
	public int getMinAllowedkVAr() {
		return minAllowedkVAr;
	}
	public void setMinAllowedkVAr(int minAllowedkVAr) {
		this.minAllowedkVAr = minAllowedkVAr;
	}
	public int getMinAllowedkW() {
		return minAllowedkW;
	}
	public void setMinAllowedkW(int minAllowedkW) {
		this.minAllowedkW = minAllowedkW;
	}
	public int getNameplateA() {
		return nameplateA;
	}
	public void setNameplateA(int nameplateA) {
		this.nameplateA = nameplateA;
	}
	public int getNameplatekVAr() {
		return nameplatekVAr;
	}
	public void setNameplatekVAr(int nameplatekVAr) {
		this.nameplatekVAr = nameplatekVAr;
	}
	public int getNameplatekW() {
		return nameplatekW;
	}
	public void setNameplatekW(int nameplatekW) {
		this.nameplatekW = nameplatekW;
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