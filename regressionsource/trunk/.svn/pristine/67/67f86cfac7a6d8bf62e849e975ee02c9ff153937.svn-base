package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.powin.modbusfiles.reports.LastCalls.ReturnGroup;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

// CodeReview: Is lastCallsContent an instance variable or a local variable?
// Refactor regular-expression matching to use getMatchString()
// Refactor move getMatchString to a StringUtils class.
// TODO Wrap these in SystemInfo calls
public class LastCalls {
	private static final String DELIMITER_SPACE = " ";
	private static final String BLOCK_INDEX_PATTERN = "(blockIndex.*?)(\\d+)";
	private static final String MAX_WATT_DISCHARGE_PATTERN = "(maxWattDischarge.*?)(\\d+)";
	private static final String MAX_WATT_CHARGE_PATTERN = "(maxWattCharge.*?)(\\d+)";
	private static final String WATT_HOUR_CAPACITY_PATTERN = "(wattHourCapacity.*?)(\\d+)";
	private static final String MAX_AMP_DISCHARGE_PATTERN = "(maxAmpDischarge.*?)(\\d+)";
	private static final String MAX_AMP_CHARGE_PATTERN = "(maxAmpCharge.*?)(\\d+)";
	private static final String AMP_HOUR_CAPACITY_PATTERN = "(ampHourCapacity.*?)(\\d+)";
	private static final String TOP_OFF_STATUS_PATTERN = "(Current Stage: TopOff\\s*\\(\\s*)(.*?)(.\\s*\\))";
	private static final String STATION_CODE_PATTERN = "(stationCode.*\")(.*)(\")";
	private static final String NO_MATCH = "<noMatch>";
	private static final String AC_REACTIVE_POWER_KVAR = "(acReactivePowerKVAR\\s*:\\s*)(.*)";
	private static final String AVAILABLE_AC_CHARGE_KW_PATTERN = "(availableACChargekW: )([^\\n\\s]*)";
	private static final String BOP_APP_STATUS_PATTERN = "(appCode: \"BOP0001\")(.*?)(appStatus:\\s*)(.*?)(\\})";
	private static final String BSF_GOOD_PATTERN = "(ACBattery)(.*?)(-\\s*)(.*)(\\s*)";
	private static final String BSF_ERROR_PATTERN = "(ACBattery)(.*?)(-\\s*)(.*?)(\\s*/)";
	private static final String APP_CODE_BSF_GOOD = "(appCode: \"BSF0001\")(.*?)(appStatus:\\s*)(.*?)(\\})";
	private static final String BFS_ERROR_CODE_PATTERN = "(Codes)(\\s*:\\s*)(.*)";
	private static final String PRIORITY_SOC_TARGET_CURRENT_STAGE_PATTERN = "(Priority :SOC TargetSOC:\\s*)(\\d+)(% Current Stage:\\s*)(\\S*)";
	private static final String AVAILABLE_AC_DISCHARGEKW_PATTERN = "(availableACDischargekW: )([^\\n\\s]*)";
	private static final String DC_BUS_VOLTAGE_PATTERN = "dcBusVoltage: (\\d+)";
	private final static Logger LOG = LogManager.getLogger(LastCalls.class.getName());
	private static final String RESOURCE_NAME = "default.properties";
	private static final String TURTLE_URL = "turtle_url";
	private static String lastCallsUrl = "";
	private static String lastCallsContent="";

	private static String getLastcallUrl() {
		if (lastCallsUrl.isEmpty()) {
			lastCallsUrl = PowinProperty.TURTLE_URL.toString();
			if (!lastCallsUrl.endsWith("/")) {
				lastCallsUrl += "/";
			}
			lastCallsUrl += "turtle/ui/ems/lastcalls.json";
		}
		return lastCallsUrl;
	}

	enum ReturnGroup {
		NONE,
		FIRST,
		SECOND,
		THIRD,
		FOURTH
	}

	private HttpHelper cHttpHelper;
	private JSONObject cReportContents;

	public LastCalls() {
		lastCallsUrl = LastCalls.getLastcallUrl();
		lastCallsContent=getLastCallsContent();
	}
	
	/**
	 * Put here for completeness, please use the SystemInfo version.
	 * @param string
	 * @return
	 */
	public String getDCBusVoltage() {
		return getMatchString(lastCallsContent, DC_BUS_VOLTAGE_PATTERN, Pattern.DOTALL, ReturnGroup.FIRST, "0");
	}
	
	private String getAcBatteryDataPattern(String arrayIndex) {
		return "(acBatteryReport.*arrayIndex: " + arrayIndex
				+ ")(.*?)(acBatteryData\\s+\\{)(.*?)(\\})";
	}
	
	public String getAvailableACChargekW(String arrayIndex)	 {
		return getAvailableAC_KW(arrayIndex, AVAILABLE_AC_CHARGE_KW_PATTERN);
	}
	
	public String getAvailableACDischargekW(String arrayIndex) {
		return getAvailableAC_KW(arrayIndex, AVAILABLE_AC_DISCHARGEKW_PATTERN);
	}
	
	private String getAvailableAC_KW(String arrayIndex, String matchPattern) {
		String matchedGroup = getMatchString(lastCallsContent, getAcBatteryDataPattern(arrayIndex), Pattern.DOTALL, ReturnGroup.FOURTH, NO_MATCH);
		return getMatchString(matchedGroup, matchPattern, Pattern.DOTALL, ReturnGroup.SECOND, "0");
	}


	public String getBlockIndex() {
		Pattern pattern = Pattern.compile(BLOCK_INDEX_PATTERN, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(lastCallsContent);
		String matchedString = NO_MATCH;
		if (matcher.find()) {
			matchedString = matcher.group(2);
		}
		return matchedString;
	}

	public String getBOPStatus() {
		return getMatchString(lastCallsContent, BOP_APP_STATUS_PATTERN, Pattern.DOTALL, ReturnGroup.FOURTH, null);
	}

	public String getBOPStatusPrioritySoc() {
		return getMatchString(lastCallsContent, PRIORITY_SOC_TARGET_CURRENT_STAGE_PATTERN, Pattern.DOTALL, ReturnGroup.FOURTH, null);
	}


	public String getBsfErrorCodes() {
		String s = getMatchString(getBSFMessage(), BFS_ERROR_CODE_PATTERN, Pattern.DOTALL, ReturnGroup.THIRD, null);
		if (null == s) {
			return null;
		}
		List<String> codes = Arrays.asList(s.split(DELIMITER_SPACE));
		codes.sort(Comparator.naturalOrder());
		return CommonHelper.convertListToString(codes);
	}


	public String getBSFMessage() {
		return getMatchString(lastCallsContent, APP_CODE_BSF_GOOD, Pattern.DOTALL, ReturnGroup.FOURTH, null);
	}

	public String getBsfStatus() {
		String matchedString = getMatchString(getBSFMessage(), BSF_ERROR_PATTERN, Pattern.DOTALL, ReturnGroup.FOURTH, null);
		if (null == matchedString) {
		   matchedString = getMatchString(getBSFMessage(), BSF_GOOD_PATTERN, Pattern.DOTALL, ReturnGroup.FOURTH, NO_MATCH);
		}
		return processMatch(matchedString, DELIMITER_SPACE);
	}
	
	public String getBSFGood() {
		Pattern pattern = Pattern.compile("(appCode: \"BSF0001\")(.*?)(appStatus:\\s*)(.*?)(\\})", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(lastCallsContent);
		if (matcher.find()) {
			String extractedText = matcher.group(4);
			extractedText = extractedText.replace("\\r", "");
			extractedText = extractedText.replace("\\n", "");
			extractedText = extractedText.replace("<br/>", "");
			extractedText = extractedText.replace("\"", "");
			return extractedText;
		}
		return null;
	}

	public String getLastCallsContent() {
		HttpHelper hh = null;
		hh = new HttpHelper(lastCallsUrl);
		JSONObject jo = hh.getJSONFromFile();
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(jo, "model|dragonToGoblin", "", results);
		lastCallsContent = results.toString();
		return results.toString();
	}

	public String getPCSReactivePower() {
		return getMatchString(lastCallsContent, AC_REACTIVE_POWER_KVAR, Pattern.MULTILINE, ReturnGroup.SECOND, NO_MATCH);
	}

	public String getPhaseVoltageAB() {
		return getPhaseVoltageAB(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageAB(String arrayIndex) {
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_A.*?TO_PHASE_B.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getPhaseVoltageAN() {
		return getPhaseVoltageAN(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageAN(String arrayIndex) {
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_A.*?TO_NEUTRAL.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getPhaseVoltageBC() {
		return getPhaseVoltageBC(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageBC(String arrayIndex) {
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_B.*?TO_PHASE_C.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getPhaseVoltageBN() {
		return getPhaseVoltageBN(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageBN(String arrayIndex) {
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_B.*?TO_NEUTRAL.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getPhaseVoltageCA() {
		return getPhaseVoltageCA(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageCA(String arrayIndex) { 
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_C.*?TO_PHASE_A.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getPhaseVoltageCN() {
		return getPhaseVoltageCN(PowinProperty.ARRAY_INDEX.toString());
	}

	public String getPhaseVoltageCN(String arrayIndex) {
		String patternString = "(arrayPcsReport.*key: " + arrayIndex
				+ ".*?)(arrayPcsPhaseData.*?PHASE_C.*?TO_NEUTRAL.*?acVoltageVolt:\\s*)(\\d+)";
		return getMatchString(lastCallsContent, patternString, Pattern.DOTALL, ReturnGroup.THIRD, NO_MATCH);
	}

	public String getSoc() {
		String BsfMessage = lastCallsContent;
		Pattern socPattern = Pattern.compile("(connectedClippedSOC)(.*?)(soc:\\s*)(.*?)(dod)", Pattern.DOTALL);
		Matcher matcher = socPattern.matcher(BsfMessage);
		String matchedString = NO_MATCH;
		if (matcher.find()) {
			matchedString = matcher.group(ReturnGroup.FOURTH.ordinal());
			return cleanString(matchedString);
		} else {
			return matchedString;
		}
	}

	
	public String getStationCode() {
		return getMatchString(getLastCallsContent(), STATION_CODE_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}

	public String getTopoffStatus(String statusString) {
		return getMatchString(statusString, TOP_OFF_STATUS_PATTERN, Pattern.DOTALL,
				ReturnGroup.SECOND, NO_MATCH);
	}

	public String getAmpHourCapacity() {
		return getMatchString(getLastCallsContent(), AMP_HOUR_CAPACITY_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}

	public String getMaxAmpCharge() {
		return getMatchString(getLastCallsContent(), MAX_AMP_CHARGE_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND, NO_MATCH);
	}

	public String getMaxAmpDischarge() {
		return getMatchString(getLastCallsContent(), MAX_AMP_DISCHARGE_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}

	public String getWattHourCapacity() {
		return getMatchString(getLastCallsContent(), WATT_HOUR_CAPACITY_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}

	public String getMaxWattCharge() {
		return getMatchString(getLastCallsContent(), MAX_WATT_CHARGE_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}

	public String getMaxWattDischarge() {
		return getMatchString(getLastCallsContent(), MAX_WATT_DISCHARGE_PATTERN, Pattern.MULTILINE, ReturnGroup.SECOND,
				NO_MATCH);
	}
	
	
	/**
	 * * TODO CodeReview: Move to StringUtils
	 * @param regex
	 * @param matchString
	 * @param mode
	 * @return
	 */
	private static Matcher getMatcher(String regex, String matchString, int mode) {
		Pattern pattern = Pattern.compile(regex, mode);
		return pattern.matcher(matchString);
	}
	
	/**
	 * Applies the regular expression to source and returns the matching group.
	 * TODO CodeReview: Move to StringUtils
	 * 
	 * @param source       - String to process
	 * @param regex        - Regular expression
	 * @param patternType  - Pattern modifier i.e. Pattern.MULTILINEl
	 * @param returnGroup  - Which group in the regular expression to return
	 * @param defaultValue - If no match is found return this value.
	 * @return - matching string
	 */
	public static String getMatchString(String source, String regex, int patternType, ReturnGroup returnGroup, String defaultValue) {
		Matcher matcher = getMatcher(regex, source, patternType);
		return matcher.find() ? cleanString(matcher.group(returnGroup.ordinal())) : defaultValue;  
	}

	private static String cleanString(String rawString) {
		String cleanedString = "";
		cleanedString = rawString.replaceAll("\\\\r|\\r", "");
		cleanedString = cleanedString.replaceAll("\\\\n|\\n", "");
		cleanedString = cleanedString.replaceAll("\\n", "");
		cleanedString = cleanedString.replaceAll("<br/>", "");
		cleanedString = cleanedString.replaceAll("\"", "");
		return cleanedString.trim();
	}
	
	private String processMatch(String matchedString, String delimiter) {
		List<String> blocks = Arrays.asList(matchedString.split(delimiter));
		blocks.sort(Comparator.naturalOrder());
		return CommonHelper.convertListToString(blocks);
	}

	public static void main(String[] args)
			throws ParseException, KeyManagementException, NoSuchAlgorithmException, IOException {
			// LastCalls lc = new LastCalls();

			// System.out.println(lc.getLastCallsContent());
			// System.out.println("Status: " +lc.getBsfStatus());
			// System.out.println("Codes: " + lc.getBsfErrorCodes());
			// System.out.println(lc.getLastcallUrl());
			// String test = "ACBattery QA22500FG:1:1 - CHG-BLOCKED OPS-BLOCKED/ Codes :
			// CHRG:HVH CHRG:HV";
			// String test ="ACBattery QA22500FG:1:1 - GOOD ";
			// String test ="ACBattery QA22500FG:1:1 - OPS-RECOVERING[19955]
			// CHG-RECOVERING[19955] DISCHG-RECOVERING[19955]";
			// Pattern recoveringPattern =
			// Pattern.compile("(ACBattery)(.*?)(-\\s*)(.*)(\\s*)",Pattern.DOTALL);
			// Pattern errorPattern =
			// Pattern.compile("(ACBattery)(.*?)(-\\s*)(.*?)(\\s*/)",Pattern.DOTALL);
			// Matcher matcher = recoveringPattern.matcher(test);
			// if (matcher.find()) {
			// System.out.println( matcher.group(4));
			// }
			// ;
			// List <String> codes = Arrays.asList(matcher.group(4).split(" "));
			// codes.sort(Comparator.naturalOrder());
			// System.out.println(lc.getSoc());

			LastCalls mLastCalls = new LastCalls();
			System.out.println(mLastCalls.getAmpHourCapacity());
			System.out.println(mLastCalls.getMaxAmpCharge());
			// System.out.println(mLastCalls.getPhaseVoltageAN("1"));
			// System.out.println(mLastCalls.getPhaseVoltageBC("1"));
			System.out.println(mLastCalls.getPhaseVoltageBC("1"));
			// mLastCalls.getPhaseVoltageBN();
			// mLastCalls.getPhaseVoltageCN();
			// Priority :SOC TargetSOC: 85% Current Stage: TopOff ( 1 : 0 / 32. )
			// mLastCalls.getTopoffStatus("Priority :SOC TargetSOC: 85% Current Stage:
			// TopOff ( 1 : 0 / 32. )");
			// mLastCalls.getTopoffStatus("Priority :SOC TargetSOC: 50% TargetP: -100.0 kW
			// Current Stage: TopOff ( 1 : 32 / 32. )\" }");
			// System.out.println(mLastCalls.getPCSReactivePower());
			// System.out.println(mLastCalls.getStationCode());
			// System.out.println(mLastCalls.getBlockIndex());
			// System.out.println(mLastCalls.getAvailableACChargekW("1"));
			// System.out.println(mLastCalls.getAvailableACChargekW("2"));
			// System.out.println(mLastCalls.getAvailableACDischargekW("1"));
			// System.out.println(mLastCalls.getAvailableACDischargekW("2"));

	}


}