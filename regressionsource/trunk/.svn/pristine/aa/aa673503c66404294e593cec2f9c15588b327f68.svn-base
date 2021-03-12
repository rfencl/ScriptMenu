package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import com.powin.modbusfiles.awe.BatteryPackVoltageNotifications;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.FileHelper;
import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class Reports {
	private final static Logger LOG = LogManager.getLogger(Reports.class.getName());
	private HttpHelper cHttpHelper;
	private JSONObject cReportContents;

	public Reports(String url)  {
		String reportUrl;
		if (url.contains("/")) {
			reportUrl = url;
		} else {
			reportUrl = getReportUrl(url);
		}
		
			cHttpHelper = new HttpHelper(reportUrl);
		
		cReportContents = cHttpHelper.getJSONFromFile();
		LOG.debug(cReportContents.toString());
	}

	private String getReportUrl(String url)  {
		String result = PowinProperty.REPORT_BASE_URL.toString();
		url = url.replaceAll("[^\\d.]", "/");
		result = result.replace("<deviceID>", url);
		LOG.debug("reportURL:{}", result);
		return result;
	}

	public JSONObject getReportContents() {
		cHttpHelper.getReport();
		cReportContents = cHttpHelper.getJSONFromFile();
		return cReportContents;
	}

	public String getStringTimestamp() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "timeStamp", "", results);
		return results.get(0).toString();
	}
	
	public String getStackType() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stackType", "", results);
		return results.get(0).toString();
	}


	public String getStringSoc() {
		List<String> results = new ArrayList<>();
	    results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|soc", "", results);
		return results.get(0).toString();
	}
	
	public String getDcBusVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|dcBusVoltage", "", results);
		return results.get(0).toString();
	}
	public String getArrayDcBusVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|dcBusVoltage", "", results);
		return results.get(0).toString();
	}
	
	public String getCalculatedStringVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|calculatedStringVoltage", "", results);
		return results.get(0).toString();
	}

	public String getMeasuredStringVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|measuredStringVoltage", "", results);
		return results.get(0).toString();
	}

	public String getStringCurrent() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|stringCurrent", "", results);
		return results.get(0).toString();
	}

	public String getStringPower() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|kW", "", results);
		return results.get(0).toString();
	}

	public String getStringkWh() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|kWh", "", results);
		return results.get(0).toString();
	}

	public String getStringAh() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|ah", "", results);
		return results.get(0).toString();
	}
	public String getMaxCellGroupVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|maxCellGroupVoltage", "", results);
		return results.get(0).toString();
	}
	public  String getMinCellGroupVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|minCellGroupVoltage", "", results);
		return results.get(0).toString();
	}
	public String getAvgCellGroupVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|avgCellGroupVoltage", "", results);
		return results.get(0).toString();
	}
	
	public String getMaxCellGroupTemperature() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|maxCellGroupTemp", "", results);
		return results.get(0).toString();
	}
	public String getMinCellGroupTemperature() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|minCellGroupTemp", "", results);
		return results.get(0).toString();
	}
	public String getAvgCellGroupTemperature() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|avgCellGroupTemp", "", results);
		return results.get(0).toString();
	}
	
	public String getStringPositiveContactorStatus() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|positiveContactorClosed", "", results);
		return results.get(0).toString();
	}
	
	public String getStringNegativeContactorStatus() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "stringData|negativeContactorClosed", "", results);
		return results.get(0).toString();
	}
    
	public List<String> getBatteryPackInfo(Boolean withTimestamp, String measuredParam) {
		List<String> results = new ArrayList<>();
		List<String> resultsV = new ArrayList<>();
		List<String> resultsT = new ArrayList<>();
		if (withTimestamp) {
			String ts = getStringTimestamp();
			ts += ",";
			switch (measuredParam.toUpperCase()) {
			case "VOLTAGE":
				results = JsonParserHelper.getFieldJSONObjectWithTimestamp(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|voltageLatest", "", results, ts);
				break;
			case "TEMPERATURE":
				results = JsonParserHelper.getFieldJSONObjectWithTimestamp(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|tempLatest", "", results, ts);
				results = CommonHelper.modifyTemperature(results);
				break;
			case "BOTH":
				resultsV = JsonParserHelper.getFieldJSONObjectWithTimestamp(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|voltageLatest", "", resultsV, ts);
				resultsT = JsonParserHelper.getFieldJSONObjectWithTimestamp(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|tempLatest", "", resultsT, ts);
				resultsT = CommonHelper.modifyTemperature(resultsT);
				results = CommonHelper.combineArrayListsElementwise(resultsV, resultsT, ":");

				break;

			}
		} else {
			switch (measuredParam.toUpperCase()) {
			case "VOLTAGE":
				results = JsonParserHelper.getFieldJSONObject(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|voltageLatest", "", results);
				break;
			case "TEMPERATURE":
				results = JsonParserHelper.getFieldJSONObject(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|tempLatest", "", results);
				results = CommonHelper.modifyTemperature(results);
				break;
			case "BOTH":
				resultsV = JsonParserHelper.getFieldJSONObject(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|voltageLatest", "", results);
				resultsT = JsonParserHelper.getFieldJSONObject(cReportContents, "batteryPackReportList|cellGroupReportList|cellGroupData|tempLatest", "", results);
				resultsT = CommonHelper.modifyTemperature(resultsT);
				results = CommonHelper.combineArrayListsElementwise(resultsV, resultsT, ":");
				break;
			}

		}
		return results;
	}

	public List<String> getBatteryPackInfo(Boolean withTimestamp, String measuredParam, int bpIndex) {
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, measuredParam);
		CollectionUtils.filter(instantaneousCgReport, o -> (((String) o).split(","))[1].equals(Integer.toString(bpIndex)));
		return instantaneousCgReport;
	}

	public List<String> getCellGroupReport(String measuredParam) throws InterruptedException {
		// A cellgroup file with one line for each cellgroup every seconds showing cell Temperature.
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, measuredParam);
		List<String> modifiedCgReport = new ArrayList<>();
		String timestamp = instantaneousCgReport.get(0).split(",")[0];
		modifiedCgReport.add(timestamp);
		for (String data : instantaneousCgReport) {
			modifiedCgReport.add(data.split(",")[3]);
		}
		return modifiedCgReport;
	}

	public List<String> getCellGroupReport(String measuredParam, int bpIndex) throws InterruptedException {
		// A cellgroup file with one line for each cellgroup every seconds showing cell Temperature.
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, measuredParam);
		CollectionUtils.filter(instantaneousCgReport, o -> (((String) o).split(","))[1].equals(Integer.toString(bpIndex)));
		List<String> modifiedCgReport = new ArrayList<>();
		String timestamp = instantaneousCgReport.get(0).split(",")[0];
		modifiedCgReport.add(timestamp);
		for (String data : instantaneousCgReport) {
			modifiedCgReport.add(data.split(",")[3]);
		}
		return modifiedCgReport;
	}

	public String getCellGroupReportAsString(String measuredParam) throws InterruptedException {
		return CommonHelper.convertArrayListToString(getCellGroupReport(measuredParam));
	}

	public String getCellGroupReportAsString(String measuredParam, int i) throws InterruptedException {
		return CommonHelper.convertArrayListToString(getCellGroupReport(measuredParam, i));
	}

	public String getCellGroupReportHeader() throws InterruptedException {
		// BP1CG1,BP1CG2,.....BP1CG16,.....BP17CG15,BP17CG16
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "voltage");
		List<String> modifiedCgReport = new ArrayList<>();
		modifiedCgReport.add("timestamp");
		for (String data : instantaneousCgReport) {
			modifiedCgReport.add("BP" + data.split(",")[1] + "CG" + data.split(",")[2]);
		}
		return CommonHelper.convertArrayListToString(modifiedCgReport);
	}

	public String getCellGroupReportHeader(int bpIndex) throws InterruptedException {
		// BP1CG1,BP1CG2,.....BP1CG16,.....BP17CG15,BP17CG16
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "voltage", bpIndex);
		List<String> modifiedCgReport = new ArrayList<>();
		modifiedCgReport.add("timestamp");
		for (String data : instantaneousCgReport) {
			modifiedCgReport.add("BP" + data.split(",")[1] + "CG" + data.split(",")[2]);
		}
		return CommonHelper.convertArrayListToString(modifiedCgReport);
	}

	public void customReport(String filePath, String reportMode, String writeIntervalSeconds) throws IOException, NumberFormatException, InterruptedException {
		FileHelper fCellGroupReport = new FileHelper(filePath);
		fCellGroupReport.writeToCSV(getCellGroupReportHeader() + "stackVoltage,stackCurrent,power,kwh,ah,soc");
		if (reportMode.toLowerCase().contains("single")) {
			fCellGroupReport.writeToCSV(getCellGroupReportAsString("voltage") + "," + getMeasuredStringVoltage() + "," + getStringCurrent() + "," + getStringPower()
					+ "," + getStringkWh() + "," + getStringAh() + "," + getStringSoc());
		} else if (reportMode.toLowerCase().contains("stream")) {
			while (true) {
				fCellGroupReport.writeToCSV(getCellGroupReportAsString("voltage") + "," + getMeasuredStringVoltage() + "," + getStringCurrent() + "," + getStringPower()
						+ "," + getStringkWh() + "," + getStringAh() + "," + getStringSoc());
				Thread.sleep(1000 * Integer.parseInt(writeIntervalSeconds));
			}
		}
	}

	private List<String> getCellGroupReportEveryMinute() throws InterruptedException {
		// A cellgroup file with one line for each cellgroup every 60 seconds showing high, avg, and low cell Temperature during that 60 second period.
		ArrayList<List<String>> collectedCgReports = new ArrayList<>();
		for (int timeIncrement = 0; timeIncrement < 60; timeIncrement++) {
			List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "voltage");
			collectedCgReports.add(instantaneousCgReport);
			Thread.sleep(1000);
		}
		return processCellGroupData(collectedCgReports);
	}

	public List<String> processCellGroupData(List<List<String>> collectedCgReports) {
		List<String> finalList = new ArrayList<>();
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		String tmpcellGroupDataSet = "";
		String cellGroupDataSet = "";
		Integer cellGroupData;
		int maxValue = 0;
		int minValue = 0;
		int avgValue = 0;
		for (int cellGroup = 0; cellGroup < collectedCgReports.get(0).size(); cellGroup++) {
			for (int reports = 0; reports < collectedCgReports.size(); reports++) {
				cellGroupDataSet = collectedCgReports.get(reports).get(cellGroup);
				cellGroupData = Integer.parseInt(cellGroupDataSet.split(",")[3]);
				tmpComputeList.add(cellGroupData);
			}
			maxValue = Collections.max(tmpComputeList);
			minValue = Collections.min(tmpComputeList);
			avgValue = CommonHelper.getCollectionAverage(tmpComputeList);
			tmpcellGroupDataSet = String.join(",", Arrays.copyOfRange(cellGroupDataSet.split(","), 0, 3));
			tmpcellGroupDataSet += "," + maxValue + "," + minValue + "," + avgValue;
			finalList.add(tmpcellGroupDataSet);
			tmpComputeList = new ArrayList<>();
		}
		return finalList;
	}

	public String getStringReport() throws InterruptedException {
		//TO DO: Looks like max,min and avg are now available in the report directly. Refactor accordingly
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "voltage");
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		String cellGroupDataSet = "";
		int cellGroupData;
		int maxCellVoltage = 0;
		int minCellVoltage= 0;
		int avgCellVoltage = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData = Integer.parseInt(cellGroupDataSet.split(",")[3]);
			tmpComputeList.add(cellGroupData);
		}
		maxCellVoltage = Collections.max(tmpComputeList);
		minCellVoltage = Collections.min(tmpComputeList);
		avgCellVoltage = CommonHelper.getCollectionAverage(tmpComputeList);
		avgCellVoltage = CommonHelper.getCollectionAverage(tmpComputeList);
		String timeStamp = cellGroupDataSet.split(",")[0];
		return timeStamp + "," + maxCellVoltage + "," + minCellVoltage + "," + avgCellVoltage + ","
				+ getStringCurrent() + "," + getStringPower() + "," + getCalculatedStringVoltage() + "," + getMeasuredStringVoltage() + "," + getStringSoc();
	}
	public String getStringReport_vt() throws InterruptedException {
		// 2.A string file with one line every second showing high and low cell Temperatures, current and power
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "voltage");
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		String cellGroupDataSet = "";
		int cellGroupData;
		int maxCellVoltage = 0;
		int minCellVoltage= 0;
		int avgCellVoltage = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData = Integer.parseInt(cellGroupDataSet.split(",")[3]);
			tmpComputeList.add(cellGroupData);
		}
		maxCellVoltage = Collections.max(tmpComputeList);
		minCellVoltage = Collections.min(tmpComputeList);
		avgCellVoltage = CommonHelper.getCollectionAverage(tmpComputeList);
		
		instantaneousCgReport = this.getBatteryPackInfo(true, "temperature");
		ArrayList<Double> tmpComputeList1 = new ArrayList<>();
		cellGroupDataSet = "";
		double cellGroupData1;
		double maxCellTemperature = 0;
		double minCellTemperature = 0;
		double avgCellTemperature = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData1 = Double.parseDouble(cellGroupDataSet.split(",")[3]);
			tmpComputeList1.add(cellGroupData1);
		}
		maxCellTemperature = Collections.max(tmpComputeList1);
		minCellTemperature = Collections.min(tmpComputeList1);
		avgCellTemperature = CommonHelper.getCollectionAverage1(tmpComputeList1);
		
		String timeStamp = cellGroupDataSet.split(",")[0];
		return timeStamp + "," + 
				maxCellVoltage + "," + minCellVoltage + "," + avgCellVoltage + ","+
				maxCellTemperature + "," + minCellTemperature + "," + avgCellTemperature + ","+
				getStringCurrent() + "," + getStringPower() + "," + getCalculatedStringVoltage() + "," + getMeasuredStringVoltage() + "," + getStringSoc();
	}
	
	public Map<String, String> getStringReport_vt_id() {
		Map<String, String> fields = new HashMap<>();
		String timeStamp = this.getStringTimestamp();
		List<String> instantaneousCgReport = this.getBatteryPackInfo(false, "voltage");
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		String cellGroupDataSet = "";
		int cellGroupData;
		int avgCellVoltage = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData = Integer.parseInt(cellGroupDataSet.split(",")[2]);
			tmpComputeList.add(cellGroupData);
		}

		avgCellVoltage = CommonHelper.getCollectionAverage(tmpComputeList);
		String maxVoltageWithId=CommonHelper.sortListBySubstring(instantaneousCgReport,false,2,"int").get(0);
		String minVoltageWithId=CommonHelper.sortListBySubstring(instantaneousCgReport,true,2,"int").get(0);
		
		instantaneousCgReport = this.getBatteryPackInfo(false, "temperature");
		ArrayList<Double> tmpComputeList1 = new ArrayList<>();
		cellGroupDataSet = "";
		double cellGroupData1;
		double avgCellTemperature = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData1 = Double.parseDouble(cellGroupDataSet.split(",")[2]);
			tmpComputeList1.add(cellGroupData1);
		}
		avgCellTemperature = CommonHelper.getCollectionAverage1(tmpComputeList1);
		String maxTemperatureWithId=CommonHelper.sortListBySubstring(instantaneousCgReport,false,2,"double").get(0);
		String minTemperatureWithId=CommonHelper.sortListBySubstring(instantaneousCgReport,true,2,"double").get(0);
		String [] sfields = { "minVoltageWithId", 		minVoltageWithId, 
							  "avgCellVoltage", 		String.valueOf(avgCellVoltage), 
	                          "maxVoltageWithId", 		maxVoltageWithId, 
	                          "minTemperatureWithId", 	minTemperatureWithId, 
	                          "avgCellTemperature", 	String.valueOf(avgCellTemperature),
	                          "maxTemperatureWithId", 	maxTemperatureWithId,
	                          "stringCurrent", 			getStringCurrent(), 
	                          "stringPower", 			getStringPower(), 
	                          "calculatedStringVoltage", getCalculatedStringVoltage(), 
	                          "measuredStringVoltage", 	getMeasuredStringVoltage(), 
	                          "stringSOC", 				getStringSoc()
	                        };
		for (int i = 0; i < sfields.length/2; i+=2) {
			fields.put(sfields[i], sfields[i+1]);
		}
		return fields;
	}

	// Array report
	//
	public String getCommunicatingStackCount() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|communicatingStackCount", "", results);
		return results.get(0).toString();
	}
	
	public String getArrayCurrent() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|amps", "", results);
		return results.get(0).toString();
	}

	public String getArrayPower() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|kW", "", results);
		return results.get(0).toString();
	}

	public String getArrayMaxAllowedChargeCurrent() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|maxAllowedChargeCurrent", "", results);
		return results.get(0).toString();
	}
	
	public String getArrayMaxCellVoltage() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|connectedStackMaxCellVoltage", "", results);
		return results.get(0).toString();
	}

	public String getArrayMaxAllowedDischargeCurrent() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "arrayData|maxAllowedDischargeCurrent", "", results);
		return results.get(0).toString();
	}
    /**
     * Get the two-d list of cells voltages 
     * @return
     * @throws InterruptedException
     */
	public List<List<String>>  getCellVoltageArrayReport() {
		// Each cell in the stack is a list element, need to organize them for printing
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "Voltage");
		List<List<String>> rowData = new ArrayList<>();
		List<String> colData = new ArrayList<>();
		rowData.add(colData);
		String lastRow = "";
		for (String s : instantaneousCgReport) {
			String [] data = s.split(",");
			String row = data[1];
			String volts = data[3];
			if(lastRow.isEmpty()) {
				lastRow = row; 
			} else if (!lastRow.equals(row)) {       // Start a new row
				colData = new ArrayList<>();   
				rowData.add(colData);
				lastRow = row;
			} 
			colData.add(volts);

		}
		return rowData;
	}
	
	public String getArrayReport() throws InterruptedException {
		List<String> instantaneousCgReport = this.getBatteryPackInfo(true, "Temperature");
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		String cellGroupDataSet = "";
		Integer cellGroupData;
		int maxCellTemperature = 0;
		int minCellTemperature = 0;
		for (int cellGroup = 0; cellGroup < instantaneousCgReport.size(); cellGroup++) {
			cellGroupDataSet = instantaneousCgReport.get(cellGroup);
			cellGroupData = Integer.parseInt(cellGroupDataSet.split(",")[3]);
			tmpComputeList.add(cellGroupData);
		}
		maxCellTemperature = Collections.max(tmpComputeList);
		minCellTemperature = Collections.min(tmpComputeList);
		String timeStamp = cellGroupDataSet.split(",")[0];
		return timeStamp + "," + maxCellTemperature + "," + minCellTemperature + "," + getStringCurrent() + "," + getStringPower();
	}

	public String getAppStatus() {
		List<String> results = new ArrayList<>();
		//results = JsonParserHelper.getFieldJSONObject(cReportContents, "dragonAppReport|dragonAppData|dragonAppSlotData", "", results);
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "requestId", "", results);
		return results.get(0).toString();
	}
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
//		Reports strReport = new Reports("https://archiva.powindev.com/repository/internal/com/powin/kobold/maven-metadata.xml");
		//Reports strReport = new Reports("https://localhost:8443/turtle/ui/ems/lastcalls.json");
		Reports strReport = new Reports("1");
		//System.out.println(strReport.getAppStatus());
		System.out.println(strReport.getArrayMaxCellVoltage());
//		System.out.println(strReport.getStringReport_vt_id());
//		System.out.println(strReport.getBatteryPackInfo(true, "both"));
//		String result = strReport.getCellGroupReportAsString("both", 1);
//		System.out.println(result);

		// //Testing class methods
		// System.out.println(strReport.getStringTimestamp());
		// System.out.println(strReport.getStringSoc());
		// System.out.println(strReport.getCalculatedStringTemperature());
		// System.out.println(strReport.getStringCurrent());
		// System.out.println(strReport.getStringPower());
		// System.out.println(strReport.getCalculatedStringTemperature());
//		System.out.println(strReport.getStringNegativeContactorStatus());
//		System.out.println(strReport.getStringPositiveContactorStatus());
		// System.out.println(strReport.getBatteryPackInfo(true));
		// System.out.println(strReport.getBatteryPackInfo(false));
//		 System.out.println(strReport.getStringReport());
		// System.out.println(Arrays.deepToString(list.toArray(strReport.getCellGroupReport()))));
		// Integer[] arr = new Integer[al.size()];
		// arr = al.toArray(arr);
		// String [] arr = new String[strReport.getCellGroupReport().size()];

		// System.out.println(record);

		// Printing string report
		// for (int idx = 0; idx < 6; idx++) {
		// fu.writeToCSV(strReport.getStringReport());
		// Thread.sleep(2000);
		// }

		// Printing cell group report
		// for (int idx = 0; idx < 2; idx++) {
		// strReport.writeToCSV(strReport.getCellGroupReportEveryMinute());
		// Thread.sleep(2000);
		// }



		// //Testing array reports
		//
		// Reports arrayReport = new Reports("1");
		// System.out.println(arrayReport.getArrayCurrent());
		// System.out.println(arrayReport.getArrayPower());
	}

}