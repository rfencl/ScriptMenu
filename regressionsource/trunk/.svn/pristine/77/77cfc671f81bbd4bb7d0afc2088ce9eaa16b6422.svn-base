package com.powin.modbusfiles.utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.powin.modbusfiles.utilities.JsonParserHelper;

public class JsonParserHelperIntegrationTest {

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
	public void tearDown() throws Exception {//		for (Map.Entry<String, Number> entry : m.entrySet()) {
//		System.out.println(entry);
//	}

	}

	
	@Test
	public void testGetJSONFromFile() {
		String path = "src/test/resources/"+"Stack225Gen2_2SafetyAndNotificationConfig.json";
		JSONObject jsonFromFile = JsonParserHelper.getJSONFromFile(path);
		assertThat(jsonFromFile.get("highCellGroupTemperatureWarningClear"), is(44.0));
	}

	
	@Test
	public void testToSnakeCase() {
		assertThat(JsonParserHelper.toSnakeCase("lowStringVoltageWarningEnabled"), is("LOW_STRING_VOLTAGE_WARNING_ENABLED"));
	}
	
	
	@Test
	public void testToSnakeCaseKeys2() throws Exception {
		String path = "src/test/resources/"+"Stack225Gen2_2SafetyAndNotificationConfig.json";
		JSONObject jsonFromFile = JsonParserHelper.getJSONFromFile(path);
		Map<String, Number> m = JsonParserHelper.snakeCaseKeys(jsonFromFile);
		for (Map.Entry<String, Number> entry : m.entrySet()) {
			System.out.println(entry);
			assertThat(entry.getKey().contains("_"), is(true));

		}
	}


}
