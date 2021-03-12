package com.powin.modbusfiles.reports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.configuration.StackType;

public class stackTypeIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(stackTypeIntegrationTest.class.getName());
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVoltageLimits() throws InstantiationException, IllegalAccessException {
		StackType st =StackType.STACK_140_GEN2;
		LOG.info("CELL_HIGH_VOLTAGE_ALARM_CLEAR: "+st.getCellVoltageLimits().CELL_HIGH_VOLTAGE_ALARM_CLEAR);
		st =StackType.STACK_225_GEN22;
		LOG.info("CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR: " +st.getCellVoltageLimits().CELL_HIGH_VOLTAGE_DELTA_ALARM_CLEAR);
	}
	@Test
	public void testNotificationContactorBehavior() throws InstantiationException, IllegalAccessException {
		StackType st =StackType.STACK_140_GEN2;
		LOG.info("Contactor behavior for CELL_HIGH_VOLTAGE_ALARM: "+st.getNotificationBehavior().CELL_HIGH_VOLTAGE_ALARM);
		st =StackType.STACK_225_GEN22;
		LOG.info("Contactor behavior for CELL_HIGH_TEMPERATURE_ALARM: "+st.getNotificationBehavior().CELL_HIGH_TEMPERATURE_ALARM);
	}

}
