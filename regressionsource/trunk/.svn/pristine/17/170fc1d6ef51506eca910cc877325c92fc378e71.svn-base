package com.powin.modbusfiles.reports;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.powin.modbusfiles.utilities.FileHelper;

class LastCallsTester extends LastCalls {
    static String lastCallContent = FileHelper.readFileAsString("src/test/resources/lastcallsData.txt");
    public String getLastCallsContent() {
    	return lastCallContent;
    }
}

class LastCallsTest {
	private final static Logger LOG = LogManager.getLogger(LastCallsTest.class.getName());
	
	LastCalls cut = new LastCallsTester();

	@BeforeEach
	void setup(TestInfo testInfo) {
		LOG.info("Test: {}", testInfo.getDisplayName());
	}

	@Test
	void testGetAvailableACChargekW() throws Exception {
		testit(cut.getAvailableACChargekW("1"), "93.271");
	}

	@Test
	void testGetAvailableACDischargekW() throws Exception {
		testit(cut.getAvailableACDischargekW("1"), "85.9584");
	}

	@Test
	void testGetDCBusVoltage() throws Exception {
		testit(cut.getDCBusVoltage(), "814");
	}

	@Test
	void testGetBlockIndex() {
		testit(cut.getBlockIndex(), "1");
	}

	@Test
	void testGetBOPStatus() {
		testit(cut.getBOPStatus(), "Disabled");
	}


	@Test
	void testGetBOPStatusPrioritySoc() {
		testit(cut.getBOPStatusPrioritySoc(), null);
	}

	@Test
	void testGetBsfErrorCodes() {
		testit(cut.getBsfErrorCodes(), null);
	}


	@Test
	void testGetBSFMessage() {
		testit(cut.getBSFMessage(), "ACBattery QASTACKE230E:1:1 - OPS-BLOCKED");
	}

	@Test
	void testGetBsfStatus() {
		testit(cut.getBsfStatus(), "OPS-BLOCKED");
	}

	@Test
	void testGetPCSReactivePower() throws Exception {
		testit(cut.getPCSReactivePower(), "<noMatch>");
	}

	@Test
	void testGetPhaseVoltageAB() throws Exception {
		testit(cut.getPhaseVoltageAB(), "484");
	}

	@Test
	void testGetPhaseVoltageAN() throws Exception {
		testit(cut.getPhaseVoltageAN(), "282");
	}

	@Test
	void testGetPhaseVoltageBC()  throws Exception {
		testit(cut.getPhaseVoltageBC(), "486");
	}

	@Test
	void testGetPhaseVoltageBN()  throws Exception {
		testit(cut.getPhaseVoltageBN(), "282");
	}


	@Test
	void testGetPhaseVoltageCA()  throws Exception {
		testit(cut.getPhaseVoltageCA(), "488");
	}

	@Test
	void testGetPhaseVoltageCN()  throws Exception {
		testit(cut.getPhaseVoltageCN(), "282");
	}


	@Test
	void testGetSoc()  throws Exception {
		testit(cut.getSoc(), "4.0");
	}

	@Test
	void testGetStationCode() {
		testit(cut.getStationCode(), "QASTACKE230E");
	}

	@Test
	void testGetTopoffStatus() throws Exception {
		testit(cut.getTopoffStatus(cut.getBOPStatus()), "<noMatch>");
	}

	@Test
	void testGetAmpHourCapacity() {
		testit(cut.getAmpHourCapacity(), "280");
	}

	@Test
	void testGetMaxAmpCharge() {
		testit(cut.getMaxAmpCharge(), "110");
	}

	@Test
	void testGetMaxAmpDischarge() {
		testit(cut.getMaxAmpDischarge(), "110");
	}

	@Test
	void testGetWattHourCapacity() {
		testit(cut.getWattHourCapacity(), "230000");
	}

	@Test
	void testGetMaxWattCharge() {
		testit(cut.getMaxWattCharge(), "76666");
	}

	@Test
	void testGetMaxWattDischarge() {
		testit(cut.getMaxWattDischarge(), "76666");
	}

	
	private void testit(String cutResults, String expected) {
		LOG.info("[{}]", cutResults);
		if (null == expected) {
			assertNull(cutResults);
		}
		else { 
			assertEquals(expected, cutResults);
		}
	}

}
