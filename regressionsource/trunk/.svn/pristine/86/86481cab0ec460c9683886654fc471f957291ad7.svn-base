package com.powin.modbusfiles.modbus;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.blocks.ImmediateControlsBlockCommon.ConnEnum;
import com.powin.modbus.sunspec.blocks.ImmediateControlsBlockCommon.OutPFSet_EnaEnum;
import com.powin.modbus.sunspec.blocks.ImmediateControlsBlockCommon.VArPct_EnaEnum;
import com.powin.modbusfiles.utilities.PowinProperty;


public class Modbus123IntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Modbus123IntegrationTest.class.getName());
	public static Modbus123 mb123;

	private static String cModbusHostName;
	private static final int cModbusPort = 4502;
	private static final int cModubusUnitId = 255;
	private static final boolean cEnableModbusLogging = false;
	
	static  {
			cModbusHostName = PowinProperty.TURTLEHOST.toString();
	}

	@BeforeClass
	public static void init()
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException {
		LOG.info("init");
		mb123 = new Modbus123(getModbusHostName(), getModbusPort(), getModubusUnitId(), isEnableModbusLogging());
	}

	public static String getModbusHostName() {
		return cModbusHostName;
	}

	public static int getModbusPort() {
		return cModbusPort;
	}

	public static int getModubusUnitId() {
		return cModubusUnitId;
	}

	public static boolean isEnableModbusLogging() {
		return cEnableModbusLogging;
	}

	@Test
	public void getId() throws ModbusException {
		LOG.info("Test getId");
		assertEquals(123, mb123.getId());
	}

	@Test
	public void getBlockLengthTest() throws ModbusException {
		LOG.info("Test getBlockLength");
		assertEquals(24, mb123.getBlockLength());
	}

	@Test
	public void getConnTest() throws ModbusException {
		LOG.info("Test getBlockLength");
		assertEquals(ConnEnum.CONNECT, mb123.getConn());
	}

	@Test
	public void getWMaxLimPctTest() throws ModbusException {
		LOG.info("Test getWMaxLimPct");
		for (int i = 0; i < 100; i++) {
			mb123.setWMaxLimPct(BigDecimal.valueOf(i));
			assertEquals(i, mb123.getWMaxLimPct());
		}
	}

	@Test
	public void setWMaxLimPctTest() throws ModbusException {
		LOG.info("Test setWMaxLimPct");
		for (int i = 0; i < 100; i++) {
			mb123.setWMaxLimPct(BigDecimal.valueOf(i));
			assertEquals(i, mb123.getWMaxLimPct());
		}
	}

	@Test
	public void getWMaxLimPct_SFTest() throws ModbusException {
		LOG.info("Test getWMaxLimPct_SF");
		assertEquals(0, mb123.getWMaxLimPct_SF());
	}

	@Test
	public void getWMaxLim_EnaTest() throws ModbusException {
		LOG.info("Test getWMaxLim_Ena");
		assertEquals(1, mb123.getWMaxLim_Ena());
	}

	@Test
	public void setgetOutPFSetTest() throws ModbusException {
		LOG.info("Test getOutPFSet and setOutPFSet");
		int originOutPf = mb123.getOutPFSet();
		for (int i = 1; i < 100; i++) {
			mb123.setOutPFSet(BigDecimal.valueOf(i));
			assertEquals(i, mb123.getOutPFSet());
		}
		mb123.setOutPFSet(BigDecimal.valueOf(originOutPf));
	}
	
	@Test
	public void getOutPFSet_EnaTest() throws ModbusException {
		LOG.info("Test getOutPFSet_Ena");
		assertEquals(OutPFSet_EnaEnum.DISABLED, mb123.getOutPFSet_Ena());
	}

	@Test
	public void getVArWMaxPctTest() throws ModbusException {
		LOG.info("Test getVArWMaxPct");
		assertEquals(0, mb123.getVArWMaxPct());
	}

	@Test
	public void getVArPct_EnaTest() throws ModbusException {
		LOG.info("Test getVArPct_Ena");
		assertEquals(VArPct_EnaEnum.ENABLED, mb123.getVArPct_Ena());
	}

	@Test
	public void getOutPFSet_SFTest() throws ModbusException {
		LOG.info("Test getOutPFSet_SF");
		assertEquals(-2, mb123.getOutPFSet_SF());
	}
}
