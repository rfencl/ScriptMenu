package com.powin.modbusfiles.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.stream.XMLStreamException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.codehaus.plexus.util.StringUtils;
import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.CellVoltageLimits;
import com.powin.modbusfiles.awe.NotificationCodes;
import com.powin.modbusfiles.configuration.ConfigurationFileCreator;
import com.powin.modbusfiles.configuration.DeviceAcBatteryBlockConfigFileCreator;
import com.powin.modbusfiles.configuration.DeviceAcBatteryConfigFileCreator;
import com.powin.modbusfiles.configuration.DevicePcsSimulatorConfigFileCreator;
import com.powin.modbusfiles.configuration.DevicePhoenixDcBatteryConfigFileCreator;
import com.powin.modbusfiles.configuration.StackType;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.powinwebappbase.HttpHelper;



public class CommonHelper {
    private final static Logger LOG = LogManager.getLogger(CommonHelper.class.getName());
	private static final String RESOURCE_NAME = "default.properties";
	private static String host;
	private static String user;
	private static String password;
	private static int port = 22;
	
	
	
	static {
		try {
			 host = PowinProperty.TURTLEHOST.toString();
			 user = PowinProperty.TURTLEUSER.toString();
			 password = PowinProperty.TURTLEPASSWORD.toString();
		} catch (Exception e) {
			LOG.error(String.format("%s not found", RESOURCE_NAME));
		}
	}
	
	public static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}
	
	public static ArrayList<String> combineArrayListsElementwise(List<String> a, List<String> b, String delimiter) {
		return (ArrayList<String>) IntStream.range(0, a.size())
				.mapToObj(i -> String.join(",", Arrays.copyOfRange(a.get(i).split(","), 0, a.get(i).split(",").length - 1)) + "," + a.get(i).split(",")[a.get(i).split(",").length - 1] + delimiter + b.get(i).split(",")[b.get(i).split(",").length - 1])
				.collect(Collectors.toList());
	}
	
	public static int compareDates(String date1, String date2) throws ParseException, java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
	    Date d1 = format.parse(date1);
	    Date d2 = format.parse(date2);
	    return d1.compareTo(d2);
	}
	
	public static boolean compareIntegers(int testValue,int referenceValue, int percentTolerance, double zeroDifference) {
		//zeroDifference --> to compare when the reference value is 0
		int absoluteDifference =Math.abs(referenceValue-testValue);
		int percentageDifference=0;
		if(referenceValue==0) {
			if(testValue==0) {
				return true;			
			}
			else {			
				return absoluteDifference < zeroDifference;
			}
		}
		else {
			percentageDifference=100*absoluteDifference/referenceValue;
			return percentageDifference <= percentTolerance;
		}
	    
	}
	
	public static boolean compareIntegers(int testValue,int referenceValue, int percentTolerance ){
		double zeroDifference=0.01;//to compare when the reference value is 0
		return compareIntegers( testValue, referenceValue,  percentTolerance,  zeroDifference) ;
	    
	}
	
	public static String convertArrayListToString(List<String> arrayList) {
		String[] arr = arrayList.toArray(new String[arrayList.size()]);
		return String.join(",", arr);
	}

	public static String convertArrayListToString(List<String> arrayList,String delimiter) {
		String[] arr = arrayList.toArray(new String[arrayList.size()]);
		return String.join(delimiter, arr);
	}

	public static String convertListToString(List<String> list) {
		String[] arr = list.toArray(new String[list.size()]);
		return String.join(",", arr);
	}
	
	public static ArrayList<Integer> convertStringArrayListToIntegerArrayList(ArrayList<String> strArrayList) {
		ArrayList<Integer> tmpComputeList = new ArrayList<>();
		for (String s : strArrayList)
			tmpComputeList.add(Integer.parseInt(s));
		return tmpComputeList;
	}

	public static org.json.JSONObject convertXmlToJson(String archivaDataFile) {
        try {
            File xmlFile = new File(archivaDataFile);
            InputStream inputStream = new FileInputStream(xmlFile);
            StringBuilder builder = new StringBuilder();
            int ptr;
            while ((ptr = inputStream.read()) != -1) {
                builder.append((char) ptr);
            }
            inputStream.close();
            org.json.JSONObject jsonObj = XML.toJSONObject(builder.toString());
           // System.out.println(jsonObj);
            return jsonObj;
        } catch (IOException ex) {

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
		
	}

	public static String convertUtcToLocalDatetime(long utcTime) {
        Date date = new Date(utcTime);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        return format.format(date);
	}
	
	public static String stringSuffix(String sourceStr, String suffix) {
		return sourceStr.endsWith(suffix)?sourceStr:sourceStr.concat(suffix);
	}

	public static Session createSession(String user, String host, int port, String password) {
		try {
			JSch jsch = new JSch();
			Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			return session;
		} catch (JSchException e) {
			System.out.println(e);
			return null;
		}
	}

	public static void deleteExistingFile(String flePath) {
		File f= new File(flePath); 		
		if (f.exists()) {
			if(f.delete())
			{  
				System.out.println(f.getName() + " deleted"); 
			}  
			else  
			{  
				System.out.println("failed");  
			}  
		}
	}
	
	public static boolean deleteFolderContents(String folderPath) {
		File f= new File(folderPath); 	
		File[] folder=f.listFiles();
		if(folder!=null) {
			for(File f2:folder)
				deleteFolderContents(f2.getAbsolutePath());
		}
		return new File(folderPath).delete();
	}
	

	public static int getCollectionAverage(ArrayList<Integer> intList) {
		int sum = 0;
		for (int i : intList) {
			sum += i;
		}
		int average = sum / intList.size();
		return average;
	}

	public static double getCollectionAverage1(ArrayList<Double> list) {
		double sum = 0;
		for (double i : list) {
			sum += i;
		}
		double average = sum / list.size();
		BigDecimal bd = new BigDecimal(Double.toString(average));
		bd = bd.setScale(1, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static String getLocalHome() {
		return System.getProperty("user.home")+"/";	
	}
	
	public static String getLocalUser() {
		return System.getProperty("user.name");	
	}

	public static String getMaxVersionFromVersionList(List<String> l_filter1) {
		List<Integer> l_filter_modified= new ArrayList<>();
		String listItemModified="";
		try{
			for (String s:l_filter1) {
				listItemModified=s.split("\\.")[2].replaceAll("[^\\d.]", "");			
				l_filter_modified.add(Integer.parseInt(listItemModified));
			}
		}
		catch(Exception e) {
			System.out.println("Error processing version list");
		}
		int maxValue = Collections.max(l_filter_modified);
		int maxIndex=l_filter_modified.indexOf(maxValue);
		return l_filter1.get(maxIndex).split("\\.")[2];
	}
	
	public static void getProps() {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println();
	}

    public static ArrayList<String> modifyTemperature(List<String> a) {
		return (ArrayList<String>) IntStream.range(0, a.size())
				.mapToObj(i -> String.join(",", Arrays.copyOfRange(a.get(i).split(","), 0, a.get(i).split(",").length - 1)) + "," + modifyTemperatureString(a.get(i).split(",")[a.get(i).split(",").length - 1]))
				.collect(Collectors.toList());
	}

	public static String modifyTemperatureString(String t) {
		double d = Double.parseDouble(t) / 10;
		return Double.toString(d);

	}

	public static void printFile(File file) throws IOException {
        if (file == null) return;
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

	public static void runScriptLocally(String command) {

		try {

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setPty(true);
			((ChannelExec) channel).setCommand(command);

			InputStream in1 = channel.getInputStream();
			OutputStream out1 = channel.getOutputStream();
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
			out1.write((password + "\n").getBytes());
			out1.flush();
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			OutputStream out = channel.getOutputStream();
			channel.setOutputStream(System.out, true);
			channel.setExtOutputStream(System.err, true);

			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					quietSleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void runScriptRemotely(String user, String host, int port,String password,String command) {

		try {

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setPty(true);
			((ChannelExec) channel).setCommand(command);

			InputStream in1 = channel.getInputStream();
			OutputStream out1 = channel.getOutputStream();
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
			out1.write((password + "\n").getBytes());
			out1.flush();
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			OutputStream out = channel.getOutputStream();
			
			channel.setOutputStream(System.out, true);
			channel.setExtOutputStream(System.err, true);


			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					quietSleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void runScriptRemotelyOnKobold(String user, String password,String command) {
		try {
			runScriptRemotely(user,  "10.0.0.5",  port, password, command);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void runScriptRemotelyOnTurtle(String command) {
		try {
			runScriptRemotely(user,  host,  port, password, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void callScriptSudo(String password, String shellScriptPath) throws IOException {
	    String[] cmd = {"/bin/bash","-c","echo "+password+"| sudo -S "+shellScriptPath};
	    Process pb = Runtime.getRuntime().exec(cmd);
	    String line;
	    BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
	    while ((line = input.readLine()) != null) {
	        System.out.println(line);
	    }
	    input.close();
	} 
	
	public static void copyLocalFiletoRemoteLocation(String user, String host, int port,String password,String localPath, String remotePath, String fileName) throws IOException, InterruptedException, JSchException {
		Session session = createSession(user, host, port, password);
		copyLocalToRemote(session, localPath, remotePath, fileName);
	}
	
	public static void copyLocalFiletoRemoteLocation(String localPath, String remotePath, String fileName) throws IOException, InterruptedException, JSchException {
		Session session = createSession(user, host, port, password);
		copyLocalToRemote(session, localPath, remotePath, fileName);
	}
	
	public static void copyLocalToRemote(Session session, String from, String to, String fileName) throws JSchException, IOException {
		boolean ptimestamp = true;
		from = from + File.separator + fileName;
		// exec 'scp -t rfile' remotely
		String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + to;
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);
		// get I/O streams for remote scp
		OutputStream out = channel.getOutputStream();
		InputStream in = channel.getInputStream();

		channel.connect();

		if (checkAck(in) != 0) {
			System.exit(0);
		}

		File _lfile = new File(from);

		if (ptimestamp) {
			command = "T" + (_lfile.lastModified() / 1000) + " 0";
			// The access time should be sent here,
			// but it is not accessible with JavaAPI ;-<
			command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) != 0) {
				System.exit(0);
			}
		}

		// send "C0644 filesize filename", where filename should not include '/'
		long filesize = _lfile.length();
		command = "C0644 " + filesize + " ";
		if (from.lastIndexOf('/') > 0) {
			command += from.substring(from.lastIndexOf('/') + 1);
		} else {
			command += from;
		}

		command += "\n";
		out.write(command.getBytes());
		out.flush();

		if (checkAck(in) != 0) {
			System.exit(0);
		}

		// send a content of lfile
		FileInputStream fis = new FileInputStream(from);
		byte[] buf = new byte[1024];
		while (true) {
			int len = fis.read(buf, 0, buf.length);
			if (len <= 0)
				break;
			out.write(buf, 0, len); // out.flush();
		}

		// send '\0'
		buf[0] = 0;
		out.write(buf, 0, 1);
		out.flush();

		if (checkAck(in) != 0) {
			System.exit(0);
		}
		out.close();

		try {
			if (fis != null)
				fis.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		channel.disconnect();
		session.disconnect();
	}
     
	/**
	 * Copies a file from turtle home to local home using the script 'copyFileFromTurtleHomeToLocalHome.sh'
	 * @param turtleFilename
	 * @param localFilename
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void copyFileFromTurtleHomeToLocalHome(String turtleFilename,String localFilename) throws IOException, InterruptedException {
		String localScriptFile=copyScriptFileToLocalHome("copyFileFromTurtleHomeToLocalHome.sh");
		executeProcess(localScriptFile,turtleFilename,localFilename);
	}
	
	public static void copyFileFromTurtleToLocal(String turtleFilename,String localFilename) throws IOException, InterruptedException {
		String localScriptFile=copyScriptFileToLocalHome("copyFileFromTurtleToLocal.sh");
		executeProcess(localScriptFile,turtleFilename,localFilename);
	}

	public static void copyFileFromLocalHomeToTurtleHome(String localFilename,String turtleFilename) throws IOException, InterruptedException {
		String localScriptFile=copyScriptFileToLocalHome("copyFileFromLocalHomeToTurtleHome.sh");
		executeProcess(localScriptFile,localFilename,turtleFilename);
	}

	/**
	 * TODO create a System class for these system functions
	 * Executes a program or shell script.
	 * @param executableFile
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void executeProcess(String... executableFile)	 {
		ProcessBuilder pb = new ProcessBuilder(executableFile);
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (InterruptedException | IOException e) {
			LOG.error("can't execute process", e);
		}
	}
	public static List<String> executeProcess(boolean wait, boolean captureOutput, String... executableFile) {
		List<String> ret = new ArrayList<>();
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(executableFile);
			LOG.debug(Arrays.asList(executableFile).toString());
			ProcessBuilder pb = new ProcessBuilder(executableFile);
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			if (captureOutput) {
				String line;
				while ((line = reader.readLine()) != null) {
					ret.add(line);
				} 
			}
			if (wait) {
				process.waitFor();
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e.getMessage());
		}
		LOG.debug(Arrays.asList(ret).toString());
		return ret;
	}
	/**
	 * Assigns the permissions to a file or directory tree.
	 * @param filename
	 * @param permissions
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void setSystemFilePermissons(String filename, String permissions) throws IOException, InterruptedException {
		executeProcess("/bin/chmod", permissions, "-R", filename);
	}

	/**
	 * Move the script file from the resources to the Home directory.
	 * The script is then given execute permissions.
	 * @param scriptFile
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static String copyScriptFileToLocalHome(String scriptFile) throws IOException, InterruptedException {
		String localScriptFile = getLocalHome()+ scriptFile ;
		File f2=new File(localScriptFile);
		//if (!f2.exists()) {
			String resourceFileLocation = getFilepathFromResources(scriptFile);
			File f1=new File(resourceFileLocation) ;
			FileHelper.copyFile(f1,f2);
			setSystemFilePermissons(localScriptFile, "777");
		//}
		return localScriptFile;
	}
	
	public static void editConfigurationJson(String scriptFile, String parameterName,String enabledStatus,String pathToConfigFile) {
		String command = "sudo sh " + scriptFile + " " + parameterName + " " + enabledStatus + " "+ pathToConfigFile;
		runScriptRemotelyOnTurtle(command);
	}

	public static void restartTomcat() throws IOException, InterruptedException, JSchException {
		String scriptFile ="/home/powin/restartTomcat.sh";
		deleteExistingFile(scriptFile);
		createTomcatRestartFile( scriptFile);
		//Copy script app_update.sh to turtle home
		String remote = "/home/powin/";
		String local = "/home/powin/";
		String fileName = "restartTomcat.sh";
		String user="powin";
		String host=PowinProperty.TURTLEHOST.toString();
		int port=22;
		String password="powin";	
		copyLocalFiletoRemoteLocation(user, host, port,password,local, remote, fileName);	
		//Run script remotely
		String command = "sudo sh "+ fileName;
		runScriptRemotelyOnTurtle(command);	
	}
	
	public static void createTomcatRestartFile(String scriptFile) {
		File mFile = new File(scriptFile);
		try (FileWriter mWriter = new FileWriter(mFile, true)) {
			//TODO:Use StringBuilder to make more readable
            mWriter.write(
            		
            		"echo \"Restarting Tomcat...\"\n" + 
            		"sudo service tomcat8 stop \n" + 
            		"sleep 2\n"+
					"sudo rm -rf /var/log/tomcat8/catalina.out /var/log/tomcat8/catalina_old1.out\n" +              		
					"sudo service tomcat8 restart \n" +            		
            		"while true ; do \n"+
            		  "echo \"Waiting for tomcat to restart...\"\n"+
            		  "result=$(grep -i \"Catalina.start Server startup in\" /var/log/tomcat8/catalina.out) # -n shows line number \n"+
            		  "if [ \"$result\" ] ; then \n"+
            		    "echo \"COMPLETE!\" \n"+
            		    "echo \"Result found is $result\"\n"+
            		    "break \n"+
            		  "fi \n"+
            		  "sleep 2\n"+
            		"done"+
            		"");
            mWriter.flush();
            System.out.print("Writing successful!");
        } catch (IOException e) {
        	System.out.print("Writing failed" + e.getLocalizedMessage());			
		}	
	}
	
	public static String getArchivaInfo(String app,String majorVersion, String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException {
		String maxVersion="";
		if (PowinProperty.ARCHIVA_USER.toString().isEmpty() || PowinProperty.ARCHIVA_PASSWORD.toString().isEmpty()) {
			LOG.error("Enter your archiva username and password into default.properties.");
			throw new KeyManagementException();
		}
		String archivaDataFile = getLocalHome() +"archivaData.xml";
		String cmd = "wget  --no-check-certificate --user="+PowinProperty.ARCHIVA_USER+" --password "+PowinProperty.ARCHIVA_PASSWORD+" -O /"+archivaDataFile+" https://archiva.powindev.com/repository/internal/com/powin/"+app+"/maven-metadata.xml";
		try {
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();
		int exitVal = process.waitFor();
		if (exitVal == 0) {
			//parse file 
			String jsonText=convertXmlToJson(archivaDataFile).toString();
			JSONParser parser = new JSONParser();
			JSONObject json = null;
			try {
				json = (JSONObject) parser.parse(jsonText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JsonParserHelper jph = new JsonParserHelper(jsonText);
			List<String> results = new ArrayList<>();
			if(majorVersion.toUpperCase().equals("LATEST")) {
				results=JsonParserHelper.getFieldJSONObject(json, "metadata|versioning|latest", "", results);
				maxVersion=results.get(0);
			}
			else {			
				results=JsonParserHelper.getFieldJSONObject(json, "metadata|versioning|versions|version", "", results);
				List<String> l_filter= new ArrayList<>();
				//CollectionUtils.filter(l, o -> (((String) o).split("."))[1].equals(Integer.toString(3)));
				results.replaceAll(s -> s.concat(".0.0"));
				l_filter = (ArrayList<String>) results.stream()
					      .filter(o -> o.split("\\.")[0].equals(majorVersion))
					      .filter(o -> o.split("\\.")[1].equals(minorVersion) )		      
					      .collect(Collectors.toList());
				maxVersion= getMaxVersionFromVersionList(l_filter);	
			}
		} 
		else {
				//abnormal...
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return maxVersion;
	}

	public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = CommonHelper.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(String.format("file %s is not found!", fileName));
        } else {
            return new File(resource.getFile());
        }
    }
	
	private static String getFilepathFromResources(String fileName) {
        ClassLoader classLoader = CommonHelper.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(String.format("file %s is not found!", fileName));
        } else {
            return resource.getFile();
        }
    }
	
	public static String getFileContentDifference(File f1, File f2) throws FileNotFoundException {
		//Assumption: One file's content includes all of the other's content but has some extra content
		File smallerFile=f1.length()>=f2.length()?f2:f1;
		File biggerFile=f1.length()>=f2.length()?f1:f2;
		Scanner input1 = new Scanner(smallerFile);// read first file
		Scanner input2 = new Scanner(biggerFile);// read second file
		String first = "";
		String second = "";
		String diff = "";
		
		while (input2.hasNextLine()) {
			if (input1.hasNextLine()) {
				first = input1.nextLine();
				second = input2.nextLine();
			} else {
				second = input2.nextLine()+"\n";
				diff += second;
			}
		}
		input1.close();
		input2.close();
		return diff;
	}
	
	// TODO Replace this with readFileAsString?
	public static String getFileContents(File f1) throws FileNotFoundException {
		//Returns file contents with each line having \n except the last line
		Scanner input = new Scanner(f1);// read first file
		String first = "";
		while (input.hasNextLine()) {
				first += input.nextLine()+"\n";
		}
		input.close();
		return StringUtils.chomp(first);
	}
	
	public static boolean compareTwoFiles(String file1Path, String file2Path)
            throws IOException {

    File file1 = new File(file1Path);
    File file2 = new File(file2Path);

    BufferedReader br1 = new BufferedReader(new FileReader(file1));
    BufferedReader br2 = new BufferedReader(new FileReader(file2));

    String thisLine = null;
    String thatLine = null;

    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();

    while ((thisLine = br1.readLine()) != null) {
        list1.add(thisLine);
    }
    while ((thatLine = br2.readLine()) != null) {
        list2.add(thatLine);
    }

    br1.close();
    br2.close();

    return list1.equals(list2);
}
	
	public static void getTurtleLog(String file) throws IOException, InterruptedException {
		//Runs this command in turtle (10.0.0.3)
		String command = "sudo scp /var/log/tomcat8/turtle.log /home/powin/";
		runScriptRemotelyOnTurtle(command);
		Thread.sleep(10000);
		String command2 = "sudo chmod 777 /home/powin/turtle.log";
		runScriptRemotelyOnTurtle(command2);
		Thread.sleep(3000);
		copyFileFromTurtleHomeToLocalHome("turtle.log",file) ;
	}
	

	//CodeReview: Both these methods around here are doing exactly same thing and differ only in the sh file
	//Combine the 2 methods and parameterize the sh file name.
	 //Resolved: 
	/**
	 * Move a shell script from resources to the home directory and execute it.
	 * 
	 * @param scriptFilename 
	 */
	public static void executeScriptFromResources(String scriptFilename)  {
		try {
			String localScriptFile=copyScriptFileToLocalHome(scriptFilename);
			executeProcess(localScriptFile);
			waitForFile(localScriptFile, 10);
		} catch (IOException | InterruptedException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException("Problem executing shell script.");
		}
	}
	
	/**
	 * Wait for file to appear on the file system.
	 * @param localScriptFile
	 * @param seconds - The number of seconds to wait for the file.
	 * @throws InterruptedException
	 */
	public static void waitForFile(String localScriptFile, int seconds) {
		File f = new File(localScriptFile);
		int i = 0;
		while(!f.exists() && i++ < seconds) {
			quietSleep(1000);
		}
	}

	public static void quietSleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}
	
	public static void sleep(int ms) {
		LOG.info("Delay for {} seconds", ms/1000);
		quietSleep(ms);
	}

	
	public static int getClosestDate(String date1, String date2, String date3) throws ParseException, java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
	    Date d1 = format.parse(date1);
	    Date d2 = format.parse(date2);
	    Date d3 = format.parse(date3);
	    
	    long lower_limit_diffInMillies = Math.abs(d3.getTime() - d1.getTime());
	    long upper_limitdiffInMillies = Math.abs(d2.getTime() - d3.getTime());
	    
	    if (lower_limit_diffInMillies < upper_limitdiffInMillies) {
	    	System.out.println("Closest to lower limit");
	    	return -1;
	    }else if(lower_limit_diffInMillies > upper_limitdiffInMillies) {
	    	System.out.println("Closest to upper limit");
	    	return 1;
	    }else {
	    	System.out.println("Equidistant to both limits");
	    	return 0;
	    }
	    
	}
	
	// TODO: Do we need to populate the error list from a property file?
	/**
	 * Waits for the Contactor open alarm to be removed from the list for the given timeout.
	 * @param timeout
	 * @param arrayIndex
	 * @param stringIndex
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ModbusException - If one of the error conditions specified are present.
	 */
	public static boolean checkForContactorsClosed(int timeout, int arrayIndex, int stringIndex){
		return checkForNotification(false, NotificationCodes.CONTACTOR_OPEN_WARNING, Arrays.asList(NotificationCodes.STRING_OUT_OF_ROTATION_WARNING), timeout, arrayIndex, stringIndex);
	}
	
	/**
	 * Waits for the Contactors open alarm to appear from the list for the given timeout.
	 * @param timeout
	 * @param arrayIndex
	 * @param stringIndex
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ModbusException - If one of the error conditions specified are present.
	 */
	public static boolean checkForContactorsOpen(int timeout, int arrayIndex, int stringIndex)
			throws IOException, InterruptedException, ModbusException {
		//return checkForNotification(true, "2534", timeout, arrayIndex, stringIndex);
		return checkForNotification(false, NotificationCodes.CONTACTOR_OPEN_WARNING, Arrays.asList(NotificationCodes.STRING_OUT_OF_ROTATION_WARNING), timeout, arrayIndex, stringIndex);
	}
	
	/**
	 * Look for specified notifications
	 * @param notificationExpected - true: wait for notification to appear, false: wait for notification to clear  
	 * @param expectedCode - code we want to look for
	 * @param errorCodes - If any of these codes are present abort.
	 * @param timeout
	 * @param arrayIndex
	 * @param stringIndex
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ModbusException 
	 */
	public static boolean checkForNotification(boolean notificationExpected, NotificationCodes expectedCode, List<NotificationCodes> errorCodes, int timeout, int arrayIndex, int stringIndex) {
		boolean status = false;
		boolean timeoutExpired = false;
		long endTime = System.currentTimeMillis() + 1000 * timeout;
		while (!status && !timeoutExpired) {
			boolean isNotification = didNotificationAppear(expectedCode, errorCodes, arrayIndex, stringIndex);
			status = notificationExpected ? isNotification
					: !isNotification;
			if (!status) {
				sleep(1000);
				timeoutExpired = System.currentTimeMillis() > endTime;
			}
		}
		return status;
	}

	public static boolean didNotificationAppear(String notificationCode, int arrayIndex, int stringIndex) throws IOException {
		List<String> notificationList = getNotifications(arrayIndex, stringIndex);
		return notificationList.contains(notificationCode);
	}

	/**
	 *  
	 * @param expected
	 * @param errorCodes
	 * @param arrayIndex
	 * @param stringIndex
	 * @return
	 * @throws IOException
	 * @throws ModbusException
	 */
	public static boolean didNotificationAppear(NotificationCodes expected, List<NotificationCodes> errorCodes, int arrayIndex, int stringIndex) {
		List<String> notificationList = getNotifications(arrayIndex, stringIndex);
		List<String> errorCodeStrings = errorCodes.stream().map(e -> e.toString()).collect(Collectors.toList());
		boolean ret = notificationList.contains(expected);
        // is error code present
//		notificationList.retainAll(errorCodeStrings);
//		if (!notificationList.isEmpty()) {
//			throw new ModbusException(String.format("%s was detected abort run.", errorCodeStrings.get(0)));
//		}
		return ret;
	}

	public static List<String> getNotifications(int arrayIndex, int stringIndex) {
		Notifications notifications = new Notifications(
				Integer.toString(arrayIndex) + "," + Integer.toString(stringIndex));
		List<String> notificationList = notifications.getNotificationsInfo(false);
		return notificationList;
	}	
	
	public static List<String> sortListBySubstring(List<String> rawList,boolean ascendingOrder,int zeroBasedIndex,String substringType) {
		if(substringType.toUpperCase().contains("INT")) {
			if(ascendingOrder)
				rawList.sort(Comparator
				    .comparing(s -> Integer.parseInt(s.split(",")[zeroBasedIndex])));
			else
				rawList.sort(Comparator
				    .comparing(s -> Integer.parseInt(s.split(",")[zeroBasedIndex]), Comparator.reverseOrder()));
	
		}
		else if(substringType.toUpperCase().contains("DOUBLE")) {
			if(ascendingOrder)
				rawList.sort(Comparator
				    .comparing(s -> Double.parseDouble(s.split(",")[zeroBasedIndex])));
			else
				rawList.sort(Comparator
				    .comparing(s -> Double.parseDouble(s.split(",")[zeroBasedIndex]), Comparator.reverseOrder()));
	
		}
		return rawList;
		
	}

	public static ArrayList<String> getTimeDiff(Date time, Date time2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String getKoboldVersion(String koblodLocation) {
		String mVersion = "unknown";
		HttpGet httpGet = new HttpGet(koblodLocation);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2500).setConnectionRequestTimeout(2500)
				.setSocketTimeout(2500).build();
		httpGet.setConfig(requestConfig);
		try {
			HttpClient mHttpClient = HttpHelper.buildHttpClient(true);
			HttpResponse response = mHttpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "UTF-8");
				int beginIndex = result.indexOf("Version") + 8;
				int endIndex = result.length();
				if (beginIndex != -1 && endIndex != -1 && endIndex > beginIndex) {
					mVersion = result.substring(beginIndex, endIndex);
				}
				if (mVersion.length() > 30) {
					mVersion = "unknown";
				}
			}

		} catch (Exception e) {
			mVersion = "unknown";
		}
		return mVersion.replaceAll("\r|\n", "");
	}

	public static String getTurtleVersion(String turtleLocation) {
		String mVersion = "unknown";
		HttpGet httpGet = new HttpGet(turtleLocation);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2500).setConnectionRequestTimeout(2500)
				.setSocketTimeout(2500).build();
		httpGet.setConfig(requestConfig);
		try {
			HttpClient mHttpClient = HttpHelper.buildHttpClient(true);
			HttpResponse response = mHttpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "UTF-8");
				int beginIndex = result.indexOf("Version") + 8;
				int endIndex = result.indexOf("\n\r");
				if (beginIndex != -1 && endIndex != -1 && endIndex > beginIndex) {
					mVersion = result.substring(beginIndex, endIndex);
				}
				if (mVersion.length() > 30) {
					mVersion = "unknown";
				}
			}

		} catch (Exception e) {
			mVersion = "unknown";
		}
		return mVersion.replaceAll("\r|\n", "");
	}
	
	public static void createDeviceConfigurtionFiles(String deviceFolderLocation,int arrayCount,int pcsMaxPower, int pcsMaxCurrent){
		//PhoenixDcBattery: device-10-PhoenixDcBattery.json
		DevicePhoenixDcBatteryConfigFileCreator PhoenixDcBattery = new DevicePhoenixDcBatteryConfigFileCreator();
		PhoenixDcBattery.createPhoenixDcBatteryConfigFiles(deviceFolderLocation, arrayCount,10000,true,10,10);
		//PcsSimulator : device-20-PcsSimulator.json
		DevicePcsSimulatorConfigFileCreator PcsSimulator = new DevicePcsSimulatorConfigFileCreator();
		PcsSimulator.createPcsSimulatorConfigFiles(deviceFolderLocation, arrayCount,"GRID_FOLLOWING",
				false,pcsMaxPower,pcsMaxPower,-pcsMaxPower,-pcsMaxPower,pcsMaxCurrent,pcsMaxPower,pcsMaxPower,true,20,20);
		//AcBattery: device-50-AcBattery.json
		DeviceAcBatteryConfigFileCreator AcBattery = new DeviceAcBatteryConfigFileCreator();
		AcBattery.createAcBatteryConfigFiles(deviceFolderLocation, arrayCount,true,50,50);
		//AcBatteryBlock: device-60-AcBatteryBlock.json
		DeviceAcBatteryBlockConfigFileCreator AcBatteryBlock = new DeviceAcBatteryBlockConfigFileCreator();
		AcBatteryBlock.createAcBatteryBlockConfigFiles(deviceFolderLocation, arrayCount,true,true,60,60);
	}
	
	public static void createAcDevicesConfigurtionFiles(String deviceFolderLocation,int arrayCount,int pcsMaxPower, int pcsMaxCurrent){
		//PhoenixDcBattery: device-10-PhoenixDcBattery.json
		DevicePhoenixDcBatteryConfigFileCreator PhoenixDcBattery = new DevicePhoenixDcBatteryConfigFileCreator();
		PhoenixDcBattery.createPhoenixDcBatteryConfigFiles(deviceFolderLocation, arrayCount,10000,true,10,10);
		//PcsSimulator : device-20-PcsSimulator.json
		DevicePcsSimulatorConfigFileCreator PcsSimulator = new DevicePcsSimulatorConfigFileCreator();
		PcsSimulator.createPcsSimulatorConfigFiles(deviceFolderLocation, arrayCount,"GRID_FOLLOWING",
				false,pcsMaxPower,pcsMaxPower,-pcsMaxPower,-pcsMaxPower,pcsMaxCurrent,pcsMaxPower,pcsMaxPower,true,20,20);
		//AcBattery: device-50-AcBattery.json
		DeviceAcBatteryConfigFileCreator AcBattery = new DeviceAcBatteryConfigFileCreator();
		AcBattery.createAcBatteryConfigFiles(deviceFolderLocation, arrayCount,true,50,50);
		//AcBatteryBlock: device-60-AcBatteryBlock.json
		DeviceAcBatteryBlockConfigFileCreator AcBatteryBlock = new DeviceAcBatteryBlockConfigFileCreator();
		AcBatteryBlock.createAcBatteryBlockConfigFiles(deviceFolderLocation, arrayCount,true,true,60,60);
	}
	
	public static void upDateDeviceFolder(int arrayCount,int pcsMaxPower, int pcsMaxCurrent) throws IOException, InterruptedException, JSchException {
		String randomFolderName="foo"+Math.round(100000*Math.random());
		String command = "sudo mkdir ~/"+randomFolderName +" && sudo chmod -R 777 ~/"+randomFolderName;
		callScriptSudo("powin",command);
		createDeviceConfigurtionFiles("/home/powin/"+randomFolderName+"/",arrayCount,pcsMaxPower, pcsMaxCurrent);
		runScriptRemotelyOnTurtle(command);
		Thread.sleep(2000);
		copyFileFromLocalHomeToTurtleHome(randomFolderName+"/device-*.json" ,randomFolderName+"/");	
		String deleteDeviceFolderFilesCommand = "sudo cp -r /etc/powin/device /etc/powin/deviceBackup && sleep 5 && sudo rm /etc/powin/device/*.json";
		runScriptRemotelyOnTurtle(deleteDeviceFolderFilesCommand);
		String command1 = "sudo scp /home/powin/"+randomFolderName+"/device-*.json /etc/powin/device/";
		runScriptRemotelyOnTurtle(command1);
		Thread.sleep(2000);	
	}
	
	public static String createDynamicFolder(String folderNamePrefix,String folderLocation) throws IOException {
		String randomFolderName	=	folderNamePrefix + Math.round(100000*Math.random());
		String newFolderPath 	= 	folderLocation+randomFolderName ;
		String command 			= 	"sudo mkdir "+newFolderPath +" && sudo chmod -R 777 "+newFolderPath;
		callScriptSudo("powin",command);
		return newFolderPath +"/";
	}
	
	public static void createConfigurationJson(String stationName,StackType stackType, int stringCount, int arrayCount) throws IOException, InterruptedException {
		String randomFolderName="foo"+Math.round(100000*Math.random());
		String command = "sudo mkdir ~/"+randomFolderName +" && sudo chmod -R 777 ~/"+randomFolderName;
		callScriptSudo("powin",command);
		ConfigurationFileCreator mCreator = new ConfigurationFileCreator();
		mCreator.CreateConfigurationFile("/home/powin/"+randomFolderName+"/",stationName,stackType,  stringCount,  arrayCount);
		runScriptRemotelyOnTurtle(command);
		Thread.sleep(2000);
		copyFileFromLocalHomeToTurtleHome(randomFolderName+"/configuration.json" ,randomFolderName+"/");	
		String deleteExistingConfigFileCommand = "sudo cp /etc/powin/configuration.json /etc/powin/configurationBackup.json && sleep 5 && sudo rm /etc/powin/configuration.json";
		runScriptRemotelyOnTurtle(deleteExistingConfigFileCommand);
		String command1 = "sudo scp /home/powin/"+randomFolderName+"/configuration.json /etc/powin/";
		runScriptRemotelyOnTurtle(command1);
		Thread.sleep(2000);	
	}
	
	public static void setupSystem(String stationCode,StackType stackType,int arrayCount, int stringCount, int pcsNameplatePower , int pcsNameplateCurrent) throws IOException, InterruptedException, JSchException {
		if(stationCode=="")
			stationCode=SystemInfo.getStationCode();
		createConfigurationJson(stationCode,stackType, arrayCount,stringCount);
		restartTomcat();
		upDateDeviceFolder(arrayCount, pcsNameplatePower, pcsNameplateCurrent);
		restartTomcat();
	}

	
	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, JSchException, XMLStreamException, ParseException, java.text.ParseException {
		CommonHelper mCommonUtils = new CommonHelper();
//		mCommonUtils.getTurtleLog("goo.txt") ;
//		runScriptRemotely(command);
//		System.out.println("coblynau:"+mCommonUtils.getArchivaInfo("coblynau","2","19"));
//		System.out.println("knocker:"+mCommonUtils.getArchivaInfo("knocker","2","19"));
//		System.out.println("kobold:"+mCommonUtils.getArchivaInfo("kobold","2","19"));
//		System.out.println("primrose:"+mCommonUtils.getArchivaInfo("primrose","2","19"));
//		System.out.println("turtle:"+mCommonUtils.getArchivaInfo("turtle","2","23"));
//		System.out.println(mCommonUtils.getDefaultProperty("report_base_url"));
//		System.out.println(mCommonUtils.getDefaultProperty("default.properties","turtle_url"));
//		System.out.println(PowinProperty.TURTLE_URL.toString());
//		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//		System.out.println(rootPath);
//		mCommonUtils.getTurtleLog("foo.log");
//		mCommonUtils.readRemote();   
		
//		File f1 = mCommonUtils.getFileFromResources("moveTurtleLogToLocalHome.sh");		
//		String fileContents = mCommonUtils.getFileContents(f1);
//		String fileLocation= mCommonUtils.getLocalHome();
//		File f2=mCommonUtils.createFile(fileLocation, "f0902.txt", fileContents);
//		mCommonUtils.printFile(f2);
		
//		String resourceFileLocation = mCommonUtils.getFilepathFromResources("moveTurtleLogToLocalHome.sh");
//		
//		System.out.println(resourceFileLocation);
//		File f1=new File(resourceFileLocation) ;
//		File f2=new File("/home/powin/testcript.sh");
//		
//		mCommonUtils.copyFile(f1,f2);
//		mCommonUtils.printFile(f2);
		
//		mCommonUtils.copyFileFromTurtleHomeToLocalHome("soc.txt","soclocalFilename1.txt");
//		mCommonUtils.copyFileFromTurtleToLocal("/etc/powin/configuration.json","/home/powin/foo123config.json");

//		mCommonUtils.getSocDataFromTurtleLog("soc1.txt") ;
//		File f1 = new File("/home/powin/turtleSocData.txt");
//		File f2 = new File("/home/powin/stringSocData.txt");		
//		File f3 = mCommonUtils.stitchFiles(f1, f2);
//		File f3 = mCommonUtils.consolidateFile(f1);		
//		CommonHelper.printFile(f3);
		
		
//			
//		String line1="2020-09-03 20:18:35.758,,0.6072058823529411";
//		String line2="2020-09-03 20:18:35.758,0.61";
//		System.out.println(mCommonUtils.mergeLines(line1, line2));
		
//		String date1="2020-09-03 20:18:37.000";
//		String date3="2020-09-03 20:18:41.000";	
//		String date2="2020-09-03 20:18:45.000";	
//		System.out.println(compareDates(date1, date2));
//		System.out.println(getClosestDate(date1, date2,date3));
		

		//mCommonUtils.callScriptSudo("powin", "/home/powin/copyFileFromTurtleHomeToLocalHome.sh"); 
//		mCommonUtils.copyFileFromTurtleHomeToLocalHome("turtle.log","ok3.log") ;
//		mCommonUtils.getTurtleLog("turtleX.log");
//		mCommonUtils.getTurtleLog("turtleX.log");
//		String s="2.23.8-SNAPSHOT";
//		System.out.println(s.replaceAll("[^\\d.]", ""));
//		ArrayList<String> l_filter= new ArrayList<>();
//		l_filter.add("2,0,3");
//		l_filter.add("1,2,4");
//		l_filter.add("3,1,2");
//		String v=mCommonUtils.getMaxVersionFromVersionList(l_filter);
//		System.out.println(v);
//		System.out.println(convertArrayListToString(
//				mCommonUtils.sortListBySubstring(l_filter,true,1,"int")
//				));

//	}
	
//	System.out.println(compareIntegers(1,0,1,.01));
//	mCommonUtils.restartTomcat();

		
//		mCommonUtils.executeScriptFromResources("getConfigurationJSON.sh");
		
//		String path1="/home/powin/QAPERF440-1/SetSafetyAndNotificationConfiguration/1-10 - SetSafetyAndNotificationConfiguration.csv";
//		String path2="/home/powin/QAPERF440-1/SetSafetyAndNotificationConfiguration/1-11 - SetSafetyAndNotificationConfiguration.csv";
//		File f1 = new File (path1);
//		File f2 = new File (path2);
//		System.out.println(new CommonHelper().compareTwoFiles(path1,path2));
//		System.out.println(new CommonHelper().getFileContentDifference(f2,f1));
		
//		mCommonUtils.createDynamicFolder("testFolder","/home/powin/");
//		System.out.println(mCommonUtils.stringSuffix("Hello", "/"));
//		mCommonUtils.deleteFolderContents("/home/powin/configTest20700/");
//		mCommonUtils.deleteFolderContents2("/home/powin/configTest18233/");
//		mCommonUtils.setupSystem("",StackType.STACK_140_GEN2, 4,4,450,600);
		mCommonUtils.copyFileFromTurtleToLocal("/etc/powin/configuration.json", "");
		

	}

	public static  CellVoltageLimits testLimit(int i) {
		CellVoltageLimits cvl = new CellVoltageLimits();
		if (i==1)
			return  cvl.get140();
		else
			return cvl.get140();
		
		//return cl;
	}

}