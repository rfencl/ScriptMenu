package com.powin.modbusfiles;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.jcraft.jsch.JSchException;
import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.awe.BatteryPackTemperatureNotifications;
import com.powin.modbusfiles.awe.NotificationCodes;
import com.powin.modbusfiles.reports.FirmwareReports;
import com.powin.modbusfiles.reports.Notifications;
import com.powin.modbusfiles.reports.Reports;




public class TurtleTcpIpStressTest {
	private String cStationCode;
	private int cArrayIndex;
	private int cStringIndex;
	
	public TurtleTcpIpStressTest(String stationCode,int arrayIndex, int stringIndex) {
		cStationCode=stationCode;
		cArrayIndex=arrayIndex;
		cStringIndex=stringIndex;		
	}
	public void runTest() throws InterruptedException, IOException, ModbusException, KeyManagementException, NoSuchAlgorithmException, JSchException, InstantiationException, IllegalAccessException {
		//Currently reporting interval cannot be set	
		long durationSecs = 60L;//
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() + 1000 * durationSecs;
		int loop=1;
		int commandInterval = 0;
		//run script for durationSecs
		while (System.currentTimeMillis() < endTime) {
			System.out.println("loop: "+loop);
			//set the alarm set points so that lots of alarms get generated.
			System.out.println("	setting alarm");
			BatteryPackTemperatureNotifications mBatteryPackTemperatureNotifications = new BatteryPackTemperatureNotifications(cStationCode, cArrayIndex, cStringIndex);
			mBatteryPackTemperatureNotifications.setNotification(NotificationCodes.CELL_HIGH_TEMPERATURE_ALARM);
			mBatteryPackTemperatureNotifications.setHighCellGroupTemperatureAlarm(20, 15);	
			System.out.println("	Waiting for "+commandInterval+" ms");
			Thread.sleep(commandInterval);
			System.out.println("	clearing alarm");
			mBatteryPackTemperatureNotifications.resetDefaultsBatteryPackTemperatureNotificationConfiguration();
			System.out.println("	Waiting for "+commandInterval+" ms");
			//Get report
			Reports strReport = new Reports("1,1");
			System.out.println(strReport.getStringReport());
			//Get notifications
			Notifications notifications = new Notifications("1,1");
			System.out.println(notifications.getNotificationsInfo(false).toString());
			//Trigger firmware report
			FirmwareReports fwReport = new FirmwareReports("1,1");	
			System.out.println("Triggered firmware report");
			Thread.sleep(commandInterval);
			loop++;
		}
		//TO BE DONE MANUALLY
		//Wait 1 minute
		//Reset notifications via Kobold to default values
		//Validation
		//Confirm stack is reporting correctly
		//Open and Close Contractor
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeyManagementException, NoSuchAlgorithmException, JSchException, ModbusException, InstantiationException, IllegalAccessException {
		TurtleTcpIpStressTest tcpipTest = new TurtleTcpIpStressTest("QA14001E",1,1);
		tcpipTest.runTest();

	}
}