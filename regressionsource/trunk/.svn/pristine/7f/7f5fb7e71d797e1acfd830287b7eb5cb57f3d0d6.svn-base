package com.powin.modbusfiles.stackoperations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.powin.modbusfiles.stackoperations.Balancing;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class BalancingIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(BalancingIntegrationTest.class.getName());
	private static Balancing balancing;
	private static String arrayIndex;
	private static String stringIndex;
	private static int providedMv;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		LOG.info("init");
		arrayIndex = "1";
		stringIndex = "1";
		providedMv = 3234;
		balancing = new Balancing(arrayIndex, stringIndex);
		LOG.info("init finished.");
	}

//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//	}


	@Test
	public void C931_BalanceSingleStackToAverageTest() {
		assertTrue(balancing.C931_BalanceSingleStackToAverage(arrayIndex, stringIndex));
	}

	@Test
	public void C932_BalanceEntrieArrayToAverageTest() {
		assertTrue(balancing.C932_BalanceEntrieArrayToAverage(arrayIndex));
	}

	@Test
	public void C933_BalanceSingleStackToProvidedTest() {
		assertTrue(balancing.C933_BalanceSingleStackToProvided(arrayIndex, stringIndex, providedMv));
	}

	@Test
	public void C934_BalanceEntrieArrayToProvidedTest() {
		assertTrue(balancing.C934_BalanceEntrieArrayToProvided(arrayIndex, providedMv));
	}

	@Test
	public void C935_StopBalancingSingleStackToAverageTest() {
		assertTrue(balancing.C935_StopBalancingSingleStackToAverage(arrayIndex, stringIndex));
	}

	@Test
	public void C936_StopBalancingEntrieArrayToAverageTest() {
		assertTrue(balancing.C936_StopBalancingEntrieArrayToAverage(arrayIndex));
	}

	@Test
	public void C937_StopBalancingSingleStackToProvidedTest() {
		assertTrue(balancing.C937_StopBalancingSingleStackToProvided(arrayIndex, stringIndex, providedMv));
	}

	@Test
	public void C938_StopBalancingEntrieArrayToProvidedTest() {
		assertTrue(balancing.C938_StopBalancingEntrieArrayToProvided(arrayIndex, providedMv));
	}

	@Test
	public void C939_UpdateBalanceFromAverageToProvidedSingleStackTest() {
		assertTrue(balancing.C939_UpdateBalanceFromAverageToProvidedSingleStack(arrayIndex, stringIndex, providedMv));
	}

	@Test
	public void C940_UpdateBalanceFromProvidedToAverageSingleStackTest() {
		assertTrue(balancing.C940_UpdateBalanceFromProvidedToAverageSingleStack(arrayIndex, stringIndex, providedMv));
	}

	@Test
	public void C941_UpdateBalanceFromAverageToProvidedEntireArrayTest() {
		assertTrue(balancing.C941_UpdateBalanceFromAverageToProvidedEntireArray(arrayIndex, providedMv));
	}

	@Test
	public void C942_UpdateBalanceFromProvidedToAverageEntireArrayTest() {
		assertTrue(balancing.C942_UpdateBalanceFromProvidedToAverageEntireArray(arrayIndex, stringIndex, providedMv));
	}

	@Test
	public void C1046_BalanceToLowestCGTest() {
		assertTrue(balancing.C1046_BalanceToLowestCG(arrayIndex, stringIndex));
	}

	@Test
	public void C1047_BalanceToHighestCGTest() {
		assertTrue(balancing.C1047_BalanceToHighestCG(arrayIndex, stringIndex));
	}
}
