package com.powin.modbusfiles.modbus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Modbus103IntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Modbus103IntegrationTest.class.getName());
	public static Modbus103 mb103;

	private static final String cModbusHostName = PowinProperty.TURTLEHOST.toString();
	private static final int cModbusPort = 4502;
	private static final int cModubusUnitId = 255;
	private static final boolean cEnableModbusLogging = false;
	private static LastCalls cLastCalls;
	private static Reports cStringReport;
	private static ModbusPowinBlock mPowinBlockTest;

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

	public static LastCalls getLastCalls() {
		return cLastCalls;
	}

	public static void setLastCalls(LastCalls lastCalls) {
		cLastCalls = lastCalls;
	}

	public Reports getStringReport() {
		return cStringReport;
	}

	public static void setStringReport(Reports stringReport) {
		cStringReport = stringReport;
	}

	@BeforeClass
	public static void init()
			throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException {
		LOG.info("init");

		mb103 = new Modbus103(cModbusHostName, cModbusPort, cModubusUnitId, cEnableModbusLogging);
		setLastCalls(new LastCalls());
		LOG.info("init finished.");
	}

	@Test
	public void getPPVphABTest() {
		LOG.info("Test getPPVphAB");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageAB());
			int actualValue = mb103.getPPVphAB();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPPVphAB. " + e.toString());
		}
	}

	@Test
	public void getPPVphBCTest() {
		LOG.info("Test getPPVphBC");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageBC());
			int actualValue = mb103.getPPVphBC();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPPVphBC. " + e.toString());
		}
	}

	@Test
	public void getPPVphCATest() {
		LOG.info("Test getPPVphCA");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageCA());
			int actualValue = mb103.getPPVphCA();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPPVphCA. " + e.toString());
		}
	}

	@Test
	public void getPhVphATest() throws ModbusException {
		LOG.info("Test getPhVphA");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageAN());
			int actualValue = mb103.getPhVphA();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPhVphA. " + e.toString());
		}
	}

	@Test
	public void getPhVphBTest() {
		LOG.info("Test getPhVphB");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageBN());
			int actualValue = mb103.getPhVphB();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPhVphB. " + e.toString());
		}
	}

	@Test
	public void getPhVphCTest() {
		LOG.info("Test getPhVphC");
		try {
			int exceptedValue = Integer.parseInt(getLastCalls().getPhaseVoltageCN());
			int actualValue = mb103.getPhVphC();
			assertTrue(actualValue < (int) (exceptedValue * 1.01 + 0.5));
			assertTrue(actualValue > (int) (exceptedValue * 0.99 - 0.5));
		} catch (Exception e) {
			LOG.info("Failed to Test getPhVphC. " + e.toString());
		}
	}

	@Test
	public void getA_SFTest() throws ModbusException {
		LOG.info("Test getA_SF");
		assertEquals(1, mb103.getA_SF());
	}

	@Test
	public void getV_SFTest() throws ModbusException {
		LOG.info("Test getV_SF");
		assertEquals(0, mb103.getV_SF());
	}

	@Test
	public void getW_SFTest() throws ModbusException {
		LOG.info("Test getW_SF");
		assertEquals(3, mb103.getW_SF());
	}

	@Test
	public void getHz_SFTest() throws ModbusException {
		LOG.info("Test getHz_SF");
		assertEquals(-2, mb103.getHz_SF());
	}

	@Test
	public void getVA_SFTest() throws ModbusException {
		LOG.info("Test getVA_SF");
		assertEquals(3, mb103.getVA_SF());
	}

	@Test
	public void getVAr_SFTest() throws ModbusException {
		LOG.info("Test getVAr_SF");
		assertEquals(3, mb103.getVAr_SF());
	}

	@Test
	public void getWH_SFTest() throws ModbusException {
		LOG.info("Test getWH_SF");
		assertEquals(3, mb103.getWH_SF());
	}

	@Test
	public void getDCA_SFTest() throws ModbusException {
		LOG.info("Test getDCA_SF");
		assertEquals(1, mb103.getDCA_SF());
	}

	@Test
	public void getDCV_SFTest() throws ModbusException {
		LOG.info("Test getDCV_SF");
		assertEquals(0, mb103.getDCV_SF());
	}

	@Test
	public void getDCW_SFTest() throws ModbusException {
		LOG.info("Test getDCW_SF");
		assertEquals(3, mb103.getDCW_SF());
	}

	@Test
	public void getTmp_SFTest() throws ModbusException {
		LOG.info("Test getTmp_SF");
		assertEquals(-1, mb103.getTmp_SF());
	}
	// 22:07:03.831 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 Amps is:
	// 0
	// 22:07:03.836 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103
	// AmpsPhaseA is: 0
	// 22:07:03.837 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103
	// AmpsPhaseB is: 0
	// 22:07:03.838 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103
	// AmpsPhaseC is: 0

	// 22:07:03.849 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 Watts
	// is: 0
	// 22:07:03.850 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 Frequecy
	// is: 59

	// 22:07:03.851 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 VA is: 0

	// 22:07:03.852 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 VAr is:
	// 0

	// 22:07:03.853 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 WH is: 0

	// 22:07:03.854 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 DC Amps
	// is: 0

	// 22:07:03.855 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 DC
	// Voltage is: 893

	// 22:07:03.856 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 DC Watts
	// is: 0

	// 22:07:03.857 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103 Cabinet
	// Temperature is: 0

	// 22:07:03.857 [main] INFO com.powin.modbusfiles.Modbus103 - Modbus103
	// Operating State is: STARTING

}
