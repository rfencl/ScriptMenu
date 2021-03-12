package com.powin.modbusfiles.modbus;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockCommon.Event1;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockCommon.EventBitfield2;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockCommon.OperatingStateEnum;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockCommon.VendorEventBitfield1;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockMaster;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Modbus103 {
	private final static Logger LOG = LogManager.getLogger();

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private InverterThreePhaseBlockMaster cInverterThreePhaseBlockMaster;
	private SunspecMasterEndpoint cSunspecMasterEndpoint;

	public Modbus103(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		this.cModbusHostName = modbusHostName;
		this.cModbusPort = modbusPort;
		this.cModbusUnitId = modbusUnitId;
		this.cEnableModbusLogging = enableModbusLogging;
		this.init();
	}

	public void init() {
		SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging, 10000);
		cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
		cInverterThreePhaseBlockMaster = cSunspecMasterEndpoint.getBlock(103, 0);

		if (cInverterThreePhaseBlockMaster == null) {
			throw new IllegalStateException("No inverter103!");
		}
	}

	public int getAmps() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getAmps().intValue();
	} 
	public int getAmpsPhaseA() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getAmpsPhaseA().intValue();
	}

	public int getAmpsPhaseB() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getAmpsPhaseB().intValue();
	}

	public int getAmpsPhaseC() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getAmpsPhaseC().intValue();
	}

	public int getA_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getA_SF();
	}

	public int getPPVphAB() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageAB().intValue();
	}

	public int getPPVphBC() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageBC().intValue();
	}

	public int getPPVphCA() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageCA().intValue();
	}

	public int getPhVphA() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageAN().intValue();
	}

	public int getPhVphB() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageBN().intValue();
	}

	public int getPhVphC() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getPhaseVoltageCN().intValue();
	}

	public int getV_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getV_SF();
	}

	public int getWatts() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWatts().intValue();
	}

	public int getW_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getW_SF();
	}

	public int getHz() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getHz().intValue();
	}

	public int getHz_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getHz_SF();
	}

	public int getVA() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getVA().intValue();
	}

	public int getVA_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getVA_SF();
	}

	public int getVAr() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getVAr().intValue();
	}

	public int getVAr_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getVAr_SF();
	}

	public int getWattHours() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWattHours().intValue();
	}

	public int getWH_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getWH_SF();
	}

	public int getDCAmps() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCAmps().intValue();
	}

	public int getDCA_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCA_SF();
	}

	public int getDCBusVoltage() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCVoltage().intValue();
	}

	public int getDCV_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCV_SF();
	}

	public int getDCWatts() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCWatts().intValue();
	}

	public int getDCW_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getDCW_SF();
	}

	public int getTmpCab() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getCabinetTemperature().intValue();
	}

	public int getTmp_SF() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getTmp_SF();
	}

	public OperatingStateEnum getOperatingState() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getOperatingState();
	}

	public Event1 getEvt1() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getEvent1();
	}

	public EventBitfield2 getEvt2() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getEventBitfield2();
	}

	public VendorEventBitfield1 getEvtVnd1() throws ModbusException {
		return cInverterThreePhaseBlockMaster.getVendorEventBitfield1();
	}

	public static void main(String[] args) throws IOException {

		try {
			Modbus103 mModbus103 = new Modbus103(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mModbus103.init();
			LOG.info("Modbus103 Amps is: {}", mModbus103.getAmps());
			LOG.info("Modbus103 AmpsPhaseA is: {}", mModbus103.getAmpsPhaseA());
			LOG.info("Modbus103 AmpsPhaseB is: {}", mModbus103.getAmpsPhaseB());
			LOG.info("Modbus103 AmpsPhaseC is: {}", mModbus103.getAmpsPhaseC());
			LOG.info("Modbus103 A_SF is: {}", mModbus103.getA_SF());
			LOG.info("Modbus103 Phase Voltage AB is: {}", mModbus103.getPPVphAB());
			LOG.info("Modbus103 Phase Voltage BC is: {}", mModbus103.getPPVphBC());
			LOG.info("Modbus103 Phase Voltage CA is: {}", mModbus103.getPPVphCA());
			LOG.info("Modbus103 Phase Voltage AN is: {}", mModbus103.getPhVphA());
			LOG.info("Modbus103 Phase Voltage BN is: {}", mModbus103.getPhVphB());
			LOG.info("Modbus103 Phase Voltage AN is: {}", mModbus103.getPhVphC());
			LOG.info("Modbus103 V_SF is: {}", mModbus103.getV_SF());
			LOG.info("Modbus103 Watts is: {}", mModbus103.getWatts());
			LOG.info("Modbus103 W_SF is: {}", mModbus103.getW_SF());
			LOG.info("Modbus103 Frequecy is: {}", mModbus103.getHz());
			LOG.info("Modbus103 Hz_SF is: {}", mModbus103.getHz_SF());
			LOG.info("Modbus103 VA is: {}", mModbus103.getVA());
			LOG.info("Modbus103 VA_SF is: {}", mModbus103.getVA_SF());
			LOG.info("Modbus103 VAr is: {}", mModbus103.getVAr());
			LOG.info("Modbus103 VAr_SF is: {}", mModbus103.getVAr_SF());
			LOG.info("Modbus103 WH is: {}", mModbus103.getWattHours());
			LOG.info("Modbus103 WH_SF is: {}", mModbus103.getWH_SF());
			LOG.info("Modbus103 DC Amps is: {}", mModbus103.getDCAmps());
			LOG.info("Modbus103 DCA_SF is: {}", mModbus103.getDCA_SF());
			LOG.info("Modbus103 DC Voltage is: {}", mModbus103.getDCBusVoltage());
			LOG.info("Modbus103 DCV_SF is: {}", mModbus103.getDCV_SF());
			LOG.info("Modbus103 DC Watts is: {}", mModbus103.getDCWatts());
			LOG.info("Modbus103 DCW_SF is: {}", mModbus103.getDCW_SF());
			LOG.info("Modbus103 Cabinet Temperature is: {}", mModbus103.getTmpCab());
			LOG.info("Modbus103 Tmp_SF is: {}", mModbus103.getTmp_SF());
			LOG.info("Modbus103 Operating State is: {}", mModbus103.getOperatingState());
			LOG.info("Modbus103 Event1 is: {}", mModbus103.getEvt1());
			LOG.info("Modbus103 Event Bitfield 2 is: {}", mModbus103.getEvt2());
			LOG.info("Modbus103 Vendor Event Bitfield 1 is: {}", mModbus103.getEvtVnd1());

		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			LOG.error("Modbus103 Exception. " + e.getMessage());
		}
	}
}
