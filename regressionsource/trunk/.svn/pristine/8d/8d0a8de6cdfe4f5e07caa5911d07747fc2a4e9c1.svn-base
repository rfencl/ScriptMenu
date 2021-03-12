package com.powin.modbusfiles.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.xml.stream.XMLStreamException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.jcraft.jsch.JSchException;

public class AppUpdate {
	private final static Logger LOG = LogManager.getLogger(AppUpdate.class.getName());
	//local info
	private static String cCloudAppsUpdateDirectory = "/home/powin/";
	private static String cCloudAppsUpdateFile = "cloudUpdate.sh";
	private static String cTurtleUpdateDirectory = "/home/powin/";
	private static String cTurtleUpdateFile = "turtleUpdate.sh";
	//kobold server (usually same as local machine
	private static String cRemoteCloudAppsUpdateDirectory = "/home/powin/";
	private static String cKoboldServerUser = "powin";
	private static String cKoboldServerPassword = "powin";
	//turtle location
	private static String cRemoteTurtleUpdateDirectory = "/home/powin/";
	
	
	
	private static StringBuilder coblynauVersion = new StringBuilder();
	private static StringBuilder knockerVersion = new StringBuilder();
	private static StringBuilder koboldVersion = new StringBuilder();
	private static StringBuilder primroseVersion = new StringBuilder();
	private static StringBuilder turtleVersion = new StringBuilder();
	
	
	public AppUpdate(String koboldServerUser,String koboldServerPassword) {
		cKoboldServerUser=koboldServerUser;
		cKoboldServerPassword=koboldServerPassword;
		init();
	}
	public AppUpdate() {
		init();
	}
	
	private void init() {
		cCloudAppsUpdateDirectory = CommonHelper.getLocalHome();
		cTurtleUpdateDirectory = CommonHelper.getLocalHome();
		cRemoteCloudAppsUpdateDirectory="/home/"+cKoboldServerUser;
	}
	public void clearVersions() {
		coblynauVersion.delete(0, coblynauVersion.length());
		turtleVersion.delete(0, turtleVersion.length());
		knockerVersion.delete(0, knockerVersion.length());
		koboldVersion.delete(0, koboldVersion.length());
		primroseVersion.delete(0, primroseVersion.length());
	}
	public void copyCloudAppsUpdateFileToRemote() throws IOException, InterruptedException, JSchException {
		String remote = cRemoteCloudAppsUpdateDirectory;
		String local = cCloudAppsUpdateDirectory;
		String fileName = cCloudAppsUpdateFile;
		String user=cKoboldServerUser;
		String host="10.0.0.5";
		int port=22;
		String password=cKoboldServerPassword;
		
		CommonHelper.copyLocalFiletoRemoteLocation(user, host, port,password,local, remote, fileName);			
	}
	
	public void copyTurtleAppUpdateFileToRemote() throws IOException, InterruptedException, JSchException {
		String remote = cRemoteTurtleUpdateDirectory;
		String local = cTurtleUpdateDirectory;
		String fileName = cTurtleUpdateFile;
		String user="powin";
		String host=PowinProperty.TURTLEHOST.toString();
		int port=22;
		String password="powin";
		
		CommonHelper.copyLocalFiletoRemoteLocation(user, host, port,password,local, remote, fileName);			
	}
	
	public void createCloudAppUpdateFile(String majorVersion ,String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException {
		getCloudAppVersions( majorVersion , minorVersion );
		File mFile = new File(cCloudAppsUpdateDirectory+cCloudAppsUpdateFile);
		String archivaUser = PowinProperty.ARCHIVA_USER.toString();
		String archivaPassword = PowinProperty.ARCHIVA_PASSWORD.toString();
		if (archivaUser.isEmpty() || archivaPassword.isEmpty()) {
			LOG.error("Enter your archiva username and password into default.properties.");
			return;
		}
		try (FileWriter mWriter = new FileWriter(mFile, true)) {
			//TODO:Use StringBuilder to make more readable
			//TO DO: create a template in default.properties and edit at runtime
            mWriter.write(
            		"ARCHIVA_LOGON='"+archivaUser+"'\n" +
            		"ARCHIVA_PASSWORD='"+archivaPassword+"'\n" +
            		"KOBOLD_VERSION='" + koboldVersion.toString() + "'\n" + 
            		"COB_VERSION='" + coblynauVersion.toString() + "'\n" + 
            		"PRIMROSE_VERSION='" + primroseVersion.toString() + "'\n" + 
            		"KNOCKER_VERSION='" + knockerVersion.toString() + "'\n" + 
            		"echo 'Downloading 'kobold' '${KOBOLD_VERSION}\n" + 
            		"wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/kobold/${KOBOLD_VERSION}/kobold-${KOBOLD_VERSION}.war -O kobold-${KOBOLD_VERSION}.war\n" + 
            		"echo 'Downloading 'coblynau' '${COB_VERSION}\n" + 
            		"wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/coblynau/${COB_VERSION}/coblynau-${COB_VERSION}.war -O coblynau-${COB_VERSION}.war\n" + 
            		"echo 'Downloading 'primrose' '${PRIMROSE_VERSION}\n" + 
            		"wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/primrose/${PRIMROSE_VERSION}/primrose-${PRIMROSE_VERSION}.war -O primrose-${PRIMROSE_VERSION}.war\n" + 
            		"echo 'Downloading 'knocker' '${KNOCKER_VERSION}\n" + 
            		"wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/knocker/${KNOCKER_VERSION}/knocker-${KNOCKER_VERSION}.war -O knocker-${KNOCKER_VERSION}.war\n" + 
            		"echo \"Halting Tomcat\"\n" + 
            		"sudo service tomcat stop \n" + 
            		"sleep 15 \n" + 
            		"echo \"Moving apps into place,..\"\n" + 
            		"sudo rm -rf /opt/tomcat/webapps/kobold* && sudo mv kobold-${KOBOLD_VERSION}.war /opt/tomcat/webapps/kobold.war \n" + 
            		"sudo rm -rf /opt/tomcat/webapps/coblynau* && sudo mv coblynau-${COB_VERSION}.war /opt/tomcat/webapps/coblynau.war \n" + 
            		"sudo rm -rf /opt/tomcat/webapps/primrose* && sudo mv primrose-${PRIMROSE_VERSION}.war /opt/tomcat/webapps/primrose.war \n"+
            		"sudo rm -rf /opt/tomcat/webapps/knocker* && sudo mv knocker-${KNOCKER_VERSION}.war /opt/tomcat/webapps/knocker.war \n" + 
					"echo \"Restarting Tomcat...\"\n" + 
					"sudo rm -rf /opt/tomcat/logs/catalina.out /opt/tomcat/logs/catalina_old1.out\n" +              		
					"sudo service tomcat restart \n" +        		
            		"while true ; do \n"+
            		  "echo \"Waiting for tomcat to restart...\"\n"+
            		  "result=$(grep -i \"Catalina.start Server startup in\" /opt/tomcat/logs/catalina.out) # -n shows line number \n"+
            		  "if [ \"$result\" ] ; then \n"+
            		    "echo \"COMPLETE!\" \n"+
            		    "echo \"Result found is $result\"\n"+
            		    "break \n"+
            		  "fi \n"+
            		  "sleep 2\n"+
            		"done"+
        		"");
            mWriter.flush();
            System.out.print("Writing successfully!");
        } catch (IOException e) {
        	System.out.print("Writing failed" + e.getLocalizedMessage());				
		}	
	}
	
	public void createTurtleUpdateFile(String majorVersion ,String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException {
		getTurtleAppVersion( majorVersion , minorVersion );
		File mFile = new File(cTurtleUpdateDirectory+cTurtleUpdateFile);
		String archivaUser = PowinProperty.ARCHIVA_USER.toString();
		String archivaPassword = PowinProperty.ARCHIVA_PASSWORD.toString();
		if (archivaUser.isEmpty() || archivaPassword.isEmpty()) {
			LOG.error("Enter your archiva username and password into default.properties.");
			return;
		}
		try (FileWriter mWriter = new FileWriter(mFile, true)) {
			//TODO:Use StringBuilder to make more readable
            mWriter.write(
            		"ARCHIVA_LOGON='"+archivaUser+"'\n" +
                    "ARCHIVA_PASSWORD='"+archivaPassword+"'\n" +
            		"TURTLE_VERSION='" + turtleVersion.toString() + "'\n" + 
            		"echo 'Downloading 'turtle' '${TURTLE_VERSION}\n" + 
            		"wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/turtle/${TURTLE_VERSION}/turtle-${TURTLE_VERSION}.war -O turtle-${TURTLE_VERSION}.war\n" + 
            		"echo \"Halting Tomcat\"\n" + 
            		"sudo service tomcat8 stop \n" + 
            		"sleep 15 \n" + 
            		"echo \"Moving apps into place,..\"\n" + 
            		"sudo rm -rf /var/lib/tomcat8/webapps/turtle* && sudo mv turtle-${TURTLE_VERSION}.war /var/lib/tomcat8/webapps/turtle.war \n" + 
            		"sleep 5 \n" + 
            		"echo \"Restarting Tomcat...\"\n" + 
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
            System.out.print("Writing successfully!");
        } catch (IOException e) {
        	System.out.print("Writing failed" + e.getLocalizedMessage());			
		}	
	}
	
	
	
	public void getCloudAppVersions(String majorVersion ,String minorVersion ) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException {		
		String partialVersion = majorVersion+"."+minorVersion+".";
		coblynauVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("coblynau",majorVersion,minorVersion));
		knockerVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("knocker",majorVersion,minorVersion));
		koboldVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("kobold",majorVersion,minorVersion));
		primroseVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("primrose",majorVersion,minorVersion));
		turtleVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("turtle",majorVersion,minorVersion));
	}
	
	public static String getCoblynauVersion() {
		return coblynauVersion.toString();
	}

	public static String getKnockerVersion() {
		return knockerVersion.toString();
	}

	public static String getKoboldVersion() {
		return koboldVersion.toString();
	}

	public static String getPrimroseVersion() {
		return primroseVersion.toString();
	}
	
	public static String getTurtleVersion() {
		return turtleVersion.toString();
	}

	public void getTurtleAppVersion(String majorVersion ,String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException {		
		String partialVersion = majorVersion+"."+minorVersion+".";
		turtleVersion.append(partialVersion).append(CommonHelper.getArchivaInfo("turtle",majorVersion,minorVersion));
	}
	
	public void updateCloudApps(String majorVersion ,String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException, InterruptedException, JSchException {
		CommonHelper.deleteExistingFile(cCloudAppsUpdateDirectory+cCloudAppsUpdateFile);
		createCloudAppUpdateFile( majorVersion , minorVersion);
		//Copy script app_update.sh to turtle home
		copyCloudAppsUpdateFileToRemote();
		//Run script remotely
		String command = "sudo sh "+ cCloudAppsUpdateFile;
		CommonHelper.runScriptRemotelyOnKobold(cKoboldServerUser,cKoboldServerPassword,command);
	}

	public void updateTurtleApp(String majorVersion ,String minorVersion) throws KeyManagementException, NoSuchAlgorithmException, IOException, XMLStreamException, ParseException, InterruptedException, JSchException {
		CommonHelper.deleteExistingFile(cTurtleUpdateDirectory+cTurtleUpdateFile);
		createTurtleUpdateFile( majorVersion , minorVersion);
		//Copy script app_update.sh to turtle home
		copyTurtleAppUpdateFileToRemote();
		//Run script remotely
		String command = "sudo sh "+ cTurtleUpdateFile;
		CommonHelper.runScriptRemotelyOnTurtle(command);
	}
	
	public static void getLatestVersions() throws Exception {
		AppUpdate cut = new AppUpdate();
		for (int i = 19; i < 30; ++i) {
			cut.getCloudAppVersions("2", String.valueOf(i));
			System.out.format("Coblynau: %s\nTurtle: %s\nKnocker: %s\nKobold: %s\nPrimrose: %s\n", AppUpdate.getCoblynauVersion(), AppUpdate.getTurtleVersion(), AppUpdate.getKnockerVersion(), AppUpdate.getKoboldVersion(), AppUpdate.getPrimroseVersion());
			System.out.println();
			cut.clearVersions();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, JSchException, KeyManagementException, NoSuchAlgorithmException, XMLStreamException, ParseException {
		AppUpdate mTest = new AppUpdate();
		System.out.println(cCloudAppsUpdateDirectory);
//		mTest.updateCloudApps("2" ,"26");
		mTest.updateTurtleApp("2" ,"26");
		
	}
}
