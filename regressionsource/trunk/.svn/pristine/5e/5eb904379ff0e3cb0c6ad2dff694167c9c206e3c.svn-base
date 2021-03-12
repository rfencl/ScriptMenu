package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class StringConfigurations {
	private String configurationName;

	private List<Integer> stringIndexes;

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getConfigurationName() {
		return this.configurationName;
	}

	public void setStringIndexes(List<Integer> stringIndexes) {
		this.stringIndexes = stringIndexes;
	}

	public List<Integer> getStringIndexes() {
		return this.stringIndexes;
	}

	public void init(int stringCount,StackType stackType) {
		
		setConfigurationName(stackType.stackConfigurationName);
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < stringCount; i++) {
			lst.add(i + 1);
		}
		setStringIndexes(lst);
	}
}