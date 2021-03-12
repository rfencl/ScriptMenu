package com.powin.modbusfiles.cycling;

import java.io.IOException;

import com.powin.modbusfiles.utilities.PowinProperty;;

public class RteTest2 extends CyclingTest {
	public RteTest2(String modbusHostName, int modbusPort, int modbusUnitId, boolean enableModbusLogging) {
		super(modbusHostName, modbusPort, modbusUnitId, enableModbusLogging);
		// TODO Auto-generated constructor stub
	}

	// Modbus parameters

	public static void main(String[] args) throws IOException {
		// argument list: chargingPowerAsPct,dischargingPowerAsPct,restPeriodSeconds,targetChargeVoltage,targetDischargeVoltage,maxCycles
		try {
			RteTest2 mRTE_Test = new RteTest2(PowinProperty.TURTLEHOST.toString(), 4502, 255, true);
			mRTE_Test.init(args);
			// mRTE_Test.fu.addHeader("operation,timestamp,calculatedStringVoltage,soc,inverterWattsAC,inverterWattsDC,stringWattsDC");
			for (int numCycles = 0; numCycles < 1; numCycles++) {
				// Discharge
				runCycle(false);
				// Rest
				rest(300);
				mRTE_Test.resetDevices();
				// Charge
				runCycle(true);
				rest(300);
				mRTE_Test.resetDevices();
			}
		} catch (Exception e) {
			// LOG.error("Exception in go.", e);
		}
	}
}
