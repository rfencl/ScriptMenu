package com.powin.stackcommander;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.apps.PowerApps;

public class StackCommandDriverStubb implements IStackCommandExecutor {
	private final static Logger LOG = LogManager.getLogger(StackCommandDriverStubb.class.getName());
	static List<String> called = new ArrayList<>();

	@Override
	public void setPowerApp(PowerApps powerApp) {
		String methodName = "setPowerApp";
		LOG.info(methodName);
		called.add(methodName);

	}

	@Override
	public void fullDischarge(Power power) throws KeyManagementException, NoSuchAlgorithmException, IOException,
			InterruptedException, ModbusException, ParseException {
		String methodName = "fullDischarge";
		LOG.info(methodName);
		called.add(methodName);

	}

	@Override
	public void activateCellBalancing() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		String methodName = "activateCellBalancing";
		LOG.info(methodName);
		called.add(methodName);
	}

	@Override
	public void stopBalancing() throws KeyManagementException, NoSuchAlgorithmException, IOException {
		String methodName = "stopBalancing";
		LOG.info(methodName);
		called.add(methodName);
	}

	@Override
	public void fullCharge(Power power) throws IOException, InterruptedException, ModbusException,
			KeyManagementException, NoSuchAlgorithmException, ParseException {
		String methodName = "fullCharge";
		LOG.info(methodName);
		called.add(methodName);
	}

	@Override
	public void chargeForDuration(Power power, Duration duration) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		String methodName = "chargeForDuration ";
		LOG.info(methodName + duration);
		called.add(methodName);
	}

	@Override
	public void dischargeForDuration(Power power, Duration duration) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		String methodName = "dischargeForDuration ";
		LOG.info(methodName + duration);
		called.add(methodName);
	}

	@Override
	public boolean restUntilBalanced(int minutes, int toleranceMv) throws IOException, KeyManagementException,
			NoSuchAlgorithmException, ParseException, InterruptedException, ModbusException {
		String methodName = "restUntilBalanced";
		LOG.info(methodName);
		called.add(methodName);
		return false;
	}

	@Override
	public void restForDuration(Duration duration) throws IOException, KeyManagementException, NoSuchAlgorithmException,
			ParseException, InterruptedException, ModbusException {
		String methodName = "restForDuration ";
		LOG.info(methodName + duration);
		called.add(methodName);

	}
	public List<String> getCalled() {
		return called;
	}

	@Override
	public void createNewReportFile(String fileName) throws IOException {
		String methodName = "createNewReportFile ";
		LOG.info(methodName + fileName);
		called.add(methodName);
		
	}

	@Override
	public void stopPower() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chargeUntilCondition(Power power, Condition condition) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		String methodName = "chargeUntilCondition ";
		LOG.info(methodName + power);
		called.add(methodName);
		
	}

	@Override
	public void dischargeUntilCondition(Power power, Condition condition) throws KeyManagementException,
			NoSuchAlgorithmException, IOException, ModbusException, ParseException, InterruptedException {
		String methodName = "dischargeUntilCondition ";
		LOG.info(methodName + power);
		called.add(methodName);
		
	}

	

}
