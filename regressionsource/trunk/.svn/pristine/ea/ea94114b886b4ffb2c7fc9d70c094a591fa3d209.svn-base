package com.powin.modbusfiles.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import com.powin.qilin.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.powin.modbusfiles.apps.SunspecPowerAppIntegrationTest;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.utilities.PowinProperty;

class SystemInfoTest {
    // TODO get the device file move it here and parse it 
	private final static Logger LOG = LogManager.getLogger(SystemInfoTest.class.getName());
	@Test
	void testgetNameplateKw() {
		assertEquals(SystemInfo.getNameplateKw(), 150);
	}
	@Ignore
	@Test
	public void testStringReport() {
    	StringReport stringReport = new StringReport();
     	 List<StringReport> stringReportList = SystemInfo.getStringReportList(PowinProperty.ARRAY_INDEX.toString(), PowinProperty.STRING_INDEX.intValue());
     	LOG.info(stringReportList.get(0).getStringData().getMinCellGroupVoltage());
		LOG.info(stringReportList.get(0).getBatteryPackReportList().toString());
	}
	@Ignore
	@Test
	void testGetBatteryPackCount() {
		assertEquals(11, SystemInfo.getBatteryPackCount());
	}
	
	@Test 
	void testGetStackType() {
		assertTrue(SystemInfo.getStackType() instanceof StackType);
	}
//	@Test
//	void testStackNameplateCurrent() {
//		LOG.info (SystemInfo.getStackNameplateChargeAmps());
//	}
	@Test
	void getQilinInfo() throws JsonProcessingException {
		SystemInfo.getQilinInfo();
	}

}
