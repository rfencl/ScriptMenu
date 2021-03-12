package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.SunspecMasterEndpoint;
import com.powin.modbus.sunspec.SunspecTcpMasterEndpointFactory;
import com.powin.modbus.sunspec.blocks.InverterThreePhaseBlockMaster;
import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.utilities.FileHelper;

public class CyclingReport {
	private final static Logger LOG = LogManager.getLogger(CyclingReport.class.getName());

	private final String cModbusHostName;
	private final int cModbusPort;
	private final int cModbusUnitId;
	private final boolean cEnableModbusLogging;

	private Modbus103 cInverterThreePhaseBlockMaster;
	private String balanceState = "OFF";
	private Reports cStringReport;
	private FileHelper cCellVoltageReportFile;
	private String cReportFolder;
	private FileHelper cReportFile;
	private int cArrayIndex;
	private int cStringIndex;



	
	public CyclingReport(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging, int arrayIndex, int stringIndex) {
		this.cModbusHostName = modbusHostName;
		this.cModbusPort = modbusPort;
		this.cModbusUnitId = modbusUnitId;
		this.cEnableModbusLogging = enableModbusLogging;
		this.cArrayIndex = arrayIndex;
		this.cStringIndex = stringIndex;
		
		cStringReport = new Reports(cArrayIndex + "," + cStringIndex);
	}
	
	public void init() {
			cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}


	
	public Reports getStringReport() {
		return cStringReport;
	}
	/**
	 * TODO move to a string utils class Reverse rotate moves the last string in a
	 * csv string to the front s1,s2,s3 -> s3,s1,s2
	 * 
	 * @param string is a csv string
	 * @return csv string
	 */
	protected static String rrotCSV(String csvString) {
		String[] ret = csvString.split(",");
		String t = ret[ret.length - 1];
		for (int i = ret.length - 1; i > 0; --i) {
			ret[i] = ret[i - 1];
		}
		ret[0] = t;

		return String.join(",", ret);
	}

	public void printInfoInConsole(){
		getStringReport().getReportContents();
		String cellVoltageMaximum = getStringReport().getMaxCellGroupVoltage();
		String cellVoltageMinimum = getStringReport().getMinCellGroupVoltage();
		String soc = getStringReport().getStringSoc();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		String stackPowerKW = getStringReport().getStringPower();
		LOG.info(String.format("vMax: %s vMin: %s SOC: %s DcbusV: %s StackKW: %s", cellVoltageMaximum,
				cellVoltageMinimum, soc, dcbusVolt, stackPowerKW));
	}

	public void saveCellVoltageDataToCsv(List<List<String>> cells)  {
		for (List<String> row : cells) {
			row.add(0, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			cCellVoltageReportFile.writeToCSV(String.join(",", (String[]) row.toArray(new String[row.size()])));
		}
		cCellVoltageReportFile.writeToCSV("--------------------------------------------------------------");
	}
	
	

	public String getReportFolder() {
		return cReportFolder;
	}
	
	public void setCellVoltageReportFile(FileHelper reportFile) {
		cCellVoltageReportFile = reportFile;
	}

	public void createNewCellVoltageReportFile(String fileName)  {
		setCellVoltageReportFile(FileHelper.createTimeStampedFile(getReportFolder(), fileName, ".csv"));
	}
	
	public FileHelper getReportFile() {
		if (null == cReportFile) {
			createNewReportFile("CyclingReport");
		}
		return cReportFile;
	}

	public void setReportFile(FileHelper reportFile) {
		cReportFile = reportFile;
	}
	/**
	 * Create a new file for this run.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void createNewReportFile(String fileName)  {
		createNewCellVoltageReportFile("cellVoltageReport");
		setReportFile(FileHelper.createTimeStampedFile(getReportFolder(), fileName, ".csv"));
		getReportFile().writeToCSV(
				"TimeStamp, StepTime, CellDeltaV, CellMinV, CellAvgV, CellMaxV, CellAvgT, DCBusV, MeasuredStackV, CalculatedStackV, StackCurrent, StackPower, PCSPowerAC, SOC, ContactorStatus, BalancingState, Notifications");
		String celsius = "\u00B0C";
		getReportFile().writeToCSV(" , , mV, mV, mV, mV, " + celsius + ", V, V, V, A, kW, kW, %, , ,");

	}

	public void saveDataToCsv(int targetSoc, int targetP) {

		getStringReport().getReportContents();
		List<List<String>> cells = getStringReport().getCellVoltageArrayReport();
		//String packDeltaV = calculatePackDeltaV(cells);
		String cellVoltMax = getStringReport().getMaxCellGroupVoltage();
		String cellVoltMin = getStringReport().getMinCellGroupVoltage();
		String cellVoltAvg = getStringReport().getAvgCellGroupVoltage();
		String cellVoltDelta = String.valueOf(Integer.valueOf(cellVoltMax) - Integer.valueOf(cellVoltMin));
		// String cellTempMax = cStringReport.getMaxCellGroupTemperature();
		// String cellTempMin = cStringReport.getMinCellGroupTemperature();
		// String cellGroupAvgTemp = cStringReport.getAvgCellGroupTemperature();
		String negContactorStatus = getStringReport().getStringNegativeContactorStatus();
		String posContactorStatus = getStringReport().getStringPositiveContactorStatus();
		String dcbusVolt = getStringReport().getDcBusVoltage();
		if (!(Boolean.valueOf(negContactorStatus) && Boolean.valueOf(posContactorStatus))) {
			dcbusVolt = "0"; // don't show hysteresis;
		}
		// String stackVolt = cStringReport.getMeasuredStringVoltage();
		String stackCurrent = getStringReport().getStringCurrent();
		String stackPowerKW = getStringReport().getStringPower();
		String soc = getStringReport().getStringSoc();
		String pcsACPower = "";
		
		try {
			pcsACPower = String.valueOf(getWatts() / 1000);
		} catch (ModbusException e) {
			LOG.error("Unable to read watts",  e);
		}
		// String pcsDCPower = String.valueOf(getDCWatts());
		String contactorStatus = negContactorStatus + "/" + posContactorStatus;
		// getLastCalls().getLastCallsContent();
		// String bopStatus = getLastCalls().getBOPStatus();

		Notifications notifications = new Notifications(
				Integer.toString(cArrayIndex) + "," + Integer.toString(cStringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);

		String notificationString = "";
		if (notificationList != null && notificationList.size() > 0) {
			for (int i = 0; i < notificationList.size(); i++)
				notificationString += notificationList.get(i) + " ";
		}

		Map<String, String> stringReport_vt_ids = getStringReport().getStringReport_vt_id();

		String[] fields = { DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), "", // StepTime
				cellVoltDelta, cellVoltMin, cellVoltAvg, cellVoltMax, stringReport_vt_ids.get("avgCellTemperature"),
				dcbusVolt, getMeasuredStackVoltage(), getCalculatedStringVoltage(), stackCurrent, stackPowerKW,
				pcsACPower, soc, contactorStatus, balanceState, notificationString };
		getReportFile().writeToCSV(String.join(",", fields));
		saveCellVoltageDataToCsv(cells);
	}

	protected String getCalculatedStringVoltage() {
		return getStringReport().getCalculatedStringVoltage();
	}

	protected String getMeasuredStackVoltage() {
		return getStringReport().getMeasuredStringVoltage();
	}

	@SuppressWarnings("unused")
	private String calculatePackDeltaV(List<List<String>> cells) {
		List<Integer> rowSums = new ArrayList<>();
		for (List<String> row : cells) {
			rowSums.add(row.stream().map(Integer::valueOf).reduce(Integer::sum).get());
		}
		Integer max = rowSums.stream().mapToInt(v -> v).max().getAsInt();
		Integer min = rowSums.stream().mapToInt(v -> v).min().getAsInt();
		return String.valueOf(max - min);
	}
	
	public int getWatts() throws ModbusException {
		int watts = 0;
		try {
			watts = cInverterThreePhaseBlockMaster.getWatts();
		} catch (ModbusException e) {
			init();
			watts = cInverterThreePhaseBlockMaster.getWatts();
		}
		return watts;
	}


}
