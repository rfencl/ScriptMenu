package com.powin.modbusfiles.cycling;

import com.powin.modbusfiles.modbus.Modbus103;
import com.powin.modbusfiles.modbus.Modbus802;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.utilities.PowinProperty;

public class CyclingTestBase {
	// Modbus parameters
	protected static String cModbusHostName;
	protected static int cModbusPort;
	protected static int cModbusUnitId;
	protected static boolean cEnableModbusLogging;
	// Devices
	protected static Modbus103 cInverterThreePhaseBlockMaster;
	protected static Modbus802 cBatteryBaseModelBlockMaster;
	protected static ModbusPowinBlock cModbusPowinBlock;

	protected static int chargingPowerKw;
	protected static int dischargingPowerKw;
	protected static int restPeriodSeconds;
	protected static int logInterval = 2;
	protected static int targetChargeVoltage;
	protected static int targetDischargeVoltage;
	protected static int maxCycles;
	
	protected void initParameters(String[] args) {
		int i = 0;
		chargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_CHARGINGPOWERW.intValue();        		
		dischargingPowerKw = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_DISCHARGINGPOWERW.intValue(); 		
		restPeriodSeconds = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_RESTPERIODSECONDS.intValue(); 		 
		targetChargeVoltage = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETCHARGEVOLTAGE.intValue(); 		
		targetDischargeVoltage = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_TARGETDISCHARGEVOLTAGE.intValue(); 
		maxCycles = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_MAXCYCLES.intValue(); 							
		logInterval = args.length > i ? Integer.parseInt(args[i++]) : PowinProperty.BC_LOGINTERVAL.intValue();
	}

	public static void resetDevices() {
		cInverterThreePhaseBlockMaster = new Modbus103(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
		cBatteryBaseModelBlockMaster = new Modbus802(cModbusHostName, cModbusPort, cModbusUnitId, cEnableModbusLogging);
	}

}
