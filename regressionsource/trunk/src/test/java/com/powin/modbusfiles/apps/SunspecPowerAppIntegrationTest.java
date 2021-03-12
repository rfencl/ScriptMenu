package com.powin.modbusfiles.apps;


import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.SunspecPowerApp;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus120;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.PowinProperty;


@RunWith(Enclosed.class)
public class SunspecPowerAppIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(SunspecPowerAppIntegrationTest.class.getName());
	public static SunspecPowerApp spApp;
	private static int maxPower;

	@BeforeClass
	public  static void init() throws Exception {
		LOG.info("init");
		LOG.info("SunspecPowerAppTest running..."	);
		spApp = new SunspecPowerApp();
//		Modbus120 cNameplateBlockMaster = new Modbus120(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
//		cNameplateBlockMaster.init();
		maxPower=SystemInfo.getNameplateKw();//cNameplateBlockMaster.getWRtg().intValue();
		LOG.info("maxPower : "+maxPower);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		getModbusPowinBlock().disableSunspec();
	}
	
  public static class NotParameterizedPart {
	  
	    @Test
		public void disableSunspecPowerCommandAppTest() throws Exception  {
	    	init();
			getModbusPowinBlock().disableSunspec();
			Thread.sleep(10000L);
			int appStatus = ModbusPowinBlock.getModbusPowinBlock().getAppStatus("SunspecPowerCommand");
			assertSame(appStatus,0);
		}
		
		@Test
		public void enableSunspecPowerCommandAppTest() throws ModbusException, InterruptedException, IOException  {
			getModbusPowinBlock().enableSunspec();
			Thread.sleep(10000L);
			int appStatus = ModbusPowinBlock.getModbusPowinBlock().getAppStatus("SunspecPowerCommand");
			assertSame(appStatus,1);
		}
  }
	
  @RunWith(Parameterized.class)
	public static class parameterizedtests  {
		private double testValuePercentageMaxPower;
	
		public parameterizedtests(	double testValuePercentageMaxPower) throws IOException {
		      this.testValuePercentageMaxPower = testValuePercentageMaxPower;
		   }
		 
		 @Parameterized.Parameters
		   public static Collection<?> percentageMaxPowerValues() {
			 return Arrays.asList(new Object[][] {
		         { 0}
		         ,{ 100}
		         ,{ 50}
		         ,{ -25}
		         ,{ -74.5 }
		      });
		   }
	@Test
		public void setPowerTest() throws ModbusException, InterruptedException, IOException, KeyManagementException, NoSuchAlgorithmException, ParseException  {
			spApp.setPower(testValuePercentageMaxPower);
			Thread.sleep(10000L);
			//actual power
			int actualPower=SystemInfo.getActualAcPower();
			//expected power
			int expectedPower = (int) testValuePercentageMaxPower*maxPower/100;
			int maxPermissiblePower = SystemInfo.getMaxAllowableChargePower(1); //maxAllowedStackCurrent*currentStackVoltage/1000;
			LOG.info("Actual power (kW) : "+actualPower	);
			int normalizedExpectedPower = expectedPower<maxPermissiblePower?expectedPower:maxPermissiblePower;
			LOG.info("Expected power (kW): :"+normalizedExpectedPower);
			assertTrue(CommonHelper.compareIntegers(normalizedExpectedPower, actualPower, 10));
		}
	}
}

