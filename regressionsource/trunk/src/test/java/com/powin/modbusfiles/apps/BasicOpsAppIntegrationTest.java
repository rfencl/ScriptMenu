package com.powin.modbusfiles.apps;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.utilities.PowinProperty;


public class BasicOpsAppIntegrationTest {

	@RunWith(Parameterized.class)
	public static class BasicOpsAppTest_UnitWatt {
		private int targetPower;
		private String expectedBopStatus;
		private int expectedTargetPowerCommand;

		private LastCalls cLastCalls;

		public ModbusPowinBlock getModbusPowinBlock() {
			return ModbusPowinBlock.getModbusPowinBlock();
		}

		public LastCalls getLastCalls() {
			return cLastCalls;
		}

		public void setLastCalls(LastCalls lastCalls) {
			cLastCalls = lastCalls;
		}

		public BasicOpsAppTest_UnitWatt(int targetP, int expectedPowCmd, String expectedStatus)
				throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
			targetPower = targetP;
			expectedBopStatus = expectedStatus;
			expectedTargetPowerCommand = expectedPowCmd;
			setLastCalls(new LastCalls());
		}

		@Parameterized.Parameters
		public static Collection<?> targetPowerValues() {
			return Arrays.asList(new Object[][] { { 1234, 1000, "1.0 kW" }, { 12345, 12000, "12.0 kW" },
					{ 123456, 123000, "123.0 kW" }, { 1234567, 1234000, "1234.0 kW" } });
		}

		@Test
		public void setBopPriorityPowerTest() throws ModbusException, InterruptedException, KeyManagementException,
				NoSuchAlgorithmException, IOException, ParseException {
			int targetSoc;
			targetSoc = targetPower > 0 ? 5 : 95;
			getModbusPowinBlock().setBasicOpPriorityPower(targetPower, targetSoc);
			Thread.sleep(15000);
			getLastCalls().getLastCallsContent();
			String actualBopStatus = getLastCalls().getBOPStatus();

			int actualTargetPowerCmd = Integer.parseInt(getModbusPowinBlock().getBasicOpTargetPowerCommand());

			assert (actualBopStatus.contains(expectedBopStatus));
			assertEquals(expectedTargetPowerCommand, actualTargetPowerCmd);
		}
	}

	@RunWith(Parameterized.class)
	public static class BasicOpsAppTest_UnitKW {
		private int targetPower;
		private String expectedBopStatus;
		private int expectedTargetPowerCommand;

		private ModbusPowinBlock cModbusPowinBlock;
		private LastCalls cLastCalls;

		public ModbusPowinBlock getModbusPowinBlock() {
			return cModbusPowinBlock;
		}

		public void setModbusPowinBlock(ModbusPowinBlock modbusPowinBlock) {
			cModbusPowinBlock = modbusPowinBlock;
		}

		public LastCalls getLastCalls() {
			return cLastCalls;
		}

		public void setLastCalls(LastCalls lastCalls) {
			cLastCalls = lastCalls;
		}

		public BasicOpsAppTest_UnitKW(int targetP, int expectedPowCmd, String expectedStatus)
				throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
			targetPower = targetP;
			expectedBopStatus = expectedStatus;
			expectedTargetPowerCommand = expectedPowCmd;
			setLastCalls(new LastCalls());
		}

		@Parameterized.Parameters
		public static Collection<?> targetPowerValues() {
			return Arrays.asList(new Object[][] { { -1234, -1000, "-1000.0 kW" }, { -12345, -12000, "-12000.0 kW" },
					{ -123456, -123000, "-123000.0 kW" }, { -1234567, -1234000, "-1234000.0 kW" } });
		}

		@Test
		public void setBopPriorityPowerTest() throws ModbusException, InterruptedException, KeyManagementException,
				NoSuchAlgorithmException, IOException, ParseException {
			int targetSoc;
			targetSoc = targetPower > 0 ? 5 : 95;
			getModbusPowinBlock().setBasicOpPriorityPower(targetPower, targetSoc);
			Thread.sleep(15000);
			getLastCalls().getLastCallsContent();
			String actualBopStatus = getLastCalls().getBOPStatus();

			int actualTargetPowerCmd = Integer.parseInt(getModbusPowinBlock().getBasicOpTargetPowerCommand());

			assert (actualBopStatus.contains(expectedBopStatus));
			assertEquals(expectedTargetPowerCommand, actualTargetPowerCmd);
		}
	}
}
