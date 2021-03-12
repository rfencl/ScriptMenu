package com.powin.modbusfiles.derate;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.jcraft.jsch.JSchException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.derating.WarnActions;


@RunWith(Enclosed.class)
public class WarnStopIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(WarnStopIntegrationTest.class.getName());
	public static WarnActions warnStop;
	private static int cArrayCount=4;
	private static int cStringCount=4;

	@BeforeClass
	public  static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException, InterruptedException, JSchException {
		LOG.info("init");
		LOG.info("WarnDerateTest running..."	);
		//For this test to work you need a 4 array 4 stack set up
		//To DO: Enable any array/stack combination
		warnStop = new WarnActions("STOP",cArrayCount,cStringCount);
	}

  @RunWith(Parameterized.class)
	public static class parameterizedtests  {		
	  private String commandSet;
	  public parameterizedtests(	 String commandSet) throws IOException { 
	      this.commandSet=commandSet;
	   } 
	 @Parameterized.Parameters
	   public static Collection stackValues() {
		 return Arrays.asList(new Object[][] {
			  { "2001,3777,1,1"}
	         ,{ "2001,3777,1,1|2001,3777,1,2"}
	         ,{ "2001,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
	         ,{ "2001,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
	    	 ,{ "2004,3777,1,1"}
	         ,{ "2004,3777,1,1|2001,3777,1,2"}
	         ,{ "2004,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
	         ,{ "2004,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
	    	 ,{ "2010,3777,1,1"}
	         ,{ "2010,3777,1,1|2001,3777,1,2"}
	         ,{ "2010,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
	         ,{ "2010,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
	         ,{ "2004,3777,1,1|2010,3777,1,2|2004,3777,1,3|2001,3777,1,2|2004,3777,2,2|2010,3777,2,2|2004,3777,2,3|2001,3777,2,2"}
			 ,{ "2004,3777,2,2|2010,3777,2,2|2004,3777,2,3|2001,3777,2,2"}
	    	 ,{ "2014,3777,1,1"} //Defect https://powinenergy.atlassian.net/browse/SP-2189 
	    	 ,{ "2019,3777,1,1"}
	    	 ,{ "2020,3777,1,1"}
	      });
	   }
	@Test
		public void validateWarnDerateTest() throws ModbusException, InterruptedException, IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, JSchException  {
			
			boolean status=true;
			//warnStop.clearNotificationX();
			warnStop.setNotification(commandSet);
			Thread.sleep(6000);	
			for (int arrayIndex=1;arrayIndex <= cArrayCount;arrayIndex++) {
				LOG.info("Validating for array: "+arrayIndex);
				status &= warnStop.validateWarnAction(commandSet,arrayIndex);
			}	
			assert(status);
			warnStop.clearNotificationX();
//			warnStop.clearNotification(commandSet);			
		}
	}
}

