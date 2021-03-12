package com.powin.modbusfiles.configuration;

import java.util.ArrayList;
import java.util.List;

public class ArrayChildNode {
	private List<StringChildNode> childNodes;

	private String dcTopologyNodeType;

	private String dcTopologyNodeName;

	private boolean hasCurrentLimit;

	private int chargeCurrentLimitAmps;

	private int dischargeCurrentLimitAmps;

	public void setChildNodes(List<StringChildNode> childNodes) {
		this.childNodes = childNodes;
	}

	public List<StringChildNode> getChildNodes() {
		return this.childNodes;
	}

	public void setDcTopologyNodeType(String dcTopologyNodeType) {
		this.dcTopologyNodeType = dcTopologyNodeType;
	}

	public String getDcTopologyNodeType() {
		return this.dcTopologyNodeType;
	}

	public void setDcTopologyNodeName(String dcTopologyNodeName) {
		this.dcTopologyNodeName = dcTopologyNodeName;
	}

	public String getDcTopologyNodeName() {
		return this.dcTopologyNodeName;
	}

	public void setHasCurrentLimit(boolean hasCurrentLimit) {
		this.hasCurrentLimit = hasCurrentLimit;
	}

	public boolean getHasCurrentLimit() {
		return this.hasCurrentLimit;
	}

	public void setChargeCurrentLimitAmps(int chargeCurrentLimitAmps) {
		this.chargeCurrentLimitAmps = chargeCurrentLimitAmps;
	}

	public int getChargeCurrentLimitAmps() {
		return this.chargeCurrentLimitAmps;
	}

	public void setDischargeCurrentLimitAmps(int dischargeCurrentLimitAmps) {
		this.dischargeCurrentLimitAmps = dischargeCurrentLimitAmps;
	}

	public int getDischargeCurrentLimitAmps() {
		return this.dischargeCurrentLimitAmps;
	}

	public void init(int stringCount, int containerIndex) {
		setDcTopologyNodeType("DCCombiner");
		setDcTopologyNodeName("Container" + containerIndex);
		setHasCurrentLimit(true);
		setChargeCurrentLimitAmps(2000);
		setDischargeCurrentLimitAmps(2000);
		List<StringChildNode> lst = new ArrayList<StringChildNode>();
		StringChildNode strNode = new StringChildNode();
		strNode.init(stringCount);
		lst.add(strNode);
		setChildNodes(lst);
	}
}
