package com.powin.modbusfiles.modbus;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.NameplateBlockCommon.DERTypEnum;
import com.powin.modbus.sunspec.blocks.NameplateBlockMaster;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Modbus120 {
	private final static Logger LOG = LogManager.getLogger();

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private static NameplateBlockMaster cNameplateBlockMaster;
	private SunspecMasterEndpoint cSunspecMasterEndpoint;

	public Modbus120(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		this.cModbusHostName = modbusHostName;
		this.cModbusPort = modbusPort;
		this.cModbusUnitId = modbusUnitId;
		this.cEnableModbusLogging = enableModbusLogging;
		init();
	}

	public void init() {
		SunspecTcpMasterEndpointFactory mSunspecMasterEndpointFactory = new SunspecTcpMasterEndpointFactory(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging, 10000);
		cSunspecMasterEndpoint = mSunspecMasterEndpointFactory.getSunspecMasterEndpoint(null);
		cNameplateBlockMaster = cSunspecMasterEndpoint.getBlock(120, 0);
		if (cNameplateBlockMaster == null) {
			throw new IllegalStateException("No Immediate Controls");
		}
	}

	//Getters
	public int getId() throws ModbusException {
		return cNameplateBlockMaster.getId();
	}

	public int getBlockLength() throws ModbusException {
		return cNameplateBlockMaster.getBlockLength();
	}

	public DERTypEnum getDerType() throws ModbusException {
		return cNameplateBlockMaster.getDERTyp();
	}

	public BigDecimal getWRtg() throws ModbusException {
		return cNameplateBlockMaster.getWRtg();
	}

	public int getWRtgSf() throws ModbusException {
		return cNameplateBlockMaster.getWRtg_SF();
	}

	public BigDecimal getVARtg() throws ModbusException {
		return cNameplateBlockMaster.getVARtg();
	}

	public int getVARtgSf() throws ModbusException {
		return cNameplateBlockMaster.getVARtg_SF();
	}

	public BigDecimal getVArRtgQ1() throws ModbusException {
		return cNameplateBlockMaster.getVArRtgQ1();
	}

	public BigDecimal getVArRtgQ2() throws ModbusException {
		return cNameplateBlockMaster.getVArRtgQ2();
	}

	public BigDecimal getVArRtgQ3() throws ModbusException {
		return cNameplateBlockMaster.getVArRtgQ3();
	}

	public BigDecimal getVArRtgQ4() throws ModbusException {
		return cNameplateBlockMaster.getVArRtgQ4();
	}

	public int getVArRtgSf() throws ModbusException {
		return cNameplateBlockMaster.getVArRtg_SF();
	}

	public BigDecimal getARtg() throws ModbusException {
		return cNameplateBlockMaster.getARtg();
	}

	public int getARtgSf() throws ModbusException {
		return cNameplateBlockMaster.getARtg_SF();
	}

	public BigDecimal getPFRtgQ1() throws ModbusException {
		return cNameplateBlockMaster.getPFRtgQ1();
	}

	public BigDecimal getPFRtgQ2() throws ModbusException {
		return cNameplateBlockMaster.getPFRtgQ2();
	}

	public BigDecimal getPFRtgQ3() throws ModbusException {
		return cNameplateBlockMaster.getPFRtgQ3();
	}

	public BigDecimal getPFRtgQ4() throws ModbusException {
		return cNameplateBlockMaster.getPFRtgQ4();
	}

	public int getPFRtgSf() throws ModbusException {
		return cNameplateBlockMaster.getPFRtg_SF();
	}

	public BigDecimal getWHRtg() throws ModbusException {
		return cNameplateBlockMaster.getWHRtg();
	}

	public int getWHRtgSf() throws ModbusException {
		return cNameplateBlockMaster.getWHRtg_SF();
	}

	public BigDecimal getAHrRtg() throws ModbusException {
		return cNameplateBlockMaster.getAhrRtg();
	}

	public int getAHrRtgSf() throws ModbusException {
		return cNameplateBlockMaster.getAhrRtg_SF();
	}

	public BigDecimal getMaxChaRte() throws ModbusException {
		return cNameplateBlockMaster.getMaxChaRte();
	}

	public int getMaxChaRteSf() throws ModbusException {
		return cNameplateBlockMaster.getMaxChaRte_SF();
	}

	public BigDecimal getMaxDisChaRte() throws ModbusException {
		return cNameplateBlockMaster.getMaxDisChaRte();
	}

	public int getMaxDisChaRteSf() throws ModbusException {
		return cNameplateBlockMaster.getMaxDisChaRte_SF();
	}

	public int getPad() throws ModbusException {
		return cNameplateBlockMaster.getPad();
	}

	public static void main(String[] args) throws IOException {
		try {
			Modbus120 mModbus120 = new Modbus120(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mModbus120.init();
			LOG.info("Modbus120 getId is: {}", mModbus120.getId());//120
			LOG.info("Modbus120 getBlockLength is: {}", mModbus120.getBlockLength());//26
			LOG.info("Modbus120 getDerType is: {}", mModbus120.getDerType());//DERTyp	PV		4; DERTyp		PV_STOR		82
			LOG.info("Modbus120 getWRtg is: {}", mModbus120.getWRtg());//125000
			LOG.info("Modbus120 getWRtgSf is: {}", mModbus120.getWRtgSf());//3
			LOG.info("Modbus120 getVARtg is: {}", mModbus120.getVARtg());//125000
			LOG.info("Modbus120 getVARtgSf is: {}", mModbus120.getVARtgSf());//3
			LOG.info("Modbus120 getVArRtgQ1 is: {}", mModbus120.getVArRtgQ1());//0
			LOG.info("Modbus120 getVArRtgQ2 is: {}", mModbus120.getVArRtgQ2());//0
			LOG.info("Modbus120 getVArRtgQ3 is: {}", mModbus120.getVArRtgQ3());//0
			LOG.info("Modbus120 getVArRtgQ4 is: {}", mModbus120.getVArRtgQ4());//0
			LOG.info("Modbus120 getVArRtgSf is: {}", mModbus120.getVArRtgSf());//3
			LOG.info("Modbus120 getARtg is: {}", mModbus120.getARtg());//200
			LOG.info("Modbus120 getARtgSf is: {}", mModbus120.getARtgSf());//1
			LOG.info("Modbus120 getPFRtgQ1 is: {}", mModbus120.getPFRtgQ1());//0
			LOG.info("Modbus120 getPFRtgQ2 is: {}", mModbus120.getPFRtgQ2());//0
			LOG.info("Modbus120 getPFRtgQ3 is: {}", mModbus120.getPFRtgQ3());//0
			LOG.info("Modbus120 getPFRtgQ4 is: {}", mModbus120.getPFRtgQ4());//0
			LOG.info("Modbus120 getPFRtgSf is: {}", mModbus120.getPFRtgSf());//-2
			LOG.info("Modbus120 getWHRtg is: {}", mModbus120.getWHRtg());//6.5535E-32764
			LOG.info("Modbus120 getWHRtgSf is: {}", mModbus120.getWHRtgSf());//-32768
			LOG.info("Modbus120 getAHrRtg is: {}", mModbus120.getAHrRtg());//6.5535E-32764
			LOG.info("Modbus120 getAHrRtgSf is: {}", mModbus120.getAHrRtgSf());//-32768
			LOG.info("Modbus120 getMaxChaRte is: {}", mModbus120.getMaxChaRte());//6.5535E-32764
			LOG.info("Modbus120 getMaxChaRteSf is: {}", mModbus120.getMaxChaRteSf());//-32768
			LOG.info("Modbus120 getMaxDisChaRte is: {}", mModbus120.getMaxDisChaRte());//6.5535E-32764
			LOG.info("Modbus120 getMaxDisChaRteSf is: {}", mModbus120.getMaxDisChaRteSf());//-32768
			LOG.info("Modbus120 getPad is: {}", mModbus120.getPad());//-32768

		} catch (ModbusException e) {
			LOG.error("Exception in Modbus123. " + e.getMessage());
		}
	}
}
