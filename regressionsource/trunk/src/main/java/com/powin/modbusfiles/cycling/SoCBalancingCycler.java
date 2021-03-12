package com.powin.modbusfiles.cycling;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.powin.modbus.ModbusException;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.stackoperations.Balancing;

public class SoCBalancingCycler extends SimpleDirectPCycler {
    private final static Logger LOG = LogManager.getLogger(SoCBalancingCycler.class.getName());
	private static Balancing cBalancing;
	private int cArrayIndex;
	private int cStringIndex;
	private boolean BALANCE_ON_DISCHARGE=false;
	
	
	public int getArrayIndex() {
		return cArrayIndex;
	}

	public void setArrayIndex(int cArrayIndex) {
		this.cArrayIndex = cArrayIndex;
	}

	public int getStringIndex() {
		return cStringIndex;
	}

	public void setStringIndex(int cStringIndex) {
		this.cStringIndex = cStringIndex;
	}

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
	   if (BALANCE_ON_DISCHARGE) {
		   if (targetP < 0) {
			   stopBalancing();
		   } else {
			   balanceToAverage();
		   }
	   }
	   movePower(targetP, minutes, ONE_MINUTE);
   }
    
	public void rest(long minutes) {
		movePower(0, minutes);
	}
	
	private void dischargeTo50PercentSoC(int targetP) {
		LOG.info("Discharging to 50% SoC");
		try {
			int SoC = ModbusPowinBlock.getModbusPowinBlock().getBlockOnlineSOC().intValue();
			while (SoC > 50) {
				movePower(targetP, ONE_MINUTE);
			}
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chargeTo50PercentSoC(int targetP) {
		LOG.info("Charging to 50% SoC");
		try {
			int SoC = ModbusPowinBlock.getModbusPowinBlock().getBlockOnlineSOC().intValue();
			while (SoC < 50) {
				movePower(-targetP, ONE_MINUTE);
			}
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void cycle() {
		movePower(0,3);
		movePower(8,3);
		movePower(0,26);
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
		movePower(46,1);
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
		movePower(0,1);
		movePower(38,38);
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
		movePower(-13,1);
		movePower(-12,1);
		movePower(-8,2);
		movePower(-21,2);
		movePower(-19,2);
		movePower(-20,1);
		movePower(-17,1);
		movePower(-15,1);
		movePower(-14,3);
		movePower(-17,1);
		movePower(-21,1);
		movePower(-23,1);
		movePower(-22,1);
		movePower(-14,1);
		movePower(-10,1);
		movePower(-21,1);
		movePower(-24,5);
		movePower(-23,1);
		movePower(-24,1);
		movePower(-23,2);
		movePower(-22,1);
		movePower(-23,1);
		movePower(-24,2);
		movePower(-25,2);
		movePower(-24,2);
		movePower(-23,1);
		movePower(-24,1);
		movePower(-20,1);
		movePower(-57,1);
		movePower(11,1);
		movePower(36,1);
		movePower(84,1);
		movePower(87,1);
		movePower(72,1);
		movePower(62,1);
		movePower(51,1);
		movePower(49,1);
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
		movePower(0,4);
		movePower(-8,1);
		movePower(0,6);
		movePower(-11,1);
		movePower(-13,1);
		movePower(-8,1);
		movePower(0,4);
		movePower(-8,4);
		movePower(0,2);
		movePower(-8,2);
		movePower(0,1);
		movePower(-8,1);
		movePower(0,4);
		movePower(8,2);
		movePower(13,1);
		movePower(20,1);
		movePower(33,1);
		movePower(35,1);
		movePower(44,1);
		movePower(45,1);
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
		movePower(96,16);
		movePower(95,1);
		movePower(79,1);
		movePower(73,5);
		movePower(68,1);
		movePower(56,1);
		movePower(49,1);
		movePower(45,1);
		movePower(38,1);
		movePower(25,1);
		movePower(24,1);
		movePower(20,1);
		movePower(17,1);
		movePower(15,1);
		movePower(14,1);
		movePower(13,1);
		movePower(14,1);
		movePower(12,2);
		movePower(11,1);
		movePower(10,1);
		movePower(9,1);
		movePower(8,3);
		movePower(-76,1);
		movePower(-80,1);
		movePower(-86,1);
		movePower(-92,1);
		movePower(-106,1);
		movePower(-108,2);
		movePower(-106,2);
		movePower(-107,1);
		movePower(-109,6);
		movePower(-108,4);
		movePower(-107,2);
		movePower(-108,2);
		movePower(-108,2);
		movePower(-108,2);
		movePower(-108,2);
		movePower(-108,3);
		movePower(-107,1);
		movePower(-108,1);
		movePower(-106,2);
		movePower(-96,15);
	}
	
	private void cycleFifteen() {
		for (int i = 0; i < 15; ++i) {
        	cycle();
        	cycle();
        	movePower(-108, 0, ONE_MINUTE); // charge to top
        	dischargeTo50PercentSoC(95);
        	cycle();
        	cycle();
        	movePower(96, 0, ONE_MINUTE);  // discharge to bottom
        	chargeTo50PercentSoC(108);
        }
	}


	public void run() {
        int SoC = -1;
		try {
			SoC = ModbusPowinBlock.getModbusPowinBlock().getBlockOnlineSOC().intValue();
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        if (SoC > 50) {
        	dischargeTo50PercentSoC(96);
        } else if (SoC > 0) {
        	chargeTo50PercentSoC(SoC);
        }
        
        balanceToAverage();
        cycleFifteen();
        stopBalancing();
        cycleFifteen();
        BALANCE_ON_DISCHARGE = true; 
        cycleFifteen();
        
    }

	public static void main(String[] args) {
		SoCBalancingCycler mSoCBalancingCycler = new SoCBalancingCycler();

		mSoCBalancingCycler.setModbusHostName("localhost"); // 10.0.0.3
		mSoCBalancingCycler.setModbusPort(4502);
		mSoCBalancingCycler.setModbusUnitId(255);
		mSoCBalancingCycler.setEnableModbusLogging(false);
		mSoCBalancingCycler.setArrayIndex(1);
		mSoCBalancingCycler.setStringIndex(1);
		setBalancing(new Balancing(mSoCBalancingCycler.getArrayIndex(), mSoCBalancingCycler.getStringIndex()));

		mSoCBalancingCycler.run();
		//mSoCBalancingCycler.movePower(56, ONE_MINUTE); // test timeout
		mSoCBalancingCycler.movePower(0, ONE_MINUTE);
    }
}
