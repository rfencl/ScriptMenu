package com.powin.stackcommander;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerApps;

//CodeReview: All interfaces should start with I. Helps distinguish between actual class and interface
public interface IStackCommandExecutor {

	void fullDischarge(Power power) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException, ModbusException, ParseException;
	void activateCellBalancing() throws IOException, KeyManagementException, NoSuchAlgorithmException;
	void stopBalancing() throws KeyManagementException, NoSuchAlgorithmException, IOException;
	void fullCharge(Power power) throws IOException, InterruptedException, ModbusException,
	KeyManagementException, NoSuchAlgorithmException, ParseException; 
	void chargeForDuration(Power power, com.powin.stackcommander.Duration duration) throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException;
	void dischargeForDuration(Power power, com.powin.stackcommander.Duration duration) throws KeyManagementException, NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException;
	boolean restUntilBalanced(int minutes, int toleranceMv) throws IOException,
	KeyManagementException, NoSuchAlgorithmException, ParseException, InterruptedException, ModbusException;
	void restForDuration(com.powin.stackcommander.Duration duration) throws IOException, KeyManagementException, NoSuchAlgorithmException,
	ParseException, InterruptedException, ModbusException;
	void createNewReportFile(String fileName) throws IOException;
	void setPowerApp(PowerApps powerApp) throws ModbusException;
	void stopPower();
	void chargeUntilCondition(Power power, Condition condition) throws KeyManagementException, NoSuchAlgorithmException,
			IOException, ModbusException, ParseException, InterruptedException;
	void dischargeUntilCondition(Power power, Condition condition) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException;
}