package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.stackoperations.Balancing;
import com.powin.modbusfiles.utilities.PowinProperty;

public class SyntheticCycleCycler extends SimpleDirectPCycler {
	private final static Logger LOG = LogManager.getLogger(SyntheticCycleCycler.class.getName());
    private static final int SOC_BOTTOM = 0;

	private static Balancing cBalancing;
	private boolean BALANCE_ON_CHARGE=true;
	
	
	public static void setBalancing(Balancing balancing) {
		cBalancing = balancing;
	}
	
	public static Balancing getBalancing() {
		return cBalancing;
	}
	

	private void balanceToAverage() {
		LOG.info("BalanceToAverage");
		getBalancing().balanceToAverage(String.valueOf(getArrayIndex()), String.valueOf(getStringIndex()));
	}
	
		
	private void stopBalancing() {
		try {
			getBalancing().balanceStop(getArrayIndex(), getStringIndex());
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Balance stopped.");
	}

   public void movePower(int targetP, long minutes) {
	   LOG.info("move power {}, {}", targetP, minutes);
	   if (BALANCE_ON_CHARGE) {
		   if (targetP < 0) {
			   balanceToAverage();
		   } else {
			   stopBalancing();
		   }
	   }
	   movePower(targetP, minutes, ONE_MINUTE);
   }
    
	public void rest(long minutes) {
		movePower(0, minutes);
	}
	
	private void dischargeToBottom(int targetP) {
		LOG.info("Discharging to 0% SoC");
		try {
			int SoC = ModbusPowinBlock.getModbusPowinBlock().getBlockOnlineSOC().intValue();
			movePower(targetP, 0L, ONE_MINUTE, SOC_BOTTOM);
		} catch (ModbusException e) {
			e.printStackTrace();
		}
	}

	private SimpleBasicOpCycler getSimpleBasicOpsCycler() {
		SimpleBasicOpCycler mSimpleBasicOpCycler = new SimpleBasicOpCycler();
        mSimpleBasicOpCycler.setModbusHostName(PowinProperty.TURTLEHOST.toString());
        mSimpleBasicOpCycler.setModbusPort(4502);
        mSimpleBasicOpCycler.setModbusUnitId(255);
        mSimpleBasicOpCycler.setEnableModbusLogging(false);
		return mSimpleBasicOpCycler;
	}

	private static SyntheticCycleCycler getSyntheticCycler() {
		SyntheticCycleCycler mSyntheticCycleCycler = new SyntheticCycleCycler();

		mSyntheticCycleCycler.setModbusHostName(PowinProperty.TURTLEHOST.toString()); // 10.0.0.3
		mSyntheticCycleCycler.setModbusPort(4502);
		mSyntheticCycleCycler.setModbusUnitId(255);
		mSyntheticCycleCycler.setEnableModbusLogging(false);
		mSyntheticCycleCycler.setArrayIndex(PowinProperty.ARRAY_INDEX.intValue());
		mSyntheticCycleCycler.setStringIndex(PowinProperty.STRING_INDEX.intValue());
		mSyntheticCycleCycler.setNameplateKw();
//		mSyntheticCycleCycler.setReporter(new CyclingReport(mSyntheticCycleCycler.getModbusHostName(), 
//				                                            mSyntheticCycleCycler.getModbusPort(),
//				                                            mSyntheticCycleCycler.getModbusUnitId(),
//				                                            mSyntheticCycleCycler.isEnableModbusLogging(),
//				                                            mSyntheticCycleCycler.getArrayIndex(), 
//				                                            mSyntheticCycleCycler.getStringIndex()));
		return mSyntheticCycleCycler;
	}
	
	public void cycle() {
		movePower(97,67);
		movePower(47,1);
		movePower(65,1);
		movePower(96,2);
		movePower(78,1);
		movePower(65,1);
		movePower(54,1);
		movePower(60,1);
		movePower(73,1);
		movePower(78,2);
		movePower(77,1);
		movePower(76,1);
		movePower(75,1);
		movePower(58,1);
		movePower(50,1);
		movePower(43,1);
		movePower(46,1);
		movePower(54,1);
		movePower(58,1);
		movePower(54,1);
		movePower(52,1);
		movePower(47,1);
		movePower(37,1);
		movePower(32,1);
		movePower(0,3);	
		movePower(26,1);
		movePower(39,1);
		movePower(13,1);
		movePower(26,3);
		movePower(27,1);
		movePower(26,3);
		movePower(31,1);
		movePower(42,1);
		movePower(-109,44);
		movePower(49,1);
		movePower(44,1);
		movePower(41,1);
		movePower(36,1);
		movePower(33,1);
		movePower(27,1);
		movePower(24,1);
		movePower(14,1);
		movePower(0 ,1);
		movePower(96,38);
		movePower(0,1);
		movePower(8,1);
		movePower(18,1);
		movePower(19,1);
		movePower(24,1);
		movePower(25,1);
		movePower(23,1);
		movePower(21,1);
		movePower(15,2);
		movePower(22,1);
		movePower(26,1);
		movePower(29,1);
		movePower(26,1);
		movePower(20,1);
		movePower(16,2);
		movePower(15,2);
		movePower(14,1);
		movePower(-13,1);
		movePower(-23,2);
		movePower(-22,1);
		movePower(-18,1);
		movePower(-16,1);
		movePower(0,1);
		movePower(-109,28);
		movePower(87,1);
		movePower(72,1);
		movePower(62,2);
		movePower(51,2);
		movePower(49,2);
		movePower(43,1);
		movePower(41,1);
		movePower(34,1);
		movePower(28,1);
		movePower(16,1);
		movePower(15,1);
		movePower(8,1);
		movePower(-33,1);
		movePower(-40,1);
		movePower(-41,1);
		movePower(-44,1);
		movePower(-45,1);
		movePower(-83,1);
		movePower(-84,1);
		movePower(-82,1);
		movePower(-81,2);
		movePower(-82,1);
		movePower(-84,1);
		movePower(-83,1);
		movePower(-84,1);
		movePower(-64,1);
		movePower(-37,2);
		movePower(-40,1);
		movePower(13,1);
		movePower(20,1);
		movePower(33,1);
		movePower(35,1);
		movePower(44,1);
		movePower(49,1);
		movePower(54,1);
		movePower(60,1);
		movePower(77,1);
		movePower(85,1);
		movePower(74,1);
		movePower(54,1);
		movePower(17,1);
		movePower(12,2);
		movePower(11,2);
		movePower(31,1);
		movePower(73,1);
		movePower(93,1);
		movePower(96,1);
		movePower(95,1);
		movePower(93,1);
		movePower(92,1);
		movePower(80,1);
		movePower(71,1);
		movePower(64,1);
		movePower(71,1);
		movePower(85,1);
		movePower(92,1);
		movePower(95,1);
		dischargeToBottom(96);
	}
    /**
     * Run 5 cycles using direct power to discharge following the prescribed schedule.	
     * BasicOps is used to charge back to 100% SoC.
     */
	public void run(boolean startWithCharge) {
		SimpleBasicOpCycler mSimpleBasicOpCycler = getSimpleBasicOpsCycler();
		if (startWithCharge) {
	        controlApps(AppControl.START_BASICOP);
	    	mSimpleBasicOpCycler.movePower(-100, 0, FIVE_MINUTES);        	
	        controlApps(AppControl.STOP_BASICOP);
		}
        for (int i = 0; i < 5; ++i) {
            controlApps(AppControl.START_SUNSPEC);
        	cycle();
            controlApps(AppControl.STOP_SUNSPEC);
            controlApps(AppControl.START_BASICOP);
        	mSimpleBasicOpCycler.movePower(-100, 0, FIVE_MINUTES);
            controlApps(AppControl.STOP_BASICOP);
        } 
        controlApps(AppControl.STOP_BASICOP);
        controlApps(AppControl.STOP_SUNSPEC);
	}
	
	
	public static void main(String[] args) {
		SyntheticCycleCycler mSyntheticCycleCycler = getSyntheticCycler();
		setBalancing(new Balancing(mSyntheticCycleCycler.getArrayIndex(), mSyntheticCycleCycler.getStringIndex()));
		int soc = mSyntheticCycleCycler.getOnlineSoC(mSyntheticCycleCycler);
		boolean startWithCharge = soc < 100;
		mSyntheticCycleCycler.run(startWithCharge);
    }


	
} // 


