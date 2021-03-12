package com.powin.modbusfiles.awe;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.jcraft.jsch.JSchException;
import com.powin.modbusfiles.awe.ZeroConfigFromString;
import com.powin.modbusfiles.awe.ZeroConfigTurtleToString;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.utilities.CommandHelper;
import com.powin.modbusfiles.utilities.CommonHelper;

	@RunWith(Enclosed.class)
	public class ZeroConfigTurtleToStringIntegrationTest {
		private final static Logger LOG = LogManager.getLogger(ZeroConfigTurtleToStringIntegrationTest.class.getName());
		public static ZeroConfigTurtleToString cZeroConfigTurtleToString;
		@Rule public TestName name = new TestName();
		
		@BeforeClass
		public static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException {
			LOG.info("init");
		
	}
	@Before
	public void setUp() throws Exception {
		LOG.info("**** Running {} ****", name.getMethodName());
	}
	
	@RunWith(Parameterized.class)
	public static class parameterizedTests {
		private StackType stackType;
		private int arrayCount;
		private int stringCount;

		public parameterizedTests(StackType stackType, int arrayCount,int stringCount) throws IOException {
			this.stackType = stackType;
			this.arrayCount = arrayCount;
			this.stringCount = stringCount;
		}

		@Parameterized.Parameters
		public static Collection<?> zeroConfigParams() {
		      return Arrays.asList(new Object[][] {
		         { StackType.STACK_140_GEN2, 1,1 }
		         ,{ StackType.STACK_140_GEN2, 4,3 }
		         ,{ StackType.STACK_225_GEN22, 1,1}
		         ,{ StackType.STACK_225_GEN22, 3,4}
		      });
		   }

		@Test
		public void validateConfigFilesSentToStringFromTurte()
				throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException, InterruptedException, JSchException {
			cZeroConfigTurtleToString = new ZeroConfigTurtleToString(stackType,"",arrayCount,stringCount);
			CommonHelper.createConfigurationJson("QAWARN123", stackType,stringCount, arrayCount);
			CommonHelper.restartTomcat();
			assertTrue(cZeroConfigTurtleToString.workFlow());
		}
	}
}
