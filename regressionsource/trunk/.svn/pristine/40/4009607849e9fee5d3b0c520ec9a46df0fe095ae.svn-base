package com.powin.modbusfiles.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * Tests for PowinProperty
 * @author Rick Fencl
 *
 */
class PropertyTest {
	private final static Logger LOG = LogManager.getLogger(PropertyTest.class.getName());

	/**
	 * Get all the properties as an array. Here we just verify that the 2nd entry is expected.
	 */
	@Test
	void testGetValues() {
		final PowinProperty[] values = PowinProperty.values();
		assertEquals("https://localhost:8443/", values[2].toString());
		//Arrays.asList(values).stream().forEach(LOG::info); 
	}
    /**
     * Verify we can get the names of the enum. 	
     */
	@Test
	void testGetNames() {
		List<String> cst = PowinProperty.getNames();
		cst.stream().forEach(LOG::info);
		assertEquals(48, cst.size());
	}
	
	/**
	 * Show that we can get the value stored in the property.
	 */
	@Test
	void testGetProperty() {
		LOG.info("{}: {}", PowinProperty.CELL_GROUP_COUNT.name(), PowinProperty.CELL_GROUP_COUNT);
		assertEquals(16, Integer.parseInt(PowinProperty.CELL_GROUP_COUNT.toString()));
	}
	
	/**
	 * Show usage for appending two enums 
	 */
	@Test
	void testAppendingEnumValue() {
		String  cmd = "wget  --no-check-certificate --user="+PowinProperty.ARCHIVA_USER+" --password "+PowinProperty.ARCHIVA_PASSWORD;
		assertEquals("wget  --no-check-certificate --user=rickf@powin.com --password ", cmd);
	}
    
	/**
	 * Demonstrate getting the property given the key. Used when building 
	 * the key programmatically
	 */
	@Test
	void testFromPropertyFileKey() {
		String key = "bc_cell_group_report_minute_csv_file_path";
		assertEquals("/home/powin/bc_cg_m.csv", PowinProperty.fromPropertyFileKey(key));
		key = "rte_targetDischargeVoltage";
		assertEquals("830", PowinProperty.fromPropertyFileKey(key));
	}
	
	/**
	 * Change the value on the fly, reload the properties and show that 
	 * the value is restored.
	 */
	@Test
	void testSetValue() {
		String key = "rte_targetDischargeVoltage";
		assertEquals("830", PowinProperty.fromPropertyFileKey(key));  // original value
		PowinProperty.setValue(key, "850");
		assertEquals("850", PowinProperty.fromPropertyFileKey(key));  // modified value
        PowinProperty.loadProperties();
		assertEquals("830", PowinProperty.fromPropertyFileKey(key));  // back to original
	}
}
