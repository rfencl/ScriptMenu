package com.powin.modbusfiles.awe;

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
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.utilities.CommonHelper;



@RunWith(Enclosed.class)
public class ZeroConfigFromStringIntegrationTest {
	private final static Logger LOG = LogManager.getLogger(ZeroConfigFromStringIntegrationTest.class.getName());
	public static ZeroConfigFromString cZeroConfigTesting;
	
	@Rule public TestName name = new TestName();
	@BeforeClass
	public static void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException, JSchException {
		LOG.info("init");
		cZeroConfigTesting = new ZeroConfigFromString();
		CommonHelper.setupSystem("", StackType.STACK_225_GEN22, 1, 1, 450, 600);
		LOG.info("init finished.");
	}

	public static class noneParameterizedPart {
	
	@Test
	public void testNotification1001()
	 throws KeyManagementException, NoSuchAlgorithmException, IOException,
	 ParseException {
	 assert (cZeroConfigTesting.testNotifications(1001, 3890));
	 }
}
	@Before
	
	public void setUp() throws Exception {
		LOG.info("**** Running {} ****", name.getMethodName());
	}

	@RunWith(Parameterized.class)
	public static class parameterizedTests {
		private int notificationId;
		private int triggerMsg;

		public parameterizedTests(int notificationId, int triggerMsg) throws IOException {
			this.notificationId = notificationId;
			this.triggerMsg = triggerMsg;
		}

		@Parameterized.Parameters
		public static Collection notificationsValues() {
			//TO DO: make these values more descriptive
			//2025 and 2561 fail currently - Mahesh 2/21/21
			return Arrays.asList(new Object[][] { { 1001, 3666 }, { 1002, 3777 }, { 1003, 3888 }, { 1004, 3999 },
					{ 1005, 3666 }, { 1006, 3777 }, { 1007, 3888 }, { 1008, 3999 }, { 1009, 3666 }, { 1010, 3777 },
					{ 1011, 3888 }, { 1012, 3999 }, { 1013, 3666 }, { 1014, 3777 }, { 1015, 3888 }, { 1016, 3999 },
					{ 1017, 3666 }, { 1018, 3777 }, { 1019, 3888 }, { 1020, 3999 }, { 1021, 3666 }, { 1022, 3777 },
					{ 1023, 3888 }, { 1024, 3999 }, { 1025, 3666 }, { 1026, 3777 }, { 1027, 3888 }, { 1028, 3999 },
					{ 1029, 3888 }, { 1030, 3999 }, { 1046, 3666 }, { 1047, 3777 }, { 1048, 3888 }, { 1049, 3999 },
					{ 1050, 3666 }, { 1051, 3777 }, { 1052, 3888 }, { 1053, 3999 }, { 1054, 3666 }, { 1055, 3777 },
					{ 1056, 3888 }, { 1057, 3999 }, { 2001, 3666 }, { 2002, 3777 }, { 2003, 3888 }, { 2004, 3999 },
					{ 2005, 3666 }, { 2006, 3777 }, { 2007, 3888 }, { 2008, 3999 }, { 2009, 3666 }, { 2010, 3777 },
					{ 2011, 3888 }, { 2012, 3999 }, { 2013, 3666 }, { 2014, 3777 }, { 2015, 3888 }, { 2016, 3999 },
					{ 2017, 3666 }, { 2018, 3777 }, { 2019, 3888 }, { 2020, 3999 }, { 2021, 3666 }, { 2022, 3777 },
					{ 2023, 3888 }, { 2024, 3999 }, { 2025, 3666 }, { 2026, 3777 }, { 2027, 3888 }, { 2028, 3999 },
					{ 2029, 3888 }, { 2030, 3999 }, { 2046, 3666 }, { 2047, 3777 }, { 2048, 3888 }, { 2049, 3999 },
					{ 2050, 3666 }, { 2051, 3777 }, { 2052, 3888 }, { 2053, 3999 }, { 2054, 3666 }, { 2055, 3777 },
					{ 2056, 3888 }, { 2057, 3999 }, { 2064, 3666 }, { 2534, 3777 }, { 2535, 3888 }, { 2561, 3999 },
					{ 2562, 3999 }, { 2563, 3999 } });
		}

		@Test
		public void notificationsTest()
				throws KeyManagementException, NoSuchAlgorithmException, IOException, ParseException {
			assert (cZeroConfigTesting.testNotifications(notificationId, triggerMsg) == true);
		}
	}
}
