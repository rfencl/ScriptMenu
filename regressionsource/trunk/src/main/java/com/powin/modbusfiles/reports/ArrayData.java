package com.powin.modbusfiles.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrayData {

	@JsonProperty(value = "kW")
	private int kW;

	private int amps;

	private SocData connectedSOC;

	private SocData connectedClippedSOC;

	private SocData notConnectedSOC;

	private SocData notConnectedClippedSOC;

	private int dcBusVoltage;

	private int communicatingStackCount;

	private int notCommunicatingStackCount;

	private int maxAllowedChargeCurrent;

	private int maxAllowedDischargeCurrent;

	private int connectedStackMinCellVoltage;

	private int connectedStackMaxCellVoltage;

	private int connectedStackAvgCellVoltage;

	private int notConnectedStackMinCellVoltage;

	private int notConnectedStackMaxCellVoltage;

	private int notConnectedStackAvgCellVoltage;

	private List<String> invalidStackIndexes;

	public void setKW(int kW) {
		this.kW = kW;
	}

	public int getKW() {
		return this.kW;
	}

	public void setAmps(int amps) {
		this.amps = amps;
	}

	public int getAmps() {
		return this.amps;
	}

	public void setConnectedSOC(SocData connectedSOC) {
		this.connectedSOC = connectedSOC;
	}

	public SocData getConnectedSOC() {
		return this.connectedSOC;
	}

	public void setConnectedClippedSOC(SocData connectedClippedSOC) {
		this.connectedClippedSOC = connectedClippedSOC;
	}

	public SocData getConnectedClippedSOC() {
		return this.connectedClippedSOC;
	}

	public void setNotConnectedSOC(SocData notConnectedSOC) {
		this.notConnectedSOC = notConnectedSOC;
	}

	public SocData getNotConnectedSOC() {
		return this.notConnectedSOC;
	}

	public void setNotConnectedClippedSOC(SocData notConnectedClippedSOC) {
		this.notConnectedClippedSOC = notConnectedClippedSOC;
	}

	public SocData getNotConnectedClippedSOC() {
		return this.notConnectedClippedSOC;
	}

	public void setDcBusVoltage(int dcBusVoltage) {
		this.dcBusVoltage = dcBusVoltage;
	}

	public int getDcBusVoltage() {
		return this.dcBusVoltage;
	}

	public void setCommunicatingStackCount(int communicatingStackCount) {
		this.communicatingStackCount = communicatingStackCount;
	}

	public int getCommunicatingStackCount() {
		return this.communicatingStackCount;
	}

	public void setNotCommunicatingStackCount(int notCommunicatingStackCount) {
		this.notCommunicatingStackCount = notCommunicatingStackCount;
	}

	public int getNotCommunicatingStackCount() {
		return this.notCommunicatingStackCount;
	}

	public void setMaxAllowedChargeCurrent(int maxAllowedChargeCurrent) {
		this.maxAllowedChargeCurrent = maxAllowedChargeCurrent;
	}

	public int getMaxAllowedChargeCurrent() {
		return this.maxAllowedChargeCurrent;
	}

	public void setMaxAllowedDischargeCurrent(int maxAllowedDischargeCurrent) {
		this.maxAllowedDischargeCurrent = maxAllowedDischargeCurrent;
	}

	public int getMaxAllowedDischargeCurrent() {
		return this.maxAllowedDischargeCurrent;
	}

	public void setConnectedStackMinCellVoltage(int connectedStackMinCellVoltage) {
		this.connectedStackMinCellVoltage = connectedStackMinCellVoltage;
	}

	public int getConnectedStackMinCellVoltage() {
		return this.connectedStackMinCellVoltage;
	}

	public void setConnectedStackMaxCellVoltage(int connectedStackMaxCellVoltage) {
		this.connectedStackMaxCellVoltage = connectedStackMaxCellVoltage;
	}

	public int getConnectedStackMaxCellVoltage() {
		return this.connectedStackMaxCellVoltage;
	}

	public void setConnectedStackAvgCellVoltage(int connectedStackAvgCellVoltage) {
		this.connectedStackAvgCellVoltage = connectedStackAvgCellVoltage;
	}

	public int getConnectedStackAvgCellVoltage() {
		return this.connectedStackAvgCellVoltage;
	}

	public void setNotConnectedStackMinCellVoltage(int notConnectedStackMinCellVoltage) {
		this.notConnectedStackMinCellVoltage = notConnectedStackMinCellVoltage;
	}

	public int getNotConnectedStackMinCellVoltage() {
		return this.notConnectedStackMinCellVoltage;
	}

	public void setNotConnectedStackMaxCellVoltage(int notConnectedStackMaxCellVoltage) {
		this.notConnectedStackMaxCellVoltage = notConnectedStackMaxCellVoltage;
	}

	public int getNotConnectedStackMaxCellVoltage() {
		return this.notConnectedStackMaxCellVoltage;
	}

	public void setNotConnectedStackAvgCellVoltage(int notConnectedStackAvgCellVoltage) {
		this.notConnectedStackAvgCellVoltage = notConnectedStackAvgCellVoltage;
	}

	public int getNotConnectedStackAvgCellVoltage() {
		return this.notConnectedStackAvgCellVoltage;
	}

	public void setInvalidStackIndexes(List<String> invalidStackIndexes) {
		this.invalidStackIndexes = invalidStackIndexes;
	}

	public List<String> getInvalidStackIndexes() {
		return this.invalidStackIndexes;
	}
}
