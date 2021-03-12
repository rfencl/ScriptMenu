package com.powin.modbusfiles.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum PowinProperty {
		ARCHIVA_USER("archiva_user"),
		ARCHIVA_PASSWORD("archiva_password"),
		TURTLE_URL("turtle_url"),
		KOBOLD_URL("kobold_url"),
		MAXPERCENTAGEOFPOWER("maxPercentageOfPower"),
		TURTLEHOST("turtleHost"),
		TURTLEUSER("turtleUser"),
		TURTLEPASSWORD("turtlePassword"),
		REPORT_BASE_URL("report_base_url"),
		FIRMWARE_REPORTS_URL("firmware_reports_url"),
		NOTIFICATIONS_BASE_URL("notifications_base_url"),
		PHOENIX_COMMAND_INJECT_URL("phoenix_command_inject_url"),
		CLOUDHOST("cloudHost"),
		ARRAY_INDEX("array_index"),
		STRING_INDEX("string_index"),
		REPORTFOLDER("reportFolder"),
		BATTERY_PACK_COUNT("battery_pack_count"),
		CELL_GROUP_COUNT("cell_group_count"),
		RTE_CSV_FILE_PATH("rte_csv_file_path"),
		RTE_CHARGINGPOWERASPCT("rte_chargingPowerAsPct"),
		RTE_DISCHARGINGPOWERASPCT("rte_dischargingPowerAsPct"),
		RTE_RESTPERIODSECONDS("rte_restPeriodSeconds"),
		RTE_TARGETCHARGEVOLTAGE("rte_targetChargeVoltage"),
		RTE_TARGETDISCHARGEVOLTAGE("rte_targetDischargeVoltage"),
		RTE_MAXCYCLES("rte_maxCycles"),
		BC_CELL_GROUP_REPORT_MINUTE_CSV_FILE_PATH("bc_cell_group_report_minute_csv_file_path"),
		BC_CELL_GROUP_REPORT_SECOND_CSV_FILE_PATH("bc_cell_group_report_second_csv_file_path"),
		BC_STRING_REPORT_CSV_FILE_PATH("bc_string_report_csv_file_path"),
		BC_ARRAY_REPORT_CSV_FILE_PATH("bc_array_report_csv_file_path"),
		BC_CHARGINGPOWERASPCT("bc_chargingPowerAsPct"),
		BC_DISCHARGINGPOWERASPCT("bc_dischargingPowerAsPct"),
		BC_CHARGINGPOWERW("bc_chargingPowerW"),
		BC_DISCHARGINGPOWERW("bc_dischargingPowerW"),
		BC_POWERCYCLEPERIODSECONDS("bc_powerCyclePeriodSeconds"),
		BC_RESTPERIODSECONDS("bc_restPeriodSeconds"),
		BC_TARGETCHARGESOC("bc_targetChargeSOC"),
		BC_TARGETDISCHARGESOC("bc_targetDischargeSOC"),
		BC_TARGETCHARGEVOLTAGE("bc_targetChargeVolresourcestage"),
		BC_TARGETDISCHARGEVOLTAGE("bc_targetDischargeVoltage"),
		BC_MAXCYCLES("bc_maxCycles"),
		BC_LOGINTERVAL("bc_logInterval"),
		BC_SEL735("bc_sel735"),
		UL_CHARGINGDURATION("ul_chargingDuration"),
		UL_DISCHARGINGDURATION("ul_dischargingDuration"),
		UL_RESTPERIODSECONDS("ul_restPeriodSeconds"),
		UL_MAXCYCLES("ul_maxCycles"),
		UL_LOGINTERVAL("ul_logInterval"),
		UL_CSV_FILE_PATH("ul_csv_file_path");
	        
		private static final String RESOURCE_NAME = "default.properties";
	        
		private final static Logger LOG = LogManager.getLogger(CommonHelper.class.getName());
        private static Properties mProperty = new Properties();
	    final String propertyFileKey;
        
	    static {
	    	loadProperties();
	    }
	    
	    /**
	     * Reload the properties from disk.
	     */
		public static void loadProperties() {
			getProperties(RESOURCE_NAME);
		}
	    /**
	     * Constructor initializes the source flags.
	     *PowinProperty.STRING_INDEX.setValue(3);
	     * @param value  - the 'key' in the property file.
	     */
	    PowinProperty(final String value) {
	        propertyFileKey = value;
	    }

	    @Override
		public String toString() {
	        return mProperty.getProperty(propertyFileKey);
	    }
	    
	    public int intValue() {
	    	return Integer.parseInt(this.toString());
	    }

		public static List<String> getNames() {
			Class<?> c = PowinProperty.class;
			Field[] flds = c.getDeclaredFields();
			List<String> names = new ArrayList<>();
			for (Field f : flds) {
				if (f.isEnumConstant()) {
					names.add(f.getName());
				}
			}
			return names;
		}

		/**
		 * This is a direct replacement for getDefaultProperty
		 * Use this when the key is constructed at runtime.
		 * @param key
		 * @return
		 */
	    public static String fromPropertyFileKey(String key) {
	       return mProperty.getProperty(key);
	    }
	    
	    /**
	     * When running as unit test we want to force the property file in the classes folder
	     * to be loaded as we don't want to maintain two files.
	     * 
	     * @param propertyFileName
	     * @return
	     */
		private static String getProperties(String propertyFileName)  {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream input = loader.getResourceAsStream("../classes/" + propertyFileName);
			//InputStream input = loader.getResourceAsStream(propertyFileName);
			try {
//				if (input == null)
//					input = loader.getResourceAsStream(propertyFileName);
				if (input == null)
					input = loader.getResourceAsStream("resources/" + propertyFileName);
				mProperty.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static void setValue(String key, String string) {
			mProperty.setProperty(key, string);
		}

		public static void main(String [] args) {
			final PowinProperty[] values = PowinProperty.values();
			Arrays.asList(values).stream().forEach(LOG::info);
		}
}
