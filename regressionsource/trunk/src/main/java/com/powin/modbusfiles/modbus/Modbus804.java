package com.powin.modbusfiles.modbus;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.LithiumIonStringModelBlockCommon.ConnectDisconnectStringEnum;
import com.powin.modbus.sunspec.blocks.LithiumIonStringModelBlockCommon.ContactorStatus;
import com.powin.modbus.sunspec.blocks.LithiumIonStringModelBlockCommon.StringEvent1;
import com.powin.modbus.sunspec.blocks.LithiumIonStringModelBlockMaster;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class Modbus804 {
	private final static Logger LOG = LogManager.getLogger();

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private LithiumIonStringModelBlockMaster cLithiumIonStringModelBlockMaster;

	private SunspecMasterEndpoint cSunspecMasterEndpoint;

	public Modbus804(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		cModbusHostName = modbusHostName;
		cModbusPort = modbusPort;
		cModbusUnitId = modbusUnitId;
		cEnableModbusLogging = enableModbusLogging;
		init();
	}

	public void init() {
		SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging, 10000);
		cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
		cLithiumIonStringModelBlockMaster = cSunspecMasterEndpoint.getBlock(804, 0);
		if (cLithiumIonStringModelBlockMaster == null) {
			throw new IllegalStateException("No lithium ion string!");
		}
	}

	// Getters
	public int getID() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getId();
	}

	public int getStringIndex() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringIndex();
	}

	public int getModuleCount() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModuleCount();
	}

	public int getStringCellBalancingCount() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringCellBalancingCount();
	}

	public BigDecimal getStringStateofCharge() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringStateofCharge();
	}

	public BigDecimal getStringDepthofDischarge() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringDepthofDischarge();
	}

	public BigDecimal getStringCurrent() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringCurrent();
	}

	public BigDecimal getStringVoltage() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringVoltage();
	}

	public BigDecimal getMaxCellVoltage() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxCellVoltage();
	}

	public int getMaxCellVoltageModule() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxCellVoltageModule();
	}

	public BigDecimal getMinCellVoltage() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinCellVoltage();
	}

	public int getMinCellVoltageModule() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinCellVoltageModule();
	}

	public BigDecimal getAverageCellVoltage() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getAverageCellVoltage();
	}

	public BigDecimal getMaxModuleTemperature() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxModuleTemperature();
	}

	public int getMaxModuleTemperatureModule() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxModuleTemperatureModule();
	}

	public BigDecimal getMinModuleTemperature() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinModuleTemperature();
	}

	public int getMinModuleTemperatureModule() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinModuleTemperatureModule();
	}

	public BigDecimal getAverageModuleTemperature() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getAverageModuleTemperature();
	}

	public int getPad() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad();
	}

	public ContactorStatus getContactorStatus() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getContactorStatus();
	}

	public StringEvent1 getStringEvent1() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getStringEvent1();
	}

	public int getSoC_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getSoC_SF();
	}

	public int getDoD_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getDoD_SF();
	}

	public int getA_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getA_SF();
	}

	public int getV_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getV_SF();
	}

	public int getCellV_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getCellV_SF();
	}

	public int getModTmp_SF() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModTmp_SF();
	}

	public int getPad2() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad2();
	}

	public int getPad3() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad3();
	}

	public int getPad4() throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad4();
	}

	public int getModuleCellCount(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModuleCellCount(repeatingBlockIndex);
	}

	public BigDecimal getModCellVMax(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModCellVMax(repeatingBlockIndex);
	}

	public int getMaxCellVoltageCell(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxCellVoltageCell(repeatingBlockIndex);
	}

	public BigDecimal getModCellVMin(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModCellVMin(repeatingBlockIndex);
	}

	public int getMinCellVoltageCell(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinCellVoltageCell(repeatingBlockIndex);
	}

	public BigDecimal getModCellVAvg(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getModCellVAvg(repeatingBlockIndex);
	}

	public BigDecimal getMaxCellTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxCellTemperature(repeatingBlockIndex);
	}

	public int getMaxCellTemperatureCell(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMaxCellTemperatureCell(repeatingBlockIndex);
	}

	public BigDecimal getMinCellTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinCellTemperature(repeatingBlockIndex);
	}

	public int getMinCellTemperatureCell(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getMinCellTemperatureCell(repeatingBlockIndex);
	}

	public BigDecimal getAvgCellTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getAverageCellTemperature(repeatingBlockIndex);
	}

	public int getPad5(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad5(repeatingBlockIndex);
	}

	public int getPad6(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad6(repeatingBlockIndex);
	}

	public int getPad7(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonStringModelBlockMaster.getPad7(repeatingBlockIndex);
	}

	//Set
	public void setConnectString() throws ModbusException {
		cLithiumIonStringModelBlockMaster.setConnectDisconnectString(ConnectDisconnectStringEnum.CONNECT_STRING);
	}

	public void setDisconnectString() throws ModbusException {
		cLithiumIonStringModelBlockMaster.setConnectDisconnectString(ConnectDisconnectStringEnum.DISCONNECT_STRING);
	}

	public final static void main(String[] args) {

		try {
			Modbus804 modBusTest = new Modbus804(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			modBusTest.init();
			int repeatingBlock = 1;
			// Tests
			LOG.info("getID()={}", modBusTest.getID());
			LOG.info("getAverageCellVoltage()={}", modBusTest.getAverageCellVoltage());
			LOG.info("getAverageModuleTemperature()={}", modBusTest.getAverageModuleTemperature());
			LOG.info("getAvgCellTemperature(repeatingBlock)={}", modBusTest.getAvgCellTemperature(repeatingBlock));
			LOG.info("getMaxCellTemperature(repeatingBlock)={}", modBusTest.getMaxCellTemperature(repeatingBlock));
			LOG.info("getMaxCellVoltage()={}", modBusTest.getMaxCellVoltage());
			LOG.info("getMaxModuleTemperature()={}", modBusTest.getMaxModuleTemperature());
			LOG.info("getMinCellTemperature(repeatingBlock)={}", modBusTest.getMinCellTemperature(repeatingBlock));
			LOG.info("getMinCellVoltage()={}", modBusTest.getMinCellVoltage());
			LOG.info("getMinModuleTemperature()={}", modBusTest.getMinModuleTemperature());
			LOG.info("getModCellVAvg(repeatingBlock)={}", modBusTest.getModCellVAvg(repeatingBlock));
			LOG.info("getModCellVMax(repeatingBlock)={}", modBusTest.getModCellVMax(repeatingBlock));
			LOG.info("getModCellVMin(repeatingBlock)={}", modBusTest.getModCellVMin(repeatingBlock));
			LOG.info("getStringCurrent()={}", modBusTest.getStringCurrent());
			LOG.info("getStringDepthofDischarge()={}", modBusTest.getStringDepthofDischarge());
			LOG.info("getStringStateofCharge()={}", modBusTest.getStringStateofCharge());
			LOG.info("getStringVoltage()={}", modBusTest.getStringVoltage());
			LOG.info("getContactorStatus()={}", modBusTest.getContactorStatus());
			LOG.info("getA_SF()={}", modBusTest.getA_SF());
			LOG.info("getCellV_SF()={}", modBusTest.getCellV_SF());
			LOG.info("getDoD_SF()={}", modBusTest.getDoD_SF());
			LOG.info("getMaxCellTemperatureCell(repeatingBlock)={}", modBusTest.getMaxCellTemperatureCell(repeatingBlock));
			LOG.info("getMaxCellVoltageCell(repeatingBlock)={}", modBusTest.getMaxCellVoltageCell(repeatingBlock));
			LOG.info("getMaxCellVoltageModule()={}", modBusTest.getMaxCellVoltageModule());
			LOG.info("getMaxModuleTemperatureModule()={}", modBusTest.getMaxModuleTemperatureModule());
			LOG.info("getMinCellTemperatureCell(repeatingBlock)={}", modBusTest.getMinCellTemperatureCell(repeatingBlock));
			LOG.info("getMinCellVoltageCell(repeatingBlock)={}", modBusTest.getMinCellVoltageCell(repeatingBlock));
			LOG.info("getMinCellVoltageModule()={}", modBusTest.getMinCellVoltageModule());
			LOG.info("getMinModuleTemperatureModule()={}", modBusTest.getMinModuleTemperatureModule());
			LOG.info("getModTmp_SF()={}", modBusTest.getModTmp_SF());
			LOG.info("getModuleCellCount(repeatingBlock)={}", modBusTest.getModuleCellCount(repeatingBlock));
			LOG.info("getModuleCount()={}", modBusTest.getModuleCount());
			LOG.info("getPad()={}", modBusTest.getPad());
			LOG.info("getPad2()={}", modBusTest.getPad2());
			LOG.info("getPad3()={}", modBusTest.getPad3());
			LOG.info("getPad4()={}", modBusTest.getPad4());
			LOG.info("getPad5(repeatingBlock)={}", modBusTest.getPad5(repeatingBlock));
			LOG.info("getPad6(repeatingBlock)={}", modBusTest.getPad6(repeatingBlock));
			LOG.info("getPad7(repeatingBlock)={}", modBusTest.getPad7(repeatingBlock));
			LOG.info("getSoC_SF()={}", modBusTest.getSoC_SF());
			LOG.info("getStringCellBalancingCount()={}", modBusTest.getStringCellBalancingCount());
			LOG.info("getStringIndex()={}", modBusTest.getStringIndex());
			LOG.info("getV_SF()={}", modBusTest.getV_SF());
			LOG.info("getStringEvent1()={}", modBusTest.getStringEvent1());
//			modBusTest.setConnectString();
//			modBusTest.setDisconnectString();

		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
