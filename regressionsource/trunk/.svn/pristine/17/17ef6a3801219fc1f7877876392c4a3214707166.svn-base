package com.powin.modbusfiles.apps;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.LastCalls;
import com.powin.modbusfiles.utilities.PowinProperty;

import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;

public class SunspecPowerApp {
	
	private final static Logger LOG = LogManager.getLogger(SunspecPowerApp.class.getName());
	private String cStationCode;
	private int cBlockIndex;


	public SunspecPowerApp() {
		LastCalls lc=new LastCalls();
		cStationCode = lc.getStationCode();
		cBlockIndex = Integer.parseInt(lc.getBlockIndex());
		getModbusPowinBlock().enableSunspec();
	}
	
    public void setPower(double powerAsPercentageofMaxPower) throws ModbusException, IOException{
		Modbus123 inverter = new Modbus123(PowinProperty.TURTLEHOST.toString(), 4502, 255, false);
		inverter.init();
		inverter.setWMaxLimPct(BigDecimal.valueOf(powerAsPercentageofMaxPower));
	}

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, ModbusException  {
		//URl TO SET POWER="https://10.0.0.5:8443/kobold/monitor/blockaction/QA1400ZC/1/power?priority=4&enabled=on&kW=42&kVAr=0";
		SunspecPowerApp sunspecPowerControlApp = new SunspecPowerApp();
		System.out.println("In Main.");
		if (args.length > 0 && args[0].equals("-d")) {
			System.out.println("Disabling Power App.");
			getModbusPowinBlock().disableSunspec();
		} else {
			getModbusPowinBlock().enableSunspec();
			sunspecPowerControlApp.setPower(11.0);
		}
		
	}
}