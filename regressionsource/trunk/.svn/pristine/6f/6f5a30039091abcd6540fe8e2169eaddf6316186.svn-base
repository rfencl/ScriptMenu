package com.powin.modbusfiles.awe;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.power.movePower;
import com.powin.modbusfiles.utilities.PowinProperty;

public class InverterSafetyIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(InverterSafetyIntegrationTest.class.getName());
	private static InverterSafety inverterSafety;
	@Rule public TestName name = new TestName();
	
	@BeforeClass
	public static void init() throws IOException {
		inverterSafety = new InverterSafety(PowinProperty.ARRAY_INDEX.intValue(), PowinProperty.STRING_INDEX.intValue());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		movePower.stopPowerPAsPercent();
	}

	@Before
	public void setUp() throws Exception {
		LOG.info("**** Running {} ****", name.getMethodName());
		LOG.info("Cleanup before test....");
		LOG.info("...stopping power");
		ModbusPowinBlock.getModbusPowinBlock().enableSunspec();
		inverterSafety.restoreDefaultConfigurationAndCloseContactors(120,"");
		movePower.stopPowerPAsPercent();
		LOG.info("...disabling Sunspec....");
		ModbusPowinBlock.getModbusPowinBlock().disableSunspec();
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("**** Finish testing {} ****", name.getMethodName());
		movePower.stopPowerPAsPercent();
	}

	@Test
	public void basicInverterChargeHighVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterDischargeHighVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterOperationHighVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_HIGH_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterChargeLowVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_LOW_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterDischargeLowVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterOperationLowVoltageLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_LOW_VOLTAGE_LIMIT));
	}
	@Test
	public void basicInverterChargeHighTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterDischargeHighTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_HIGH_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterOperationHighTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_HIGH_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterChargeLowTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_LOW_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterDischargeLowTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterOperationLowTemperatureLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_LOW_TEMPERATURE_LIMIT));
	}
	@Test
	public void basicInverterChargeVoltageDeltaLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_VOLTAGE_DELTA_LIMIT));
	}
	@Test
	public void basicInverterDischargeVoltageDeltaLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_VOLTAGE_DELTA_LIMIT));
	}
	@Test
	public void basicInverterOperationVoltageDeltaLimit() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_VOLTAGE_DELTA_LIMIT));
	}
	@Test
	public void basicInverterChargeMinimumStringCount() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_CHARGE_MIN_STRING_COUNT));
	}
	@Test
	public void basicInverterDischargeMinimumStringCount() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_DISCHARGE_MIN_STRING_COUNT));
	}
	@Test
	public void basicInverterOperationMinimumStringCount() throws Exception {
		assertTrue("Test Fails", inverterSafety.basicInverterTest(InverterSafetyLimits.INVERTER_OPERATION_MIN_STRING_COUNT));
	}
    
	@Test
	public void hysteresisTestInverterChargeHighVoltageLimit()  throws Exception {
		assertTrue("Test Fails", inverterSafety.runHysteresisTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT));
	}

	@Test
	public void hysteresisTestInverterChargeLowVoltageLimit()  throws Exception {
		assertTrue("Test Fails", inverterSafety.runHysteresisTest(InverterSafetyLimits.INVERTER_DISCHARGE_LOW_VOLTAGE_LIMIT));
	}
	@Test
	public void inverterDischargingHappyPath()  throws Exception {
		assertTrue("Test Fails", inverterSafety.inverterDischargeHappyPath(5));
	}
	@Test
	public void inverterChargingHappyPath()  throws Exception {
		assertTrue("Test Fails", inverterSafety.inverterChargeHappyPath(5));
	}
    @Test
    public void openContactorsWhileIdle() throws Exception {
    	assertTrue("Test Fails", inverterSafety.testOpenContactorsWhileIdle());
    }
    @Test
    public void closeContactorsAfterOpeningWhileIdle() throws Exception {
    	assertTrue("Test Fails", inverterSafety.testCloseContactorsAfterOpeningWhileIdle());
    }
    @Test
    public void openContactorsWhileDischarging() throws Exception {
    	assertTrue("Test Fails", inverterSafety.testOpenContactorsWhileDischarging());
    }
    @Test
    public void openContactorsWhileCharging() throws Exception {
    	assertTrue("Test Fails", inverterSafety.testOpenContactorsWhileCharging());
    }
    @Test
    public void runRecoveryTimeTest() throws Exception {
    	assertTrue("Test Fails", inverterSafety.runRecoveryTimeTest(InverterSafetyLimits.INVERTER_CHARGE_HIGH_VOLTAGE_LIMIT));
    }

}	


