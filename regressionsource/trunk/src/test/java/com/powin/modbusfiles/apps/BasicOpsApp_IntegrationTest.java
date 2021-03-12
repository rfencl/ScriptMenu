package com.powin.modbusfiles.apps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.powin.modbusfiles.utilities.PowinProperty;

// @Ignore
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class BasicOpsApp_IntegrationTest {

	private final static Logger LOG = LogManager.getLogger(BasicOpsApp_IntegrationTest.class.getName());
	public static BasicOpsApp basicOps;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		LOG.info("init");
		int arrayIndex = Integer.parseInt(PowinProperty.ARRAY_INDEX.toString());
		int stringIndex = Integer.parseInt(PowinProperty.STRING_INDEX.toString());
		basicOps = new BasicOpsApp(arrayIndex, stringIndex);
		LOG.info("init finished.");
	}

	// @Test
	// public void C531_BulkChargeTo100Test() {
	// assertTrue(basicOps.C531_BulkChargeTo100());
	// }
	// @Test
	// public void C521_BulkDischargeTo0Test() {
	// assertTrue(basicOps.C521_BulkDischargeTo0());
	// }

	@Test
	public void C524_EnableBasicOpsTest() {
		assertTrue(basicOps.C524_EnableBasicOps());
	}

	@Test
	public void C526_PriorityPowerDischargeTest() {
		assertTrue(basicOps.C526_PriorityPowerDischarge());
	}

	@Test
	public void C527_PrioritySocChargeTest() {
		assertTrue(basicOps.C527_PrioritySocCharge());
	}

	@Test
	public void C528_PriorityPowerSocChargeTest() {
		assertTrue(basicOps.C528_PriorityPowerSocCharge());
	}

	@Test
	public void C529_PrioritySocPowerDischargeTest() {
		assertTrue(basicOps.C529_PrioritySocPowerDischarge());
	}

	@Test
	public void C530_DisableBasicOpsTest() {
		assertTrue(basicOps.C530_DisableBasicOps());
	}

	@Test
	public void C537_PriorityPowerChargeTest() {
		assertTrue(basicOps.C537_PriorityPowerCharge());
	}

	@Test
	public void C538_PrioritySocDischargeTest() {
		assertTrue(basicOps.C538_PrioritySocDischarge());
	}

	@Test
	public void C539_PriorityPowerSocDischargeTest() {
		assertTrue(basicOps.C539_PriorityPowerSocDischarge());
	}

	@Test
	public void C540_PrioritySocPowerChargeTest() {
		assertTrue(basicOps.C540_PrioritySocPowerCharge());
	}

	@Test
	public void C677_StackSweepTest() {
		assertTrue(basicOps.C677_StackSweep(65));
	}

	// @Test
	// public void C683_TopOffToTargetTest() {
	// assertTrue(basicOps.C683_TopOffToTarget(50));
	// }

	@Test
	public void C695_PrioritySocDoneTest() {
		assertTrue(basicOps.C695_PrioritySocDone(true));
		assertTrue(basicOps.C695_PrioritySocDone(false));
	}
}
