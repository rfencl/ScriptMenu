package com.powin.modbusfiles.derate;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.derating.CellVoltageDerate;
import com.powin.modbusfiles.derating.WarnActions;
import com.powin.modbusfiles.utilities.CommonHelper;

public class CellVoltageDerateIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(WarnActionsIntegrationTest.class.getName());
	private static int cArrayCount=1;
	private static int cStringCount=1;
	public static CellVoltageDerate voltageDerate=new CellVoltageDerate();
	
	private static CommonHelper mCommonHelper = new CommonHelper();

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
	public void triggerOnCharge() {
		assertTrue(true);
	}

}
