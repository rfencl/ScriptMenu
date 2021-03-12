package com.powin.modbusfiles.reports;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import com.powin.modbusfiles.utilities.HttpHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
//This class is used to get contents of notifications.json by exercising the qa endpoint 
public class Notifications {
	 private final static Logger LOG = LogManager.getLogger(Notifications.class.getName());
	private HttpHelper cHttpHelper;
	private JSONObject notificationsContents;
	private static final String RESOURCE_NAME = "default.properties";// with jar need to be resources/default.properties
	private static final String NOTIFICATIONS_BASE_URL = "notifications_base_url";

	public Notifications(String url)  {
		String reportUrl;
		if (url.contains("/")) {
			reportUrl = url;
		} else {
			try {
				reportUrl = getNotificationsUrl(url);
			} catch (IOException e) {
				LOG.error(e.getMessage());
				throw new RuntimeException("Unable read url " + url);
			}
		}
		cHttpHelper = new HttpHelper(reportUrl);
		notificationsContents = cHttpHelper.getJSONFromFile();
	}

	public String getNotificationsUrl(String url) throws IOException {
		String result = PowinProperty.NOTIFICATIONS_BASE_URL.toString();
		url = url.replaceAll("[^\\d.]", "/");
		result = result.replace("<deviceID>", url);
		return result;
	}

	public String getStringTimestamp() {
		List<String> results = new ArrayList<>();
		results = JsonParserHelper.getFieldJSONObject(notificationsContents, "timeStamp", "", results);
		return results.get(0).toString();
	}
	
	public List<String> getNotificationsInfo() {
		// TODO Auto-generated method stub
		return getNotificationsInfo(false);
	}
	
	public List<String> getNotificationsInfoWithTimeStamp() {
		// TODO Auto-generated method stub
		return getNotificationsInfo(true);
	}
	// TODO: refactor to two methods above.
	public List<String> getNotificationsInfo(Boolean withTimestamp) {
		List<String> results = new ArrayList<>();
		if (withTimestamp) {
			String ts = getStringTimestamp();
			ts += ",";
			results = JsonParserHelper.getFieldJSONObjectWithTimestampRaw(notificationsContents, "notification|notificationType|notificationId", "", results, ts);
		} else
			results = JsonParserHelper.getFieldJSONObjectRaw(notificationsContents, "notification|notificationType|notificationId", "", results);
		return results;
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		Notifications strReport = new Notifications("1,1");
		System.out.println(strReport.getNotificationsInfo(false).toString());

	}


}