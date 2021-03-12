package com.powin.modbusfiles.modbus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StringReport;
import com.powin.modbusfiles.reports.SystemInfo;

public class Modbus803IntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Modbus803IntegrationTest.class.getName());
	public static Modbus803 mb803;

	private static final String cModbusHostName = "127.0.0.1";
	private static final int cModbusPort = 4502;
	private static final int cModubusUnitId = 255;
	private static final boolean cEnableModbusLogging = false;
	private static LastCalls cLastCalls;
	private static Reports cStringReport;
	private static String cArrayIndex;
	private static int cStackCount;

	public static String getArrayIndex() {
		return cArrayIndex;
	}

	public static void setArrayIndex(String arrayIndex) {
		cArrayIndex = arrayIndex;
	}

	public static int getStackCount() {
		return cStackCount;
	}

	public static void setStackCount(int stackCount) {
		cStackCount = stackCount;
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
    // TODO Figure out what to do with this stack count.
	@BeforeClass
	public static void init() throws IOException {
		LOG.info("init");
		mb803 = new Modbus803(getModbusHostName(), getModbusPort(), getModubusUnitId(), isEnableModbusLogging());
		setStringReport(new Reports("1,1"));
		setArrayIndex("1");
		setStackCount(20);
		LOG.info("init finished.");
	}

	@Test
	public void getAverageModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getAverageModuleTemperature");
		getStringReport().getReportContents();
		int exceptedValue = (int) Double.parseDouble(getStringReport().getAvgCellGroupTemperature());
		int actualValue = mb803.getAverageModuleTemperature().setScale(1).multiply(BigDecimal.valueOf(10)).intValue();
		assertTrue(Math.abs(exceptedValue - actualValue) <= 2);
	}

	@Test
	public void getAverageStringCurrentTest() throws ModbusException {
		LOG.info("Test getAverageStringCurrent");
		getStringReport().getReportContents();
		BigDecimal exceptedValue = BigDecimal.valueOf(Double.parseDouble(getStringReport().getStringCurrent()))
				.setScale(1);
		assertEquals(exceptedValue, mb803.getAverageStringCurrent().setScale(1));
	}

	@Test
	public void getAverageStringVoltageTest() throws ModbusException {
		LOG.info("Test getAverageStringVoltage");
		List<StringReport> stringReportList = SystemInfo.getStringReportList(getArrayIndex(), getStackCount());
		assertTrue((stringReportList != null && stringReportList.size() > 0));
		if (stringReportList != null && stringReportList.size() > 0) {
			getStringReport().getReportContents();
			BigDecimal exceptedValue = BigDecimal
					.valueOf(SystemInfo.getAverageStringVoltage(stringReportList)).setScale(0);
			assertEquals(exceptedValue, mb803.getAverageStringVoltage().setScale(0));
		}
	}

	@Test
	public void getMaxModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getMaxModuleTemperature");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getMaxCellGroupTemperature());
		int actualValue = mb803.getMaxModuleTemperature().setScale(1).multiply(BigDecimal.valueOf(10)).intValue();
		assertEquals(exceptedValue, actualValue);
	}

	@Test
	public void getMaxStringCurrentTest() throws ModbusException {
		LOG.info("Test getMaxStringCurrent");
		getStringReport().getReportContents();
		BigDecimal exceptedValue = BigDecimal.valueOf(Double.parseDouble(getStringReport().getStringCurrent()))
				.setScale(1);
		assertEquals(exceptedValue, mb803.getMaxStringCurrent().setScale(1));
	}

	@Test
	public void getMaxStringVoltageTest() throws ModbusException {
		LOG.info("Test getMaxStringVoltage");

		List<StringReport> stringReportList = SystemInfo.getStringReportList(getArrayIndex(), getStackCount());
		assertTrue((stringReportList != null && stringReportList.size() > 0));
		if (stringReportList != null && stringReportList.size() > 0) {
			BigDecimal exceptedValue = BigDecimal.valueOf(SystemInfo.getMaxStringVoltage(stringReportList))
					.setScale(0);
			assertEquals(exceptedValue, mb803.getMaxStringVoltage().setScale(0));
		}
	}

	@Test
	public void getMinModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getMinModuleTemperature");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getMinCellGroupTemperature());
		int actualValue = mb803.getMinModuleTemperature().setScale(1).multiply(BigDecimal.valueOf(10)).intValue();
		assertEquals(exceptedValue, actualValue);
	}

	@Test
	public void getMinStringCurrentTest() throws ModbusException {
		LOG.info("Test getMinStringCurrent");
		getStringReport().getReportContents();
		BigDecimal exceptedValue = BigDecimal.valueOf(Double.parseDouble(getStringReport().getStringCurrent()))
				.setScale(1);
		assertEquals(exceptedValue, mb803.getMinStringCurrent().setScale(1));
	}

	@Test
	public void getMinStringVoltageTest() throws ModbusException {
		LOG.info("Test getMinStringVoltage");
		List<StringReport> stringReportList = SystemInfo.getStringReportList(getArrayIndex(), getStackCount());
		assertTrue((stringReportList != null && stringReportList.size() > 0));
		if (stringReportList != null && stringReportList.size() > 0) {
			BigDecimal exceptedValue = BigDecimal.valueOf(SystemInfo.getMinStringVoltage(stringReportList))
					.setScale(0);
			assertEquals(exceptedValue, mb803.getMinStringVoltage().setScale(0));
		}
	}

	@Test
	public void getStringAverageModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getStringAverageModuleTemperature");
		getStringReport().getReportContents();
		int exceptedValue = (int) Double.parseDouble(getStringReport().getAvgCellGroupTemperature());
		int actualValue = mb803.getStringAverageModuleTemperature(0).setScale(1).multiply(BigDecimal.valueOf(10))
				.intValue();
		assertTrue(Math.abs(exceptedValue - actualValue) <= 2);
	}

	@Test
	public void getStringCellVMaxTest() throws ModbusException {
		LOG.info("Test getStringCellVMax");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
		int actualValue = mb803.getStringCellVMax(0).setScale(3).multiply(BigDecimal.valueOf(1000)).setScale(0)
				.intValue();
		assertTrue(Math.abs(exceptedValue - actualValue) <= 2);
	}

	@Test
	public void getStringCellVMinTest() throws ModbusException {
		LOG.info("Test getStringCellVMin");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
		int actualValue = mb803.getStringCellVMin(0).setScale(3).multiply(BigDecimal.valueOf(1000)).intValue();
		assertTrue(Math.abs(exceptedValue - actualValue) <= 2);
	}

	@Test
	public void getStringCurrentTest() throws ModbusException {
		LOG.info("Test getStringCurrent");
		getStringReport().getReportContents();
		BigDecimal exceptedValue = BigDecimal.valueOf(Double.parseDouble(getStringReport().getStringCurrent()))
				.setScale(1);
		assertEquals(exceptedValue, mb803.getStringCurrent(0).setScale(1));
	}

	@Test
	public void getStringMaxModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getStringMaxModuleTemperature");
		getStringReport().getReportContents();
		BigDecimal exceptedValue = BigDecimal
				.valueOf(Double.parseDouble(getStringReport().getMaxCellGroupTemperature())).setScale(1);
		assertEquals(exceptedValue,
				mb803.getStringMaxModuleTemperature(0).setScale(1).multiply(BigDecimal.valueOf(10)));
	}

	@Test
	public void getStringMinModuleTemperatureTest() throws ModbusException {
		LOG.info("Test getStringMinModuleTemperature");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getMinCellGroupTemperature());
		int actualValue = mb803.getStringMinModuleTemperature(0).setScale(1).multiply(BigDecimal.valueOf(10))
				.intValue();
		assertEquals(exceptedValue, actualValue);
	}

	@Test
	public void getASFTest() throws ModbusException {
		LOG.info("Test getASF");
		assertEquals(1, mb803.getASF());
	}

	@Test
	public void getCellVSFTest() throws ModbusException {
		LOG.info("Test getCellVSF");
		assertEquals(-3, mb803.getCellVSF());
	}

//	@Test
//	public void getConnectedStringCountTest() throws ModbusException {
//		LOG.info("Test getConnectedStringCount");
//		getStringReport().getReportContents();
//		LOG.info("ConnectedStringCount=" + getStringReport().getCommunicatingStackCount());
//		int exceptedValue = Integer.parseInt(getStringReport().getCommunicatingStackCount());
//		assertEquals(exceptedValue, mb803.getConnectedStringCount());
//	}

	@Test
	public void getIDTest() throws ModbusException {
		LOG.info("Test getID");
		assertEquals(803, mb803.getID());
	}

	@Test
	public void getMaxModuleTemperatureStringTest() throws ModbusException {
		LOG.info("Test getMaxModuleTemperatureString");
		assertEquals(1, mb803.getMaxModuleTemperatureString());
	}

	@Test
	public void getMaxStringCurrentStringTest() throws ModbusException {
		LOG.info("Test getMaxStringCurrentString");
		assertEquals(1, mb803.getMaxStringCurrentString());
	}

	@Test
	public void getMaxStringVoltageStringTest() throws ModbusException {
		LOG.info("Test getMaxStringVoltageString");
		List<StringReport> stringReportList = SystemInfo.getStringReportList(getArrayIndex(), getStackCount());
		assertTrue((stringReportList != null && stringReportList.size() > 0));
		if (stringReportList != null && stringReportList.size() > 0) {
			int exceptedValue = SystemInfo.getMaxStringVoltageIndex(stringReportList);
			assertEquals(exceptedValue, mb803.getMaxStringVoltageString());
		}
	}

	@Test
	public void getMinModuleTemperatureStringTest() throws ModbusException {
		LOG.info("Test getMinModuleTemperatureString");
		assertEquals(1, mb803.getMinModuleTemperatureString());
	}

	@Test
	public void getMinStringCurrentStringTest() throws ModbusException {
		LOG.info("Test getMinStringCurrentString");
		assertEquals(1, mb803.getMinStringCurrentString());
	}

	@Test
	public void getMinStringVoltageStringTest() throws ModbusException {
		LOG.info("Test getMinStringVoltageString");
		List<StringReport> stringReportList = SystemInfo.getStringReportList(getArrayIndex(), getStackCount());
		assertTrue((stringReportList != null && stringReportList.size() > 0));
		if (stringReportList != null && stringReportList.size() > 0) {
			int exceptedValue = SystemInfo.getMinStringVoltageIndex(stringReportList);
			assertEquals(exceptedValue, mb803.getMinStringVoltageString());
		}
	}

	@Test
	public void getModTmpSFTest() throws ModbusException {
		LOG.info("Test getModTmpSF");
		assertEquals(-1, mb803.getModTmpSF());
	}

	@Test
	public void getPad1Test() throws ModbusException {
		LOG.info("Test getPad1");
		assertEquals(-32768, mb803.getPad1(0));
	}

	@Test
	public void getPad2Test() throws ModbusException {
		LOG.info("Test getPad2");
		assertEquals(-32768, mb803.getPad2(0));
	}

	@Test
	public void getSocSFTest() throws ModbusException {
		LOG.info("Test getSocSF");
		assertEquals(0, mb803.getSocSF());
	}

	@Test
	public void getSohSFTest() throws ModbusException {
		LOG.info("Test getSohSF");
		assertEquals(-32768, mb803.getSohSF());
	}

	// @Test
	// public void getStringCountTest() throws ModbusException {
	// LOG.info("Test getStringCount");
	// getStringReport().getReportContents();
	// int exceptedValue =
	// Integer.parseInt(getStringReport().getCommunicatingStackCount());
	// assertEquals(exceptedValue, mb803.getStringCount());
	// }

	@Test
	public void getVSFTest() throws ModbusException {
		LOG.info("Test getVSF");
		assertEquals(0, mb803.getVSF());
	}

	// public int getMaxModuleTemperatureModule() throws ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getMaxModuleTemperatureModule();
	// }
	//

	// public int getMinModuleTemperatureModule() throws ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getMinModuleTemperatureModule();
	// }

	// public int getBatteryCellBalancingCount() throws ModbusException {
	// return cLithiumIonBatteryBankModelBlockMaster.getBatteryCellBalancingCount();
	// }

	// public int getStringCellVMaxModule(int repeatingBlockIndex) throws
	// ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getMaxCellVoltageModule(repeatingBlockIndex);
	// }

	// public int getStringCellVMinModule(int repeatingBlockIndex) throws
	// ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getMinCellVoltageModule(repeatingBlockIndex);
	// }

	// public int getStringMaxModuleTemperatureModule(int repeatingBlockIndex)
	// throws ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMaxMod(repeatingBlockIndex);
	// }

	// public int getStringMinModuleTemperatureModule(int repeatingBlockIndex)
	// throws ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getStrModTmpMinMod(repeatingBlockIndex);
	// }

	// public ContactorStatus getStringConnectorStatus(int repeatingBlockIndex)
	// throws ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getContactorStatus(repeatingBlockIndex);
	// }
	//
	// public StringEvent1 getStringEvent1(int repeatingBlockIndex) throws
	// ModbusException {
	// return
	// cLithiumIonBatteryBankModelBlockMaster.getStringEvent1(repeatingBlockIndex);
	// }

	// //Set
	// public void setConnectString(int repeatingBlockIndex) throws ModbusException
	// {
	// cLithiumIonBatteryBankModelBlockMaster.setConnectDisconnectStringValue(repeatingBlockIndex,
	// ConnectDisconnectStringEnum.CONNECT_STRING.getRawValue());
	// }
	//
	// public void setDisconnectString(int repeatingBlockIndex) throws
	// ModbusException {
	// cLithiumIonBatteryBankModelBlockMaster.setConnectDisconnectStringValue(repeatingBlockIndex,
	// ConnectDisconnectStringEnum.DISCONNECT_STRING.getRawValue());
	// }

	// 01:05:21.456 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringConnectorStatus(int repeatingBlock)=true

	// 01:05:21.458 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getBatteryCellBalancingCount()=0

	// 01:05:21.461 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getMaxModuleTemperatureModule()=1

	// 01:05:21.464 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getMinModuleTemperatureModule()=3

	// 01:05:21.473 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringCellVMaxModule(int repeatingBlock)=2
	// 01:05:21.474 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringCellVMinModule(int repeatingBlock)=5

	// 01:05:21.476 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringMaxModuleTemperatureModule(int repeatingBlock)=65535
	// 01:05:21.477 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringMinModuleTemperatureModule(int repeatingBlock)=65535
	// 01:05:21.478 [main] INFO com.powin.modbusfiles.Modbus803 -
	// getStringEvent1(int
	// repeatingBlock)=com.powin.modbus.sunspec.blocks.LithiumIonBatteryBankModelBlockCommon$StringEvent1@22a637e7

}
