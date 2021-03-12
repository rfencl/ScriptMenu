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
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.derating.WarnActions;
import com.powin.modbusfiles.utilities.CommonHelper;

@RunWith(Enclosed.class)
public class WarnActionsIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(WarnActionsIntegrationTest.class.getName());
	public static WarnActions warnAction=new WarnActions();
	private static int cArrayCount=4;
	private static int cStringCount=4;
 
	@BeforeClass
	public  static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, ModbusException, InterruptedException, JSchException {
		LOG.info("init - creating system with {} arrays and {} strings",cArrayCount,cStringCount);
		//Creating configuration for cArrayCount, cStringCount
		CommonHelper.setupSystem("", StackType.STACK_140_GEN2, cArrayCount, cStringCount, warnAction.getSystemMaxChargePower(), warnAction.getSystemMaxChargeCurrent());
		//mCommonHelper.setupSystem("", StackType.S20_140_200_1000_E40_derate01, cArrayCount, cStringCount, 450, 800);
		
	}

  @RunWith(Parameterized.class)
	public static class parameterizedtests  {		
	  private String commandSet;
	  public parameterizedtests(String commandSet) throws IOException { 
	      this.commandSet=commandSet;
	   } 
	 //TODO Generate test data with a function
	 @Parameterized.Parameters
	   public static Collection stackValues() {
		 return Arrays.asList(new Object[][] {
			 { "2001,3777,1,1"}
//	         ,{ "2001,3777,1,1|2001,3777,1,2"}
//	         ,{ "2001,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
//	         ,{ "2001,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
//	    	 ,{ "2004,3777,1,1"}
//	         ,{ "2004,3777,1,1|2001,3777,1,2"}
//	         ,{ "2004,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
//	         ,{ "2004,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
//	    	 ,{ "2010,3777,1,1"}
//	         ,{ "2010,3777,1,1|2001,3777,1,2"}
//	         ,{ "2010,3777,1,1|2001,3777,1,2|2001,3777,1,3"}
//	         ,{ "2010,3777,1,1|2001,3777,1,2|2001,3777,1,3|2001,3777,1,4"}
//	         ,{ "2004,3777,1,1|2010,3777,1,2|2004,3777,1,3|2001,3777,1,2|2004,3777,2,2|2010,3777,2,2|2004,3777,2,3|2001,3777,2,2"}
//			 ,{ "2004,3777,2,2|2010,3777,2,2|2004,3777,2,3|2001,3777,2,2"}
//	    	 ,{ "2014,3777,1,1"} 
//	    	 ,{ "2019,3777,1,1"}
//	    	 ,{ "2020,3777,1,1"}
	      });
	   }
//	 @Test
//	public void validateWarnActionWarnStopEnabledTest() throws ModbusException, InterruptedException, IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, JSchException  {
//		LOG.info("Warn Action test with warn stop enabled running..."	);	
//		warnAction = new WarnActions("STOP",cArrayCount,cStringCount);
//		warnAction.setNotification(commandSet);
//		Thread.sleep(6000);	
//		boolean status=true;
//		for (int arrayIndex=1;arrayIndex <= cArrayCount;arrayIndex++) {
//			status &= warnAction.validateWarnAction(commandSet,arrayIndex);
//		}
//		assert(status);
//		warnAction.clearNotificationX();
//	}
  	@Test
	public void validateWarnActionWarnStopDisabledTest() throws ModbusException, InterruptedException, IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, JSchException  {
		LOG.info("Warn Action test with warn stop disabled running..."	);	
		warnAction = new WarnActions("DERATE",cArrayCount,cStringCount);
		warnAction.setNotification(commandSet);
		Thread.sleep(6000);	
		boolean status=true;
		for (int arrayIndex=1;arrayIndex <= cArrayCount;arrayIndex++) {
			warnAction.validateWarnAction( commandSet,arrayIndex) ;
			status &= warnAction.validateWarnAction(commandSet,arrayIndex);
		}
		assert(status);
		warnAction.clearNotification(commandSet);
	}
  }
}

