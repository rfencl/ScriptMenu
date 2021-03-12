package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;


public class KoboldData {
	private String cKoboldDataUrl;
	private HttpHelper cHttpHelper;
	private JSONObject cReportContents;

	public KoboldData() {
		setKoboldDataUrl("https://10.0.0.5:8443/kobold/monitor/block/QA22500FG/1/data");

		cHttpHelper = new HttpHelper(getKoboldDataUrl());
		cReportContents = cHttpHelper.getJSONFromFile();
	}

	public String getKoboldDataUrl() {
		return cKoboldDataUrl;
	}

	public void setKoboldDataUrl(String koboldDataUrl) {
		cKoboldDataUrl = koboldDataUrl;
	}
	
	public String getAppStatus() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(cReportContents, "dragonAppSlotData|appStatus", "", results);
		return results.get(0).toString();
	}
	
	public static void main(String[] args) {
		KoboldData kd = new KoboldData();
		String str = kd.getAppStatus();
				
	}
}
