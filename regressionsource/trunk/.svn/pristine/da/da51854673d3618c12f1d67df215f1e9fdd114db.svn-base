package com.powin.modbusfiles.modbus;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.LithiumIonBatteryBankModelBlockCommon.ContactorStatus;
import com.powin.modbus.sunspec.blocks.LithiumIonBatteryBankModelBlockCommon.StringEvent1;
import com.powin.modbus.sunspec.blocks.LithiumIonBatteryBankModelBlockMaster;
import com.powin.modbus.sunspec.blocks.LithiumIonStringModelBlockCommon.ConnectDisconnectStringEnum;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class Modbus803 {
	private final static Logger LOG = LogManager.getLogger();

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private LithiumIonBatteryBankModelBlockMaster cLithiumIonBatteryBankModelBlockMaster;

	private SunspecMasterEndpoint cSunspecMasterEndpoint;

	public Modbus803(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		cModbusHostName = modbusHostName;
		cModbusPort = modbusPort;
		cModbusUnitId = modbusUnitId;
		cEnableModbusLogging = enableModbusLogging;
		init();
	}

	public void init() {
		SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging, 10000);
		cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
		cLithiumIonBatteryBankModelBlockMaster = cSunspecMasterEndpoint.getBlock(803, 0);
		if (cLithiumIonBatteryBankModelBlockMaster == null) {
			throw new IllegalStateException("No lithium ion battery bank!");
		}
	}

	// Getters
	public int getID() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getId();
	}

	public int getStringCount() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStringCount();
	}

	public int getConnectedStringCount() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getConnectedStringCount();
	}

	public BigDecimal getMaxModuleTemperature() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxModuleTemperature();
	}

	public int getMaxModuleTemperatureString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxModuleTemperatureString();
	}

	public int getMaxModuleTemperatureModule() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxModuleTemperatureModule();
	}

	public BigDecimal getMinModuleTemperature() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinModuleTemperature();
	}

	public int getMinModuleTemperatureModule() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinModuleTemperatureModule();
	}

	public int getMinModuleTemperatureString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinModuleTemperatureString();
	}

	public BigDecimal getAverageModuleTemperature() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getAverageModuleTemperature();
	}

	public BigDecimal getMaxStringVoltage() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxStringVoltage();
	}

	public int getMaxStringVoltageString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxStringVoltageString();
	}

	public BigDecimal getMinStringVoltage() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinStringVoltage();
	}

	public int getMinStringVoltageString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinStringVoltageString();
	}

	public BigDecimal getAverageStringVoltage() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getAverageStringVoltage();
	}

	public BigDecimal getMaxStringCurrent() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxStringCurrent();
	}

	public int getMaxStringCurrentString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxStringCurrentString();
	}

	public BigDecimal getMinStringCurrent() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinStringCurrent();
	}

	public int getMinStringCurrentString() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinStringCurrentString();
	}

	public BigDecimal getAverageStringCurrent() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getAverageStringCurrent();
	}

	public int getBatteryCellBalancingCount() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getBatteryCellBalancingCount();
	}

	public int getSocSF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getSoC_SF();
	}

	public int getSohSF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getSoH_SF();
	}

	public int getASF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getA_SF();
	}

	public int getVSF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getV_SF();
	}

	public int getCellVSF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getCellV_SF();
	}

	public int getModTmpSF() throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getModTmp_SF();
	}

	public BigDecimal getStringCurrent(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStringCurrent(repeatingBlockIndex);
	}

	public BigDecimal getStringCellVMax(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxCellVoltage(repeatingBlockIndex);
	}

	public int getStringCellVMaxModule(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMaxCellVoltageModule(repeatingBlockIndex);
	}

	public BigDecimal getStringCellVMin(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinCellVoltage(repeatingBlockIndex);
	}

	public int getStringCellVMinModule(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getMinCellVoltageModule(repeatingBlockIndex);
	}

	public BigDecimal getStringMaxModuleTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMax(repeatingBlockIndex);
	}

	public int getStringMaxModuleTemperatureModule(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMaxMod(repeatingBlockIndex);
	}

	public BigDecimal getStringMinModuleTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMin(repeatingBlockIndex);
	}

	public int getStringMinModuleTemperatureModule(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMinMod(repeatingBlockIndex);
	}

	public BigDecimal getStringAverageModuleTemperature(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStrModTmpAvg(repeatingBlockIndex);
	}

	public ContactorStatus getStringConnectorStatus(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getContactorStatus(repeatingBlockIndex);
	}

	public StringEvent1 getStringEvent1(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getStringEvent1(repeatingBlockIndex);
	}

	public int getPad1(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getPad(repeatingBlockIndex);
	}

	public int getPad2(int repeatingBlockIndex) throws ModbusException {
		return cLithiumIonBatteryBankModelBlockMaster.getPad2(repeatingBlockIndex);
	}

	//Set
	public void setConnectString(int repeatingBlockIndex) throws ModbusException {
		cLithiumIonBatteryBankModelBlockMaster.setConnectDisconnectStringValue(repeatingBlockIndex, ConnectDisconnectStringEnum.CONNECT_STRING.getRawValue());
	}

	public void setDisconnectString(int repeatingBlockIndex) throws ModbusException {
		cLithiumIonBatteryBankModelBlockMaster.setConnectDisconnectStringValue(repeatingBlockIndex, ConnectDisconnectStringEnum.DISCONNECT_STRING.getRawValue());
	}

	public final static void main(String[] args) {

		try {
			Modbus803 modBusTest = new Modbus803(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			modBusTest.init();
			int repeatingBlock = 0;
			// Tests
			LOG.info("getAverageModuleTemperature()={}", modBusTest.getAverageModuleTemperature());
			LOG.info("getAverageStringCurrent()={}", modBusTest.getAverageStringCurrent());
			LOG.info("getAverageStringVoltage()={}", modBusTest.getAverageStringVoltage());
			LOG.info("getMaxModuleTemperature()={}", modBusTest.getMaxModuleTemperature());
			LOG.info("getMaxStringCurrent()={}", modBusTest.getMaxStringCurrent());
			LOG.info("getMaxStringVoltage()={}", modBusTest.getMaxStringVoltage());
			LOG.info("getMinModuleTemperature()={}", modBusTest.getMinModuleTemperature());
			LOG.info("getMinStringCurrent()={}", modBusTest.getMinStringCurrent());
			LOG.info("getMinStringVoltage()={}", modBusTest.getMinStringVoltage());
			LOG.info("getStringAverageModuleTemperature(int repeatingBlock)={}", modBusTest.getStringAverageModuleTemperature(repeatingBlock));
			LOG.info("getStringCellVMax(int repeatingBlock)={}", modBusTest.getStringCellVMax(repeatingBlock));
			LOG.info("getStringCellVMin(int repeatingBlock)={}", modBusTest.getStringCellVMin(repeatingBlock));
			LOG.info("getStringCurrent(int repeatingBlock)={}", modBusTest.getStringCurrent(repeatingBlock));
			LOG.info("getStringMaxModuleTemperature(int repeatingBlock)={}", modBusTest.getStringMaxModuleTemperature(repeatingBlock));
			LOG.info("getStringMinModuleTemperature(int repeatingBlock)={}", modBusTest.getStringMinModuleTemperature(repeatingBlock));
			LOG.info("getStringConnectorStatus(int repeatingBlock)={}", modBusTest.getStringConnectorStatus(repeatingBlock).getCONTACTOR_1());
			LOG.info("getASF()={}", modBusTest.getASF());
			LOG.info("getBatteryCellBalancingCount()={}", modBusTest.getBatteryCellBalancingCount());
			LOG.info("getCellVSF()={}", modBusTest.getCellVSF());
			LOG.info("getConnectedStringCount()={}", modBusTest.getConnectedStringCount());
			LOG.info("getID()={}", modBusTest.getID());
			LOG.info("getMaxModuleTemperatureModule()={}", modBusTest.getMaxModuleTemperatureModule());
			LOG.info("getMaxModuleTemperatureString()={}", modBusTest.getMaxModuleTemperatureString());
			LOG.info("getMaxStringCurrentString()={}", modBusTest.getMaxStringCurrentString());
			LOG.info("getMaxStringVoltageString()={}", modBusTest.getMaxStringVoltageString());
			LOG.info("getMinModuleTemperatureModule()={}", modBusTest.getMinModuleTemperatureModule());
			LOG.info("getMinModuleTemperatureString()={}", modBusTest.getMinModuleTemperatureString());
			LOG.info("getMinStringCurrentString()={}", modBusTest.getMinStringCurrentString());
			LOG.info("getMinStringVoltageString()={}", modBusTest.getMinStringVoltageString());
			LOG.info("getModTmpSF()={}", modBusTest.getModTmpSF());
			LOG.info("getPad1(int repeatingBlock)={}", modBusTest.getPad1(repeatingBlock));
			LOG.info("getPad2(int repeatingBlock)={}", modBusTest.getPad2(repeatingBlock));
			LOG.info("getSocSF()={}", modBusTest.getSocSF());
			LOG.info("getSohSF()={}", modBusTest.getSohSF());
			LOG.info("getStringCellVMaxModule(int repeatingBlock)={}", modBusTest.getStringCellVMaxModule(repeatingBlock));
			LOG.info("getStringCellVMinModule(int repeatingBlock)={}", modBusTest.getStringCellVMinModule(repeatingBlock));
			LOG.info("getStringCount()={}", modBusTest.getStringCount());
			LOG.info("getStringMaxModuleTemperatureModule(int repeatingBlock)={}", modBusTest.getStringMaxModuleTemperatureModule(repeatingBlock));
			LOG.info("getStringMinModuleTemperatureModule(int repeatingBlock)={}", modBusTest.getStringMinModuleTemperatureModule(repeatingBlock));
			LOG.info("getVSF()={}", modBusTest.getVSF());
			LOG.info("getStringEvent1(int repeatingBlock)={}", modBusTest.getStringEvent1(repeatingBlock));
//			modBusTest.setConnectString(repeatingBlock);
//			modBusTest.setDisconnectString(repeatingBlock);

		} catch (Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
