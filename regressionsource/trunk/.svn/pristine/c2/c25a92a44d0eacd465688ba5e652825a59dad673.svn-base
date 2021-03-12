package com.powin.modbusfiles.awe;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.power.movePower;
import com.powin.modbusfiles.reports.Reports;
import com.powin.modbusfiles.reports.StringReport;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class BatteryPackVoltageNotificationsIntegrationTest {
	private static final int ARRAY_INDEX = PowinProperty.ARRAY_INDEX.intValue();
	private static final int STRING_INDEX = PowinProperty.STRING_INDEX.intValue();
	private static final StackType stackType = SystemInfo.getStackType();
	private final static Logger LOG = LogManager.getLogger(BatteryPackVoltageNotificationsIntegrationTest.class.getName());
	static BatteryPackVoltageNotifications batteryPackVoltageNotifications;
	
	@Rule public TestName name = new TestName();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		batteryPackVoltageNotifications = new BatteryPackVoltageNotifications(ARRAY_INDEX, STRING_INDEX);
    }

	@Before
	public void setUp() throws Exception {
		LOG.info("**** Running {} ****", name.getMethodName());
		BatteryPackVoltageNotificationCommands.resetDefaultsBatteryPackVoltageNotificationConfiguration();
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		CommonHelper.sleep(5000);
		movePower.stopPowerPAsPercent();
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("Stopping power after test...");
		movePower.setPAsPercent(BigDecimal.ZERO);
		Thread.sleep(10000);
		LOG.info("Test Complete");
	}

	@Test
	public void testHighVoltageAlarm() throws Exception {
		assertTrue("This test failed", batteryPackVoltageNotifications.runTest(NotificationCodes.CELL_HIGH_VOLTAGE_ALARM));
	}
	@Test
	public void testLowVoltageAlarm() throws Exception {
		assertTrue("Test failed", batteryPackVoltageNotifications.runTest(NotificationCodes.CELL_LOW_VOLTAGE_ALARM));
	}
	@Test
	public void testHighVoltageDeltaAlarm() throws Exception {
		assertTrue("This test failed", batteryPackVoltageNotifications.runTest(NotificationCodes.CELL_HIGH_VOLTAGE_DELTA_ALARM));
    }
}
