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
import org.junit.Ignore;
import org.junit.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.blocks.NameplateBlockCommon.DERTypEnum;
import com.powin.modbusfiles.utilities.PowinProperty;


public class Modbus120IntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Modbus120IntegrationTest.class.getName());
	public static Modbus120 mb120;
	
	private static String cModbusHostName;
	private static final int cModbusPort = 4502;
	private static final int cModubusUnitId = 255;
	private static final boolean cEnableModbusLogging = false;
	
	static {
		
			cModbusHostName = PowinProperty.TURTLEHOST.toString();
	}

	@BeforeClass
	public static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LOG.info("init");
		mb120 = new Modbus120(getModbusHostName(), getModbusPort(), getModubusUnitId(), isEnableModbusLogging());
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
	public void getIdTest() throws ModbusException {
		LOG.info("Test getId");
		assertEquals(120, mb120.getId());
	}
	
	@Test
	public void getBlockLengthTest() throws ModbusException {
		LOG.info("Test getBlockLength");
		assertEquals(26, mb120.getBlockLength());
	}
	
	@Test 
	public void getDerTypeTest() throws ModbusException {
		LOG.info("Test getDerTypeTest");
		assertEquals(DERTypEnum.PV_STOR, mb120.getDerType());
	}
	
	@Test
	@Ignore
	public void getWRtgTest() throws ModbusException {
		LOG.info("Test getWRtg");
		assertEquals(BigDecimal.valueOf(125000), mb120.getWRtg());
	}
	
	@Test
	public void getWRtgSfTest() throws ModbusException {
		LOG.info("Test getWRtgSf");
		assertEquals(3, mb120.getWRtgSf());
	}
	
	@Test
	@Ignore
	public void getVARtgTest() throws ModbusException {
		LOG.info("Test getVARtg");
		assertEquals(BigDecimal.valueOf(125000), mb120.getVARtg());
	}

	@Test
	public void getVARtgSfTest() throws ModbusException {
		LOG.info("Test getVARtgSf");
		assertEquals(3, mb120.getVARtgSf());
	}

	@Test
	public void getVArRtgQ1Test() throws ModbusException {
		LOG.info("Test getVArRtgQ1");
		assertEquals(BigDecimal.ZERO, mb120.getVArRtgQ1());
	}
	
	@Test
	public void getVArRtgQ2Test() throws ModbusException {
		LOG.info("Test getVArRtgQ2");
		assertEquals(BigDecimal.ZERO, mb120.getVArRtgQ2());
	}
	
	@Test
	public void getVArRtgQ3Test() throws ModbusException {
		LOG.info("Test getVArRtgQ3");
		assertEquals(BigDecimal.ZERO, mb120.getVArRtgQ3());
	}
	
	@Test
	public void getVArRtgQ4Test() throws ModbusException {
		LOG.info("Test getVArRtgQ4");
		assertEquals(BigDecimal.ZERO, mb120.getVArRtgQ4());
	}
	
	@Test
	public void getVArRtgSfTest() throws ModbusException {
		LOG.info("Test getVArRtgSf");
		assertEquals(3, mb120.getVArRtgSf());
	}

	@Test
	@Ignore
	public void getARtgTest() throws ModbusException {
		LOG.info("Test getARtg");
		assertEquals(BigDecimal.valueOf(200), mb120.getARtg());
	}
	
	@Test
	public void getARtgSfTest() throws ModbusException {
		LOG.info("Test getARtgSf");
		assertEquals(1, mb120.getARtgSf());
	}

	@Test
	public void getPFRtgQ1Test() throws ModbusException {
		LOG.info("Test getPFRtgQ1");
		assertEquals(BigDecimal.ZERO, mb120.getPFRtgQ1().setScale(0));
	}
	
	@Test
	public void getPFRtgQ2Test() throws ModbusException {
		LOG.info("Test getPFRtgQ2");
		assertEquals(BigDecimal.ZERO, mb120.getPFRtgQ2().setScale(0));
	}
	
	@Test
	public void getPFRtgQ3Test() throws ModbusException {
		LOG.info("Test getPFRtgQ3");
		assertEquals(BigDecimal.ZERO, mb120.getPFRtgQ3().setScale(0));
	}
	
	@Test
	public void getPFRtgQ4Test() throws ModbusException {
		LOG.info("Test getPFRtgQ4");
		assertEquals(BigDecimal.ZERO, mb120.getPFRtgQ4().setScale(0));
	}

	@Test
	public void getPFRtgSfTest() throws ModbusException {
		LOG.info("Test getPFRtgSf");
		assertEquals(-2, mb120.getPFRtgSf());
	}
	
	@Test
	public void getWHRtgTest() throws ModbusException {
		LOG.info("Test getWHRtg");
		BigDecimal expected = new BigDecimal("6.5535E-32764");
		assertEquals(expected, mb120.getWHRtg());
	}
	
	@Test
	public void getWHRtgSfTest() throws ModbusException {
		LOG.info("Test getWHRtgSf");
		assertEquals(-32768, mb120.getWHRtgSf());
	}
	
	@Test
	public void getAHrRtgTest() throws ModbusException {
		LOG.info("Test getAHrRtg");
		BigDecimal expected = new BigDecimal("6.5535E-32764");
		assertEquals(expected, mb120.getAHrRtg());
	}

	@Test
	public void getMaxChaRteTest() throws ModbusException {
		LOG.info("Test getMaxChaRte");
		BigDecimal expected = new BigDecimal("6.5535E-32764");
		assertEquals(expected, mb120.getMaxChaRte());
	}
	
	@Test
	public void getMaxDisChaRteTest() throws ModbusException {
		LOG.info("Test getMaxDisChaRte");
		BigDecimal expected = new BigDecimal("6.5535E-32764");
		assertEquals(expected, mb120.getMaxDisChaRte());
	}
	
	@Test
	public void getAHrRtgSfTest() throws ModbusException {
		LOG.info("Test getAHrRtgSf");
		assertEquals(-32768, mb120.getAHrRtgSf());
	}
	
	@Test
	public void getMaxChaRteSfTest() throws ModbusException {
		LOG.info("Test getMaxChaRteSf");
		assertEquals(-32768, mb120.getMaxChaRteSf());
	}
	
	@Test
	public void getMaxDisChaRteSfTest() throws ModbusException {
		LOG.info("Test getMaxDisChaRteSf");
		assertEquals(-32768, mb120.getMaxDisChaRteSf());
	}
	
	@Test
	public void getPadTest() throws ModbusException {
		LOG.info("Test getPad");
		assertEquals(-32768, mb120.getPad());
	}

}
