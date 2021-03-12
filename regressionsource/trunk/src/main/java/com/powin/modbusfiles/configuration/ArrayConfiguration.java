package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class ArrayConfiguration {
	private List<StringConfigurations> stringConfigurations;

	private List<Integer> arrayIndexes;

	private boolean useWarnStop;

	private DcTopology dcTopology;

	public void setStringConfigurations(List<StringConfigurations> stringConfigurations) {
		this.stringConfigurations = stringConfigurations;
	}

	public List<StringConfigurations> getStringConfigurations() {
		return this.stringConfigurations;
	}

	public void setArrayIndexes(List<Integer> arrayIndexes) {
		this.arrayIndexes = arrayIndexes;
	}

	public List<Integer> getArrayIndexes() {
		return this.arrayIndexes;
	}

	public void setUseWarnStop(boolean useWarnStop) {
		this.useWarnStop = useWarnStop;
	}

	public boolean getUseWarnStop() {
		return this.useWarnStop;
	}

	public void setDcTopology(DcTopology dcTopology) {
		this.dcTopology = dcTopology;
	}

	public DcTopology getDcTopology() {
		return this.dcTopology;
	}

	public void init(StackType stackType,int stringCount, int containerIndex, int arrayCount, int arrayStartIndex) {
		List<StringConfigurations> stringConfigList = new ArrayList<StringConfigurations>();
		StringConfigurations strConfig = new StringConfigurations();
		strConfig.init(stringCount,stackType);
		stringConfigList.add(strConfig);
		setStringConfigurations(stringConfigList);

		List<Integer> arrIndex = new ArrayList<Integer>();
		for (int i = 0; i < arrayCount; i++) {
			arrIndex.add(arrayStartIndex + i);
		}
		setArrayIndexes(arrIndex);

		setUseWarnStop(false);

		DcTopology dcTop = new DcTopology();
		dcTop.init(stringCount, containerIndex);

		setDcTopology(dcTop);
	}
}