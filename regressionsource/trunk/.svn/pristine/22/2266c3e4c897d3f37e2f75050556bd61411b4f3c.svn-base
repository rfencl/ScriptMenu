package com.powin.modbusfiles.cycling;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeUtils;
import org.json.simple.JSONObject;

import com.powin.modbus.ModbusException;
import com.powin.modbus.sunspec.blocks.PowinBlockCommon.BasicOpStatusEnum;
import com.powin.modbusfiles.modbus.Modbus123;
import com.powin.modbusfiles.modbus.ModbusPowinBlock;
import com.powin.modbusfiles.reports.CyclingReport;
import com.powin.modbusfiles.reports.SystemInfo;
import com.powin.modbusfiles.utilities.CommonHelper;
import com.powin.modbusfiles.utilities.JsonParserHelper;
import com.powin.modbusfiles.utilities.PowinProperty;
import static com.powin.modbusfiles.modbus.ModbusPowinBlock.getModbusPowinBlock;


public class SimpleDirectPCycler {
    private static final int MILLIS_PER_SECOND = 1000;
	private static final int NO_TARGET_SOC = -1;
	private final static Logger LOG = LogManager.getLogger(SimpleDirectPCycler.class.getName());
    protected final static long ONE_MINUTE = 60 * 1000;
    protected final static long FIVE_SECONDS = 5 * 1000;
    protected final static long FIVE_MINUTES = 5 * ONE_MINUTE;
	private static final long SECONDS_PER_MINUTE = 60;
    protected static final String HOME_POWIN_DEVICE_20 = "/home/powin/device-20";
    protected enum AppControl {
    	STOP_SUNSPEC,
    	STOP_BASICOP,
    	START_SUNSPEC,
    	START_BASICOP
    }

    private String cModbusHostName;
    private int cModbusPort;
    private int cModbusUnitId;
    private boolean cEnableModbusLogging;
	protected double nameplateKw;
	private CyclingReport reporter;


    // managed by getModbusPowinBlock() / resetModbusPowinBlock. Should never be accessed directly.
    private ModbusPowinBlock cModbusPowinBlock = null;
	private int cArrayIndex;
	private int cStringIndex;
	

	public void setReporter(CyclingReport cyclingReport) {
		this.reporter = cyclingReport;
	}

     public String getModbusHostName() {
    	LOG.info("cModbusHostName={}", cModbusHostName);
        return cModbusHostName;
    }

    public void setModbusHostName(String modbusHostName) {
        cModbusHostName = modbusHostName;
    }

    public int getModbusPort() {
        return cModbusPort;
    }

    public void setModbusPort(int modbusPort) {
        cModbusPort = modbusPort;
    }

    public int getModbusUnitId() {
        return cModbusUnitId;
    }

    public void setModbusUnitId(int modbusUnitId) {
        cModbusUnitId = modbusUnitId;
    }
    
    public void setNameplateKw() {
		nameplateKw = SystemInfo.getNameplateKw();
		LOG.info("namePlateKw set = {}", nameplateKw);
    }
    
    public boolean isEnableModbusLogging() {
        return cEnableModbusLogging;
    }

    public void setEnableModbusLogging(boolean enableModbusLogging) {
        cEnableModbusLogging = enableModbusLogging;
    }
    
	private void logPowerInfo(int targetP, int targetSoc) {
		if (null != reporter) {
			reporter.printInfoInConsole();
			reporter.saveDataToCsv(targetSoc, targetP);
		}
	}
	public void setPAsPercent(BigDecimal percent) throws ModbusException {
		Modbus123 cImmediateControlsBlockMaster = new Modbus123(getModbusHostName(), 4502, 255, false);
		cImmediateControlsBlockMaster.setWMaxLimPct(percent);
	}

    /**
     * Start or stops and app, retrying on failure.
     * @param action
     */
    protected void controlApps(AppControl action) {
        boolean mSucceededInStoppingApps = false;
        while (!mSucceededInStoppingApps) {
            try {
            	switch (action) {
            	case STOP_BASICOP:
                    getModbusPowinBlock().disableBasicOp();
                    break;
            	case STOP_SUNSPEC:
                    getModbusPowinBlock().disableSunspec();
                    break;
            	case START_BASICOP:
            		getModbusPowinBlock().enableApp("BasicOp");
            		break;
            	case START_SUNSPEC:
            		getModbusPowinBlock().enableApp("SunspecPowerCommand");
            		break;
            	}
                mSucceededInStoppingApps = true;
            }
            catch (Exception e) {
            	String startstop = action.ordinal() > 1 ? "start" : "stop";
                LOG.error("Failed to {} apps. Will retry in {} milliseconds.", startstop, FIVE_SECONDS, e);
                try {
                    Thread.sleep(FIVE_SECONDS);
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
	protected double kwToPercentage(int kw) {
		double percent = ((kw/nameplateKw) * 100);
        LOG.info("percent:{}, kw:{}, namePlateKw:{}",percent, kw, nameplateKw);
		return percent;
	}

    protected void movePower(int targetP, long minutes, long logInterval, int targetSoC) {
        LOG.info("Start moving {}kW using DirectP.", targetP);

        boolean mSucceededInStarting = false;
        double percent = kwToPercentage(targetP);
        while (!mSucceededInStarting) {
            try {
            	setPAsPercent(BigDecimal.valueOf(percent));
                mSucceededInStarting = true;
                LOG.info("Suceeded in starting moving power @ {}kW.", targetP);
            }
            catch (ModbusException e) {
                LOG.error("Failed to start moving power @ {}kW. Will retry in {} ms.", targetP, FIVE_SECONDS, e);
                try {
                    Thread.sleep(FIVE_SECONDS);
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        long mNextWaitingLog = DateTimeUtils.currentTimeMillis() + logInterval;
        long mStopTime = DateTimeUtils.currentTimeMillis() + minutes*SECONDS_PER_MINUTE*MILLIS_PER_SECOND;
        
        boolean mIsDirectPComplete = false;
        while (!mIsDirectPComplete) {
            long mNow = DateTimeUtils.currentTimeMillis(); 
            try {
                if (mNow >= mNextWaitingLog) {
                    LOG.info("Waiting. Power moving is {} kW with {}% online SOC of {}kWh.", getModbusPowinBlock().getBlockW().movePointLeft(3),
                            getModbusPowinBlock().getBlockOnlineSOC().intValue(), getModbusPowinBlock().getBlockOnlineCapacityWh().movePointLeft(3));
                    logPowerInfo(targetP, targetSoC);
                    mNextWaitingLog = mNow + logInterval;
                }
                Thread.sleep(FIVE_SECONDS);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (ModbusException e) {
                LOG.error("Failed to get data for logging wait event. Will retry immediately.", e);
                mNextWaitingLog = DateTimeUtils.currentTimeMillis();
            }

            try {
            	int SoC = getModbusPowinBlock().getBlockOnlineSOC().intValue();
                mIsDirectPComplete = SoC > 0 ? SoC == targetSoC : true;
                mIsDirectPComplete |= targetP < 0 ? SoC > 99 : false; 
                if (minutes > 0) {
                	mIsDirectPComplete |= mNow >= mStopTime;
                }
            }
            catch (ModbusException e) {
                LOG.error("Failed to check if Cycle is done. will recheck immediately.", e);
                mIsDirectPComplete = false;
            }
        }

        LOG.info("Done moving {}kW using DirectP.", targetP);
    }
    
    public void movePower(int targetP, long minutes, long logInterval) {
    	movePower(targetP, minutes, logInterval, NO_TARGET_SOC);
    }
    
    public void run(int kw, boolean startWithCharge) {

        if (startWithCharge) {
            movePower(-kw, 0, FIVE_MINUTES);
        }

        while (true) {
            movePower(kw, 0, FIVE_MINUTES);
            movePower(-kw, 0, FIVE_MINUTES);
        }
    }

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

	int getOnlineSoC(SyntheticCycleCycler mSyntheticCycleCycler) {
		int soc = 0;
		try {
			soc =  getModbusPowinBlock().getBlockOnlineSOC().intValue();
		} catch (ModbusException e) {
			LOG.error("Unable to get SoC", e);
			throw new RuntimeException(e.getMessage());
		}
		return soc;
	}

	public  static void main(String[] args) {
        SimpleDirectPCycler mSimpleDirectPCycler = new SimpleDirectPCycler();
       
        mSimpleDirectPCycler.setModbusHostName(PowinProperty.TURTLEHOST.toString()); // 10.0.0.3
        mSimpleDirectPCycler.setModbusPort(4502);
        mSimpleDirectPCycler.setModbusUnitId(255);
        mSimpleDirectPCycler.setEnableModbusLogging(false);
        mSimpleDirectPCycler.setArrayIndex(1);
        mSimpleDirectPCycler.setStringIndex(1);
        mSimpleDirectPCycler.setNameplateKw();
        mSimpleDirectPCycler.run(100, true);
    }
}
