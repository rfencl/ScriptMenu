package com.powin.modbusfiles.configuration;

import java.util.List;

public class BlockConfiguration {
	private String stationCode;

	private int blockIndex;

	private List<ArrayConfiguration> arrayConfigurations;

	private boolean simplifiedSafetyConfig;

	private String turtleConfigurationCommandsName;

	private int socoperatingRangeMax;

	private int socoperatingRangeMin;

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setBlockIndex(int blockIndex) {
		this.blockIndex = blockIndex;
	}

	public int getBlockIndex() {
		return this.blockIndex;
	}

	public void setArrayConfigurations(List<ArrayConfiguration> arrayConfigurations) {
		this.arrayConfigurations = arrayConfigurations;
	}

	public List<ArrayConfiguration> getArrayConfigurations() {
		return this.arrayConfigurations;
	}

	public void setSimplifiedSafetyConfig(boolean simplifiedSafetyConfig) {
		this.simplifiedSafetyConfig = simplifiedSafetyConfig;
	}

	public boolean getSimplifiedSafetyConfig() {
		return this.simplifiedSafetyConfig;
	}

	public void setTurtleConfigurationCommandsName(String turtleConfigurationCommandsName) {
		this.turtleConfigurationCommandsName = turtleConfigurationCommandsName;
	}

	public String getTurtleConfigurationCommandsName() {
		return this.turtleConfigurationCommandsName;
	}

	public void setSocoperatingRangeMax(int socoperatingRangeMax) {
		this.socoperatingRangeMax = socoperatingRangeMax;
	}

	public int getSocoperatingRangeMax() {
		return this.socoperatingRangeMax;
	}

	public void setSocoperatingRangeMin(int socoperatingRangeMin) {
		this.socoperatingRangeMin = socoperatingRangeMin;
	}

	public int getSocoperatingRangeMin() {
		return this.socoperatingRangeMin;
	}

	public void init(String stationCode,StackType stackType) {
		setStationCode(stationCode);
		setBlockIndex(1);
		setSimplifiedSafetyConfig(true);
		setTurtleConfigurationCommandsName(stackType.turtleCommandConfigurationName);
		setSocoperatingRangeMax(95);
		setSocoperatingRangeMin(5);
	}
}
