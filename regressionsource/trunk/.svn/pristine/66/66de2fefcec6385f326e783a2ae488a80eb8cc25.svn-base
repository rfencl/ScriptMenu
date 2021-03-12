package com.powin.modbusfiles.apps;


import static org.junit.Assert.assertSame;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerCommandApp;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.PowinProperty;

@RunWith(Enclosed.class)
public class PowerCommandAppIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(PowerCommandAppIntegrationTest.class.getName());
	public static PowerCommandApp pcApp;
	
	@BeforeClass
	public static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
		LOG.info("init");
		pcApp = new PowerCommandApp();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		pcApp.disablePowerCommandApp();
	}	
		
  public static class NotParameterizedPart {
		@Test
		public void disablePowerCommandAppTest() throws InterruptedException, ModbusException, IOException  {
			pcApp.disablePowerCommandApp();
			Thread.sleep(10000L);
			int appStatus = ModbusPowinBlock.getModbusPowinBlock().getAppStatus("PowerCommand");
			Thread.sleep(1000);
			assertSame(appStatus,0);
		}
		@Test
		public void enablePowerCommandAppTest() throws ModbusException, InterruptedException, IOException  {
			pcApp.enablePowerCommandAppWithZeroPower();
			Thread.sleep(10000L);
			int appStatus = ModbusPowinBlock.getModbusPowinBlock().getAppStatus("PowerCommand");
			Thread.sleep(1000);
			assertSame(appStatus,1);
		}
  }
	
	@Ignore
  @RunWith(Parameterized.class)
	public static class parameterizedtests  {
		private int testValueRealPowerPercent;
		private int testValueReactivePowerPercent;

		public parameterizedtests(	int testValueRealPowerPercent,int testValueReactivePowerPercent) throws IOException {
		      this.testValueRealPowerPercent = testValueRealPowerPercent;
		      this.testValueReactivePowerPercent = testValueReactivePowerPercent;
		   }
		 
		 @Parameterized.Parameters
		   public static Collection<?> powerValuesPercent() {
		      return Arrays.asList(new Object[][] {
		         { 20, 0 },
		         { -20, 0 },
		         { -20, 2},
		         { -20, -2 },
		         { 20, 2 },
		         { 20, -2 }
		      });
		   }
	@Test
		public void setPowerTest() throws ModbusException, InterruptedException, IOException, KeyManagementException, NoSuchAlgorithmException, ParseException  {
			int testValueRealPower=testValueRealPowerPercent*SystemInfo.getNameplateKw()/100;
			int testValueReactivePower=testValueReactivePowerPercent*SystemInfo.getNameplateKw()/100;
			pcApp.setPower(testValueRealPower,testValueReactivePower);
			Thread.sleep(10000L);
			int realPower;
			int reactivePower;
			Modbus103 mModbus103 = new Modbus103(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
			mModbus103.init();
			realPower=mModbus103.getWatts()/1000;			
			reactivePower=mModbus103.getVAr() /1000;	
			LOG.info(this.getClass().getSimpleName() +" running: Realpower: "+testValueRealPower+" and reactive power: "+testValueReactivePower)	;
			LOG.info("real power during discharge:"+realPower);
			LOG.info("reactive power during charge:"+reactivePower);
			assert(Math.abs(testValueRealPower-realPower)<1);
			assert(Math.abs(testValueReactivePower-reactivePower)<1);
		}
	}
}

