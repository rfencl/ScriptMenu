package com.powin.modbusfiles.modbus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.utilities.PowinProperty;


public class Modbus802IntegrationTest {
	private final static Logger LOG = LogManager.getLogger(Modbus802IntegrationTest.class.getName());
	public static Modbus802 mb802;

	private static String cModbusHostName;
	private static final int cModbusPort = 4502;
	private static final int cModubusUnitId = 255;
	private static final boolean cEnableModbusLogging = false;
	private static LastCalls cLastCalls;
	private static Reports cStringReport;
	private static int cExceptedNameplateMaxSOC;
	private static int cExceptedNameplateMinSOC;
	private static int cExceptedNameplateMaxChargeRate;
	private static int cExceptedNameplateMaxDischargeRate;
	private static int cExceptedMaxChargeCurrent;
	private static int cExceptedMaxDishargeCurrent;
	private static int cExceptedAmpHourCapacity;
	private static int cExceptedWattHourCapacity;
	private static int cExceptedCellVSF = 0;
	private static boolean cIsDebug = false;

	static  {
			cModbusHostName = PowinProperty.TURTLEHOST.toString();
	}

	
	@BeforeClass
	public static void init()
			throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, ModbusException {
		LOG.info("init");
		getRemoteTurtleConfig();
		getExceptedNameplateValue();
		mb802 = new Modbus802(getModbusHostName(), getModbusPort(), getModubusUnitId(), isEnableModbusLogging());
		setStringReport(new Reports("1,1"));
		mb802.setCellV_SF(cExceptedCellVSF);
		setLastCalls(new LastCalls());
		LOG.info("init finished.");
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

	@Test
	@Ignore
	public void getAvgCellVoltageTest()
			throws ModbusException, KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
		LOG.info("Test getAvgCellVoltage");
		getStringReport().getReportContents();
//		mb802.setCellV_SF(cExceptedCellVSF);
		int avgCellVoltActual = Integer.parseInt(getStringReport().getAvgCellGroupVoltage());
		assertEquals(avgCellVoltActual, mb802.getAvgCellVoltage());
	}

	@Test
	@Ignore
	public void getMaxCellVoltageTest() throws ModbusException {
		LOG.info("Test getMaxCellVoltage");
		getStringReport().getReportContents();
//		mb802.setCellV_SF(cExceptedCellVSF);
		int maxCellVoltActual = Integer.parseInt(getStringReport().getMaxCellGroupVoltage());
		assertEquals(maxCellVoltActual, mb802.getMaxCellVoltage());
	}
    @Ignore
	@Test
	public void getMinCellVoltageTest() throws ModbusException {
		LOG.info("Test getMinCellVoltage");
		getStringReport().getReportContents();
		mb802.setCellV_SF(cExceptedCellVSF);
		int minCellVoltActual = Integer.parseInt(getStringReport().getMinCellGroupVoltage());
		assertEquals(minCellVoltActual, mb802.getMinCellVoltage());
	}

	@Test
	public void getSOCTest() throws ModbusException {
		LOG.info("Test getSOC");
		getStringReport().getReportContents();
		int exceptedValue = Integer.parseInt(getStringReport().getStringSoc());
		int actualValue = mb802.getSOC();
		assertTrue(actualValue <= (int) (exceptedValue * 1.01 + 0.5));
		assertTrue(actualValue >= (int) (exceptedValue * 0.99 - 0.5));
	}

	@Test
	public void getCellVoltagve_SFTest() throws ModbusException {
		LOG.info("Test getCellV_SF");
		assertEquals(cExceptedCellVSF, mb802.getCellVoltage_SF());
	}

	@Test
	public void getControlModeTest() throws ModbusException {
		LOG.info("Test getControlMode");
		assertEquals("REMOTE", mb802.getControlMode());
	}

	@Test
	public void getNameplateMaxSOCTest() throws ModbusException {
		LOG.info("Test getNameplateMaxSOC");
		assertEquals(cExceptedNameplateMaxSOC, mb802.getNameplateMaxSOC());
	}

	@Test
	public void getNameplateMinSOCTest() throws ModbusException {
		LOG.info("Test getNameplateMinSOC");
		assertEquals(cExceptedNameplateMinSOC, mb802.getNameplateMinSOC());
	}

	@Test
	public void getMaxChargeCurrentTest() throws ModbusException {
		LOG.info("Test getMaxChargeCurrent");

		if (cExceptedMaxChargeCurrent == 0) {
			cExceptedMaxChargeCurrent = Integer.parseInt(getLastCalls().getMaxAmpCharge());
		}
		assertEquals(cExceptedMaxChargeCurrent, mb802.getMaxChargeCurrent());
	}

	@Test
	public void getMaxDischargeCurrentTest() throws ModbusException {
		LOG.info("Test getMaxDischargeCurrent");
		if (cExceptedMaxDishargeCurrent == 0) {
			cExceptedMaxDishargeCurrent = Integer.parseInt(getLastCalls().getMaxAmpDischarge());
		}
		assertEquals(cExceptedMaxDishargeCurrent, mb802.getMaxDischargeCurrent());
	}

	@Test
	public void getNameplateChargeCapacityTest() throws ModbusException {
		LOG.info("Test getNameplateChargeCapacity");
		if (cExceptedAmpHourCapacity==0) {
			cExceptedAmpHourCapacity = Integer.parseInt(getLastCalls().getAmpHourCapacity());
		}
		assertEquals(cExceptedAmpHourCapacity, mb802.getNameplateChargeCapacity());
	}

	@Test
	public void getNameplateEnergyCapacityTest() throws ModbusException {
		LOG.info("Test getNameplateEnergyCapacity");
		if (cExceptedWattHourCapacity == 0) {
			cExceptedWattHourCapacity = Integer.parseInt(getLastCalls().getWattHourCapacity());
		}
		assertEquals(cExceptedWattHourCapacity, mb802.getNameplateEnergyCapacity());
	}

	@Test
	public void getTotalDCCurrentTest() throws ModbusException {
		LOG.info("Test getTotalDCCurrent");
		getStringReport().getReportContents();
		int excepted = (int) Float.parseFloat(getStringReport().getStringCurrent());
		assertEquals(excepted, mb802.getTotalDCCurrent());
	}

	@Test
	public void getTotalPowerTest() throws ModbusException {
		LOG.info("Test getTotalPower");
		getStringReport().getReportContents();
		int excepted = ((int) Float.parseFloat(getStringReport().getStringPower())) * 1000;
		assertEquals(excepted, mb802.getTotalPower());
	}

	@Test
	public void getNameplateMaxChargeRateTest() throws ModbusException {
		LOG.info("Test getNameplateMaxChargeRate");
		if (cExceptedNameplateMaxChargeRate == 0) {
			cExceptedNameplateMaxChargeRate = Integer.parseInt(getLastCalls().getMaxWattCharge());
		}
		assertEquals(cExceptedNameplateMaxChargeRate, mb802.getNameplateMaxChargeRate());
	}

	@Test
	public void getNameplateMaxDischargeRateTest() throws ModbusException {
		LOG.info("Test getNameplateMaxDischargeRate");
		if (cExceptedNameplateMaxDischargeRate == 0) {
			cExceptedNameplateMaxDischargeRate = Integer.parseInt(getLastCalls().getMaxWattDischarge());
		}
		assertEquals(cExceptedNameplateMaxDischargeRate, mb802.getNameplateMaxDischargeRate());
	}

	@Test
	public void getChargeDischargeRateMax_SFTest() throws ModbusException {
		LOG.info("Test getChargeDischargeRateMax_SF");
		assertEquals(3, mb802.getChargeDischargeRateMax_SF());
	}

	@Test
	public void getBatteryTypeValueTest() throws ModbusException {
		LOG.info("Test getBatteryTypeValue");
		assertEquals(4, mb802.getBatteryTypeValue());
	}
	
	@Test
	public void getSOC_SFTest() throws ModbusException {
		LOG.info("Test getSOC_SF");
		assertEquals(0, mb802.getSOC_SF());
	}
	
	@Test
	public void getDCBusVoltage_SFTest() throws ModbusException {
		LOG.info("Test getDCBusVoltage_SF");
		assertEquals(0, mb802.getDCBusVoltage_SF());
	}
	
	
	

	public static void getRemoteTurtleConfig() throws IOException {
		JSch jsch = new JSch();
		Session session = null;
		try {
			if (cIsDebug) {
				session = jsch.getSession("root", "192.168.3.18", 22);
				session.setPassword("admin");
			} else {
				session = jsch.getSession("powin", PowinProperty.TURTLEHOST.toString(), 22);
				session.setPassword("powin");
			}

			session.setConfig("StrictHostKeyChecking", "no");

			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;

			InputStream stream = sftpChannel.get("/etc/powin/configuration.json");
			BufferedWriter bw;
			if (cIsDebug) {
				bw = new BufferedWriter(new FileWriter("/home/jim/turtleConfig.json"));
			} else {
				bw = new BufferedWriter(new FileWriter("/home/powin/turtleConfig.json"));
			}

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = br.readLine()) != null) {
					// System.out.println(line);
					bw.write(line + "\r\n");
				}
			} catch (IOException io) {
				System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
				io.getMessage();

			} catch (Exception e) {
				System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
				e.getMessage();
			}

			sftpChannel.exit();
			session.disconnect();
			bw.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

	public static void getExceptedNameplateValue() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser parser = new JsonParser();
		JsonObject object;
		if (cIsDebug) {
			object = (JsonObject) parser.parse(new FileReader("/home/jim/turtleConfig.json"));
		}
		else {
			object = (JsonObject) parser.parse(new FileReader("/home/powin/turtleConfig.json"));
		}
		cExceptedNameplateMaxSOC = 0;
		cExceptedNameplateMinSOC = 0;
		try {
			JsonElement jsonElementBlockConf = object.get("blockConfiguration");
			cExceptedNameplateMaxSOC = ((JsonObject) jsonElementBlockConf).get("socoperatingRangeMax").getAsInt();
			cExceptedNameplateMinSOC = ((JsonObject) jsonElementBlockConf).get("socoperatingRangeMin").getAsInt();

			JsonArray arrayConfig = ((JsonObject) jsonElementBlockConf).get("arrayConfigurations").getAsJsonArray();
			if (arrayConfig != null && arrayConfig.size() > 0) {
				JsonElement jsonElementArray = arrayConfig.get(0);
				JsonArray stringConfig = ((JsonObject) jsonElementArray).get("stringConfigurations").getAsJsonArray();
				JsonElement dcTopology = ((JsonObject) jsonElementArray).get("dcTopology");
				JsonArray childNodes = ((JsonObject) dcTopology).get("childNodes").getAsJsonArray();

				if (stringConfig != null && stringConfig.size() > 0) {
					JsonElement jsonElementStringConfig = stringConfig.get(0);
					cExceptedMaxChargeCurrent = ((JsonObject) jsonElementStringConfig).get("maxAmpCharge").getAsInt();
					cExceptedMaxDishargeCurrent = ((JsonObject) jsonElementStringConfig).get("maxAmpDischarge")
							.getAsInt();
					cExceptedAmpHourCapacity = ((JsonObject) jsonElementStringConfig).get("ampHourCapacity").getAsInt();
					cExceptedWattHourCapacity = ((JsonObject) jsonElementStringConfig).get("wattHourCapacity")
							.getAsInt();
					cExceptedNameplateMaxChargeRate = ((JsonObject) jsonElementStringConfig).get("maxWattCharge")
							.getAsInt();
					cExceptedNameplateMaxDischargeRate = ((JsonObject) jsonElementStringConfig).get("maxWattDischarge")
							.getAsInt();
				}
			}

		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e.getMessage());
		}
	}

	//
	// public int getDepthOfDischarge() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getDepthofDischarge().intValue();
	// }
	//
	// public int getChargeStatus() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getChargeStatusValue();
	// }
	// public int getBatteryHeartbeat() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getBatteryHeartbeat();
	// }
	//
	// public int getControllerHeartbeat() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getControllerHeartbeat();
	// }
	// public int getStateOfBatteryBank() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getStateoftheBatteryBankValue();
	// }
	//
	// public long getBatteryEvent1Bitfield() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getBatteryEvent1BitfieldValue();
	// }
	//
	// public long getBatteryEvent2Bitfield() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getBatteryEvent2BitfieldValue();
	// }
	//
	// public long getVendorEventBitfield1() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getVendorEventBitfield1Value();
	// }
	//
	// public long getVendorEventBitfield2() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getVendorEventBitfield2Value();
	// }
	//
	// public int getExternalBatteryVoltage() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getExternalBatteryVoltage().intValue();
	// }

	// public int getMaximumBatteryVoltage() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getMaxBatteryVoltage().intValue();
	// }
	//
	// public int getBatteryState() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getStateoftheBatteryBankValue();
	// }
	//
	// // Scaling factors
	// public int getChargeCapacitySF() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getAHRtg_SF();
	// }
	//
	// public int getEnergySF() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getWHRtg_SF();
	// }


	// // Scale factor for DC current.
	// public int getDcCurrent_SF() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getA_SF();
	// }
	//
	// // Scale factor for instantaneous DC charge/discharge current.
	// public int getInstantaneousDcCurrent_SF() throws ModbusException {
	// return cBatteryBaseModelBlockMaster.getAMax_SF();
	// }
	//
	// // Setters
	// public void setBatteryContactorsOff() throws ModbusException {
	// cBatteryBaseModelBlockMaster.setSetOperationValue(2);
	// }
	//
	// public void setBatteryContactorsOn() throws ModbusException {
	// cBatteryBaseModelBlockMaster.setSetOperationValue(1);
	// }
	//
	// public void resetAlarm() throws ModbusException {
	// cBatteryBaseModelBlockMaster.setAlarmReset(1);
	// }
	//
	// public void setOperation(SetOperationEnum value) throws ModbusException {
	// cBatteryBaseModelBlockMaster.setSetOperation(value);
	// }
	//
	// public void setOperationValue(int value) throws ModbusException {
	// cBatteryBaseModelBlockMaster.setSetOperationValue(value);
	// }
	//
	// public void setInverterState(SetInverterStateEnum value) throws
	// ModbusException {
	// cBatteryBaseModelBlockMaster.setSetInverterState(value);
	// }
	//
	// public void setInverterStateValue(int value) throws ModbusException {
	// cBatteryBaseModelBlockMaster.setSetInverterStateValue(value);
	// }

	// getBatteryEvent1Bitfield()=1

	// getBatteryEvent2Bitfield()=0

	// getBatteryHeartbeat()=35086

	// getBatteryState()=1

	// getChargeCapacitySF()=1

	// getChargeStatus()=0

	// getControllerHeartbeat()=0


	// getDcCurrent_SF()=1

	// getDepthOfDischarge()=0

	// getEnergySF()=3

	// getExternalBatteryVoltage()=0

	// getInstantaneousDcCurrent_SF()=1

	// getMaximumBatteryVoltage()=65535

	// getStateOfBatteryBank()=1

	// getVendorEventBitfield1()=0

	// getVendorEventBitfield2()=0

}
