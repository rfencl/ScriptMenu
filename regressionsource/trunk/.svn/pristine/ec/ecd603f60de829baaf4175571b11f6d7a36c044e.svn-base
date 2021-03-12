package com.powin.modbusfiles.utilities;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("deprecation")
public class HttpHelper {
	// private static final String RESOURCE_NAME = "/sunspectests/src/main/resources/default.properties";
	// private static final String RESOURCE_NAME = "default.properties";// with jar need to be resources/default.properties
	// private static final String REPORT_BASE_URL = "report_base_url";
	private String reportUrl = "";
	private String reportContents;

	public HttpHelper(String url)  {
		reportUrl = url;
		this.getReport();
	}

	public HttpHelper() {

	}
	
	public HttpHelper(String url,boolean onlyConnect) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		reportUrl = url;
		this.getConnection();
	}
	
	public URLConnection getConnection() throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException {
		/* fix for Exception in thread "main" javax.net.ssl.SSLHandshakeException:
		 * sun.security.validator.ValidatorException: PKIX path building failed:
		 * sun.security.provider.certpath.SunCertPathBuilderException: unable to find
		 * valid certification path to requested target */
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/* end of the fix */

		URL url = new URL(reportUrl);
		URLConnection con = null;
		try {
			con = url.openConnection();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	public String getReport()  {
		// Read from connection
		URLConnection con = null;
		try {
			con = getConnection();
		} catch (KeyManagementException | NoSuchAlgorithmException | MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Reader reader = null;
		try {
			reader = new InputStreamReader(con.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder s = new StringBuilder();
		while (true) {
			int ch = 0;
			try {
				ch = reader.read();
				if (ch != -1) {
					s.append((char) ch);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// We don't want the end of file character
			if (ch == -1) {
				break;
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reportContents = s.toString();
		return s.toString();
	}

	public JSONObject getJSONFromFile() {
		String jsonText = "";
		jsonText = reportContents;
        
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(jsonText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		HttpHelper mHttpHelper = new HttpHelper("https://archiva.powindev.com/repository/internal/com/powin/kobold/maven-metadata.xml");
		System.out.println(mHttpHelper.getReport());

	}
}