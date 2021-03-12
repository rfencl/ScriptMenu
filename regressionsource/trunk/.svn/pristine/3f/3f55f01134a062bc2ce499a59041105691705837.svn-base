package com.powin.modbusfiles.awe;


import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.jcraft.jsch.JSchException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.power.movePower;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class BatteryPackTemperatureNotificationsIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(BatteryPackTemperatureNotificationsIntegrationTest.class.getName());
	private static final int ARRAY_INDEX = PowinProperty.ARRAY_INDEX.intValue();
	private static final int STRING_INDEX = PowinProperty.STRING_INDEX.intValue();
	private static BatteryPackTemperatureNotifications batteryPackTemperatureNotifications;
	
	@Rule public TestName name = new TestName();
	@BeforeClass
	public static void init() throws IOException, InstantiationException, IllegalAccessException {
		batteryPackTemperatureNotifications = new BatteryPackTemperatureNotifications(ARRAY_INDEX, STRING_INDEX);
		batteryPackTemperatureNotifications.resetDefaultsBatteryPackTemperatureNotificationConfiguration();
	}
	
	@Before
	public void setUp() throws Exception {
		LOG.info("**** Running {} ****", name.getMethodName());
	}
	
	@After
	public void tearDown() throws Exception {
		LOG.info("Stopping power after test...");
		movePower.setPAsPercent(BigDecimal.ZERO);
		Thread.sleep(10000);
		LOG.info("Test Complete");
	}
	
	@Test
	public void testHighTemperatureAlarm() throws Exception {
		assertTrue("This test failed", batteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_HIGH_TEMPERATURE_ALARM));
	}
     
	@Test
	public void testLowTemperatureAlarm() throws Exception {
		assertTrue("This test failed", batteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_LOW_TEMPERATURE_ALARM));
	}

	
	@Test
	public void testHighTemperatureDeltaAlarm() throws Exception {
		boolean willContactorsOpenWhenAlarmSet = SystemInfo.getStackType() == StackType.STACK_140_GEN2;
		assertTrue("This test failed", batteryPackTemperatureNotifications.runTest(NotificationCodes.CELL_HIGH_TEMPERATURE_DELTA_ALARM));
	}

}
