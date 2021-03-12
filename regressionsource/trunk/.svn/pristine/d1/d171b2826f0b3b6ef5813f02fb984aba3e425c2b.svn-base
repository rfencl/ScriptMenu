package com.powin.modbusfiles.modbus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.BatteryBaseModelBlockCommon.SetInverterStateEnum;
import com.powin.modbus.sunspec.blocks.BatteryBaseModelBlockCommon.SetOperationEnum;
import com.powin.modbus.sunspec.blocks.BatteryBaseModelBlockMaster;
import com.powin.modbusfiles.utilities.PowinProperty;;

public class Modbus802 {
	private final static Logger LOG = LogManager.getLogger();

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private BatteryBaseModelBlockMaster cBatteryBaseModelBlockMaster;
	private SunspecMasterEndpoint cSunspecMasterEndpoint;

	public Modbus802(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		cModbusHostName = modbusHostName;
		cModbusPort = modbusPort;
		cModbusUnitId = modbusUnitId;
		cEnableModbusLogging = enableModbusLogging;
		init();
	}

	public void init() {
		SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging, 10000);
		cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
		cBatteryBaseModelBlockMaster = cSunspecMasterEndpoint.getBlock(802, 0);
		if (cBatteryBaseModelBlockMaster == null) {
			throw new IllegalStateException("No battery!");
		}
	}
	// Getters

	public int getNameplateChargeCapacity() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateChargeCapacity().intValue();
	}

	public int getNameplateEnergyCapacity() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateEnergyCapacity().intValue();
	}

	public int getNameplateMaxChargeRate() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateMaxChargeRate().intValue();
	}

	public int getNameplateMaxDischargeRate() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateMaxDischargeRate().intValue();
	}

	public int getNameplateMaxSOC() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateMaxSoC().intValue();
	}

	public int getNameplateMinSOC() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getNameplateMinSoC().intValue();
	}

	public int getSOC() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getStateofCharge().intValue();
	}

	public int getDepthOfDischarge() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getDepthofDischarge().intValue();
	}

	public int getChargeStatus() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getChargeStatusValue();
	}

	public String getControlMode() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getControlMode().name();
	}

	public int getBatteryHeartbeat() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getBatteryHeartbeat();
	}

	public int getControllerHeartbeat() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getControllerHeartbeat();
	}

	public int getBatteryTypeValue() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getBatteryTypeValue();
	}

	public int getStateOfBatteryBank() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getStateoftheBatteryBankValue();
	}

	public long getBatteryEvent1Bitfield() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getBatteryEvent1BitfieldValue();
	}

	public long getBatteryEvent2Bitfield() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getBatteryEvent2BitfieldValue();
	}

	public long getVendorEventBitfield1() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getVendorEventBitfield1Value();
	}

	public long getVendorEventBitfield2() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getVendorEventBitfield2Value();
	}

	public int getExternalBatteryVoltage() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getExternalBatteryVoltage().intValue();
	}

	public int getMaxCellVoltage() throws ModbusException {
//		cBatteryBaseModelBlockMaster.setCellV_SF(3);
		return cBatteryBaseModelBlockMaster.getMaxCellVoltage().intValue();
	}

	public int getMinCellVoltage() throws ModbusException {
//		cBatteryBaseModelBlockMaster.setCellV_SF(3);
		return cBatteryBaseModelBlockMaster.getMinCellVoltage().intValue();
	}

	public int getAvgCellVoltage() throws ModbusException {
//		cBatteryBaseModelBlockMaster.setCellV_SF(3);
		return cBatteryBaseModelBlockMaster.getAverageCellVoltage().intValue();
	}

	public int getTotalDCCurrent() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getTotalDCCurrent().intValue();
	}

	public int getMaxChargeCurrent() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getMaxChargeCurrent().intValue();
	}

	public int getMaxDischargeCurrent() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getMaxDischargeCurrent().intValue();
	}

	public int getTotalPower() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getTotalPower().intValue();
	}

	public int getMaximumBatteryVoltage() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getMaxBatteryVoltage().intValue();
	}

	public int getBatteryState() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getStateoftheBatteryBankValue();
	}

	// Scaling factors
	public int getChargeCapacitySF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getAHRtg_SF();
	}

	public int getEnergySF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getWHRtg_SF();
	}

	public int getChargeDischargeRateMax_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getWChaDisChaMax_SF();
	}

	public int getSOC_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getSoC_SF();
	}

	// Scale factor for DC bus voltage.
	public int getDCBusVoltage_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getV_SF();
	}

	// Scale factor for cell voltage.
	public int getCellVoltage_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getCellV_SF();
	}

	// Scale factor for DC current.
	public int getDcCurrent_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getA_SF();
	}

	// Scale factor for instantaneous DC charge/discharge current.
	public int getInstantaneousDcCurrent_SF() throws ModbusException {
		return cBatteryBaseModelBlockMaster.getAMax_SF();
	}

	// Setters
	public void setBatteryContactorsOff() throws ModbusException {
		cBatteryBaseModelBlockMaster.setSetOperationValue(2);
	}

	public void setBatteryContactorsOn() throws ModbusException {
		 cBatteryBaseModelBlockMaster.setSetOperationValue(1);
	}

	public void resetAlarm() throws ModbusException {
		cBatteryBaseModelBlockMaster.setAlarmReset(1);
	}

	public void setOperation(SetOperationEnum value) throws ModbusException {
		cBatteryBaseModelBlockMaster.setSetOperation(value);
	}

	public void setOperationValue(int value) throws ModbusException {
		cBatteryBaseModelBlockMaster.setSetOperationValue(value);
	}

	public void setInverterState(SetInverterStateEnum value) throws ModbusException {
		cBatteryBaseModelBlockMaster.setSetInverterState(value);
	}

	public void setInverterStateValue(int value) throws ModbusException {
		cBatteryBaseModelBlockMaster.setSetInverterStateValue(value);
	}
	
	public void setCellV_SF(int value) throws ModbusException {
		cBatteryBaseModelBlockMaster.setCellV_SF(value);
	}

	public final static void main(String[] args) {

		try {
			Modbus802 modBus_Test = new Modbus802(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			modBus_Test.init();

			// Tests
			 LOG.info(modBus_Test.getNameplateMaxDischargeRate());
			 LOG.info(modBus_Test.getNameplateMaxSOC());
			 LOG.info(modBus_Test.getNameplateMinSOC());
			 LOG.info("getAvgCellVoltage()={}", modBus_Test.getAvgCellVoltage());
			 LOG.info("getBatteryEvent1Bitfield()={}", modBus_Test.getBatteryEvent1Bitfield());
			 LOG.info("getBatteryEvent2Bitfield()={}", modBus_Test.getBatteryEvent2Bitfield());
			 LOG.info("getBatteryHeartbeat()={}", modBus_Test.getBatteryHeartbeat());
			 LOG.info("getBatteryState()={}", modBus_Test.getBatteryState());
			 LOG.info("getBatteryTypeValue()={}", modBus_Test.getBatteryTypeValue());
			 LOG.info("getCellVoltage_SF()={}", modBus_Test.getCellVoltage_SF());
			 LOG.info("getChargeCapacitySF()={}", modBus_Test.getChargeCapacitySF());
			 LOG.info("getChargeDischargeRateMax_SF()={}", modBus_Test.getChargeDischargeRateMax_SF());
			 LOG.info("getChargeStatus()={}", modBus_Test.getChargeStatus());
			 LOG.info("getControllerHeartbeat()={}", modBus_Test.getControllerHeartbeat());
			 LOG.info("getControlMode()={}", modBus_Test.getControlMode());
			 LOG.info("getDCBusVoltage_SF()={}", modBus_Test.getDCBusVoltage_SF());
			 LOG.info("getDcCurrent_SF()={}", modBus_Test.getDcCurrent_SF());
			 LOG.info("getDepthOfDischarge()={}", modBus_Test.getDepthOfDischarge());
			 LOG.info("getEnergySF()={}", modBus_Test.getEnergySF());
			 LOG.info("getExternalBatteryVoltage()={}", modBus_Test.getExternalBatteryVoltage());
			 LOG.info("getInstantaneousDcCurrent_SF()={}", modBus_Test.getInstantaneousDcCurrent_SF());
			LOG.info("getMaxCellVoltage()={}", modBus_Test.getMaxCellVoltage());
			 LOG.info("getMaxChargeCurrent()={}", modBus_Test.getMaxChargeCurrent());
			 LOG.info("getMaxDischargeCurrent()={}", modBus_Test.getMaxDischargeCurrent());
			 LOG.info("getMaximumBatteryVoltage()={}", modBus_Test.getMaximumBatteryVoltage());
			 LOG.info("getMinCellVoltage()={}", modBus_Test.getMinCellVoltage());
			 LOG.info("getNameplateChargeCapacity()={}", modBus_Test.getNameplateChargeCapacity());
			 LOG.info("getNameplateEnergyCapacity()={}", modBus_Test.getNameplateEnergyCapacity());
			 LOG.info("getNameplateMaxChargeRate()={}", modBus_Test.getNameplateMaxChargeRate());
			 LOG.info("getNameplateMaxDischargeRate()={}", modBus_Test.getNameplateMaxDischargeRate());
			 LOG.info("getNameplateMaxSOC()={}", modBus_Test.getNameplateMaxSOC());
			 LOG.info("getNameplateMinSOC()={}", modBus_Test.getNameplateMinSOC());
			 LOG.info("getSOC_SF()={}", modBus_Test.getSOC_SF());
			 LOG.info("getSOC()={}", modBus_Test.getSOC());
			 LOG.info("getStateOfBatteryBank()={}", modBus_Test.getStateOfBatteryBank());
			 LOG.info("getTotalDCCurrent()={}", modBus_Test.getTotalDCCurrent());
			 LOG.info("getTotalPower()={}", modBus_Test.getTotalPower());
			 LOG.info("getVendorEventBitfield1()={}", modBus_Test.getVendorEventBitfield1());//TODO
			 LOG.info("getVendorEventBitfield2()={}", modBus_Test.getVendorEventBitfield2());//TODO
//			 modBus_Test.resetAlarm();//TODO
//			modBus_Test.setBatteryContactorsOn();
//			 modBus_Test.setBatteryContactorsOff();
//			 modBus_Test.setInverterState(SetInverterStateEnum.INVERTER_STOPPED);
//			 modBus_Test.setInverterStateValue(1);
//			 modBus_Test.setOperation(SetOperationEnum.DISCONNECT);
//			 modBus_Test.setOperation(SetOperationEnum.CONNECT);
//			 modBus_Test.setOperationValue(2);//disconnect
//			 modBus_Test.setOperationValue(1);//connect

		} catch (

		Exception e) {
			LOG.error("Exception in go.", e);
		}
	}
}
