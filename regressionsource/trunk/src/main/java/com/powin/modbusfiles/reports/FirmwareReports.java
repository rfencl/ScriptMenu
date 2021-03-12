package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;

import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.PowinProperty;

public class FirmwareReports {
	private HttpHelper cHttpHelper;
	private JSONObject cReportContents;
	private static final String RESOURCE_NAME = "default.properties";// with jar need to be resources/default.properties
	private static final String REPORT_BASE_URL = "firmware_reports_url";

	public FirmwareReports(String url) throws IOException {
		String reportUrl;
		if (url.contains("/")) {
			reportUrl = url;
		} else {
			reportUrl = getReportUrl(url);
		}
		try {
			cHttpHelper = new HttpHelper(reportUrl,false);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getReportUrl(String url) throws IOException {
		String result = PowinProperty.REPORT_BASE_URL.toString();
		url = url.replaceAll("[^\\d.]", "/");
		result = result.replace("<deviceID>", url);
		return result;
	}

	
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		FirmwareReports fwReport = new FirmwareReports("1,1");	
	}

}