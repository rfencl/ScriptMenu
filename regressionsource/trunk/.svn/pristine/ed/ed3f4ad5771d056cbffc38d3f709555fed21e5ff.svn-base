package com.powin.modbusfiles.utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppUpdateIntegrationTest {
    AppUpdate cut = new AppUpdate();
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

	/**
	 * Pull version 2.21 latest and make sure that the versions have the correct format.
	 * @throws Exception
	 */
    @Test
	public void testGetCloudAppVersion() throws Exception {
		cut.getCloudAppVersions("2", "21");
		String matchVersion = "\\d\\.\\d\\d\\.\\d\\d?";
		assertThat(AppUpdate.getCoblynauVersion().matches(matchVersion), is(true));
		assertThat(AppUpdate.getTurtleVersion().matches(matchVersion), is(true));
		assertThat(AppUpdate.getKnockerVersion().matches(matchVersion), is(true));
		assertThat(AppUpdate.getKoboldVersion().matches(matchVersion), is(true));
		assertThat(AppUpdate.getPrimroseVersion().matches(matchVersion), is(true));
	}
	
	


}
